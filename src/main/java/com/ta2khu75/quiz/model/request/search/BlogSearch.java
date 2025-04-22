package com.ta2khu75.quiz.model.request.search;

import java.util.List;

import com.ta2khu75.quiz.model.AccessModifier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlogSearch extends Search {
	private List<String> tagNames;
	private Integer minView;
	private Integer maxView;
	private String authorId;
	private AccessModifier accessModifier;
}
