package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;

import com.ta2khu75.quiz.model.request.RoleRequest;
import com.ta2khu75.quiz.model.response.RoleResponse;
import com.ta2khu75.quiz.model.entity.Permission;
import com.ta2khu75.quiz.model.entity.Role;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "permissions", ignore = true)
	@Mapping(target = "accountStatuses", ignore = true)
	Role toEntity(RoleRequest request);
	
	@Named("toRoleResponse")
	@Mapping(target = "permissionIds", ignore = true)
	RoleResponse toResponse(Role role);

	@Mapping(target = "permissionIds", source = "permissions")
	RoleResponse toDetailResponse(Role role);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "permissions", ignore = true)
	@Mapping(target = "accountStatuses", ignore = true)
	void update(RoleRequest request, @MappingTarget Role role);
	
	default Long map(Permission permission) {	
		if(permission == null) {
			return null;
		}
		return permission.getId();
	}
}
