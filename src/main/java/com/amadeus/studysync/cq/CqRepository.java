package com.amadeus.studysync.cq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CqRepository extends JpaRepository<Cq, UUID> {
    @Query("SELECT i FROM Cq i JOIN FETCH i.quiz WHERE i.id = :cqId and i.createdBy = :id")
    Optional<Cq> findCqByIdJoinFetch(@Param("cqId") UUID cqId, @Param("id") UUID id);

    @Query("FROM Cq WHERE isFlashcard = true and createdBy = :id")
    Optional<List<Cq>> findFlashcards(@Param("id") UUID id);

    @Query(value = "select * from upload d where d.created_by = :id", nativeQuery = true)
    List<Cq> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from upload d where d.created_by = :id and d.id = :cqId", nativeQuery = true)
    Optional<Cq> findByIdAndUser(@Param("cqId") UUID cqId, @Param("id") UUID id);
}
