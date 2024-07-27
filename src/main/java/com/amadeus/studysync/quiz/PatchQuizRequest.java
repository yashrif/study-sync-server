package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PatchQuizRequest {
    @Nullable
    private String title;

    @Nullable
    private List<Mcq> mcqs;

    @Nullable
    private List<Cq> cqs;
}
