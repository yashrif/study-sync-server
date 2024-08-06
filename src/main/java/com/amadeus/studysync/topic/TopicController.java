package com.amadeus.studysync.topic;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicServiceImpl service;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> findAllTopics() {
        List<Topic> topics = (service.findAll());

        return ResponseEntity.ok(TopicResponse.from(topics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> findTopicById(@PathVariable UUID id) throws Exception {
        Topic topic = service.findTopicByIdJoinFetch(id);

        if (topic == null) {
            throw new NotFoundException("Topic not found - " + id);
        }

        return ResponseEntity.ok(topic);
    }

    @PostMapping
    public ResponseEntity<TopicResponse> save(
            @RequestBody TopicPostRequest request
    ) {
        Topic topic = service.save(request);

        TopicResponse response = new TopicResponse();

        response.setId(topic.getId());
        response.setName(topic.getName());
        response.setDescription(topic.getDescription());
        response.setStatus(topic.getStatus());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Topic> partiallyUpdate(@PathVariable UUID id, @RequestBody TopicPatchRequest updates) {
        Topic updatedTopic = service.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedTopic);
    }

    @PutMapping
    public ResponseEntity<Topic> update(
            @RequestBody Topic request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
