package com.ta2khu75.quiz.model;

import lombok.Data;

@Data
public class TokenConfig {
    private String secret;
    private long expiration; // hoặc Duration nếu thích
}
