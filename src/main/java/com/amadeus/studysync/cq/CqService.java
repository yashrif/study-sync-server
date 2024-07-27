package com.amadeus.studysync.cq;

import com.amadeus.studysync.service.AppService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CqService extends AppService<Cq, PostCqRequest, PatchCqRequest, UUID> {
    Cq partialUpdate(UUID theId, @RequestBody PatchCqRequest updates);

    Cq findCqByIdJoinFetch(UUID theId);

    Optional<List<Cq>> findFlashcards();

}
