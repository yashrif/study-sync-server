package com.amadeus.studysync.upload;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatchUploadRequest {
    @Nullable
    private String title;

    @Nullable
    private String name;

    @Nullable
    private String type;

    @Nullable
    private Boolean isIndexed;
}
