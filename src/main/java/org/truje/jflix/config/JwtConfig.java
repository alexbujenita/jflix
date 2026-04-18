package org.truje.jflix.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtConfig {

    @Bean
    RSAPublicKey jwtPublicKey(@Value("${jwt.public-key-location}") Resource publicKeyLocation) {

        try {
            String pem = readPem(publicKeyLocation)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);

            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (IOException | GeneralSecurityException ex) {
            throw new IllegalStateException("Failed to load RSA public key", ex);
        }
    }

    @Bean
    RSAPrivateKey jwtPrivateKey(@Value("${jwt.private-key-location}") Resource privateKeyLocation) {

        try {
            String pem = readPem(privateKeyLocation)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(pem);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);

            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (IOException | GeneralSecurityException ex) {
            throw new IllegalStateException("Failed to load RSA private key", ex);
        }
    }

    @Bean
    JwtEncoder jwtEncoder(RSAPublicKey jwtPublicKey, RSAPrivateKey jwtPrivateKey) {
        return NimbusJwtEncoder.withKeyPair(jwtPublicKey, jwtPrivateKey).build();
    }

    @Bean
    JwtDecoder jwtDecoder(RSAPublicKey jwtPublicKey, @Value("${jwt.issuer}") String issuer) {
        NimbusJwtDecoder jwtDecoder =
                NimbusJwtDecoder.withPublicKey(jwtPublicKey).build();
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuer));
        return jwtDecoder;
    }

    private static String readPem(Resource resource) throws IOException {
        try (InputStream is = resource.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
