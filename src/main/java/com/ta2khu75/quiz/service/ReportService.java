package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.entity.id.ReportId;
import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.request.search.ReportSearch;
import com.ta2khu75.quiz.model.response.ReportResponse;
import com.ta2khu75.quiz.service.base.CrudService;
import com.ta2khu75.quiz.service.base.SearchService;

public interface ReportService extends CrudService<ReportRequest, ReportResponse, String>, SearchService<ReportResponse, ReportSearch> {
	ReportResponse create(ReportRequest request);
	ReportResponse updateStatus(ReportId id, ReportStatus status);
	void delete(String id);
}
