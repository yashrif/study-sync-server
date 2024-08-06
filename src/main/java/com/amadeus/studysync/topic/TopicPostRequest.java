package com.amadeus.studysync.topic;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TopicPostRequest {
    @Nullable
    private UUID id;

    @NonNull
    private String name;

    @Nullable
    private String description;

    @NonNull
    private String color;

    @Nullable
    private Status status;

    @Nullable
    private List<LocalDateTime> dates;
}
