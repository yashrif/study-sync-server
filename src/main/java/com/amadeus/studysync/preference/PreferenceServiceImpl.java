package com.amadeus.studysync.preference;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository repository;

    public Optional<Preference> find(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByUserId(user.getId());
    }

    @Override
    public Preference save(PostPreferenceRequest preferenceRequest) {
        Preference preference = Preference.builder()
                .id(UUID.randomUUID())  // Generate the UUID here
                .studyId(preferenceRequest.getStudyId())
                .build();

        return repository.save(preference);
    }

    @Override
    public Preference update(Preference thePreference) {
        repository.findById(thePreference.getId())
                .orElseThrow(() -> new NotFoundException("Preference not found with id - " + thePreference.getId()));
        return repository.save(thePreference);
    }

    @Override
    public Preference partialUpdate(PatchPreferenceRequest updates, Principal connectedUser) {
        Optional<Preference> preference = find(connectedUser);

        if (preference.isPresent()) {
            preference.get().setStudyId(updates.getStudyId() != null ? updates.getStudyId() : preference.get().getStudyId());
            preference.get().setRefreshToken(updates.getRefreshToken());
            return repository.save(preference.get());

        }
        return save(PostPreferenceRequest.builder().build());

    }
}