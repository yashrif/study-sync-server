package com.amadeus.studysync.user;

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
public class UserResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    public static UserResponse from(User upload) {
        return UserResponse.builder()
                .id(upload.getId())
                .firstName(upload.getFirstName())
                .lastName(upload.getLastName())
                .email(upload.getEmail())
                .role(upload.getRole())
                .createDate(upload.getCreateDate())
                .build();
    }

    public static List<UserResponse> from(List<User> uploads) {
        return uploads.stream()
                .map(upload -> UserResponse.builder()
                        .id(upload.getId())
                        .firstName(upload.getFirstName())
                        .lastName(upload.getLastName())
                        .email(upload.getEmail())
                        .role(upload.getRole())
                        .createDate(upload.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
