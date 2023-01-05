package com.agk.crm.service;

import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserInfoEntity;
import com.agk.crm.model.userInfoModel.UserInfoGetModel;
import com.agk.crm.model.userInfoModel.UserInfoPostModel;

import java.util.Date;
import java.util.List;

public interface UserInfoService {
    List<UserInfoEntity> getAllUserInfo();
    UserInfoEntity setNewUserInfo(UserInfoEntity entity);
    List<UserInfoEntity> getUserInfoByName(String name);
    List<UserInfoEntity> getUserInfoBySerName(String serName);
    List<UserInfoEntity> getUserInfoByPatrol(String patrol);
    UserInfoEntity getUserInfoByPhone(String phone);
    List<UserInfoEntity> getUserInfoByDataBerth(Date dataBerth);
    UserInfoEntity getUserInfoByUserEntity(UserEntity entity);

    UserInfoEntity updateInfo(UserInfoEntity entity);
    UserInfoEntity deleteInfo(UserInfoEntity entity);

    UserInfoEntity convertByUserInfoModel(UserInfoGetModel infoGetModel);
    UserInfoEntity convertByUserInfoModel(UserInfoPostModel infoPostModel);
    UserInfoGetModel convertToGetModel(UserInfoEntity entity);
    UserInfoPostModel convertToPostModel(UserInfoEntity entity);

    List<UserInfoEntity> convertGetModelToEntityList (List<UserInfoGetModel> getList);
    List<UserInfoEntity> convertPostModelToEntityList (List<UserInfoPostModel> postList);
    List<UserInfoGetModel> convertEntityToGetModel(List<UserInfoEntity> entityList);
    List<UserInfoPostModel> convertEntityToPostModel(List<UserInfoEntity> entityList);

}
