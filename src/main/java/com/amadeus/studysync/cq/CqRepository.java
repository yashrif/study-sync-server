package com.amadeus.studysync.cq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CqRepository extends JpaRepository<Cq, UUID> {
    @Query("SELECT i FROM Cq i JOIN FETCH i.quiz WHERE i.id = :theId")
    Optional<Cq> findCqByIdJoinFetch(@Param("theId") UUID theId);

    @Query("FROM Cq WHERE isFlashcard = true")
    Optional<List<Cq>> findFlashcards();
}
