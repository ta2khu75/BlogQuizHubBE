package com.ta2khu75.quiz.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ta2khu75.quiz.anotation.EndpointMapping;
import com.ta2khu75.quiz.anotation.SnakeCaseModelAttribute;
import com.ta2khu75.quiz.model.entity.BlogTag;
import com.ta2khu75.quiz.model.request.search.Search;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.service.BlogTagService;

@RestController
@RequestMapping("${app.api-prefix}/blog-tags")
public class BlogTagController extends BaseController<BlogTagService> {

	public BlogTagController(BlogTagService service) {
		super(service);
	}

	@GetMapping
	@EndpointMapping(name = "Search blog tags")
	public ResponseEntity<PageResponse<BlogTag>> search(@SnakeCaseModelAttribute Search search) {
		return ResponseEntity.ok(service.search(search));
	}

	@DeleteMapping("/{id}")
	@EndpointMapping(name = "Delete blog tag")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
