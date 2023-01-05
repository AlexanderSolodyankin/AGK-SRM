package com.agk.crm.controller;

import com.agk.crm.entity.UserInfoEntity;
import com.agk.crm.model.userInfoModel.UserInfoGetModel;
import com.agk.crm.model.userInfoModel.UserInfoPostModel;
import com.agk.crm.model.usersModel.UserModelGet;
import com.agk.crm.service.UserInfoService;
import com.agk.crm.service.impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/getAll")
    public List<UserInfoGetModel> getAllUserInfo(){
        return userInfoService.convertEntityToGetModel ( userInfoService.getAllUserInfo () );
    }

    @PostMapping("/setNewUserInfo")
    public UserInfoGetModel setNewUserInfo(@RequestBody UserInfoPostModel postModel){
        return userInfoService.convertToGetModel (
                userInfoService.setNewUserInfo (
                        userInfoService.convertByUserInfoModel ( postModel )
                )
        );
    }

    @PostMapping("/getUserInfoByNameOrSerNameOrPatrols")
    public List<UserInfoGetModel> getUserInfoByNameOrSerNameOrPatrols(@RequestBody String string){
        List<UserInfoGetModel> getUsersName;

        getUsersName = userInfoService.convertEntityToGetModel ( userInfoService.getUserInfoByName ( string ) );
        if(getUsersName == null){
            getUsersName = userInfoService.convertEntityToGetModel ( userInfoService.getUserInfoBySerName ( string ));
        }
        if(getUsersName == null){
            getUsersName = userInfoService.convertEntityToGetModel ( userInfoService.getUserInfoByPatrol ( string ));
        }

        return getUsersName;
    }

    @PostMapping("/getUserInfoByPhone")
    public UserInfoGetModel getUserInfoByPhone(@RequestBody String phone){
        return userInfoService.convertToGetModel ( userInfoService.getUserInfoByPhone ( phone ) );
    }

    @PostMapping("/getUsersInfoByDataBerth")
    public List<UserInfoGetModel> getUsersInfoByDataBerth (@RequestBody Date dataBerth){
        return userInfoService.convertEntityToGetModel ( userInfoService.getUserInfoByDataBerth ( dataBerth ) );
    }

    @PostMapping("/getUserInfoByUser")
    public UserInfoGetModel getUserInfoByUser(@RequestBody UserModelGet userModelGet){

        return userInfoService.convertToGetModel (
                userInfoService.getUserInfoByUserEntity (
                        usersService.convertModelToEntity ( userModelGet )
                )
        );

    }

    @PutMapping("/")
    public UserInfoGetModel updateInfoUser(@RequestBody UserInfoGetModel updateModel){

        UserInfoEntity entity = userInfoService.convertByUserInfoModel ( updateModel );
        entity.setId ( updateModel.getId () );
        entity.setName ( updateModel.getName ( ) );
        entity.setSerName ( updateModel.getSerName ( ) );
        entity.setPatrol ( updateModel.getPatrol () );
        entity.setPhone ( updateModel.getPhone () );
        entity.setDataBirth ( updateModel.getDataBerth () );

        return userInfoService.convertToGetModel (
          userInfoService.updateInfo ( entity )
        );
    }

    @DeleteMapping("/")
    public UserInfoGetModel deleteInfo(@RequestBody UserInfoGetModel deleteModel){
        return userInfoService.convertToGetModel (
                userInfoService.deleteInfo (
                        userInfoService.convertByUserInfoModel ( deleteModel )
                )
        );
    }

}
