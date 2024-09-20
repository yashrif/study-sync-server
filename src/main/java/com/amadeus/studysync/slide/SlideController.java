package com.amadeus.studysync.slide;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/slides")
@RequiredArgsConstructor
public class SlideController {

    private final SlideServiceImpl service;

    @GetMapping
    public ResponseEntity<List<SlideResponse>> findAllTopics(Principal connectedUser) {
        List<Slide> slides = (service.findAll(connectedUser));

        return ResponseEntity.ok(SlideResponse.from(slides));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slide> findTopicById(@PathVariable UUID id, Principal connectedUser) throws Exception {
        Slide slide = service.findSlideByIdJoinFetch(id, connectedUser);

        if (slide == null) {
            throw new NotFoundException("Planner not found - " + id);
        }

        return ResponseEntity.ok(slide);
    }

    @PostMapping
    public ResponseEntity<SlideResponse> save(
            @RequestBody SlidePostRequest request
    ) {
        Slide slide = service.save(request);

        SlideResponse response = new SlideResponse();

        response.setId(slide.getId());
        response.setName(slide.getName());
        response.setContent(slide.getContent());
        response.setTopics(slide.getTopics());
        response.setCreateDate(slide.getCreateDate());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Slide> update(
            @RequestBody Slide request, Principal connectedUser
    ) {
        return ResponseEntity.ok((service.update(request, connectedUser)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Slide> partiallyUpdate(@PathVariable UUID id, @RequestBody SlidePatchRequest updates, Principal connectedUser) {
        Slide updatedSlide = service.partialUpdate(id, updates, connectedUser);
        return ResponseEntity.ok(updatedSlide);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Principal connectedUser) {
        service.deleteById(id, connectedUser);
    }
}
