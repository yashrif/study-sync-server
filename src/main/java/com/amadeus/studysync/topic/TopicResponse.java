package com.amadeus.studysync.topic;

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
public class TopicResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("color")
    private String color;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<TopicResponse> from(List<Topic> topics) {
        return topics.stream()
                .map(topic -> TopicResponse.builder()
                        .id(topic.getId())
                        .name(topic.getName())
                        .description(topic.getDescription())
                        .color(topic.getColor())
                        .status(topic.getStatus())
                        .createDate(topic.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
