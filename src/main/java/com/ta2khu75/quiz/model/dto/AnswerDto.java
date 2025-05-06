package com.ta2khu75.quiz.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerDto(Long id, @NotBlank String content, boolean correct) {
}
