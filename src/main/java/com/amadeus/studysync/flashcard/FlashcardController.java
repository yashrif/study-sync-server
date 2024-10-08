package com.amadeus.studysync.flashcard;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.cq.CqResponse;
import com.amadeus.studysync.cq.CqServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/flashcards")
@RequiredArgsConstructor
public class FlashcardController {

    private final CqServiceImpl service;

    @GetMapping
    public ResponseEntity<List<CqResponse>> findAllCqs(Principal connectedUser) {
        Optional<List<Cq>> cqs = (service.findFlashcards(connectedUser));

        if (cqs.isPresent())
            return ResponseEntity.ok(CqResponse.from(cqs.orElse(null)));

        return ResponseEntity.ok(null);

    }
}
