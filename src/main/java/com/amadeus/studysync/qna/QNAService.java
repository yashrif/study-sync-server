package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.MCQ;
import com.amadeus.studysync.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QNAService implements AppService<QNA, QNARequest, UUID> {

    private final QNARepository repository;

    @Override
    public List<QNA> findAll() {
        return repository.findAll();
    }

    @Override
    public QNA findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public QNA save(QNARequest request) {

        QNA qna = new QNA();
        qna.setTitle(request.getTitle());

        if (request.getMcqs() != null) {
            List<MCQ> mcqs = new ArrayList<>();

            for (MCQ mcq : request.getMcqs()) {
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
}