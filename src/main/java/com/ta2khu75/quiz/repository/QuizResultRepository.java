package com.ta2khu75.quiz.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ta2khu75.quiz.model.entity.QuizResult;

public interface QuizResultRepository extends JpaRepository<QuizResult, String> {

	Optional<QuizResult> findByAccountIdAndQuizIdAndEndTimeAfterAndUpdatedAtIsNull(String id, String examId,
			Instant now);

	List<QuizResult> findByEndTimeBeforeAndUpdatedAtIsNull(Instant now);
	@Query("SELECT q FROM QuizResult q WHERE "
			+ "(:keyword IS NULL OR q.quiz.title LIKE %:keyword%) "
			+ "AND (:quizCategoryIds IS NULL OR q.quiz.quizCategory.id IN (:quizCategoryIds)) "
			+ "AND (:accountId IS NULL OR q.account.id= :accountId) "
			+ "AND (:fromDate IS NULL OR q.updatedAt >= :fromDate) AND (:toDate IS NULL OR q.updatedAt <= :toDate) ")
	Page<QuizResult> search(@Param("keyword") String keyword, 
			@Param("quizCategoryIds") Set<Long> quizCategoryIds,
			@Param("accountId") String accountId,
			@Param("fromDate") Instant fromDate, @Param("toDate") Instant toDate,
			Pageable pageable);
//	Page<ExamResult> findByAccountIdAndUpdatedAtIsNotNull(String accountId, Pageable pageable);
}
