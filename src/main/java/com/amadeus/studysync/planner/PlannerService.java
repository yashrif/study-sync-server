package com.amadeus.studysync.planner;

import com.amadeus.studysync.service.AppService;

import java.security.Principal;
import java.util.UUID;

public interface PlannerService extends AppService<Planner, PlannerPostRequest, PlannerPatchRequest, UUID> {
    Planner findPlannerByIdJoinFetch(UUID theId, Principal connectedUser);
}
