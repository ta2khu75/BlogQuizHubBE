package com.ta2khu75.quiz.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class AdviceException implements ResponseBodyAdvice<Object> {
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.badRequest()
				.body(new ExceptionResponse(ex.getConstraintViolations().iterator().next().getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest()
				.body(new ExceptionResponse(ex.getBindingResult().getFieldError().getDefaultMessage()));
	}
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnAuthorizedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ex.getMessage()));
	}
	@ExceptionHandler(value = { NoResourceFoundException.class, NotFoundException.class })
	public ResponseEntity<ExceptionResponse> handleInValidDataException(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.internalServerError().body(new ExceptionResponse(ex.getMessage()));
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ExceptionResponse> handleDisabledException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(ex.getMessage()));
	}

	@ExceptionHandler(value = { NotMatchesException.class, ExistingException.class, InvalidDataException.class,
			MissingRequestCookieException.class })
	public ResponseEntity<ExceptionResponse> handleNotMatchesException(Exception ex) {
		return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
	}

	@Override
	public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
			@NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request,
			@NonNull ServerHttpResponse response) {
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		int statusCode = servletResponse.getStatus();
		if (selectedContentType.includes(MediaType.APPLICATION_JSON)) {
			if (statusCode < HttpStatus.BAD_REQUEST.value()) {
				return new ApiResponse(body, null, statusCode);
			} else if (body instanceof ExceptionResponse exceptionResponse) {
				return new ApiResponse(null, exceptionResponse.messageError, statusCode);
			}
		}
		return body;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class ApiResponse {
		Object data;
		String message;
		int statusCode;
	}

	public record ExceptionResponse(String messageError) {
	}

}
