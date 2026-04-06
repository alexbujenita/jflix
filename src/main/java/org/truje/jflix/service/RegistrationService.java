package org.truje.jflix.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.truje.jflix.UserAccountRepository;
import org.truje.jflix.model.request.auth.RegisterRequest;
import org.truje.jflix.repository.entity.UserAccountEntity;

@Service
public class RegistrationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {

        UserAccountEntity userAccountEntity = new UserAccountEntity();

        userAccountEntity.setFirstName(registerRequest.firstName());
        userAccountEntity.setLastName(registerRequest.lastName());
        userAccountEntity.setEmail(registerRequest.email());
        userAccountEntity.setPasswordHash(passwordEncoder.encode(registerRequest.password()));

        userAccountRepository.save(userAccountEntity);
    }
}
