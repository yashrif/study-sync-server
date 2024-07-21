package com.amadeus.studysync.quiz;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizServiceImpl service;

    @GetMapping
    public ResponseEntity<List<QuizResponse>> findAllQuizzes() {
        List<Quiz> quizzes = (service.findAll());

        return ResponseEntity.ok(QuizResponse.from(quizzes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable UUID id) throws Exception {
        Quiz quiz = service.findQuizByIdJoinFetch(id);

        if (quiz == null) {
            throw new NotFoundException("Quiz not found - " + id);
        }

        return ResponseEntity.ok(quiz);
    }

    @PostMapping
    public ResponseEntity<QuizResponse> save(
            @RequestBody QuizRequest request
    ) {
        Quiz quiz = service.save(request);

        QuizResponse response = new QuizResponse();

        response.setId(quiz.getId());
        response.setTitle(quiz.getTitle());
        response.setMcqs(quiz.getMcqs());
        response.setCqs(quiz.getCqs());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Quiz> update(
            @RequestBody Quiz request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Quiz> partiallyUpdate(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Quiz updatedQuiz = service.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
