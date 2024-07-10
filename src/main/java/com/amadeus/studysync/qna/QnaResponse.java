package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.Mcq;
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
public class QnaResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mcqs")
    private List<Mcq> mcqs;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QnaResponse> from(List<Qna> qnas) {
        return qnas.stream()
                .map(qna -> QnaResponse.builder()
                        .id(qna.getId())
                        .title(qna.getTitle())
                        .createDate(qna.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
