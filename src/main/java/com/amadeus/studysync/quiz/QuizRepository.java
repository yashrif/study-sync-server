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
    @Query("select i from Quiz i JOIN FETCH i.mcqs where i.id=:quizId and i.createdBy=:id")
    Optional<Quiz> findQuizByIdJoinFetch(@Param("quizId") UUID quizId, @Param("id") UUID id);

    @Query("from Mcq where quiz.id=:quizId and quiz.createdBy=:id")
    Optional<List<Mcq>> findMcqsByQuizId(@Param("quizId") UUID quizId, @Param("id") UUID id);

    @Query("from Cq where quiz.id=:quizId and quiz.createdBy=:id")
    Optional<List<Cq>> findCqsByQuizId(@Param("quizId") UUID quizId, @Param("id") UUID id);

    @Query(value = "select * from quiz d where d.created_by = :id", nativeQuery = true)
    List<Quiz> findAllByUser(@Param("id") UUID id);

    @Query(value = "select * from quiz d where d.created_by = :id and d.id = :quizId", nativeQuery = true)
    Optional<Quiz> findByIdAndUser(@Param("quizId") UUID quizId, @Param("id") UUID id);
}
