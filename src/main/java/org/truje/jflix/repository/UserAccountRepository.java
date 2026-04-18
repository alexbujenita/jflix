package org.truje.jflix.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.truje.jflix.repository.entity.UserAccountEntity;

public interface UserAccountRepository extends CrudRepository<UserAccountEntity, Long> {
    Optional<UserAccountEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
