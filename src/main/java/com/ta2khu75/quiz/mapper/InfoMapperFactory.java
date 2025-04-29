package com.ta2khu75.quiz.mapper;

import java.io.Serializable;

import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

import com.ta2khu75.quiz.model.entity.base.EntityBaseCustom;
import com.ta2khu75.quiz.model.response.InfoResponse;

@Component
public class InfoMapperFactory {

    @ObjectFactory
    public <T extends Serializable> InfoResponse<T> toResponse(EntityBaseCustom<T> entity) {
        if (entity == null) return null;
//        InfoResponse<T> response = new InfoResponse<>();
//        response.setId(entity.getId());
//        response.setCreatedAt(entity.getCreatedAt());
//        response.setUpdatedAt(entity.getUpdatedAt());
//        return response;
        return null;
    }
}
