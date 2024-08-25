package com.amadeus.studysync.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UploadRepository extends JpaRepository<Upload, UUID> {
    @Query(value = "select * from upload d where d.name = :name and d.created_by = :id", nativeQuery = true)
    Upload findByName(@Param("name") String name, @Param("id") UUID id);

    @Query(value = "select * from upload d where d.created_by = :id", nativeQuery = true)
    List<Upload> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from upload d where d.created_by = :id and d.id = :uploadId", nativeQuery = true)
    Optional<Upload> findByIdAndUser(@Param("uploadId") UUID uploadId, @Param("id") UUID id);
}
