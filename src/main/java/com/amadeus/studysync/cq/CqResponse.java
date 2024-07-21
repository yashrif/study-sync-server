package com.amadeus.studysync.cq;

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
public class CqResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("isFlashcard")
    private Boolean isFlashcard;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<CqResponse> from(List<Cq> cqs) {
        return cqs.stream()
                .map(cq -> CqResponse.builder()
                        .id(cq.getId())
                        .question(cq.getQuestion())
                        .answer(cq.getAnswer())
                        .isFlashcard(cq.getIsFlashcard())
                        .createDate(cq.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
