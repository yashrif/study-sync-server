package com.amadeus.studysync.planner;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.topic.Status;
import com.amadeus.studysync.topic.Topic;
import com.amadeus.studysync.upload.Upload;
import com.amadeus.studysync.upload.UploadRepository;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final PlannerRepository repository;
    private final UploadRepository uploadRepository;

    @Override
    public List<Planner> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Planner findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
    }

    @Override
    public Planner save(PlannerPostRequest request) {

        Planner planner = Planner.builder()
                .id(request.getId())
                .title(request.getTitle())
                .endDate(request.getEndDate())
                .build();

        List<Topic> topics = new ArrayList<>();

        for (Topic topic : request.getTopics()) {
            topic.setStatus(Status.WEAK);
            topic.setPlanner(planner);
            topics.add(topic);
        }
        planner.setTopics(topics);

        request.getUploads().forEach(upload -> {
            Upload theUpload = uploadRepository.findById(upload.getId())
                    .orElseThrow(() -> new NotFoundException("Planner not found with id - " + upload.getId()));
            planner.addUpload(theUpload);
        });

        return repository.save(planner);
    }

    @Override
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Planner not found with id - " + theId));

        repository.deleteById(theId);
    }

    @Override
    public Planner update(Planner thePlanner, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(thePlanner.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException("Planner not found with id - " + thePlanner.getId()));
        return repository.save(thePlanner);
    }

    @Override
    public Planner partialUpdate(UUID theId, PlannerPatchRequest updates, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Planner planner = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Planner not found with id - " + theId));

        planner.setTitle(updates.getTitle() != null ? updates.getTitle() : planner.getTitle());
        planner.setEndDate(updates.getEndDate());

        if (updates.getTopics() != null)
            updates.getTopics().forEach(planner::addTopic);

        return repository.save(planner);
    }

    @Override
    public Planner findPlannerByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Planner> planner = repository.findPlannerByIdJoinFetch(theId, user.getId());
        return planner.orElseGet(() -> repository.findById(theId).orElse(null));
    }

}

