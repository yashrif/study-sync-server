package com.amadeus.studysync.mcq;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/mcqs")
@RequiredArgsConstructor
public class McqController {

    private final McqServiceImpl service;

    @GetMapping
    public ResponseEntity<List<McqResponse>> findAllMCQs() {
        List<Mcq> cqs = (service.findAll());

        return ResponseEntity.ok(McqResponse.from(cqs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mcq> findMCQById(@PathVariable UUID id) throws Exception {
        Mcq cqs = service.findMcqByIdJoinFetch(id);

        if (cqs == null) {
            throw new NotFoundException("MCQ not found - " + id);
        }

        return ResponseEntity.ok(cqs);
    }

    @PostMapping
    public ResponseEntity<McqResponse> save(
            @RequestBody McqRequest request
    ) {
        Mcq cqs = service.save(request);

        McqResponse response = new McqResponse();

        response.setId(cqs.getId());
        response.setQuestion(cqs.getQuestion());
        response.setChoices(cqs.getChoices());
        response.setAnswers(cqs.getAnswers());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
