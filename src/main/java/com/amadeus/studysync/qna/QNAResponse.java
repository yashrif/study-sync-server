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

    @JsonProperty("mcq")
    private List<MCQ> mcq;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QNAResponse> from(List<QNA> QNAs) {
        return QNAs.stream()
                .map(QNA -> QNAResponse.builder()
                        .id(QNA.getId())
                        .title(QNA.getTitle())
                        .mcq(QNA.getMcq())
                        .createDate(QNA.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}