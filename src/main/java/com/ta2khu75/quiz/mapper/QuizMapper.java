package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.request.QuizRequest;
import com.ta2khu75.quiz.model.response.BlogResponse;
import com.ta2khu75.quiz.model.response.QuizResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.entity.QuizCategory;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { InfoMapper.class, QuestionMapper.class, AccountMapper.class })
public interface QuizMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "imagePath", ignore = true)
	@Mapping(target = "results", ignore = true)
	@Mapping(target = "blog", source = "blogId")
	@Mapping(target = "category", source = "categoryId")
	@Mapping(target = "questions", source = "questions", qualifiedByName = "toQuestion")
	Quiz toEntity(QuizRequest request);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "imagePath", ignore = true)
	@Mapping(target = "results", ignore = true)
	@Mapping(target = "blog", source = "blogId")
	@Mapping(target = "category", source = "categoryId")
	@Mapping(target = "questions", source = "questions", qualifiedByName = "toQuestion")
	void update(QuizRequest request, @MappingTarget Quiz quiz);

	default QuizCategory makeCategory(Long categoryId) {
		if (categoryId == null) {
			return null;
		}
		QuizCategory category = new QuizCategory();
		category.setId(categoryId);
		return category;
	}

	default Blog makeBlog(String blogId) {
		if (blogId == null) {
			return null;
		}
		Blog blog = new Blog();
		blog.setId(blogId);
		return blog;
	}

	@Named("toQuizResponse")
	@Mapping(target = "info", source = "quiz", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "blog", source = "blog")
	@Mapping(target = "questions", ignore = true)
	QuizResponse toResponse(Quiz quiz);

	@Mapping(target = "commentCount", source = "comments")
	@Mapping(target = "info", source = "blog", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "content", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	BlogResponse toResponse(Blog blog);

	@Named("toQuizDetailResponse")
	@Mapping(target = "questions", source = "questions", qualifiedByName = "toQuestionDetailResponse")
	@Mapping(target = "info", source = "quiz", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "blog", source = "blog")
	QuizResponse toDetailResponse(Quiz quiz);

	@Named("toQuizQuestionDetailResponse")
	@Mapping(target = "questions", source = "questions", qualifiedByName = "toQuestionAnswerDetailResponse")
	@Mapping(target = "info", source = "quiz", qualifiedByName = "toInfoResponse")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	@Mapping(target = "blog", source = "blog")
	QuizResponse toQuizQuestionDetailResponse(Quiz quiz);

	@Mapping(target = "page", source = "number")
	@Mapping(target = "content", source = "content", qualifiedByName = "toQuizResponse")
	PageResponse<QuizResponse> toPageResponse(Page<Quiz> page);
}
