package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import com.amadeus.studysync.upload.Upload;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PostQuizRequest {
    private UUID id;
    @NonNull
    private String title;
    private List<Mcq> mcqs;
    private List<Cq> cqs;

    @NonNull
    private List<Upload> uploads;
}
