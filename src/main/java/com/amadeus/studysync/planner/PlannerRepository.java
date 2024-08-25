package com.amadeus.studysync.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlannerRepository extends JpaRepository<Planner, UUID> {
    @Query("select i from Planner i JOIN FETCH i.topics where i.id=:plannerId and i.createdBy=:id")
    Optional<Planner> findPlannerByIdJoinFetch(@Param("plannerId") UUID plannerId, @Param("id") UUID id);

    @Query(value = "select * from planner d where d.created_by = :id", nativeQuery = true)
    List<Planner> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from planner d where d.created_by = :id and d.id = :plannerId", nativeQuery = true)
    Optional<Planner> findByIdAndUser(@Param("plannerId") UUID plannerId, @Param("id") UUID id);
}
