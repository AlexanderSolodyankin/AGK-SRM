package com.agk.crm.service.impl;

import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserInfoEntity;
import com.agk.crm.model.userInfoModel.UserInfoGetModel;
import com.agk.crm.model.userInfoModel.UserInfoPostModel;
import com.agk.crm.repository.UserInfoRepository;
import com.agk.crm.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfoEntity> getAllUserInfo() {
        return userInfoRepository.findAll ();
    }

    @Override
    public UserInfoEntity setNewUserInfo(UserInfoEntity userInfoEntity) {
        return userInfoRepository.save ( userInfoEntity );
    }

    @Override
    public List<UserInfoEntity> getUserInfoByName(String name) {
        return userInfoRepository.findByName ( name ).orElse ( null );
    }

    @Override
    public List<UserInfoEntity> getUserInfoBySerName(String serName) {
        return userInfoRepository.findBySerName ( serName ).orElse ( null );
    }

    @Override
    public List<UserInfoEntity> getUserInfoByPatrol(String patrol) {
        return userInfoRepository.findByPatrol ( patrol ).orElse ( null );
    }

    @Override
    public UserInfoEntity getUserInfoByPhone(String phone) {
        return userInfoRepository.findByPhone ( phone ).orElse ( null );
    }

    @Override
    public List<UserInfoEntity> getUserInfoByDataBerth(Date dataBerth) {
        return userInfoRepository.findByDataBirth ( dataBerth ).orElse ( null );
    }

    @Override
    public UserInfoEntity getUserInfoByUserEntity(UserEntity userEntity) {
        return userInfoRepository.findByUserEntity ( userEntity ).orElse ( null );
    }

    @Override
    public UserInfoEntity updateInfo(UserInfoEntity entity) {
        return setNewUserInfo ( entity );
    }

    @Override
    public UserInfoEntity deleteInfo(UserInfoEntity entity) {
        userInfoRepository.delete ( entity );
        return entity;
    }

    @Override
    public UserInfoEntity convertByUserInfoModel(UserInfoGetModel infoGetModel) {
        UserInfoEntity entity1 = userInfoRepository.findById ( infoGetModel.getId () ).orElse ( null );
        List<UserInfoEntity> entityList = userInfoRepository.findByName ( infoGetModel.getName () ).orElse ( null );
        List<UserInfoEntity> entityList1 = userInfoRepository.findBySerName ( infoGetModel.getSerName () ).orElse ( null );
        List<UserInfoEntity> entityList2 = userInfoRepository.findByPatrol ( infoGetModel.getPatrol () ).orElse ( null );

        UserEntity userEntity = entity1.getUserEntity ();
        if(userEntity != null){
            if(entityList != null) {
                for (UserInfoEntity el : entityList) {
                    if (userEntity.equals ( el.getUserEntity ( ) )) {
                        return el;
                    }
                }
            }
            if(entityList != null) {
                for (UserInfoEntity el : entityList1) {
                    if (userEntity.equals ( el.getUserEntity ( ) )) {
                        return el;
                    }
                }
            }
            if(entityList != null) {
                for (UserInfoEntity el : entityList2) {
                    if (userEntity.equals ( el.getUserEntity ( ) )) {
                        return el;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UserInfoEntity convertByUserInfoModel(UserInfoPostModel infoPostModel) {
        List<UserInfoEntity> entityListName = userInfoRepository.findByName ( infoPostModel.getName () ).orElse ( null );
        List<UserInfoEntity> entityListSerName = userInfoRepository.findBySerName ( infoPostModel.getSerName () ).orElse ( null );
        List<UserInfoEntity> entityListPatrol = userInfoRepository.findByPatrol ( infoPostModel.getPatrol () ).orElse ( null );

       UserEntity entityName = null;
       UserEntity entitySerName = null;
       UserEntity entityPatrol = null;

       UserInfoEntity userInfoEntity = null;

       if(entityListName != null) {
           for (UserInfoEntity el : entityListName) {
               if (infoPostModel.getSerName ( ).equals ( el.getSerName ( ) ) &&
                       infoPostModel.getPatrol ( ).equals ( el.getPatrol ( ) )
               ) {
                   entityName = el.getUserEntity ( );
                   userInfoEntity = el;
               }
           }
       }

        if(entityListSerName != null) {
            for (UserInfoEntity el : entityListSerName) {
                if (infoPostModel.getSerName ( ).equals ( el.getSerName ( ) ) &&
                        infoPostModel.getPatrol ( ).equals ( el.getPatrol ( ) )
                ) {
                    entitySerName = el.getUserEntity ( );
                    userInfoEntity = el;
                }
            }
        }

        if(entityListPatrol != null) {
            for (UserInfoEntity el : entityListPatrol) {
                if (infoPostModel.getSerName ( ).equals ( el.getSerName ( ) ) &&
                        infoPostModel.getPatrol ( ).equals ( el.getPatrol ( ) )
                ) {
                    entityPatrol = el.getUserEntity ( );
                    userInfoEntity = el;
                }
            }
        }

        if (entityName != entitySerName && entityName != entityPatrol && entityName == null) {
            return userInfoEntity;
        }else {

            userInfoEntity = new UserInfoEntity (  );
            userInfoEntity.setName ( infoPostModel.getName () );
            userInfoEntity.setSerName ( infoPostModel.getSerName () );
            userInfoEntity.setPatrol ( infoPostModel.getPatrol () );
            userInfoEntity.setPhone ( infoPostModel.getPhone () );
            userInfoEntity.setDataBirth ( infoPostModel.getDataBerth () );

        }
        return userInfoEntity;

    }

    @Override
    public UserInfoGetModel convertToGetModel(UserInfoEntity userInfoEntity) {
        UserInfoGetModel getModel  = new UserInfoGetModel ();
        getModel.setId ( userInfoEntity.getId () );
        getModel.setName ( userInfoEntity.getName ( ) );
        getModel.setSerName ( userInfoEntity.getSerName ( ) );
        getModel.setPatrol ( userInfoEntity.getPatrol ( ) );
        getModel.setPhone ( userInfoEntity.getPhone ( ) );
        getModel.setDataBerth ( userInfoEntity.getDataBirth () );
        return getModel;
    }

    @Override
    public UserInfoPostModel convertToPostModel(UserInfoEntity userInfoEntity) {
        UserInfoPostModel postModel = new UserInfoPostModel ();
        postModel.setName ( userInfoEntity.getName ( ) );
        postModel.setSerName ( userInfoEntity.getSerName ( ) );
        postModel.setPatrol ( userInfoEntity.getPatrol ( ) );
        postModel.setPhone ( userInfoEntity.getPhone ( ) );
        postModel.setDataBerth ( userInfoEntity.getDataBirth () );
        return postModel;
    }

    @Override
    public List<UserInfoEntity> convertGetModelToEntityList(List<UserInfoGetModel> getList) {
        List<UserInfoEntity> entityList = new ArrayList<> (  );
        for(UserInfoGetModel el : getList){
            entityList.add(convertByUserInfoModel ( el ));
        }
        return entityList;
    }

    @Override
    public List<UserInfoEntity> convertPostModelToEntityList(List<UserInfoPostModel> postList) {
        List<UserInfoEntity> entityList = new ArrayList<> (  );
        for(UserInfoPostModel el : postList){
            entityList.add(convertByUserInfoModel ( el ));
        }
        return entityList;
    }

    @Override
    public List<UserInfoGetModel> convertEntityToGetModel(List<UserInfoEntity> entityList) {
        List<UserInfoGetModel> getModelList = new ArrayList<> (  );

        for(UserInfoEntity el : entityList){
            getModelList.add ( convertToGetModel ( el ) );
        }
        return getModelList;
    }

    @Override
    public List<UserInfoPostModel> convertEntityToPostModel(List<UserInfoEntity> entityList) {
        List<UserInfoPostModel> postModelList = new ArrayList<> (  );

        for(UserInfoEntity el : entityList){
            postModelList.add ( convertToPostModel ( el ) );
        }
        return postModelList;
    }
}
