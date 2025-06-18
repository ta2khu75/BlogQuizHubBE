package com.ta2khu75.quiz.repository.custom.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ta2khu75.quiz.model.entity.Blog;
import com.ta2khu75.quiz.model.entity.QBlog;
import com.ta2khu75.quiz.model.request.search.BlogSearch;
import com.ta2khu75.quiz.repository.custom.BlogRepositoryCustom;
import static com.ta2khu75.quiz.util.QueryDslUtil.applyIfNotNull;
import static com.ta2khu75.quiz.util.QueryDslUtil.getOrderSpecifiers;

import java.util.List;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class BlogRepositoryImpl implements 	BlogRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	@Override
	public Page<Blog> search(BlogSearch search) {
		QBlog blog = QBlog.blog;
		Pageable pageable = search.toPageable();
		List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable, blog);
		Predicate[] conditions = new Predicate[] {
				applyIfNotNull(search.getKeyword(), () -> blog.title.containsIgnoreCase(search.getKeyword())),
				applyIfNotNull(search.getTagIds(), () -> blog.tags.any().id.in(search.getTagIds())),
				applyIfNotNull(search.getMinView(),()->blog.viewCount.goe(search.getMinView())),
				applyIfNotNull(search.getMaxView(),()->blog.viewCount.loe(search.getMinView())),
				applyIfNotNull(search.getAccessModifier(),()->blog.accessModifier.eq(search.getAccessModifier())),
				applyIfNotNull(search.getAuthorId(), () -> blog.author.id.eq(search.getAuthorId())),
		};
		JPAQuery<Blog> query = queryFactory.selectFrom(blog).where(conditions).orderBy(orderSpecifiers.toArray(new OrderSpecifier[0])).offset(pageable.getOffset())
				.limit(pageable.getPageSize());

		List<Blog> content = query.fetch();

		// 3. Đếm tổng số bản ghi
		long total = queryFactory.select(blog.count()).from(blog).where(conditions).fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

}
