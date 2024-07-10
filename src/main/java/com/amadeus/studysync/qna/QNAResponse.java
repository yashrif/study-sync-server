package com.amadeus.studysync.qna;

import com.amadeus.studysync.mcq.MCQ;
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
public class QNAResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mcqs")
    private List<MCQ> mcqs;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QNAResponse> from(List<QNA> qnas) {
        return qnas.stream()
                .map(qna -> QNAResponse.builder()
                        .id(qna.getId())
                        .title(qna.getTitle())
                        .mcqs(qna.getMcqs())
                        .createDate(qna.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
