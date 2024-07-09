package com.amadeus.studysync.qna;

import com.amadeus.studysync.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        return repository.save(QNA.builder()
                .title(request.getTitle())
                .mcq(request.getMcqs())
                .build());
    }


    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }
}