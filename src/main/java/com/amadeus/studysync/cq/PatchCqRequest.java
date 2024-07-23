package com.amadeus.studysync.cq;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatchCqRequest {
    @Nullable
    private String question;

    @Nullable
    private String answer;

    @Nullable
    private Boolean isFlashcard;

    @Nullable
    private Status status;
}
