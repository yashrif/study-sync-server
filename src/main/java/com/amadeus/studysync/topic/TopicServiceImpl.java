package com.amadeus.studysync.topic;

import com.amadeus.studysync.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;
    private final EntityManager entityManager;

    @Override
    public List<Topic> findAll() {
        return repository.findAll();
    }

    @Override
    public Topic findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Topic save(TopicPostRequest request) {

        return repository.save(Topic.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .color(request.getColor())
                .status(request.getStatus() != null ? request.getStatus() : Status.WEAK)
                .dates(request.getDates())
                .build());
    }

    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Topic update(Topic theCqs) {
        repository.findById(theCqs.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }


    @Override
    public Topic partialUpdate(UUID theId, TopicPatchRequest updates) {
        Topic topic = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Cq not found with email - " + theId));

        topic.setName(updates.getName() != null ? updates.getName() : topic.getName());
        topic.setDescription(updates.getDescription() != null ? updates.getDescription() : topic.getDescription());
        topic.setColor(updates.getColor() != null ? updates.getColor() : topic.getColor());
        topic.setStatus(updates.getStatus() != null ? updates.getStatus() : topic.getStatus());

        if (updates.getDate() != null) {
            topic.addDate(updates.getDate());
        }

        return repository.save(topic);
    }

    @Override
    public Topic findTopicByIdJoinFetch(UUID theId) {
        Optional<Topic> topic = repository.findTopicByIdJoinFetch(theId);
        return topic.orElseGet(() -> repository.findById(theId).orElse(null));
    }
}