package org.truje.jflix.controller.v1.about;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.truje.jflix.model.auth.AuthenticatedUser;
import org.truje.jflix.model.response.MeResponse;

@RestController
@RequestMapping("/api/v1/about")
public class AboutController {

    @GetMapping("/me-stateless")
    public ResponseEntity<MeResponse> meStateless(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null || jwt.getSubject() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        long userId;
        try {
            userId = Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException _) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token subject");
        }

        List<String> roles = extractRoles(jwt);
        String email = jwt.getClaimAsString("email");

        return ResponseEntity.ok(new MeResponse(userId, email, roles, jwt.getIssuedAt(), jwt.getExpiresAt()));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal AuthenticatedUser user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_") ? role.substring("ROLE_".length()) : role)
                .toList();

        return ResponseEntity.ok(new MeResponse(user.userId(), user.getUsername(), roles, null, null));
    }

    private static List<String> extractRoles(Jwt jwt) {
        Object claim = jwt.getClaims().get("roles");
        if (claim instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return List.of();
    }
}
