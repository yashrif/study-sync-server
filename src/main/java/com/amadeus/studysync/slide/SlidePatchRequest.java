package com.amadeus.studysync.slide;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SlidePatchRequest {
    @Nullable
    private String name;

    @Nullable
    private String content;
}
