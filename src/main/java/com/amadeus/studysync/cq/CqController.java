package com.amadeus.studysync.cq;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cqs")
@RequiredArgsConstructor
public class CqController {

    private final CqServiceImpl service;

    @GetMapping
    public ResponseEntity<List<CqResponse>> findAllCqs() {
        List<Cq> cqs = (service.findAll());

        return ResponseEntity.ok(CqResponse.from(cqs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cq> findCqById(@PathVariable UUID id) throws Exception {
        Cq cq = service.findCqByIdJoinFetch(id);

        if (cq == null) {
            throw new NotFoundException("Cq not found - " + id);
        }

        return ResponseEntity.ok(cq);
    }

    @PostMapping
    public ResponseEntity<CqResponse> save(
            @RequestBody CqRequest request
    ) {
        Cq cq = service.save(request);

        CqResponse response = new CqResponse();

        response.setId(cq.getId());
        response.setQuestion(cq.getQuestion());
        response.setAnswer(cq.getAnswer());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cq> partiallyUpdate(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Cq updatedCq = service.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedCq);
    }

    @PutMapping
    public ResponseEntity<Cq> update(
            @RequestBody Cq request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
