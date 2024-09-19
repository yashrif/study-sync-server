package com.amadeus.studysync.slide;

import com.amadeus.studysync.upload.UploadResponse;
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
public class SlideResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    @JsonProperty("uploads")
    private List<UploadResponse> uploads;

    public static List<SlideResponse> from(List<Slide> slides) {

        return slides.stream()
                .map(slide -> SlideResponse.builder()
                        .id(slide.getId())
                        .name(slide.getName())
                        .content(slide.getContent())
                        .uploads(slide.getUploads().stream()
                                .map(upload -> UploadResponse.builder()
                                        .id(upload.getId())
                                        .title(upload.getTitle())
                                        .name(upload.getName())
                                        .type(upload.getType())
                                        .isIndexed(upload.getIsIndexed())
                                        .build())
                                .collect(Collectors.toList()))
                        .createDate(slide.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
