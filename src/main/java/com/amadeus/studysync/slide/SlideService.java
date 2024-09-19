package com.amadeus.studysync.slide;

import com.amadeus.studysync.service.AppService;

import java.security.Principal;
import java.util.UUID;

public interface SlideService extends AppService<Slide, SlidePostRequest, SlidePatchRequest, UUID> {
    Slide findSlideByIdJoinFetch(UUID theId, Principal connectedUser);
}
