package com.ta2khu75.quiz.repository.custom.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ta2khu75.quiz.model.entity.QQuiz;
import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.request.search.QuizSearch;
import com.ta2khu75.quiz.repository.custom.QuizRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	@Override
	public Page<Quiz> search(QuizSearch search, Pageable pageable) {
		QQuiz quiz = QQuiz.quiz;
		  Predicate[] conditions = new Predicate[] {
			        applyIfNotNull(search.getKeyword(), () -> quiz.title.contains(search.getKeyword())),
			        applyIfNotNull(search.getCategories(), () -> quiz.category.id.in(search.getCategories())),
			        applyIfNotNull(search.getLevels(), () -> quiz.level.in(search.getLevels())),
			        applyIfNotNull(search.getCompleted(), () -> quiz.completed.eq(search.getCompleted())),
			        applyIfNotNull(search.getMinDuration(), () -> quiz.duration.goe(search.getMinDuration())),
			        applyIfNotNull(search.getMaxDuration(), () -> quiz.duration.loe(search.getMaxDuration())),
			        applyIfNotNull(search.getAccessModifier(), () -> quiz.accessModifier.eq(search.getAccessModifier())),
			        applyIfNotNull(search.getAuthorId(), () -> quiz.author.id.eq(search.getAuthorId()))
			    };
		JPAQuery<Quiz> query=queryFactory.selectFrom(quiz).where(conditions).offset(pageable.getOffset())
				.limit(pageable.getPageSize());
		List<Quiz> content = query.fetch();
		
		long total = queryFactory.select(quiz.count()).from(quiz)
				.where(conditions).fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

//	@Override
//	public Page<Account> search(AccountSearch search, Pageable pageable) {
//		QAccount account = QAccount.account;
//		JPAQuery<Account> query = queryFactory.selectFrom(account).where(
//				applyIfNotNull(search.getRoleId(), ()->account.status.role.id.eq(search.getRoleId())),
//				applyIfNotNull(search.getEnabled(),()-> account.status.enabled.eq(search.getEnabled())),
//				applyIfNotNull(search.getNonLocjed(),()-> account.status.nonLockked.eq(search.getNonLocked())),
//				applyIfNotNull(search.getCreatedFrom(),()-> account.createdAt.goe(search.getCreatedFrom())),
//				applyIfNotNull(search.getCreatedTo(),()-> account.createdAt.loe(search.getCreatedTo()))
//				).offset(pageable.getOffset())
//				.limit(pageable.getPageSize());
//		// 2. Fetch kết quả
//		List<Account> content = query.fetch();
//
//		// 3. Đếm tổng số bản ghi
//		long total = queryFactory.select(account.count()).from(account)
//				.where(
//				applyIfNotNull(search.getRoleId(), ()->account.status.role.id.eq(search.getRoleId())),
//				applyIfNotNull(search.getEnabled(),()-> account.status.enabled.eq(search.getEnabled())),
//				applyIfNotNull(search.getNonLocked(),()-> account.status.nonLocked.eq(search.getNonLocked())),
//				applyIfNotNull(search.getCreatedFrom(),()-> account.createdAt.goe(search.getCreatedFrom())),
//				applyIfNotNull(search.getCreatedTo(),()-> account.createdAt.loe(search.getCreatedTo()))
//				).fetchOne();
//		return new PageImpl<>(content, pageable, total);
//	}
//
	private BooleanExpression applyIfNotNull(Object data,  Supplier<BooleanExpression> expressionSupplier) {
		return data != null ? expressionSupplier.get() : null;
	}

}
