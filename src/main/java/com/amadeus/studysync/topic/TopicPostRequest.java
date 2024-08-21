package com.amadeus.studysync.topic;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.SortedSet;
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
    private SortedSet<Record> records;
}
