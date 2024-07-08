package com.amadeus.studysync.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static List<UploadResponse> from(List<Upload> uploads) {
        return uploads.stream()
                .map(upload -> UploadResponse.builder()
                        .id(upload.getId())
                        .title(upload.getTitle())
                        .name(upload.getName())
                        .type(upload.getType())
                        .createDate(upload.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
