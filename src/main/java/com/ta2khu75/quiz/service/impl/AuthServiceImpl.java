package com.ta2khu75.quiz.service.impl;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.model.request.AuthRequest;
import com.ta2khu75.quiz.model.request.account.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountRequest;
import com.ta2khu75.quiz.model.response.AuthResponse;
import com.ta2khu75.quiz.model.response.TokenResponse;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;
import com.ta2khu75.quiz.exception.ExistingException;
import com.ta2khu75.quiz.exception.NotMatchesException;
import com.ta2khu75.quiz.mapper.AccountMapper;
import com.ta2khu75.quiz.model.RoleDefault;
import com.ta2khu75.quiz.model.TokenType;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.AccountProfile;
import com.ta2khu75.quiz.model.entity.AccountStatus;
import com.ta2khu75.quiz.model.entity.Role;
import com.ta2khu75.quiz.repository.RoleRepository;
import com.ta2khu75.quiz.repository.account.AccountProfileRepository;
import com.ta2khu75.quiz.repository.account.AccountRepository;
import com.ta2khu75.quiz.repository.account.AccountStatusReporitory;
import com.ta2khu75.quiz.scheduling.SendMailScheduling;
import com.ta2khu75.quiz.service.AuthService;
import com.ta2khu75.quiz.service.base.BaseService;
import com.ta2khu75.quiz.service.util.JWTUtil;
import com.ta2khu75.quiz.util.EmailTemplateUtil;
import com.ta2khu75.quiz.util.FunctionUtil;
import com.ta2khu75.quiz.util.SecurityUtil;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl extends BaseService<AccountRepository, AccountMapper> implements AuthService {
	private final JWTUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final AccountStatusReporitory statusRepository;
	private final AccountProfileRepository profileRepository;
	private final AuthenticationManager authenticationManager;
	private final SendMailScheduling sendMailScheduling;

	public AuthServiceImpl(AccountRepository repository, AccountMapper mapper, JWTUtil jwtUtil,
			PasswordEncoder passwordEncoder, RoleRepository roleRepository, AccountStatusReporitory statusRepository,
			AccountProfileRepository profileRepository, AuthenticationManager authenticationManager,
			SendMailScheduling sendMailScheduling) {
		super(repository, mapper);
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.statusRepository = statusRepository;
		this.profileRepository = profileRepository;
		this.authenticationManager = authenticationManager;
		this.sendMailScheduling = sendMailScheduling;
	}

	@Override
	public AuthResponse login(AuthRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Account account = (Account) authentication.getPrincipal();
		return this.makeAuthResponse(account);
	}

	@Override
	public void changePassword(AccountPasswordRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new NotMatchesException("New password and confirm password not matches");
		Account account = findById();
		if (!passwordEncoder.matches(request.getPassword(), account.getPassword()))
			throw new NotMatchesException("Password not matches");
		account.setPassword(passwordEncoder.encode(request.getNewPassword()));
		account = repository.save(account);
	}

	@Override
	public void register(AccountRequest request) throws MessagingException {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new NotMatchesException("password and confirm password not matches");
		if (repository.existsByEmail(request.getEmail()))
			throw new ExistingException("Email already exists");
		AccountProfile profile = mapper.toEntity(request.getProfile());
		profile.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
		Role role = FunctionUtil.findOrThrow(RoleDefault.USER.name(), Role.class, roleRepository::findByName);
		AccountStatus status = new AccountStatus();
		status.setRole(role);
		status.setCodeVerify(UUID.randomUUID().toString());

		Account account = new Account();
		account.setEmail(request.getEmail().toLowerCase());
		account.setPassword(passwordEncoder.encode(request.getPassword()));
		account.setProfile(profile);
		account.setStatus(status);
		try {
			account = repository.save(account);
		} catch (DataIntegrityViolationException e) {
			throw new ExistingException("Email already exists");
		}
		sendMailScheduling.addMail(account.getEmail(), "Confirm your email",
				EmailTemplateUtil.getVerify(status.getCodeVerify()), true);
	}

	@Override
	public AuthResponse refreshToken(String token) {
		Jwt jwt = jwtUtil.validateToken(token, TokenType.REFRESH);
		Long statusId = jwtUtil.getClaim(jwt, "statusId", Long.class);
		Account account = validateRefreshToken(statusId, token);
		return this.makeAuthResponse(account);
	}

	private AuthResponse makeAuthResponse(Account account) {
		AccountResponse response = mapper.toResponse(account);
		TokenResponse refreshToken = jwtUtil.createRefreshToken(response);
		this.updateRefreshToken(account.getStatus(), refreshToken.getToken());
		return new AuthResponse(response.getProfile(), response.getStatus().getRole().getName(),
				jwtUtil.createAccessToken(response), refreshToken);
	}

	private void updateRefreshToken(AccountStatus status, String token) {
		status.setRefreshToken(token);
		statusRepository.save(status);
	}

	private Account validateRefreshToken(Long staticId, String refreshToken) {
		Account account = FunctionUtil.findOrThrow(staticId, Account.class, repository::findByStatusId);
		if (!account.getStatus().getRefreshToken().equals(refreshToken))
			throw new NotMatchesException("Refresh token not invalid");
		return account;
	}

	private Account findById() {
		return FunctionUtil.findOrThrow(SecurityUtil.getCurrentAccountId(), Account.class, repository::findById);
	}

	@Override
	public void logout() {
		Account account = findById();
		AccountStatus accountStatus = account.getStatus();
		accountStatus.setRefreshToken(null);
		statusRepository.save(accountStatus);
	}

	@Override
	public boolean verify(String code) {
//		Account account = repository.findByCodeVerify(code).orElse(null);
//		if (account != null) {
//			AccountStatus accountStatus = account.getStatus();
//			accountStatus.setEnabled(true);
//			accountStatus.setCodeVerify(null);
//			statusRepository.save(accountStatus);
//			return true;
//		}
		return false;
	}

	@Override
	public boolean checkAdmin() {
		return SecurityUtil.getCurrentRole().equals(RoleDefault.ADMIN.name());
	}

	@Override
	public AccountProfileResponse changeProfile(AccountProfileRequest request) {
		Long profileId = SecurityUtil.getCurrentProfileId();
		AccountProfile profile = FunctionUtil.findOrThrow(profileId, AccountProfile.class, profileRepository::findById);
		mapper.update(request, profile);
		return mapper.toResponse(profileRepository.save(profile));
	}
}
