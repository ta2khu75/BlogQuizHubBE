package com.ta2khu75.quiz.service.base;

import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.PageResponse;

public interface SearchService<RES,S extends Search> {
	PageResponse<RES> search(S search);
}
