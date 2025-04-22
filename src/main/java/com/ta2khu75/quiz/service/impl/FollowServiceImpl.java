package com.ta2khu75.quiz.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ta2khu75.quiz.exception.ExistingException;
import com.ta2khu75.quiz.exception.InvalidDataException;
import com.ta2khu75.quiz.mapper.FollowMapper;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.Follow;
import com.ta2khu75.quiz.model.entity.id.FollowId;
import com.ta2khu75.quiz.model.response.FollowResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.repository.FollowRepository;
import com.ta2khu75.quiz.repository.account.AccountRepository;
import com.ta2khu75.quiz.service.FollowService;
import com.ta2khu75.quiz.service.base.BaseService;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

@Service
public class FollowServiceImpl extends BaseService<FollowRepository, FollowMapper> implements FollowService {
	private final AccountRepository accountRepository;

	public FollowServiceImpl(FollowRepository repository, FollowMapper mapper, AccountRepository accountRepository) {
		super(repository, mapper);
		this.accountRepository = accountRepository;
	}

	@Override
	@Transactional
	public FollowResponse create(String followingId) {
		String followerId = SecurityUtil.getIdCurrentUserLogin();
		if(followingId.equals(followerId)) {
			throw new InvalidDataException("Cannot follow yourself");
		}
		Optional<Follow> existingFollow = repository.findById(new FollowId(followerId, followingId));
		if (existingFollow.isPresent()) {
			throw new ExistingException("Already following this user");
		}
		Account following = FunctionUtil.findOrThrow(followingId, Account.class, accountRepository::findById);
		Account follower = FunctionUtil.findOrThrow(followerId, Account.class, accountRepository::findById);
		Follow follow = new Follow();
		follow.setId(new FollowId(follower.getId(), following.getId()));
//		follow.setFollower(follower);
//		follow.setFollowing(following);
		return mapper.toResponse(repository.save(follow));
	}

	@Override
	@Transactional
	public void delete(String followingId) {
		String followerId = SecurityUtil.getIdCurrentUserLogin();
		repository.deleteById(new FollowId(followerId, followingId));
	}

	@Override
	public FollowResponse read(String followingId) {
		String followerId = SecurityUtil.getIdCurrentUserLogin();
		Follow follow = repository.findById(new FollowId(followerId, followingId)).orElse(null);
		return follow == null ? null : mapper.toResponse(follow);
	}

	@Override
	public PageResponse<FollowResponse> readPage(String followingId, Pageable pageable) {
		return mapper.toPageResponse(repository.findByFollowingId(followingId, pageable));
	}

}
