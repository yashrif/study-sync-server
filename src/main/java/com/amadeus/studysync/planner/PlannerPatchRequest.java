package com.amadeus.studysync.planner;

import com.amadeus.studysync.topic.Topic;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PlannerPatchRequest {
    @Nullable
    private String title;

    @Nullable
    private List<Topic> topics;
}
