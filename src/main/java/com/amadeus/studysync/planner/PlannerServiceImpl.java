package com.amadeus.studysync.planner;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.topic.Status;
import com.amadeus.studysync.topic.Topic;
import com.amadeus.studysync.upload.Upload;
import com.amadeus.studysync.upload.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<Planner> findAll() {
        return repository.findAll();
    }

    @Override
    public Planner findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Planner save(PlannerPostRequest request) {

        Planner planner = Planner.builder()
                .id(request.getId())
                .title(request.getTitle())
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
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Planner update(Planner thePlanner) {
        repository.findById(thePlanner.getId())
                .orElseThrow(() -> new NotFoundException("Planner not found with id - " + thePlanner.getId()));
        return repository.save(thePlanner);
    }

    @Override
    public Planner partialUpdate(UUID theId, PlannerPatchRequest updates) {
        Planner planner = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Planner not found with id - " + theId));

        planner.setTitle(updates.getTitle() != null ? updates.getTitle() : planner.getTitle());

        if (updates.getTopics() != null)
            updates.getTopics().forEach(planner::addTopic);

        return repository.save(planner);
    }

    @Override
    public Planner findPlannerByIdJoinFetch(UUID theId) {
        Optional<Planner> planner = repository.findPlannerByIdJoinFetch(theId);
        return planner.orElseGet(() -> repository.findById(theId).orElse(null));
    }

}

