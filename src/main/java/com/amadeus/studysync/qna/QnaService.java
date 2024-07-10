package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.Mcq;
import com.amadeus.studysync.service.AppService;

import java.util.List;
import java.util.UUID;

public interface QnaService extends AppService<Qna, QnaRequest, UUID> {
    Qna findQnaByIdJoinFetch(UUID theId);

    List<Mcq> findMcqsByQnaId(UUID theId);
}
