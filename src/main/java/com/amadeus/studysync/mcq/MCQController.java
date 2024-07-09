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
public class MCQController {

    private final MCQService service;

    @GetMapping
    public ResponseEntity<List<MCQResponse>> findAllMCQs() {
        List<MCQ> MCQS = (service.findAll());

        return ResponseEntity.ok(MCQResponse.from(MCQS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MCQ> findMCQById(@PathVariable UUID id) throws Exception {
        MCQ file = service.findById(id);

        if (file == null) {
            throw new NotFoundException("MCQ not found - " + id);
        }

        return ResponseEntity.ok(file);
    }

    @PostMapping
    public ResponseEntity<MCQResponse> save(
            @RequestBody MCQRequest request
    ) {
        MCQ mcq = service.save(request);

        MCQResponse response = new MCQResponse();

        response.setId(mcq.getId());
        response.setQuestion(mcq.getQuestion());
        response.setChoice(mcq.getChoice());
        response.setAnswer(mcq.getAnswer());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}