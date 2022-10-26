package com.agk.crm.repository;


import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserEntity(UserEntity userEntity);
}
