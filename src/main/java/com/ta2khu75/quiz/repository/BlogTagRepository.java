package com.ta2khu75.quiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.repository.custom.BlogTagRepositoryCustom;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long>, BlogTagRepositoryCustom {
	Set<BlogTag> findAllByNameInIgnoreCase(Set<String> name);
	List<BlogTag> findAllByNameContainingIgnoreCase(String keyword);
}
