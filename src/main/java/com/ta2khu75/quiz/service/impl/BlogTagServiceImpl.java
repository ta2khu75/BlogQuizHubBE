package com.ta2khu75.quiz.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.mapper.PageMapper;
import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.BlogTagRepository;
import com.ta2khu75.quiz.service.BlogTagService;
import com.ta2khu75.quiz.service.base.BaseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogTagServiceImpl implements BlogTagService {
	BlogTagRepository blogTagRepository;
//	@Override
//	public BlogTag create(String name) {
//		BlogTag blog = new BlogTag();
//		blog.setName(name);
//		return blogTagRepository.save(blog);
//	}
//	@Override
//	public List<BlogTag> readAll() {
//		return blogTagRepository.findAll();
//	}
//	@Override
//	public List<BlogTag> readAll(String keyword) {
//		return blogTagRepository.findAllByNameContainingIgnoreCase(keyword);
//	}
//	@Override
//	public Set<BlogTag> createOrReadAll(Set<String> names) {
//		Set<BlogTag> existingBlogTags = new HashSet<>(blogTagRepository.findAllByNameInIgnoreCase(names));
//		Map<String, BlogTag> existingBlogTagMap = existingBlogTags.stream()
//				.collect(Collectors.toMap(BlogTag::getName, blogTag -> blogTag));
//		List<BlogTag> newBlogTags = blogTagRepository
//				.saveAll(names.stream().map(blogTagName -> existingBlogTagMap.computeIfAbsent(blogTagName, n -> {
//					BlogTag blogTag = new BlogTag();
//					blogTag.setName(blogTag.getName());
//					blogTag.setName(blogTagName);
//					return blogTag;
//				})).toList());
//		existingBlogTags.addAll(newBlogTags);
//		return new HashSet<>(existingBlogTags);
//	}

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
