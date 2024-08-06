package com.amadeus.studysync.planner;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/planners")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerServiceImpl service;

    @GetMapping
    public ResponseEntity<List<PlannerResponse>> findAllTopics() {
        List<Planner> planners = (service.findAll());

        return ResponseEntity.ok(PlannerResponse.from(planners));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planner> findTopicById(@PathVariable UUID id) throws Exception {
        Planner planner = service.findPlannerByIdJoinFetch(id);

        if (planner == null) {
            throw new NotFoundException("Planner not found - " + id);
        }

        return ResponseEntity.ok(planner);
    }

    @PostMapping
    public ResponseEntity<PlannerResponse> save(
            @RequestBody PlannerPostRequest request
    ) {
        Planner planner = service.save(request);

        PlannerResponse response = new PlannerResponse();

        response.setId(planner.getId());
        response.setTitle(planner.getTitle());
        response.setCreateDate(planner.getCreateDate());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Planner> update(
            @RequestBody Planner request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Planner> partiallyUpdate(@PathVariable UUID id, @RequestBody PlannerPatchRequest updates) {
        Planner updatedPlanner = service.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedPlanner);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
