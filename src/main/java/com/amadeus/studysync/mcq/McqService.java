package com.amadeus.studysync.mcq;

import com.amadeus.studysync.service.AppService;

import java.util.UUID;

public interface McqService extends AppService<Mcq, PostMcqRequest, PatchMcqRequest, UUID> {
    Mcq findMcqByIdJoinFetch(UUID theId);

}