package com.ta2khu75.quiz.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.BlogTagRepository;
import com.ta2khu75.quiz.service.BlogTagService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogTagServiceImpl implements BlogTagService {
	BlogTagRepository blogTagRepository;

	@Override
	public PageResponse<BlogTag> search(Search search) {
		Page<BlogTag> page = blogTagRepository.search(search);
		return new PageResponse<>(page.getNumber(), page.getSize(), page.getTotalPages(), page.getContent());
}

	@Override
	public void delete(Long id) {
		blogTagRepository.deleteById(id);	
	}
}
