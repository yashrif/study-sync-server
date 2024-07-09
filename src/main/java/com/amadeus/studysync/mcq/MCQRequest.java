package com.amadeus.studysync.mcq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MCQRequest {
    private String question;
    private List<String> choice;
    private List<Boolean> answer;
}
