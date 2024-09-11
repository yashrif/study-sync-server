package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.mcq.Mcq;
import com.amadeus.studysync.upload.Upload;
import com.amadeus.studysync.upload.UploadRepository;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repository;
    private final UploadRepository uploadRepository;

    @Override
    public List<Quiz> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Quiz findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
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

        request.getUploads().forEach(upload -> {
            Upload theUpload = uploadRepository.findById(upload.getId())
                    .orElseThrow(() -> new NotFoundException("Quiz not found with id - " + upload.getId()));
            quiz.addUpload(theUpload);
        });

        return repository.save(quiz);
    }

    @Override
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Quiz quiz = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Quiz not found with id - " + theId));

        quiz.getCqs().forEach(cq -> cq.setQuiz(null));

        repository.deleteById(theId);
    }

    @Override
    public Quiz update(Quiz theQuiz, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theQuiz.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException("Quiz not found with id - " + theQuiz.getId()));
        return repository.save(theQuiz);
    }

    @Override
    public Quiz partialUpdate(UUID theId, PatchQuizRequest updates, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Quiz quiz = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Quiz not found with email - " + theId));

        quiz.setTitle(updates.getTitle() != null ? updates.getTitle() : quiz.getTitle());

        if (updates.getCqs() != null)
            updates.getCqs().forEach(quiz::addCq);

        if (updates.getMcqs() != null)
            updates.getMcqs().forEach(quiz::addMcq);

        return repository.save(quiz);
    }

    @Override
    public Quiz findQuizByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Quiz> quiz = repository.findQuizByIdJoinFetch(theId, user.getId());
        return quiz.orElseGet(() -> repository.findById(theId).orElse(null));
    }

    @Override
    public List<Mcq> findMcqsByQuizId(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<List<Mcq>> mcqs = repository.findMcqsByQuizId(theId, user.getId());
        return mcqs.orElseGet(() -> Objects.requireNonNull(repository.findById(theId).orElse(null)).getMcqs());
    }

    @Override
    public List<Cq> findCqsByQuizId(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<List<Cq>> cqs = repository.findCqsByQuizId(theId, user.getId());
        return cqs.orElseGet(() -> Objects.requireNonNull(repository.findById(theId).orElse(null)).getCqs());
    }
}

