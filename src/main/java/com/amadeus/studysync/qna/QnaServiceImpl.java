package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.Mcq;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final QnaRepository repository;
    private final EntityManager entityManager;

    @Override
    public List<Qna> findAll() {
        return repository.findAll();
    }

    @Override
    public Qna findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Qna save(QnaRequest request) {

        Qna qna = new Qna();
        qna.setTitle(request.getTitle());

        if (request.getMcqs() != null) {
            List<Mcq> mcqs = new ArrayList<>();

            for (Mcq mcq : request.getMcqs()) {
                mcq.setQna(qna);
                mcqs.add(mcq);
            }
            qna.setMcqs(mcqs);
        }

        return repository.save(qna);
    }

    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Qna findQnaByIdJoinFetch(UUID id) {
        TypedQuery<Qna> query = entityManager.createQuery(
                "select i from Qna i "
                        + "JOIN FETCH i.mcqs "
                        + "where i.id=:data", Qna.class
        );
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public List<Mcq> findMcqsByQnaId(UUID theId) {
        TypedQuery<Mcq> query = entityManager.createQuery(
                "from Mcq where qna.id=:data", Mcq.class
        );
        query.setParameter("data", theId);
        return query.getResultList();
    }
}

