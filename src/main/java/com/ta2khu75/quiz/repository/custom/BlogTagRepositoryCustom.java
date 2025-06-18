package com.ta2khu75.quiz.repository.custom;

import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.request.search.Search;

public interface BlogTagRepositoryCustom {
	Page<BlogTag> search(Search search);
}
