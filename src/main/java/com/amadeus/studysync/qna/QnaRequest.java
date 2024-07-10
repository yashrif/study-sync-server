package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.Mcq;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QnaRequest {
    private String title;
    private List<Mcq> mcqs;
}
