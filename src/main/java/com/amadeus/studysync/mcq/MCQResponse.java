package com.amadeus.studysync.mcq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MCQResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("question")
    private String question;

    @JsonProperty("choice")
    private List<String> choice;

    @JsonProperty("answer")
    private List<Boolean> answer;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<MCQResponse> from(List<MCQ> MCQS) {
        return MCQS.stream()
                .map(mcq -> MCQResponse.builder()
                        .id(mcq.getId())
                        .question(mcq.getQuestion())
                        .choice(mcq.getChoice())
                        .answer(mcq.getAnswer())
                        .createDate(mcq.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
