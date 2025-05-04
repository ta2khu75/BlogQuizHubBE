package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Report;
import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.ReportResponse;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface ReportMapper extends BaseMapper<Report, ReportResponse> {
	@Mapping(target="id", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "reportStatus", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Report toEntity(ReportRequest request);
	
	@Mapping(target = "target", ignore = true)
//	@Mapping(target = "info", source = "entity")
	@Mapping(target = "author", source = "author", qualifiedByName = "toProfileResponse")
	ReportResponse toResponse(Report entity);
	
	@Mapping(target="id", ignore = true)
	@Mapping(target = "reportStatus", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target="author", ignore = true)
	void update(ReportRequest request, @MappingTarget Report entity);

	@Mapping(target = "page", source = "number")
	PageResponse<ReportResponse> toPageResponse(Page<ReportResponse> page);
}
