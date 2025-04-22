package com.ta2khu75.quiz.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.service.util.FileUtil;

public abstract class BaseFileService<Repository extends JpaRepository<?,?>, Mapper> extends BaseService<Repository, Mapper> {

	protected final FileUtil fileUtil;

	protected BaseFileService(Repository repository, Mapper mapper, FileUtil fileUtil) {
		super(repository, mapper);
		this.fileUtil = fileUtil;
	}
}