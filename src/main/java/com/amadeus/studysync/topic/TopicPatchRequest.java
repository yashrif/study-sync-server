package com.amadeus.studysync.topic;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TopicPatchRequest {
    @Nullable
    private String name;

    @Nullable
    private String description;

    @Nullable
    private String color;

    @Nullable
    private Status status;

    @Nullable
    private LocalDateTime date;
}
