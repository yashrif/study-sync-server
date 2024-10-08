package com.amadeus.studysync.slide;

import com.amadeus.studysync.upload.Upload;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SlidePostRequest {
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String content;

    @NonNull
    private List<String> topics;

    @Nullable
    private List<Upload> uploads;
}
