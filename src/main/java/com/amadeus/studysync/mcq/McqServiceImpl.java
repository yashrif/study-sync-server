package com.amadeus.studysync.mcq;

import com.amadeus.studysync.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class McqServiceImpl implements McqService {

    private final McqRepository repository;
    private final EntityManager entityManager;

    @Override
    public List<Mcq> findAll() {
        return repository.findAll();
    }

    @Override
    public Mcq findById(UUID theId) {
        return repository.findById(theId).orElse(null);
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
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Mcq update(Mcq theCqs) {
        repository.findById(theCqs.getId())
                .orElseThrow(() -> new NotFoundException("Mcq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }

    @Override
    public Mcq partialUpdate(UUID theId, PatchMcqRequest updates) throws Exception {
        Mcq mcq = repository.findById(theId)
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
    public Mcq findMcqByIdJoinFetch(UUID theId) {
        Optional<Mcq> mcq = repository.findMcqByIdJoinFetch(theId);
        return mcq.orElseGet(() -> repository.findById(theId).orElse(null));
    }
}