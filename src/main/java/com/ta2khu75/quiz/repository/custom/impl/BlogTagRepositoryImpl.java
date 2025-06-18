package com.ta2khu75.quiz.repository.custom.impl;

import static com.ta2khu75.quiz.util.QueryDslUtil.applyIfNotNull;
import static com.ta2khu75.quiz.util.QueryDslUtil.getOrderSpecifiers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.entity.QBlogTag;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.repository.custom.BlogTagRepositoryCustom;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class BlogTagRepositoryImpl implements BlogTagRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<BlogTag> search(Search search) {
		QBlogTag blogTag = QBlogTag.blogTag;
		Pageable pageable = search.toPageable();
		List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable, blogTag);
		Predicate[] conditions = new Predicate[] {
				applyIfNotNull(search.getKeyword(), ()->blogTag.name.containsIgnoreCase(search.getKeyword()))};
		JPAQuery<BlogTag> query = queryFactory.selectFrom(blogTag).where(conditions)
				.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0])).offset(pageable.getOffset())
				.limit(pageable.getPageSize());
		// 2. Fetch kết quả
		List<BlogTag> content = query.fetch();

		// 3. Đếm tổng số bản ghi
		long total = queryFactory.select(blogTag.count()).from(blogTag).where(conditions).fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

}
