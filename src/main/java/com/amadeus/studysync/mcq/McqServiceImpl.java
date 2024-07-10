package com.amadeus.studysync.mcq;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Mcq save(McqRequest request) {

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
    public Mcq findMcqByIdJoinFetch(UUID id) {
        TypedQuery<Mcq> query = entityManager.createQuery(
                "select i from Mcq i "
                        + "JOIN FETCH i.qna "
                        + "where i.id=:data", Mcq.class
        );
        query.setParameter("data", id);
        return query.getSingleResult();
    }
}