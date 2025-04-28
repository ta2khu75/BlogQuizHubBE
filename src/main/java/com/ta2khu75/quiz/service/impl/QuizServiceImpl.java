package com.ta2khu75.quiz.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.ta2khu75.quiz.model.request.QuizRequest;
import com.ta2khu75.quiz.model.request.search.QuizSearch;
import com.ta2khu75.quiz.model.response.QuizResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.event.NotificationEvent;
import com.ta2khu75.quiz.exception.NotFoundException;
import com.ta2khu75.quiz.mapper.QuizMapper;
import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.repository.QuizRepository;
import com.ta2khu75.quiz.service.QuizService;
import com.ta2khu75.quiz.service.base.BaseFileService;
import com.ta2khu75.quiz.service.util.FileUtil;
import com.ta2khu75.quiz.service.util.FileUtil.Folder;
import com.ta2khu75.quiz.util.SecurityUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Validated
public class QuizServiceImpl extends BaseFileService<QuizRepository, QuizMapper> implements QuizService {
	private final ApplicationEventPublisher applicationEventPublisher;

	public QuizServiceImpl(QuizRepository repository, QuizMapper mapper, FileUtil fileUtil,
			ApplicationEventPublisher applicationEventPublisher) {
		super(repository, mapper, fileUtil);
		this.applicationEventPublisher = applicationEventPublisher;
	}

	private Quiz findById(String id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Could not found quiz with id: " + id));
	}

	private QuizResponse save(Quiz quiz) {
		Quiz quizSaved = repository.save(quiz);
		applicationEventPublisher.publishEvent(new NotificationEvent(this, quizSaved.getId(), TargetType.QUIZ));
		return mapper.toResponse(quizSaved);
	}

	@Override
	@Transactional
	@Validated(value = { Default.class })
	public QuizResponse create(@Valid QuizRequest quizRequest, MultipartFile file) throws IOException {
		Quiz quiz = mapper.toEntity(quizRequest);
		quiz.setAuthor(SecurityUtil.getCurrentProfile());
		fileUtil.saveFile(quiz, file, Folder.QUIZ_FOLDER, Quiz::setImagePath);
		return this.save(quiz);
//		Quiz quizSaved = repository.save(quiz);
//		applicationEventPublisher.publishEvent(new NotificationEvent(this, quizSaved.getId(), TargetType.QUIZ));
//		return mapper.toResponse(quizSaved);
//		Quiz quiz = mapper.toEntity(quizRequest);
//		fileUtil.saveFile(quiz, file, Folder.QUIZ_FOLDER, Quiz::setImagePath);
//		quiz.setCategory(this.findExamCategoryById(quizRequest.getCategoryId()));
//		quiz.setAuthor(SecurityUtil.getCurrentProfile());
//		if(quizRequest.getBlogId() != null) {
//			
//		}
//		setBlog(quiz, quizRequest.getBlogId());
//		Quiz quizSaved = repository.save(quiz);
//		quizRequest.getQuestions().forEach(question-> {
//			question.setQuiz(quizSaved);
//			questionService.create(question);
//		});
//		applicationEventPublisher.publishEvent(new NotificationEvent(this, quizSaved.getId(), TargetType.QUIZ));
//		return mapper.toResponse(repository.save(quizSaved));
	}

	@Override
	@Transactional
	@Validated(value = { Default.class })
	public QuizResponse update(String id, @Valid QuizRequest quizRequest, MultipartFile file) throws IOException {
		Quiz quiz = this.findById(id);

//		Map<Long, QuestionRequest> requestQuestionMap = quizRequest.getQuestions().stream().filter(question -> question.getId() != null)
//				.collect(Collectors.toMap(QuestionRequest::getId, Function.identity()));
//		if (!quiz.isCompleted()) {
//			Iterator<Question> questionIterable = quiz.getQuestions().iterator();
//			while (questionIterable.hasNext()) {
//				Question existingQuestion = questionIterable.next();
//				QuestionRequest questionRequest= requestQuestionMap.get(existingQuestion.getId());
//				if (questionRequest!= null) {
//					questionService.update(questionRequest.getId(), questionRequest);
//				} else {
//					questionIterable.remove();
//					questionService.delete(existingQuestion.getId());
//				}
//			}
//			quizRequest.getQuestions().stream().filter(question -> question.getId() == null).forEach(question-> {
//				question.setQuiz(quiz);
//				questionService.create(question);
//			});
//		}
//		mapper.update(quizRequest, quiz);
//		setBlog(quiz, quizRequest.getBlogId());
		fileUtil.saveFile(quiz, file, Folder.QUIZ_FOLDER, Quiz::setImagePath);
		mapper.update(quizRequest, quiz);
//		if (quiz.getCategory().getId().equals(quizRequest.getCategoryId()))
//			quiz.setCategory(this.findExamCategoryById(quizRequest.getCategoryId()));
		// mapper.toResponse(repository.save(quiz));
		return this.save(quiz);
	}

	@Override
	public QuizResponse read(String id) {
		Quiz quiz = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not found quiz with id: " + id));
		return mapper.toDetailResponse(quiz);
	}

	@Override
	@Transactional
	public void delete(String id) {
		Quiz quiz = this.findById(id);
		if (quiz.isCompleted()) {
			quiz.setDeleted(true);
			repository.save(quiz);
		} else {
			repository.delete(quiz);
		}
	}

	@Override
	public QuizResponse readDetail(String id) {
		Quiz quiz = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not found quiz with id: " + id));
		return mapper.toQuizQuestionDetailResponse(quiz);
	}

	@Override
	public PageResponse<QuizResponse> search(QuizSearch search) {
		if (!SecurityUtil.isAuthor(search.getAuthorId()))
			search.setAccessModifier(AccessModifier.PUBLIC);
		Pageable pageable = Pageable.ofSize(search.getSize()).withPage(search.getPage() - 1);
		return mapper.toPageResponse(repository.search(search.getKeyword(), search.getAuthorId(),
				search.getQuizCategoryIds(), search.getQuizLevels(), search.getCompleted(), search.getMinDuration(),
				search.getMaxDuration(), search.getAccessModifier(), pageable));
	}

	@Override
	public List<QuizResponse> readAllByAuthorIdAndKeywork(Long authorId, String keyword) {
		return repository.findByAuthorIdAndTitleContainingIgnoreCaseAndBlogIsNull(authorId, keyword).stream()
				.map(mapper::toResponse).collect(Collectors.toList());
	}

//	@Override
//	public Long countByAuthorEmail(String authorEmail) {
//		return repository.countByAuthorEmail(authorEmail);
//	}
//
//	@Override
//	public Long countByAuthorIdAndAccessModifier(String authorId, AccessModifier accessModifier) {
//		return repository.countByAuthorIdAndAccessModifier(authorId, accessModifier);
//	}
//
//	@Override
//	public List<QuizResponse> myReadAllById(List<String> ids) {
//		return repository.findAllById(ids).stream().map(mapper::toResponse).collect(Collectors.toList());
//	}
//
//	@Override
//	public PageResponse<QuizResponse> mySearchExamNull(String keyword, Pageable pageable) {
//		String accountId = SecurityUtil.getCurrentUserLogin();
//		Page<Quiz> response = repository.findByAuthorIdAndTitleContainingAndBlogIdIsNull(accountId, keyword, pageable);
//		return mapper.toPageResponse(response);
//	}
}
