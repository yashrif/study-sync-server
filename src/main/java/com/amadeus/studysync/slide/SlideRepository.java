package com.amadeus.studysync.slide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SlideRepository extends JpaRepository<Slide, UUID> {
    @Query("select i from Slide i JOIN FETCH i.uploads where i.id=:slideId and i.createdBy=:id")
    Optional<Slide> findSlideByIdJoinFetch(@Param("slideId") UUID slideId, @Param("id") UUID id);

    @Query(value = "select * from slide i where i.created_by = :id", nativeQuery = true)
    List<Slide> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from slide i where i.created_by = :id and i.id = :slideId", nativeQuery = true)
    Optional<Slide> findByIdAndUser(@Param("slideId") UUID slideId, @Param("id") UUID id);
}
