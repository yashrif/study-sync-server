package com.amadeus.studysync.mcq;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class McqServiceImpl implements McqService {

    private final McqRepository repository;

    @Override
    public List<Mcq> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Mcq findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
    }

    @Override
    public Mcq save(PostMcqRequest request) {

        return repository.save(Mcq.builder()
                .question(request.getQuestion())
                .choices(request.getChoices())
                .answers(request.getAnswers())
                .build());
    }


    @Override
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Mcq not found with id - " + theId));

        repository.deleteById(theId);
    }

    @Override
    public Mcq update(Mcq theCqs, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theCqs.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException("Mcq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }

    @Override
    public Mcq partialUpdate(UUID theId, PatchMcqRequest updates, Principal connectedUser) throws Exception {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Mcq mcq = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Mcq not found with email - " + theId));

        if (updates.getAnswers() != null && updates.getChoices() != null && updates.getAnswers().size() == updates.getChoices().size()) {
            mcq.setQuestion(updates.getQuestion() != null ? updates.getQuestion() : mcq.getQuestion());

            updates.getAnswers().forEach(mcq::addAnswer);
            updates.getChoices().forEach(mcq::addChoice);
        } else {
            throw new Exception("Size of choices and answers are different!");
        }

        return repository.save(mcq);
    }

    @Override
    public Mcq findMcqByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Mcq> mcq = repository.findMcqByIdJoinFetch(theId, user.getId());
        return mcq.orElseGet(() -> repository.findByIdAndUser(theId, user.getId()).orElse(null));
    }
}