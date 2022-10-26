package com.agk.crm.service.impl;


import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserRole;
import com.agk.crm.model.usersModel.UserAuthModelPost;
import com.agk.crm.model.usersModel.UserModelGet;
import com.agk.crm.model.usersModel.UserModelPost;
import com.agk.crm.model.usersModel.UserUpdateModelPassword;

import java.util.List;

public interface UsersService {

    UserEntity newUser(UserModelPost userModelPost);

    List<UserEntity> getAllUsers();

    UserEntity getByUser(String login);

    UserEntity getByUser(Long id);

    String getAuthorizedToken(UserAuthModelPost userAuthModelPost) throws IllegalAccessException;

    UserEntity deleteUser(UserEntity userEntity);

    UserEntity updatePassword(UserUpdateModelPassword userNewPassword) throws IllegalAccessException;

    UserEntity getCurrentUser();

    String activationUser(String activation);

    UserModelGet convertUserEntityToUserModel(UserEntity userEntity);

    List<UserModelGet> convertUserEntityToUserModel(List<UserEntity> userEntity);

    UserEntity convertModelToEntity(UserModelPost userModelPost);

    UserEntity convertModelToEntity(UserModelGet getUser);

    UserRole getRoleByUser(UserEntity entity);

    Boolean isAdmin(UserEntity entity);


}
