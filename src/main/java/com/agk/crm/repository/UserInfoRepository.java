package com.agk.crm.repository;

import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    Optional<List<UserInfoEntity>> findByName(String name);
    Optional<List<UserInfoEntity>> findBySerName(String serName);
    Optional<List<UserInfoEntity>> findByPatrol(String patrol);
    Optional<List<UserInfoEntity>> findByDataBirth(Date dataBerth);
    Optional<UserInfoEntity> findByPhone(String phone);
    Optional<UserInfoEntity> findByUserEntity(UserEntity userEntity);


}
