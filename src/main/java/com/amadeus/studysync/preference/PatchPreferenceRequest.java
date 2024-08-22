package com.amadeus.studysync.preference;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchPreferenceRequest {
    @Nullable
    private String studyId;

}
