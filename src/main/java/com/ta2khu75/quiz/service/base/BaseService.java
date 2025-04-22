package com.ta2khu75.quiz.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<Repository extends JpaRepository<?,?>, Mapper> {
	protected BaseService(Repository repository, Mapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}
	protected final Repository repository;
	protected final Mapper mapper;
}
