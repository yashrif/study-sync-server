package com.amadeus.studysync.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("isIndexed")
    private Boolean isIndexed;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static UploadResponse from(Upload upload) {
        return UploadResponse.builder()
                .id(upload.getId())
                .title(upload.getTitle())
                .name(upload.getName())
                .type(upload.getType())
                .isIndexed(upload.getIsIndexed())
                .createDate(upload.getCreateDate())
                .build();
    }

    public static List<UploadResponse> from(List<Upload> uploads) {
        return uploads.stream()
                .map(upload -> UploadResponse.builder()
                        .id(upload.getId())
                        .title(upload.getTitle())
                        .name(upload.getName())
                        .type(upload.getType())
                        .isIndexed(upload.getIsIndexed())
                        .createDate(upload.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
