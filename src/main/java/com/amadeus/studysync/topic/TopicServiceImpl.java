package com.amadeus.studysync.topic;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;

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

        Topic topic = repository.save(Topic.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .color(request.getColor())
                .records(request.getRecords())
                .build());

        if (topic.getRecords() == null) {
            topic.setStatus(Status.WEAK);
        } else {
            topic.setStatus(topic.getRecords().last().getStatus());
        }

        return topic;
    }

    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Topic update(Topic topic) {
        repository.findById(topic.getId())
                .orElseThrow(() -> new NotFoundException("Topic not found with id - " + topic.getId()));

        if (topic.getRecords() == null) {
            topic.setStatus(Status.WEAK);
        } else {
            topic.setStatus(topic.getRecords().last().getStatus());
        }

        return repository.save(topic);
    }


    @Override
    public Topic partialUpdate(UUID theId, TopicPatchRequest updates) {
        System.out.println(updates);
        Topic topic = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Cq not found with email - " + theId));


        Topic newTopic = Topic.builder()
                .name(updates.getName() != null ? updates.getName() : topic.getName())
                .description(updates.getDescription() != null ? updates.getDescription() : topic.getDescription())
                .color(updates.getColor() != null ? updates.getColor() : topic.getColor())
                .records(updates.getRecords() != null ? updates.getRecords() : topic.getRecords())
                .status(updates.getRecords() == null || updates.getRecords().isEmpty()
                        ? Status.WEAK
                        : updates.getRecords().last().getStatus())
                .build();

        return repository.save(newTopic);
    }

    @Override
    public Topic findTopicByIdJoinFetch(UUID theId) {
        Optional<Topic> topic = repository.findTopicByIdJoinFetch(theId);
        return topic.orElseGet(() -> repository.findById(theId).orElse(null));
    }
}