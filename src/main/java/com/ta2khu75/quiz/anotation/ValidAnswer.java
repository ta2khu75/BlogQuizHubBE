package com.ta2khu75.quiz.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ta2khu75.quiz.validator.AnswerValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = AnswerValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAnswer {
	String message() default "Quiz must have at least one correct answer";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
