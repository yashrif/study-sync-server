package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.MCQ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QNARequest {
    private String title;
    private List<MCQ> mcq;
}
