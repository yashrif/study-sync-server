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
public class QnaController {

    private final QnaServiceImpl service;

    @GetMapping
    public ResponseEntity<List<QnaResponse>> findAllQnas() {
        List<Qna> qnas = (service.findAll());

        return ResponseEntity.ok(QnaResponse.from(qnas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Qna> findQnaById(@PathVariable UUID id) throws Exception {
        Qna qna = service.findQnaByIdJoinFetch(id);


//        System.out.println(service.findMcqsByQnaId(id));

        if (qna == null) {
            throw new NotFoundException("Qna not found - " + id);
        }

        return ResponseEntity.ok(qna);
    }

    @PostMapping
    public ResponseEntity<QnaResponse> save(
            @RequestBody QnaRequest request
    ) {
        Qna qna = service.save(request);

        QnaResponse response = new QnaResponse();

        response.setId(qna.getId());
        response.setTitle(qna.getTitle());
        response.setMcqs(qna.getMcqs());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
