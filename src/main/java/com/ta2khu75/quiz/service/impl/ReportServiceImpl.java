package com.ta2khu75.quiz.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ta2khu75.quiz.exception.InvalidDataException;
import com.ta2khu75.quiz.mapper.BlogMapper;
import com.ta2khu75.quiz.mapper.QuizMapper;
import com.ta2khu75.quiz.mapper.ReportMapper;
import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.entity.Report;
import com.ta2khu75.quiz.model.entity.id.ReportId;
import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.request.search.ReportSearch;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.ReportResponse;
import com.ta2khu75.quiz.repository.BlogRepository;
import com.ta2khu75.quiz.repository.QuizRepository;
import com.ta2khu75.quiz.repository.ReportRepository;
import com.ta2khu75.quiz.repository.account.AccountRepository;
import com.ta2khu75.quiz.service.ReportService;
import com.ta2khu75.quiz.service.base.BaseService;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImpl extends BaseService<ReportRepository, ReportMapper> implements ReportService{
	AccountRepository accountRepository;
	BlogRepository blogRepository;
	BlogMapper blogMapper;
	QuizRepository quizRepository;
	QuizMapper quizMapper;

	public ReportServiceImpl(ReportRepository repository, ReportMapper mapper, AccountRepository accountRepository,
			BlogRepository blogRepository, BlogMapper blogMapper, QuizRepository quizRepository, QuizMapper quizMapper) {
		super(repository, mapper);
		this.accountRepository = accountRepository;
		this.blogRepository = blogRepository;
		this.blogMapper = blogMapper;
		this.quizRepository = quizRepository;
		this.quizMapper = quizMapper;
	}
	private Object getTarget(String targetId,TargetType targetType) {
		switch (targetType) {
		case BLOG: {
			return FunctionUtil.findOrThrow(targetId, Blog.class, blogRepository::findById);
		}
		case QUIZ: {
			return FunctionUtil.findOrThrow(targetId, Quiz.class, quizRepository::findById);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + targetType);
		}
	}
	private ReportResponse toResponse(Report report, Object target) {
		ReportResponse response = mapper.toResponse(report);
		if(response.getTargetType().equals(TargetType.BLOG)) {
			response.setTarget(blogMapper.toResponse((Blog) target));
		}else {
			response.setTarget(quizMapper.toResponse((Quiz) target));
		}
		return response;
	}
	
	private void checkIsAuthor(Object target, TargetType targetType, String accountId) {
		String authorId;
		switch (targetType) {
		case BLOG: {
//			authorId = ((Blog) target).getAuthor().getId();
			break;
		}
		case QUIZ: {
//			authorId = ((Quiz) target).getAuthor().getId();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + targetType);
		}
//		if(authorId.equals(authorId)) {
//			throw new InvalidDataException("You can't report your own content");
//		}
	}
	
	@Override
	@Transactional
	public ReportResponse create(ReportRequest request) {
		String accountId = SecurityUtil.getIdCurrentUserLogin();
		Account account = FunctionUtil.findOrThrow(accountId, Account.class, accountRepository::findById);
		Object target=getTarget(request.getTargetId(), request.getTargetType());
		checkIsAuthor(target, request.getTargetType(), accountId);
		Report report= mapper.toEntity(request);
		report.setId(new ReportId(account.getId(), request.getTargetId()));
//		report.setAuthor(account);
		report=repository.save(report);
		return toResponse(report, target);
	}

	@Override
	public void delete(String id) {
		String accountId = SecurityUtil.getIdCurrentUserLogin();
		repository.deleteById(new ReportId(accountId, id));
	}

	@Override
	public PageResponse<ReportResponse> search(ReportSearch search) {
		Pageable pageable = Pageable.ofSize(search.getSize()).withPage(search.getPage() - 1);
		Page<Report> page = repository.search(search.getAuthorId(), search.getTargetType(), search.getReportType(),
				search.getReportStatus(), search.getFromDate(), search.getToDate(), pageable);
		PageResponse<ReportResponse> response = mapper.toPageResponse(page.map(report -> toResponse(report, getTarget(report.getId().getTargetId(), report.getTargetType()))));
		return response;
	}
	@Override
	public ReportResponse update(String id, @Valid ReportRequest request) {
		String accountId=SecurityUtil.getIdCurrentUserLogin();
		Report report= FunctionUtil.findOrThrow(new ReportId(accountId, id), Report.class, repository::findById);
		if(report.getReportStatus().equals(ReportStatus.REJECTED)) {
			throw new InvalidDataException("You can't update rejected report");
		}else {
			mapper.update(request, report);
			report=repository.save(report);
			return mapper.toResponse(report);
		}
	}
	@Override
	public ReportResponse read(String id) {
		String accountId=SecurityUtil.getIdCurrentUserLogin();
		Report report= FunctionUtil.findOrThrow(new ReportId(accountId, id), Report.class, repository::findById);
		return mapper.toResponse(report);
	}
	@Override
	public ReportResponse updateStatus(ReportId id, ReportStatus status) {
		Report report= FunctionUtil.findOrThrow(id, Report.class, repository::findById);
		report.setReportStatus(status);
		return toResponse(repository.save(report), getTarget(id.getTargetId(), report.getTargetType()));
	}
}
