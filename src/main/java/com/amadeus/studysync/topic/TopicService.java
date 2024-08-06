package com.amadeus.studysync.topic;

import com.amadeus.studysync.service.AppService;

import java.util.UUID;

public interface TopicService extends AppService<Topic, TopicPostRequest, TopicPatchRequest, UUID> {

    Topic findTopicByIdJoinFetch(UUID theId);
}
