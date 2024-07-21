package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class QuizRequest {
    private UUID id;
    private String title;
    private List<Mcq> mcqs;
    private List<Cq> cqs;
}
