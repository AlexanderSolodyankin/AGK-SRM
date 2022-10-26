package com.agk.crm.service.impl;


import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserRole;
import com.agk.crm.model.usersModel.UserAuthModelPost;
import com.agk.crm.model.usersModel.UserModelGet;
import com.agk.crm.model.usersModel.UserModelPost;
import com.agk.crm.model.usersModel.UserUpdateModelPassword;
import com.agk.crm.repository.RoleRepository;
import com.agk.crm.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UsersServiceImpl implements com.agk.crm.service.impl.UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity newUser(UserModelPost userModelPost) {
        UserEntity userDb = usersRepository.findByLogin(userModelPost.getLogin()).orElse(null);
        if (userDb != null) {
            throw new IllegalArgumentException("Такой пользователь существует!!");
        }
        String activationCode = userModelPost.getLogin() + ":" + userModelPost.getPassword();
        activationCode = new String(Base64.getEncoder().encode(activationCode.getBytes()));

        String encoderPassword = passwordEncoder.encode(userModelPost.getPassword());
        userModelPost.setPassword(encoderPassword);

        UserEntity userEntity = convertModelToEntity(userModelPost);
        userEntity.setIsActive(1L); // тут поменять на нуль чтобы актевировать активацию акаунта

        userEntity.setActivationCode(activationCode);
        userEntity = usersRepository.save(userEntity);


        UserRole userRole = new UserRole();
        userRole.setRoleName("ROLE_USER");
        userRole.setUserEntity(userEntity);
        roleRepository.save(userRole);

        return userEntity;

    }

    @Override
    public List<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public UserEntity getByUser(String login) {
        return usersRepository.findByLogin(login).orElse(null);
    }


    @Override
    public UserEntity getByUser(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" Пользователя под таким ID номером не существует! ")
        );
    }

    @Override
    public UserEntity updatePassword(UserUpdateModelPassword userNewPassword) throws IllegalAccessException {
        UserEntity user = getCurrentUser();
        boolean isPasswordMatches = passwordEncoder.matches(userNewPassword.getOldPassword(), user.getPassword());
        if (!isPasswordMatches) {
            throw new IllegalAccessException("Неверный пароль.");
        }
        String newPassword = passwordEncoder.encode(userNewPassword.getNewPassword());
        user.setPassword(newPassword);
        return usersRepository.save(user);
    }

    @Override
    public String getAuthorizedToken(UserAuthModelPost userAuthModelPost) throws IllegalAccessException {
        UserEntity userEntity = usersRepository.findByLogin(userAuthModelPost.getLogin()).orElseThrow(
                () -> new IllegalArgumentException("Неверный логин или пароль."));

        boolean isPasswordMatches = passwordEncoder.matches(userAuthModelPost.getPassword(), userEntity.getPassword());
        if (!isPasswordMatches) {
            throw new IllegalAccessException("Неверный логин или пароль.");
        }
        String userNamePasswordPair = userAuthModelPost.getLogin() + ":" + userAuthModelPost.getPassword();
        return "Basic " + new String(Base64.getEncoder().encode(userNamePasswordPair.getBytes()));
    }

    @Override
    public UserEntity deleteUser(UserEntity userEntity) {
        UserRole userRoleDelete = roleRepository.findByUserEntity(userEntity).orElse(null);
        if (userRoleDelete == null) {
            try {
                throw new IllegalAccessException("Такой роли не существует");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        roleRepository.delete(userRoleDelete);
        usersRepository.delete(userEntity);
        return userEntity;

    }

    @Override
    public UserEntity getCurrentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUser(userName);
    }

    @Override
    public String activationUser(String activation) {
        UserEntity userEntity = usersRepository.findByActivationCode(activation).orElseThrow(
                () -> new IllegalArgumentException("Проблемы с активацией Юзера"));

        if (userEntity.getActivationCode().equals(activation)) {
            userEntity.setIsActive(1L);
            userEntity.setActivationCode(null);
        }

        usersRepository.save(userEntity);
        return "Basic " + activation;
    }

    @Override
    public UserModelGet convertUserEntityToUserModel(UserEntity userEntity) {
        UserModelGet userModelGet = new UserModelGet();
        userModelGet.setId(userEntity.getId());
        userModelGet.setLogin(userEntity.getLogin());
        userModelGet.setEmail(userEntity.getEmail());
        return userModelGet;
    }

    @Override
    public List<UserModelGet> convertUserEntityToUserModel(List<UserEntity> userEntity) {
        List<UserModelGet> userModelGetList = new ArrayList<>();
        for (UserEntity userEnty : userEntity) {
            userModelGetList.add(convertUserEntityToUserModel(userEnty));
        }
        return userModelGetList;
    }

    @Override
    public UserEntity convertModelToEntity(UserModelPost userModelPost) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userModelPost.getLogin());
        userEntity.setEmail(userModelPost.getEmail());
        userEntity.setPassword(userModelPost.getPassword());
        return userEntity;
    }

    @Override
    public UserEntity convertModelToEntity(UserModelGet getUser) {
        return getByUser(getUser.getId());
    }

    @Override
    public UserRole getRoleByUser(UserEntity entity) {
        return roleRepository.findByUserEntity(entity).orElse(null);
    }

    @Override
    public Boolean isAdmin(UserEntity entity) {
        UserRole role = getRoleByUser(entity);

        if(role.getRoleName().equals("ROLE_ADMIN")) return true;
        else return false;
    }


}
