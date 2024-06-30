package com.amadeus.studysync.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
