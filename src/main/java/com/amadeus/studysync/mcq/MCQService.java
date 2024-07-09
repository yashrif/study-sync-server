package com.amadeus.studysync.mcq;

import com.amadeus.studysync.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MCQService implements AppService<MCQ, MCQRequest, UUID> {

    private final MCQRepository repository;

    @Override
    public List<MCQ> findAll() {
        return repository.findAll();
    }

    @Override
    public MCQ findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public MCQ save(MCQRequest request) {

        return MCQ.builder()
                .question(request.getQuestion())
                .choice(request.getChoice())
                .answer(request.getAnswer())
                .build();
    }


    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }
}