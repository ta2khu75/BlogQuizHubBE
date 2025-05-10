package com.ta2khu75.quiz.repository.custom.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.QAccount;
import com.ta2khu75.quiz.model.request.search.AccountSearch;
import com.ta2khu75.quiz.repository.custom.AccountRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Account> search(AccountSearch search, Pageable pageable) {
		QAccount account = QAccount.account;
		JPAQuery<Account> query = queryFactory.selectFrom(account).where(
				applyIfNotNull(search.getRoleId(), ()->account.status.role.id.eq(search.getRoleId())),
				applyIfNotNull(search.getEnabled(),()-> account.status.enabled.eq(search.getEnabled())),
				applyIfNotNull(search.getNonLocked(),()-> account.status.nonLocked.eq(search.getNonLocked())),
				applyIfNotNull(search.getCreatedFrom(),()-> account.createdAt.goe(search.getCreatedFrom())),
				applyIfNotNull(search.getCreatedTo(),()-> account.createdAt.loe(search.getCreatedTo()))
				).offset(pageable.getOffset())
				.limit(pageable.getPageSize());
		// 2. Fetch kết quả
		List<Account> content = query.fetch();

		// 3. Đếm tổng số bản ghi
		long total = queryFactory.select(account.count()).from(account)
				.where(
				applyIfNotNull(search.getRoleId(), ()->account.status.role.id.eq(search.getRoleId())),
				applyIfNotNull(search.getEnabled(),()-> account.status.enabled.eq(search.getEnabled())),
				applyIfNotNull(search.getNonLocked(),()-> account.status.nonLocked.eq(search.getNonLocked())),
				applyIfNotNull(search.getCreatedFrom(),()-> account.createdAt.goe(search.getCreatedFrom())),
				applyIfNotNull(search.getCreatedTo(),()-> account.createdAt.loe(search.getCreatedTo()))
				).fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression applyIfNotNull(Object data,  Supplier<BooleanExpression> expressionSupplier) {
		return data != null ? expressionSupplier.get() : null;
	}

}
