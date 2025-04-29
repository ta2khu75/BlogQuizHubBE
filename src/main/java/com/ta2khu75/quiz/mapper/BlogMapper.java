package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.request.BlogRequest;
import com.ta2khu75.quiz.model.response.BlogResponse;
import com.ta2khu75.quiz.model.response.PageResponse;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { QuizMapper.class, AccountMapper.class })
public interface BlogMapper extends PageMapper<Blog, BlogResponse>, InfoMapper<Blog, BlogResponse> {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "viewCount", ignore = true)
	@Mapping(target = "tags", ignore = true)
	Blog toEntity(BlogRequest blogResponse);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "viewCount", ignore = true)
	@Mapping(target = "tags", ignore = true)
	void update(BlogRequest blogResponse, @MappingTarget Blog blog);

	@Named("toBlogResponse")
	@Mapping(target = "commentCount", expression = "java(blog.getComments().size())")
//	@Mapping(target = "commentCount", source = "comments", qualifiedByName = "commentCount")
//	@Mapping(target = "info", source = "blog", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "content", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	BlogResponse toResponse(Blog blog);

	@Named("toBlogDetailsResponse")
	@Mapping(target = "commentCount", expression = "java(blog.getComments().size())")
//	@Mapping(target = "commentCount", source = "comments", qualifiedByName = "commentCount")
//	@Mapping(target = "info", source = "blog", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "quizzes", source = "quizzes", qualifiedByName = "toQuizResponse")
	BlogResponse toDetailsResponse(Blog blog);

	@Mapping(target = "page", source = "number")
	@Mapping(target = "content", source = "content", qualifiedByName = "toBlogResponse")
	PageResponse<BlogResponse> toPageResponse(Page<Blog> blogs);
}
