package com.amadeus.studysync.mcq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface McqRepository extends JpaRepository<Mcq, UUID> {
    @Query("SELECT i FROM Mcq i JOIN FETCH i.quiz WHERE i.id = :mcqId and i.createdBy = :id")
    Optional<Mcq> findMcqByIdJoinFetch(@Param("mcqId") UUID mcqId, @Param("id") UUID id);

    @Query(value = "select * from mcq d where d.created_by = :id", nativeQuery = true)
    List<Mcq> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from mcq d where d.created_by = :id and d.id = :mcqId", nativeQuery = true)
    Optional<Mcq> findByIdAndUser(@Param("mcqId") UUID mcqId, @Param("id") UUID id);
}
