package com.ta2khu75.quiz.repository.custom;

import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.request.search.BlogSearch;

public interface BlogRepositoryCustom {
	Page<Blog> search(BlogSearch search);
}
