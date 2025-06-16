package com.ta2khu75.quiz.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.QuizLevel;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.repository.custom.QuizRepositoryCustom;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {
//	@Query("SELECT q FROM Quiz q WHERE "
//			+ "(:keyword IS NULL OR q.title LIKE %:keyword% OR q.description LIKE %:keyword% OR q.author.displayName LIKE %:keyword% OR q.category.name LIKE %:keyword%) "
//			+ "AND (:authorId IS NULL OR q.author.id = :authorId) "
//			+ "AND (:quizCategoryIds IS NULL OR q.category.id IN (:quizCategoryIds)) "
//			+ "AND (:quizLevels IS NULL OR q.level IN (:quizLevels))"
//			+ "AND (:completed IS NULL OR q.completed = :completed) "
//			+ "AND (:minDuration IS NULL OR q.duration >= :minDuration) "
//			+ "AND (:maxDuration IS NULL OR q.duration <= :maxDuration) "
//			+ "AND (:accessModifier IS NULL OR q.accessModifier = :accessModifier) ")
//	Page<Quiz> search(@Param("keyword") String keyword, 
//			@Param("authorId") Long authorId,
//			@Param("quizCategoryIds") List<Long> quizCategoryIds,
//			@Param("quizLevels") List<QuizLevel> quizLevel,
//			@Param("completed") Boolean completed,
//			@Param("minDuration") Integer minDuration,
//			@Param("maxDuration") Integer maxDuration,
//			@Param("accessModifier") AccessModifier accessModifier,
//			Pageable pageable);

	List<Quiz> findByAuthorIdAndTitleContainingIgnoreCaseAndBlogIsNull(Long authorId, String keyword);

	Long countByAuthorIdAndAccessModifier(Long authorId, AccessModifier accessModifier);

	Optional<Quiz> findByIdAndAuthorId(Long quizId, Long authorId);
}
