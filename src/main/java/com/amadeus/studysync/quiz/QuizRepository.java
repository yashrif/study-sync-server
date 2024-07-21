package com.amadeus.studysync.quiz;

import com.amadeus.studysync.cq.Cq;
import com.amadeus.studysync.mcq.Mcq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @Query("select i from Quiz i JOIN FETCH i.mcqs where i.id=:theId")
    Optional<Quiz> findQuizByIdJoinFetch(@Param("theId") UUID theId);

    @Query("from Mcq where quiz.id=:theId")
    Optional<List<Mcq>> findMcqsByQuizId(@Param("theId") UUID theId);

    @Query("from Cq where quiz.id=:theId")
    Optional<List<Cq>> findCqsByQuizId(@Param("theId") UUID theId);
}
