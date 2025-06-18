package com.ta2khu75.quiz.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.model.request.account.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountRequest;
import com.ta2khu75.quiz.model.request.account.AccountStatusRequest;
import com.ta2khu75.quiz.model.request.search.AccountSearch;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;
import com.ta2khu75.quiz.model.response.account.AccountStatusResponse;
import com.ta2khu75.quiz.exception.ExistingException;
import com.ta2khu75.quiz.exception.InvalidDataException;
import com.ta2khu75.quiz.exception.NotMatchesException;
import com.ta2khu75.quiz.exception.UnAuthorizedException;
import com.ta2khu75.quiz.mapper.AccountMapper;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.AccountProfile;
import com.ta2khu75.quiz.model.entity.AccountStatus;
import com.ta2khu75.quiz.model.entity.Role;
import com.ta2khu75.quiz.repository.RoleRepository;
import com.ta2khu75.quiz.repository.account.AccountProfileRepository;
import com.ta2khu75.quiz.repository.account.AccountRepository;
import com.ta2khu75.quiz.repository.account.AccountStatusReporitory;
import com.ta2khu75.quiz.service.AccountService;
import com.ta2khu75.quiz.service.base.BaseService;
import com.ta2khu75.quiz.service.util.RedisUtil;
import com.ta2khu75.quiz.service.util.RedisUtil.NameModel;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl extends BaseService<AccountRepository, AccountMapper> implements AccountService {
	RoleRepository roleRepository;
	AccountProfileRepository profileRepository;
	AccountStatusReporitory statusRepository;
	PasswordEncoder passwordEncoder;
	RedisUtil redisUtil;

	public AccountServiceImpl(AccountRepository repository, AccountMapper mapper, RoleRepository roleRepository,
			AccountProfileRepository profileRepository, AccountStatusReporitory statusRepository,
			PasswordEncoder passwordEncoder, RedisUtil redisUtil) {
		super(repository, mapper);
		this.roleRepository = roleRepository;
		this.profileRepository = profileRepository;
		this.statusRepository = statusRepository;
		this.passwordEncoder = passwordEncoder;
		this.redisUtil = redisUtil;
	}


	@Override
	public AccountResponse create(AccountRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new NotMatchesException("password and confirm password not matches");
		if (repository.existsByEmail(request.getEmail()))
			throw new ExistingException("Email already exists");
		AccountProfile profile = mapper.toEntity(request.getProfile());
		profile.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
		Role role = FunctionUtil.findOrThrow(request.getRoleId(), Role.class, roleRepository::findById);
		AccountStatus status = new AccountStatus();
		status.setRole(role);
		status.setEnabled(true);

		Account account = new Account();
		account.setEmail(request.getEmail().toLowerCase());
		account.setPassword(passwordEncoder.encode(request .getPassword()));
		account.setProfile(profile);
		account.setStatus(status);
		try {
			account = repository.save(account);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new ExistingException(e.getMessage());
		}
		return mapper.toResponse(account);
	}

	@Override
	public AccountProfileResponse readProfile(Long id) {
		AccountProfile profile = FunctionUtil.findOrThrow(id, AccountProfile.class, profileRepository::findById);
		return mapper.toDetailProfileResponse(profile);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public AccountStatusResponse updateStatus(Long id, AccountStatusRequest request) {
		AccountStatus status = FunctionUtil.findOrThrow(id, AccountStatus.class, statusRepository::findById);
		mapper.update(request, status);
		if (!status.getRole().getId().equals(request.getRoleId())) {
			status.setRole(FunctionUtil.findOrThrow(request.getRoleId(), Role.class, roleRepository::findById));
		}
		status =statusRepository.save(status);
		if (status.isNonLocked()) {
			redisUtil.delete(NameModel.ACCOUNT_LOCK, status.getId());
		} else {
			redisUtil.create(NameModel.ACCOUNT_LOCK, status.getId(), status);
		}
		return mapper.toResponse(status);
	}
	@Override
	public AccountResponse updatePassword(String id, AccountPasswordRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new NotMatchesException("New password and confirm password not matches");
		Account account = FunctionUtil.findOrThrow(id, Account.class, repository::findById);
		if(account.getCreatedBy()==null) throw new UnAuthorizedException("You can't change password");
		if (!passwordEncoder.matches(request.getPassword(), account.getPassword()))
			throw new NotMatchesException("Password not matches");
		account.setPassword(passwordEncoder.encode(request.getNewPassword()));
		account = repository.save(account);
		return mapper.toResponse(account);
	}

	@Override
	public AccountProfileResponse updateProfile(Long profileId, AccountProfileRequest request) {
		if(!SecurityUtil.getCurrentProfileId().equals(profileId)) 
			throw new InvalidDataException("You can't update other's profile");
		AccountProfile profile = FunctionUtil.findOrThrow(profileId, AccountProfile.class, profileRepository::findById);
		mapper.update(request, profile);
		return mapper.toResponse(profile);
	}

	@Override
	public PageResponse<AccountResponse> search(AccountSearch search) {
		Page<Account> page = repository.search(search);
		return mapper.toPageResponse(page);
	}
}
