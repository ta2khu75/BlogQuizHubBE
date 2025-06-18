package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.request.CommentRequest;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.CommentResponse;
import com.ta2khu75.quiz.model.response.PageResponse;

public interface CommentService  {
	PageResponse<CommentResponse> readPageByBlogId(String blogId, Search search);
	CommentResponse create(String blogId, CommentRequest request);
	CommentResponse update(String id, CommentRequest request);
	void delete(String id);
}
