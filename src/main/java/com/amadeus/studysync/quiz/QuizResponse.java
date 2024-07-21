package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
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
public class QuizResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mcqs")
    private List<Mcq> mcqs;

    @JsonProperty("cqs")
    private List<Cq> cqs;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<QuizResponse> from(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(quiz -> QuizResponse.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .createDate(quiz.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
