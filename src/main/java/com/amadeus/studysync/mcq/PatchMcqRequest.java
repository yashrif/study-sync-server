package com.amadeus.studysync.mcq;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PatchMcqRequest {
    @Nullable
    private String question;

    @Nullable
    private List<String> choices;

    @Nullable
    private List<Boolean> answers;
}
