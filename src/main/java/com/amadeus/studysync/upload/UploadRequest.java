package com.amadeus.studysync.upload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UploadRequest {
    private UUID id;
    private String title;
    private String name;
    private String type;
    private Boolean isIndexed;
}
