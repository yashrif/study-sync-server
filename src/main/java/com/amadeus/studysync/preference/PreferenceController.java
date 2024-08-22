package com.amadeus.studysync.preference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceServiceImpl service;

    @GetMapping
    public ResponseEntity<Preference> findPreferences(Principal connectedUser) {
        Optional<Preference> preferences = (service.find(connectedUser));

        return preferences.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(service.save(PostPreferenceRequest.builder().build())));
    }

    @PutMapping
    public ResponseEntity<Preference> update(
            @RequestBody Preference request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @PatchMapping
    public ResponseEntity<Preference> partiallyUpdate(@RequestBody PatchPreferenceRequest updates, Principal connectedUser) {
        System.out.println(updates);
        Preference updatedPreference = service.partialUpdate(updates, connectedUser);
        return ResponseEntity.ok(updatedPreference);
    }
}
