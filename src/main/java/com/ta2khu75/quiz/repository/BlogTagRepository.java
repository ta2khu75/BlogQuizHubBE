package com.ta2khu75.quiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.model.entity.BlogTag;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
	Set<BlogTag> findAllByNameInIgnoreCase(Set<String> name);
	List<BlogTag> findAllByNameContainingIgnoreCase(String keyword);
}
