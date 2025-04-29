package com.ta2khu75.quiz.mapper;

import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.response.PageResponse;

public interface PageMapper<E,RES> {
	default PageResponse<RES> toPageResponse(Page<E> page) {
		return new PageResponse<>(page.getNumber(), page.getTotalElements(), page.getTotalPages(), page.map(this::toResponse).getContent());
	}
	
	RES toResponse(E source);
}
