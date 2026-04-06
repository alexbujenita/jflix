package org.truje.jflix;

import org.springframework.data.repository.CrudRepository;
import org.truje.jflix.repository.entity.UserAccountEntity;

public interface UserAccountRepository extends CrudRepository<UserAccountEntity, Long> {}
