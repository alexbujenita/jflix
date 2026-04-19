package org.truje.jflix.controller.v1.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.truje.jflix.model.auth.request.LoginRequest;
import org.truje.jflix.model.auth.request.RegisterRequest;
import org.truje.jflix.model.auth.response.LoginResponse;
import org.truje.jflix.service.auth.LoginService;
import org.truje.jflix.service.auth.RegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(
            RegistrationService registrationService,
            LoginService loginService,
            SecurityContextRepository securityContextRepository) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        registrationService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login-stateless")
    public ResponseEntity<LoginResponse> loginUserStateless(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.loginStateless(loginRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = loginService.authenticate(loginRequest);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JFLIXSESSION", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
