package com.ta2khu75.quiz.validator;

import java.util.List;

import com.ta2khu75.quiz.anotation.ValidAnswer;
import com.ta2khu75.quiz.model.request.AnswerRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnswerValidator implements ConstraintValidator<ValidAnswer, List<AnswerRequest>> {

    @Override
    public void initialize(ValidAnswer constraintAnnotation) {
        // Initialize if needed (currently not used)
    }

    @Override
    public boolean isValid(List<AnswerRequest> answers, ConstraintValidatorContext context) {
        if (answers == null) {
            return true; 
        }
        return answers.stream().anyMatch(AnswerRequest::isCorrect);
    }
}
