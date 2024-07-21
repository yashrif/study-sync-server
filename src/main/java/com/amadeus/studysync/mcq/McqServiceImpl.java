package com.amadeus.studysync.mcq;

import com.amadeus.studysync.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
    public Mcq update(Mcq theCqs) {
        repository.findById(theCqs.getId())
                .orElseThrow(() -> new NotFoundException("Mcq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }

    @Override
    public Mcq partialUpdate(UUID theId, Map<String, Object> updates) {
        Mcq upload = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Mcq not found with email - " + theId));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Mcq.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, upload, value);
            }
        });

        return repository.save(upload);
    }

    @Override
    public Mcq findMcqByIdJoinFetch(UUID theId) {
        Optional<Mcq> mcq = repository.findMcqByIdJoinFetch(theId);
        return mcq.orElseGet(() -> repository.findById(theId).orElse(null));
    }
}