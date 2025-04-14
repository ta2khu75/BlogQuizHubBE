package com.ta2khu75.quiz.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.ta2khu75.quiz.event.NotificationEvent;
import com.ta2khu75.quiz.mapper.BlogMapper;
import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.request.BlogRequest;
import com.ta2khu75.quiz.model.request.search.BlogSearch;
import com.ta2khu75.quiz.model.response.BlogResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.AccountRepository;
import com.ta2khu75.quiz.repository.BlogRepository;
import com.ta2khu75.quiz.repository.QuizRepository;
import com.ta2khu75.quiz.service.BlogService;
import com.ta2khu75.quiz.service.BlogTagService;
import com.ta2khu75.quiz.service.base.BaseFileService;
import com.ta2khu75.quiz.service.util.FileUtil;
import com.ta2khu75.quiz.service.util.FileUtil.Folder;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

@Service
@Validated
public class BlogServiceImpl extends BaseFileService<BlogRepository, BlogMapper> implements BlogService {
	private final AccountRepository accountRepository;
	private final BlogTagService blogTagService;
	private final QuizRepository examRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public BlogServiceImpl(BlogRepository repository, BlogMapper mapper, FileUtil fileUtil,
			AccountRepository accountRepository, BlogTagService blogTagService, QuizRepository examRepository,
			ApplicationEventPublisher applicationEventPublisher) {
		super(repository, mapper, fileUtil);
		this.accountRepository = accountRepository;
		this.blogTagService = blogTagService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.examRepository = examRepository;
	}


	@Override
	@Validated({ Default.class })
	@Transactional
	public BlogResponse create(@Valid BlogRequest request, MultipartFile file) throws IOException {
		Blog blog = mapper.toEntity(request);
		Account account = FunctionUtil.findOrThrow(SecurityUtil.getCurrentUserLogin(), Account.class,
				accountRepository::findById);
		blog.setAuthor(account);
		fileUtil.saveFile(blog, file, Folder.BLOG_FOLDER, Blog::setImagePath);
		Set<BlogTag> blogTags =blogTagService.createOrReadAll(request.getBlogTags());
		blog.setBlogTags(blogTags);
		this.addQuizzes(blog, request);
		return this.save(blog);
	}

	@Override
	@Validated({ Default.class })
	@Transactional
	public BlogResponse update(String id, @Valid BlogRequest request, MultipartFile file) throws IOException {
		Blog blog = FunctionUtil.findOrThrow(id, Blog.class, repository::findById);
		mapper.update(request, blog);
		fileUtil.saveFile(blog, file, Folder.BLOG_FOLDER, Blog::setImagePath);
		Set<BlogTag> blogTags = blogTagService.createOrReadAll(request.getBlogTags());
		blog.setBlogTags(blogTags);
		// Xóa các Exams cũ
		blog.getQuizzes().forEach(exam -> exam.setBlog(null));
		blog.getQuizzes().clear();
		this.addQuizzes(blog, request);
		return save(repository.save(blog));
	}

	@Override
	public BlogResponse read(String id) {
		return save(FunctionUtil.findOrThrow(id, Blog.class, repository::findById));
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public BlogResponse readDetail(String id) {
		Blog blog = FunctionUtil.findOrThrow(id, Blog.class, repository::findById);
		blog.setViewCount(blog.getViewCount() + 1);
		return mapper.toDetailsResponse(repository.save(blog));
	}

	@Override
	public PageResponse<BlogResponse> search(BlogSearch blogSearchRequest) {
		if(!SecurityUtil.isAuthor(blogSearchRequest.getAuthorId())) blogSearchRequest.setAccessModifier(AccessModifier.PUBLIC);
		Pageable pageable = Pageable.ofSize(blogSearchRequest.getSize()).withPage(blogSearchRequest.getPage() - 1);
		return mapper.toPageResponse(repository.searchBlog(blogSearchRequest.getBlogTagNames(),
				blogSearchRequest.getKeyword(), blogSearchRequest.getAuthorId(),
				blogSearchRequest.getMinView(), blogSearchRequest.getMaxView(), blogSearchRequest.getAccessModifier(),
				pageable));
	}

	@Override
	public Long countByAuthorEmail(String authorEmail) {
		return repository.countByAuthorEmail(authorEmail);
	}

	@Override
	public Long countByAuthorIdAndAccessModifier(String authorId, AccessModifier accessModifier) {
		return repository.countByAuthorIdAndAccessModifier(authorId, accessModifier);
	}
	private void addQuizzes( Blog blog, BlogRequest request) {
		Set<String> quizIds=request.getQuizIds();
		if (quizIds != null && !quizIds.isEmpty()) {
			List<Quiz> quizzes= examRepository.findAllById(quizIds);
			for (Quiz quiz: quizzes) {
				blog.addQuiz(quiz); // Đồng bộ hai chiều
			}
		}
		
	}

	private BlogResponse save(Blog blog) {
		Blog blogSaved=repository.save(blog);
		applicationEventPublisher.publishEvent(new NotificationEvent(this, blog.getId(), TargetType.BLOG));
		return mapper.toResponse(blogSaved);
	}
}
