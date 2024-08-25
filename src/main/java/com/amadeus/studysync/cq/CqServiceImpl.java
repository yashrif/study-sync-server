package com.amadeus.studysync.cq;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CqServiceImpl implements CqService {

    private final CqRepository repository;

    @Override
    public List<Cq> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Cq findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
    }

    @Override
    public Cq save(PostCqRequest request) {

        return repository.save(Cq.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .isFlashcard(request.getIsFlashcard() != null ? request.getIsFlashcard() : false)
                .build());
    }


    @Override
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with id - " + theId));

        repository.deleteById(theId);
    }

    @Override
    public Cq update(Cq theCqs, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theCqs.getId(),user.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with id - " + theCqs.getId()));
        return repository.save(theCqs);
    }


    @Override
    public Cq partialUpdate(UUID theId, PatchCqRequest updates, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Cq cq = repository.findByIdAndUser(theId,user.getId())
                .orElseThrow(() -> new NotFoundException("Cq not found with email - " + theId));

        cq.setQuestion(updates.getQuestion() != null ? updates.getQuestion() : cq.getQuestion());
        cq.setAnswer(updates.getAnswer() != null ? updates.getAnswer() : cq.getAnswer());
        cq.setIsFlashcard(updates.getIsFlashcard() != null ? updates.getIsFlashcard() : cq.getIsFlashcard());
        cq.setStatus(updates.getStatus() != null ? updates.getStatus() : cq.getStatus());

        return repository.save(cq);
    }

    @Override
    public Cq findCqByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Cq> cq = repository.findCqByIdJoinFetch(theId,user.getId());
        return cq.orElseGet(() -> repository.findByIdAndUser(theId,user.getId()).orElse(null));
    }

    @Override
    public Optional<List<Cq>> findFlashcards(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findFlashcards(user.getId());
    }
}