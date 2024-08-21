package com.amadeus.studysync.topic;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.SortedSet;

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
    private SortedSet<Record> records;
}
