package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import com.amadeus.studysync.upload.Upload;
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
public class QuizDetailedResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mcqs")
    private List<Mcq> mcqs;

    @JsonProperty("cqs")
    private List<Cq> cqs;

    @JsonProperty("uploads")
    private List<Upload> uploads;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QuizDetailedResponse> from(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(quiz -> QuizDetailedResponse.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .createDate(quiz.getCreateDate())
                        .cqs(quiz.getCqs())
                        .mcqs(quiz.getMcqs())
                        .uploads(quiz.getUploads())
                        .build())
                .collect(Collectors.toList());
    }
}
