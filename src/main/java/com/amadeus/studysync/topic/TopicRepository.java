package com.amadeus.studysync.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    @Query("SELECT i FROM Topic i JOIN FETCH i.planner WHERE i.id = :theId")
    Optional<Topic> findTopicByIdJoinFetch(@Param("theId") UUID theId);
}
