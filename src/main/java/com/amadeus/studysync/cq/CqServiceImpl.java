package com.amadeus.studysync.cq;

import com.amadeus.studysync.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CqServiceImpl implements CqService {

    private final CqRepository repository;
    private final EntityManager entityManager;

    @Override
    public List<Cq> findAll() {
        return repository.findAll();
    }

    @Override
    public Cq findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Cq save(PostCqRequest request) {

        return repository.save(Cq.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .isFlashcard(request.getIsFlashcard() != null ? request.getIsFlashcard() : false)
                .build());
    }


    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Cq update(Cq theCqs) {
        repository.findById(theCqs.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }


    @Override
    public Cq partialUpdate(UUID theId, PatchCqRequest updates) {
        Cq cq = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Cq not found with email - " + theId));

        cq.setQuestion(updates.getQuestion() != null ? updates.getQuestion() : cq.getQuestion());
        cq.setAnswer(updates.getAnswer() != null ? updates.getAnswer() : cq.getAnswer());
        cq.setIsFlashcard(updates.getIsFlashcard() != null ? updates.getIsFlashcard() : cq.getIsFlashcard());
        cq.setStatus(updates.getStatus() != null ? updates.getStatus() : cq.getStatus());

        return repository.save(cq);
    }

    @Override
    public Cq findCqByIdJoinFetch(UUID theId) {
        Optional<Cq> cq = repository.findCqByIdJoinFetch(theId);
        return cq.orElseGet(() -> repository.findById(theId).orElse(null));
    }

    @Override
    public Optional<List<Cq>> findFlashcards() {
        return repository.findFlashcards();
    }
}