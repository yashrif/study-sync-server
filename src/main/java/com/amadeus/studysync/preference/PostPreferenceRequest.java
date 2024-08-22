package com.amadeus.studysync.preference;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPreferenceRequest {
    @Nullable
    private UUID id;

    @Nullable
    private String studyId;
}
