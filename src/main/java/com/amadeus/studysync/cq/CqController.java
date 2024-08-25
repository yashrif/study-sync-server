package com.amadeus.studysync.cq;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cqs")
@RequiredArgsConstructor
public class CqController {

    private final CqServiceImpl service;

    @GetMapping
    public ResponseEntity<List<CqResponse>> findAllCqs(Principal connectedUser) {
        List<Cq> cqs = (service.findAll(connectedUser));

        return ResponseEntity.ok(CqResponse.from(cqs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cq> findCqById(@PathVariable UUID id, Principal connectedUser) throws Exception {
        Cq cq = service.findCqByIdJoinFetch(id, connectedUser);

        if (cq == null) {
            throw new NotFoundException("Cq not found - " + id);
        }

        return ResponseEntity.ok(cq);
    }

    @PostMapping
    public ResponseEntity<CqResponse> save(
            @RequestBody PostCqRequest request
    ) {
        Cq cq = service.save(request);

        CqResponse response = new CqResponse();

        response.setId(cq.getId());
        response.setQuestion(cq.getQuestion());
        response.setAnswer(cq.getAnswer());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cq> partiallyUpdate(@PathVariable UUID id, @RequestBody PatchCqRequest updates, Principal connectedUser) {
        Cq updatedCq = service.partialUpdate(id, updates, connectedUser);
        return ResponseEntity.ok(updatedCq);
    }

    @PutMapping
    public ResponseEntity<Cq> update(
            @RequestBody Cq request, Principal connectedUser
    ) {
        return ResponseEntity.ok((service.update(request, connectedUser)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Principal connectedUser) {
        service.deleteById(id, connectedUser);
    }
}
