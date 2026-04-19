package org.truje.jflix.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.truje.jflix.model.auth.AuthenticatedUser;
import org.truje.jflix.model.auth.request.LoginRequest;
import org.truje.jflix.model.auth.response.LoginResponse;
import org.truje.jflix.util.UtilClass;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public LoginService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public LoginResponse loginStateless(LoginRequest loginRequest) {
        String normalizedEmail = UtilClass.normalizeEmail(loginRequest.email());

        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(normalizedEmail, loginRequest.password()));

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return jwtTokenService.createAccessToken(authenticatedUser);
    }

    public Authentication authenticate(LoginRequest loginRequest) {
        String normalizedEmail = UtilClass.normalizeEmail(loginRequest.email());

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(normalizedEmail, loginRequest.password()));
    }
}
