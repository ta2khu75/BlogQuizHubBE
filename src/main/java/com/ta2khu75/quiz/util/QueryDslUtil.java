package com.ta2khu75.quiz.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class QueryDslUtil {

	public static BooleanExpression applyIfNotNull(Object data, Supplier<BooleanExpression> expressionSupplier) {
		return data != null ? expressionSupplier.get() : null;
	}

	public static BooleanExpression applyIfNotEmpty(String data, Supplier<BooleanExpression> expressionSupplier) {
	    return (data != null && !data.trim().isEmpty()) ? expressionSupplier.get() : null;
	}

	public static BooleanExpression applyIfNotEmpty(Collection<?> data, Supplier<BooleanExpression> expressionSupplier) {
	    return (data != null && !data.isEmpty()) ? expressionSupplier.get() : null;
	}
	public static <T extends EntityPathBase<?>> List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable,
			T entity) {
		List<OrderSpecifier<?>> orders = new ArrayList<>();
		PathBuilder<?> pathBuilder = new PathBuilder<>(entity.getType(), entity.getMetadata());

		for (Sort.Order order : pageable.getSort()) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			orders.add(
					new OrderSpecifier<>(direction, pathBuilder.getComparable(order.getProperty(), Comparable.class)));
		}
		return orders;
	}
}
