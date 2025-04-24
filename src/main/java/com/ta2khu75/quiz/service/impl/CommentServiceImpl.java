package com.ta2khu75.quiz.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ta2khu75.quiz.mapper.CommentMapper;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.Comment;
import com.ta2khu75.quiz.model.request.CommentRequest;
import com.ta2khu75.quiz.model.response.CommentResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.BlogRepository;
import com.ta2khu75.quiz.repository.CommentRepository;
import com.ta2khu75.quiz.repository.account.AccountRepository;
import com.ta2khu75.quiz.service.CommentService;
import com.ta2khu75.quiz.service.base.BaseService;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

@Service
public class CommentServiceImpl extends BaseService<CommentRepository, CommentMapper> implements CommentService {
	private final BlogRepository blogRepository;

	public CommentServiceImpl(CommentRepository repository, CommentMapper mapper, BlogRepository blogRepository,
			AccountRepository accountRepository) {
		super(repository, mapper);
		this.blogRepository = blogRepository;
	}

	@Override
	@Transactional
	public CommentResponse create(CommentRequest request) {
		Comment comment = mapper.toEntity(request);
		comment.setAuthor(SecurityUtil.getCurrentProfile());
		comment.setBlog(FunctionUtil.findOrThrow(request.getBlogId(), Blog.class, blogRepository::findById));
		return mapper.toResponse(repository.save(comment));
	}

	@Override
	public CommentResponse update(String id, CommentRequest request) {
		Comment comment = FunctionUtil.findOrThrow(id, Comment.class, repository::findById);
		mapper.update(request, comment);
		return mapper.toResponse(repository.save(comment));
	}

	@Override
	public CommentResponse read(String id) {
		return mapper.toResponse(FunctionUtil.findOrThrow(id, Comment.class, repository::findById));
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public PageResponse<CommentResponse> readPageByBlogId(String blogId, Pageable pageable) {
		Page<Comment> commentPage= repository.findByBlogId(blogId, pageable);
		return mapper.toPageResponse(commentPage);
	}

}
