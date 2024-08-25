package com.amadeus.studysync.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    @Query("SELECT i FROM Topic i JOIN FETCH i.planner WHERE i.id = :theId and i.planner.createdBy = :id")
    Optional<Topic> findTopicByIdJoinFetch(@Param("theId") UUID theId, @Param("id") UUID id);

    @Query(value = "select * from topic d where d.created_by = :id", nativeQuery = true)
    List<Topic> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from topic d where d.created_by = :id and d.id = :topicId", nativeQuery = true)
    Optional<Topic> findByIdAndUser(@Param("topicId") UUID topicId, @Param("id") UUID id);
}
