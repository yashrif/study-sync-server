package com.amadeus.studysync.mcq;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MCQRepository extends JpaRepository<MCQ, UUID> {

}
