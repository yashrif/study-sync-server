package com.amadeus.studysync.cq;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CqRequest {
    @Nullable
    private UUID id;

    @NonNull
    private String question;

    @NonNull
    private String answer;

    @Nullable
    private Boolean isFlashcard;

    @Nullable
    private Status status;
}
