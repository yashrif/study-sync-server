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

@Builder
class TopicResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlannerResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("topics")
    private List<TopicResponse> topics;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    public static List<PlannerResponse> from(List<Planner> planners) {

        return planners.stream()
                .map(planner -> PlannerResponse.builder()
                        .id(planner.getId())
                        .title(planner.getTitle())
                        .topics(planner.getTopics().stream()
                                .map(topic -> TopicResponse.builder()
                                        .id(topic.getId())
                                        .name(topic.getName())
                                        .color(topic.getColor())
                                        .build())
                                .collect(Collectors.toList()))
                        .createDate(planner.getCreateDate())
                        .endDate(planner.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }
}
