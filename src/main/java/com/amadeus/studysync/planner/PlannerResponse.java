package com.amadeus.studysync.planner;

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
public class PlannerResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<PlannerResponse> from(List<Planner> quizzes) {
        return quizzes.stream()
                .map(planner -> PlannerResponse.builder()
                        .id(planner.getId())
                        .title(planner.getTitle())
                        .createDate(planner.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
