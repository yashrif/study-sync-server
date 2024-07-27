package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import com.amadeus.studysync.service.AppService;

import java.util.List;
import java.util.UUID;

public interface QuizService extends AppService<Quiz, PostQuizRequest, PatchQuizRequest, UUID> {
    Quiz findQuizByIdJoinFetch(UUID theId);

    List<Mcq> findMcqsByQuizId(UUID theId);

    List<Cq> findCqsByQuizId(UUID theId);
}
