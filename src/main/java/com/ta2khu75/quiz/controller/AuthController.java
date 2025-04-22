package com.ta2khu75.quiz.controller;

import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ta2khu75.quiz.anotation.EndpointMapping;
import com.ta2khu75.quiz.model.request.AccountCreateRequest;
import com.ta2khu75.quiz.model.request.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.AuthRequest;
import com.ta2khu75.quiz.model.response.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.AuthResponse;
import com.ta2khu75.quiz.model.response.BooleanResponse;
import com.ta2khu75.quiz.model.response.TokenResponse;
import com.ta2khu75.quiz.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${app.api-prefix}/auth")
public class AuthController extends BaseController<AuthService> {
	protected AuthController(AuthService service) {
		super(service);
	}
	private final String REFRESH_TOKEN = "refresh_token";
	private final String ACCESS_TOKEN = "access_token";

	@PostMapping("login")
	@EndpointMapping(name = "Login")
	public ResponseEntity<AccountProfileResponse> login(@Valid @RequestBody AuthRequest request) {
		AuthResponse response = service.login(request);
		ResponseCookie cookieRefresh = createCookie(REFRESH_TOKEN,response.getRefreshToken());
		ResponseCookie cookieAccess = createCookie(ACCESS_TOKEN,response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookieAccess.toString(), cookieRefresh.toString()).body(response.getProfile());
	}

	@PostMapping("register")
	@EndpointMapping(name = "Register")
	public ResponseEntity<AccountResponse> register(@Valid @RequestBody AccountCreateRequest request)
			throws MessagingException {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
	}

	@GetMapping("refresh")
	@EndpointMapping(name = "Refresh token")
	public ResponseEntity<AuthResponse> createRefreshToken(@CookieValue("refresh_token") String refreshToken) {
		AuthResponse response = service.refreshToken(refreshToken);
		ResponseCookie refresh = createCookie(REFRESH_TOKEN,response.getRefreshToken());
		ResponseCookie access= createCookie(ACCESS_TOKEN,response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refresh.toString(), access.toString()).body(response);
	}

	@PutMapping("change-password")
	@EndpointMapping(name = "Change password")
	public ResponseEntity<AccountResponse> changePassword(@Valid @RequestBody AccountPasswordRequest request) {
		return ResponseEntity.ok(service.changePassword(request));
	}

	@GetMapping("logout")
	@EndpointMapping(name = "Logout")
	public ResponseEntity<Void> logout() {
		service.logout();
		ResponseCookie refresh = createCookie(REFRESH_TOKEN,new TokenResponse(null, 0L));
		ResponseCookie access = createCookie(ACCESS_TOKEN,new TokenResponse(null, 0L));
		return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, refresh.toString(), access.toString()).build();
	}

	@GetMapping("check-admin")
	@EndpointMapping(name = "Check admin")
	public ResponseEntity<BooleanResponse> checkAdmin() {
		return ResponseEntity.ok(new BooleanResponse(service.checkAdmin()));
	}

	@GetMapping("verify")
	@EndpointMapping(name = "Verify account")
	public RedirectView verify(@RequestParam String code) {
		boolean isVerified = service.verify(code);
		String clientRedirectUrl;
		if (isVerified) {
			clientRedirectUrl = "http://localhost:5173/login?verified=true";
		} else {
			clientRedirectUrl = "http://localhost:5173/login";
		}
		return new RedirectView(clientRedirectUrl);
	}

	private ResponseCookie createCookie(String name,TokenResponse toke) {
		return ResponseCookie.from(name, toke.getToken()).httpOnly(true).secure(true).sameSite("Strict")
				.maxAge(getExpiration(toke.getExpiration())).path("/").build();
	}
	private long getExpiration(long expirationTime) {
		 long remainingMillis = expirationTime - Instant.now().toEpochMilli();
		 return Math.max(remainingMillis / 1000, 0);
	}
}
