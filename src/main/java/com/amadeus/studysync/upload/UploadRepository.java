package com.amadeus.studysync.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UploadRepository extends JpaRepository<Upload, String> {
    @Query(value = "select * from upload d where d.name = :name", nativeQuery = true)
    Upload findByName(@Param("name") String name);
}
