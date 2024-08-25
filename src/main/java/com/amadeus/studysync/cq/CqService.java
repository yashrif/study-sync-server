package com.amadeus.studysync.cq;

import com.amadeus.studysync.service.AppService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CqService extends AppService<Cq, PostCqRequest, PatchCqRequest, UUID> {

    Cq findCqByIdJoinFetch(UUID theId, Principal connectedUser);

    Optional<List<Cq>> findFlashcards(Principal connectedUser);

}
