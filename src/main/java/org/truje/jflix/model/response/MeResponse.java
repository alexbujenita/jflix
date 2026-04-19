package org.truje.jflix.model.response;

import java.time.Instant;
import java.util.List;

public record MeResponse(Long id, String email, List<String> roles, Instant issuedAt, Instant expiresAt) {}
