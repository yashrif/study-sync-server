package com.amadeus.studysync.quiz;

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
public class QuizBasicResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mcqs")
    private Integer mcqs;

    @JsonProperty("cqs")
    private Integer cqs;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QuizBasicResponse> from(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(quiz -> QuizBasicResponse.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .cqs(quiz.getCqs().size())
                        .mcqs(quiz.getMcqs().size())
                        .createDate(quiz.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
