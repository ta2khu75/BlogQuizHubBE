package com.ta2khu75.quiz.service;

import java.util.List;

import com.ta2khu75.quiz.model.request.BlogRequest;
import com.ta2khu75.quiz.model.request.search.BlogSearch;
import com.ta2khu75.quiz.model.response.BlogResponse;
import com.ta2khu75.quiz.service.base.CrudFileService;
import com.ta2khu75.quiz.service.base.SearchService;

public interface BlogService extends CrudFileService<BlogRequest, BlogResponse, String>, SearchService<BlogResponse, BlogSearch> {
	BlogResponse readDetail(String id);
	List<BlogResponse> readAllByAuthorIdAndKeywork(Long authorId, String keyword);
}
