package com.amadeus.studysync.planner;

import com.amadeus.studysync.topic.Topic;
import com.amadeus.studysync.upload.Upload;
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
public class PlannerPostRequest {
    private UUID id;
    @NonNull
    private String title;

    @NonNull
    private List<Upload> uploads;

    @NonNull
    private List<Topic> topics;

    @Nullable
    private LocalDateTime endDate;
}
