package com.amadeus.studysync.mcq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PostMcqRequest {
    private UUID id;
    private String question;
    private List<String> choices;
    private List<Boolean> answers;
}
