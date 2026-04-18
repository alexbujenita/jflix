package org.truje.jflix.model.auth.response;

public record LoginResponse(String accessToken, String tokenType, long expiresInSeconds) {}
