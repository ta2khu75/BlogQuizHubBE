package com.ta2khu75.quiz.model.request;

import com.ta2khu75.quiz.model.base.AnswerBase;
import com.ta2khu75.quiz.model.entity.Question;
import com.ta2khu75.quiz.model.group.Create;
import com.ta2khu75.quiz.model.group.Update;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerRequest extends AnswerBase{
	Long id;
}
