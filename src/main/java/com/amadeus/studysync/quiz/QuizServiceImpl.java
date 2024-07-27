package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.mcq.Mcq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repository;

    @Override
    public List<Quiz> findAll() {
        return repository.findAll();
    }

    @Override
    public Quiz findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Quiz save(PostQuizRequest request) {

        Quiz quiz = Quiz.builder()
                .id(request.getId())
                .title(request.getTitle())
                .build();

        if (request.getMcqs() != null) {
            List<Mcq> mcqs = new ArrayList<>();

            for (Mcq mcq : request.getMcqs()) {
                mcq.setQuiz(quiz);
                mcqs.add(mcq);
            }
            quiz.setMcqs(mcqs);
        }

        if (request.getCqs() != null) {
            List<Cq> cqs = new ArrayList<>();

            for (Cq cq : request.getCqs()) {
                cq.setQuiz(quiz);
                cqs.add(cq);
            }
            quiz.setCqs(cqs);
        }

        return repository.save(quiz);
    }

    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Quiz update(Quiz theQuiz) {
        repository.findById(theQuiz.getId())
                .orElseThrow(() -> new NotFoundException("Quiz not found with id - " + theQuiz.getId()));
        return repository.save(theQuiz);
    }

    @Override
    public Quiz partialUpdate(UUID theId, PatchQuizRequest updates) {
        Quiz quiz = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Quiz not found with email - " + theId));

        quiz.setTitle(updates.getTitle() != null ? updates.getTitle() : quiz.getTitle());

        if (updates.getCqs() != null)
            updates.getCqs().forEach(quiz::addCq);

        if (updates.getMcqs() != null)
            updates.getMcqs().forEach(quiz::addMcq);

        return repository.save(quiz);
    }

    @Override
    public Quiz findQuizByIdJoinFetch(UUID theId) {
        Optional<Quiz> quiz = repository.findQuizByIdJoinFetch(theId);
        return quiz.orElseGet(() -> repository.findById(theId).orElse(null));
    }

    @Override
    public List<Mcq> findMcqsByQuizId(UUID theId) {
        Optional<List<Mcq>> mcqs = repository.findMcqsByQuizId(theId);
        return mcqs.orElseGet(() -> Objects.requireNonNull(repository.findById(theId).orElse(null)).getMcqs());
    }

    @Override
    public List<Cq> findCqsByQuizId(UUID theId) {
        Optional<List<Cq>> cqs = repository.findCqsByQuizId(theId);
        return cqs.orElseGet(() -> Objects.requireNonNull(repository.findById(theId).orElse(null)).getCqs());
    }
}

