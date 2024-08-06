package com.amadeus.studysync.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PlannerRepository extends JpaRepository<Planner, UUID> {
    @Query("select i from Planner i JOIN FETCH i.topics where i.id=:theId")
    Optional<Planner> findPlannerByIdJoinFetch(@Param("theId") UUID theId);
}
