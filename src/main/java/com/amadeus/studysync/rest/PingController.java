package com.amadeus.studysync.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PingController {
    @GetMapping("/ping")
    public String ping() {
        return "Server is live...";
    }
}
