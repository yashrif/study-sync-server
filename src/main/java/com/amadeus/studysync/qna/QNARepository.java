package com.amadeus.studysync.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QNARepository extends JpaRepository<QNA, UUID> {

}
