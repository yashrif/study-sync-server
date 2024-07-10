package com.amadeus.studysync.qna;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/qnas")
@RequiredArgsConstructor
public class QNAController {

    private final QNAService service;

    @GetMapping
    public ResponseEntity<List<QNAResponse>> findAllQNAs() {
        List<QNA> qnas = (service.findAll());

        return ResponseEntity.ok(QNAResponse.from(qnas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QNA> findQNAById(@PathVariable UUID id) throws Exception {
        QNA file = service.findById(id);

        if (file == null) {
            throw new NotFoundException("QNA not found - " + id);
        }

        return ResponseEntity.ok(file);
    }

    @PostMapping
    public ResponseEntity<QNAResponse> save(
            @RequestBody QNARequest request
    ) {
        QNA qna = service.save(request);

        QNAResponse response = new QNAResponse();

        response.setId(qna.getId());
        response.setTitle(qna.getTitle());
        response.setMcqs(qna.getMcqs());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
