package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.PageResponse;

public interface BlogTagService {
	PageResponse<BlogTag> search(Search search);
	void delete(Long id);
}
