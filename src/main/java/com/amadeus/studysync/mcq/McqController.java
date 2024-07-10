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
        List<Mcq> mcqs = (service.findAll());

        return ResponseEntity.ok(McqResponse.from(mcqs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mcq> findMCQById(@PathVariable UUID id) throws Exception {
        Mcq mcq = service.findMcqByIdJoinFetch(id);

        if (mcq == null) {
            throw new NotFoundException("MCQ not found - " + id);
        }

        return ResponseEntity.ok(mcq);
    }

    @PostMapping
    public ResponseEntity<McqResponse> save(
            @RequestBody McqRequest request
    ) {
        Mcq mcq = service.save(request);

        McqResponse response = new McqResponse();

        response.setId(mcq.getId());
        response.setQuestion(mcq.getQuestion());
        response.setChoices(mcq.getChoices());
        response.setAnswers(mcq.getAnswers());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
