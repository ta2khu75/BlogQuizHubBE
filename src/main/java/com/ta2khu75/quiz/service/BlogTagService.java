package com.ta2khu75.quiz.service;

import java.util.List;
import java.util.Set;

import com.ta2khu75.quiz.model.entity.BlogTag;

public interface BlogTagService {
	BlogTag create(String name);
	List<BlogTag> readAll(String keyword);
	List<BlogTag> readAll();
	Set<BlogTag> createOrReadAll(Set<String> names);
}
