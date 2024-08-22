package com.amadeus.studysync.preference;

import java.security.Principal;
import java.util.Optional;

public interface PreferenceService {
    Optional<Preference> find(Principal connectedUser);

    Preference save(PostPreferenceRequest theEntity);

    Preference update(Preference theEntity);

    Preference partialUpdate(PatchPreferenceRequest updates, Principal connectedUser) throws Exception;
}
