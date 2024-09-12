package com.amadeus.studysync.topic;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;

    @Override
    public List<Topic> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Topic findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
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
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Topic not found with id - " + theId));

        repository.deleteById(theId);
    }

    @Override
    public Topic update(Topic topic, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(topic.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException("Topic not found with id - " + topic.getId()));

        if (topic.getRecords() == null) {
            topic.setStatus(Status.WEAK);
        } else {
            topic.setStatus(topic.getRecords().last().getStatus());
        }

        return repository.save(topic);
    }


    @Override
    public Topic partialUpdate(UUID theId, TopicPatchRequest updates, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Topic topic = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with email - " + theId));

        Topic newTopic = Topic.builder()
                .id(topic.getId())
                .planner(topic.getPlanner())
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
    public Topic findTopicByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Topic> topic = repository.findTopicByIdJoinFetch(theId, user.getId());
        return topic.orElseGet(() -> repository.findById(theId).orElse(null));
    }
}