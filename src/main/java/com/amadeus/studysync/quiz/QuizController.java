package com.amadeus.studysync.quiz;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizServiceImpl service;

    @GetMapping
    public ResponseEntity<List<QuizBasicResponse>> findAllQuizzes(Principal connectedUser) {
        List<Quiz> quizzes = (service.findAll(connectedUser));

        return ResponseEntity.ok(QuizBasicResponse.from(quizzes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable UUID id, Principal connectedUser) throws Exception {
        Quiz quiz = service.findQuizByIdJoinFetch(id, connectedUser);

        if (quiz == null) {
            throw new NotFoundException("Quiz not found - " + id);
        }

        return ResponseEntity.ok(quiz);
    }

    @PostMapping
    public ResponseEntity<QuizDetailedResponse> save(
            @RequestBody PostQuizRequest request
    ) {
        Quiz quiz = service.save(request);

        QuizDetailedResponse response = new QuizDetailedResponse();

        response.setId(quiz.getId());
        response.setTitle(quiz.getTitle());
        response.setMcqs(quiz.getMcqs());
        response.setCqs(quiz.getCqs());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Quiz> update(
            @RequestBody Quiz request, Principal connectedUser
    ) {
        return ResponseEntity.ok((service.update(request, connectedUser)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Quiz> partiallyUpdate(@PathVariable UUID id, @RequestBody PatchQuizRequest updates, Principal connectedUser) {
        Quiz updatedQuiz = service.partialUpdate(id, updates, connectedUser);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Principal connectedUser) {
        service.deleteById(id, connectedUser);
    }
}
