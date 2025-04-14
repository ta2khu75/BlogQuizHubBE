package com.ta2khu75.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ta2khu75.quiz.anotation.EndpointMapping;
import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.request.search.ReportSearch;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.ReportResponse;
import com.ta2khu75.quiz.service.ReportService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${app.api-prefix}/reports")
public class ReportController extends BaseController<ReportService> {

	public ReportController(ReportService service) {
		super(service);
	}

	@PostMapping
	@EndpointMapping(name = "Create report")
	public ResponseEntity<ReportResponse> create(@Valid @RequestBody ReportRequest request) {
		return ResponseEntity.ok(service.create(request));
	}

	@DeleteMapping("{id}")
	@EndpointMapping(name = "Delete report")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping()
	@EndpointMapping(name = "Search report")
	public ResponseEntity<PageResponse<ReportResponse>> get(@ModelAttribute ReportSearch search) {
		return ResponseEntity.ok(service.search(search));
	}
	
}
