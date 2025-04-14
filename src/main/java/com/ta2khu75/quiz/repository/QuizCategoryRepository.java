package com.ta2khu75.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.model.entity.QuizCategory;

public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long> {

}
