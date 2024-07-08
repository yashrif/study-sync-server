package com.amadeus.studysync.upload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadRequest {
    private String id;
    private String title;
    private String name;
    private String type;
}
