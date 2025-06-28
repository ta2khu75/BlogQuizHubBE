package com.ta2khu75.quiz.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.ta2khu75.quiz.event.NotificationEvent;
import com.ta2khu75.quiz.mapper.BlogMapper;
import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.request.BlogRequest;
import com.ta2khu75.quiz.model.request.search.BlogSearch;
import com.ta2khu75.quiz.model.response.BlogResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.BlogRepository;
import com.ta2khu75.quiz.repository.BlogTagRepository;
import com.ta2khu75.quiz.repository.QuizRepository;
import com.ta2khu75.quiz.service.BlogService;
import com.ta2khu75.quiz.service.base.BaseFileService;
import com.ta2khu75.quiz.service.util.FileUtil;
import com.ta2khu75.quiz.util.Base62;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SaltedType;
import com.ta2khu75.quiz.util.SecurityUtil;
import com.ta2khu75.quiz.util.SqidsUtil;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

@Service
@Validated
public class BlogServiceImpl extends BaseFileService<BlogRepository, BlogMapper> implements BlogService {
	private final QuizRepository quizRepository;
	private final BlogTagRepository blogTagRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public BlogServiceImpl(BlogRepository repository, BlogMapper mapper, FileUtil fileUtil,
			QuizRepository quizRepository, BlogTagRepository blogTagRepository,
			ApplicationEventPublisher applicationEventPublisher) {
		super(repository, mapper, fileUtil);
		this.applicationEventPublisher = applicationEventPublisher;
		this.quizRepository = quizRepository;
		this.blogTagRepository = blogTagRepository;
	}

	@Override
	@Validated({ Default.class })
	@Transactional
	public BlogResponse create(@Valid BlogRequest request, MultipartFile file) throws IOException {
		Blog blog = mapper.toEntity(request);
		Set<Quiz> quizzes = request.getQuizIds().stream().map(quizId -> quizRepository.getReferenceById(SqidsUtil.decodeWithSalt(quizId, SaltedType.QUIZ)))
				.collect(Collectors.toSet());
		blog.setAuthor(SecurityUtil.getCurrentProfile());
		blog.setTags(this.getTags(request.getTags()));
		blog.setQuizzes(quizzes);
		return this.save(blog);
	}
	private Set<BlogTag> getTags(Set<BlogTag> tags){
	return 	tags.stream().map(tag -> {
			if (tag.getId() != null) {
				return FunctionUtil.findOrThrow(tag.getId(), BlogTag.class, blogTagRepository::findById);
			} else {
				return new BlogTag(tag.getName());
			}
		}).collect(Collectors.toSet());
	}

	@Override
	@Validated({ Default.class })
	@Transactional
	public BlogResponse update(String id, @Valid BlogRequest request, MultipartFile file) throws IOException {
		Blog blog = FunctionUtil.findOrThrow(Base62.decodeWithSalt(id, SaltedType.BLOG), Blog.class, repository::findById);
		mapper.update(request, blog);
		blog.setTags(this.getTags(request.getTags()));
		this.updateBlogQuizzes(blog, request.getQuizIds().stream().map(quizId->Base62.decodeWithSalt(quizId, SaltedType.QUIZ)).collect(Collectors.toSet()));
		return this.save(blog);
	}

	private void updateBlogQuizzes(Blog blog, Set<Long> quizIds) {
		for (Quiz quiz : new HashSet<>(blog.getQuizzes())) {
			if (!quizIds.contains(quiz.getId())) {
				blog.removeQuiz(quiz);
			}
		}
		quizIds.forEach(quizId -> {
			Quiz quiz = quizRepository.getReferenceById(quizId);
			blog.addQuiz(quiz);
		});
	}

	@Override
	public BlogResponse read(String id) {
		return save(FunctionUtil.findOrThrow(decodeId(id, SaltedType.BLOG), Blog.class, repository::findById));
	}

	@Override
	public void delete(String id) {
		repository.deleteById(decodeId(id, SaltedType.BLOG));
	}
	private Long decodeId(String id, SaltedType saltedType) {
		return Base62.decodeWithSalt(id, saltedType);
	}
	@Override
	public BlogResponse readDetail(String id) {
		Blog blog = FunctionUtil.findOrThrow(decodeId(id, SaltedType.BLOG), Blog.class, repository::findById);
		blog.setViewCount(blog.getViewCount() + 1);
		return mapper.toDetailsResponse(repository.save(blog));
	}

	@Override
	public PageResponse<BlogResponse> search(BlogSearch blogSearchRequest) {
		if (!SecurityUtil.isAuthor(blogSearchRequest.getAuthorId()))
			blogSearchRequest.setAccessModifier(AccessModifier.PUBLIC);
		return mapper.toPageResponse(repository.search(blogSearchRequest));
	}

	private BlogResponse save(Blog blog) {
		Blog blogSaved = repository.save(blog);
		applicationEventPublisher.publishEvent(new NotificationEvent(this, blog.getId(), TargetType.BLOG));
		return mapper.toResponse(blogSaved);
	}

	@Override
	public List<BlogResponse> readAllByAuthorIdAndKeywork(Long authorId, String keyword) {
		return repository.findAllByAuthorIdAndTitleContainingIgnoreCase(authorId, keyword).stream()
				.map(mapper::toResponse).toList();
	}
}
