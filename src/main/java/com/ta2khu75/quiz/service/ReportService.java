package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.request.search.ReportSearch;
import com.ta2khu75.quiz.model.response.ReportResponse;
import com.ta2khu75.quiz.service.base.CrudService;
import com.ta2khu75.quiz.service.base.SearchService;

public interface ReportService extends CrudService<ReportRequest, ReportResponse, String>, SearchService<ReportResponse, ReportSearch> {
	ReportResponse create(ReportRequest request);
	void delete(String id);
}
