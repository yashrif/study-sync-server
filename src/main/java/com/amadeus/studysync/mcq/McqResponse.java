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
public class McqResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("question")
    private String question;

    @JsonProperty("choices")
    private List<String> choices;

    @JsonProperty("answers")
    private List<Boolean> answers;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<McqResponse> from(List<Mcq> Mcqs) {
        return Mcqs.stream()
                .map(mcq -> McqResponse.builder()
                        .id(mcq.getId())
                        .question(mcq.getQuestion())
                        .choices(mcq.getChoices())
                        .answers(mcq.getAnswers())
                        .createDate(mcq.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
