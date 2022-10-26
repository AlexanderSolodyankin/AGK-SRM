package com.agk.crm.controller;


import com.agk.crm.model.usersModel.UserAuthModelPost;
import com.agk.crm.model.usersModel.UserModelGet;
import com.agk.crm.model.usersModel.UserModelPost;
import com.agk.crm.model.usersModel.UserUpdateModelPassword;
import com.agk.crm.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;


    @GetMapping("/getAll")
    public List<UserModelGet> userMenu() {
        return usersService.convertUserEntityToUserModel(usersService.getAllUsers());
    }

    @PostMapping("/registration")
    public UserModelGet newUser(@RequestBody UserModelPost userModelPost) {
        System.out.println("Получили юзера ");
        return usersService.convertUserEntityToUserModel(usersService.newUser(userModelPost));
    }

    @PostMapping("/sing-in")
    public ResponseEntity<String> sing(@RequestBody UserAuthModelPost userAuthModelPost) throws IllegalAccessException {
        return ResponseEntity.ok(usersService.getAuthorizedToken(userAuthModelPost));
    }

    @GetMapping("/get-current")
    public UserModelGet getCurrent() {
        System.out.println("Зашол в гет каррент");
        return usersService.convertUserEntityToUserModel(usersService.getCurrentUser());
    }

    @PostMapping("/update")
    public UserModelGet setUpdateUser(@RequestBody UserUpdateModelPassword userNewPassword) throws IllegalAccessException {
        return usersService.convertUserEntityToUserModel(usersService.updatePassword(userNewPassword));
    }

    @DeleteMapping("/deleteUser")
    public UserModelGet deleteUser() {
        return usersService.convertUserEntityToUserModel(usersService.deleteUser(usersService.getCurrentUser()));
    }

    @GetMapping("/activation/{code}")
    public String acivationUser(@PathVariable String code) {
        return usersService.activationUser(code);
    }

}