package com.semicolon.ecommerceTask.domain.model;

import java.time.LocalDateTime;

public record PendingRegistration(String email, String token, LocalDateTime expiration) {
}