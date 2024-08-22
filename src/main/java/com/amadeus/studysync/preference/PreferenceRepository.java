package com.amadeus.studysync.preference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
    @Query(value = "select * from preference d where d.created_by = :id", nativeQuery = true)
    Optional<Preference> findByUserId(@Param("id") UUID id);
}
