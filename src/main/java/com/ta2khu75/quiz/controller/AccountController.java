package com.ta2khu75.quiz.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import com.ta2khu75.quiz.anotation.ControllerConfig;
import com.ta2khu75.quiz.anotation.EndpointMapping;
import com.ta2khu75.quiz.model.group.Admin;
import com.ta2khu75.quiz.model.request.account.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountRequest;
import com.ta2khu75.quiz.model.request.account.AccountStatusRequest;
import com.ta2khu75.quiz.model.request.search.AccountSearch;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;
import com.ta2khu75.quiz.model.response.account.AccountStatusResponse;
import com.ta2khu75.quiz.service.AccountService;

@ControllerConfig("${app.api-prefix}/accounts")
public class AccountController extends BaseController<AccountService> {
	public AccountController(AccountService service) {
		super(service);
	}

	@PostMapping
	@Validated(value = { Default.class, Admin.class })
	@EndpointMapping(name = "Create account")
	public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
	}

	@DeleteMapping
	@EndpointMapping(name = "Delete account")
	ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	@EndpointMapping(name = "Read account")
	ResponseEntity<AccountProfileResponse> read(@PathVariable Long id) {
		return ResponseEntity.ok(service.readProfile(id));
	}

	@GetMapping
	@EndpointMapping(name = "Search account status")
	public ResponseEntity<PageResponse<AccountResponse>> search(@ModelAttribute AccountSearch search) {
		return ResponseEntity.ok(service.search(search));
	}

	@PutMapping("profile/{id}")
	@EndpointMapping(name = "Update account profile")
	public ResponseEntity<AccountProfileResponse> updateProfile(@PathVariable Long id,
			@Valid @RequestBody AccountProfileRequest request) {
		return ResponseEntity.ok(service.updateProfile(id, request));
	}

	@PutMapping("status/{id}")
	@EndpointMapping(name = "Update account status")
	public ResponseEntity<AccountStatusResponse> updateStatus(@PathVariable Long id,
			@Valid @RequestBody AccountStatusRequest request) {
		return ResponseEntity.ok(service.updateStatus(id, request));
	}

	@PutMapping("password/{id}")
	@EndpointMapping(name = "Update account status")
	public ResponseEntity<AccountResponse> updateStatus(@PathVariable String id,
			@Valid @RequestBody AccountPasswordRequest request) {
		return ResponseEntity.ok(service.updatePassword(id, request));
	}
}
