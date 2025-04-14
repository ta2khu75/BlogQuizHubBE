package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Report;
import com.ta2khu75.quiz.model.request.ReportRequest;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.ReportResponse;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, InfoMapperFactory.class})
public interface ReportMapper {
	@Mapping(target="id", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Report toEntity(ReportRequest request);
	
	@Mapping(target = "target", ignore = true)
	@Mapping(target = "info", source = "entity")
	@Mapping(target = "author", source = "author", qualifiedByName = "toAccountResponse")
	ReportResponse toResponse(Report entity);

	@Mapping(target = "page", source = "number")
	PageResponse<ReportResponse> toPageResponse(Page<ReportResponse> page);
}
