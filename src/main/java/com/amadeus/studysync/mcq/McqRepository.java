package com.amadeus.studysync.mcq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface McqRepository extends JpaRepository<Mcq, UUID> {
    @Query("SELECT i FROM Mcq i JOIN FETCH i.quiz WHERE i.id = :theId")
    Optional<Mcq> findMcqByIdJoinFetch(@Param("theId") UUID theId);
}
