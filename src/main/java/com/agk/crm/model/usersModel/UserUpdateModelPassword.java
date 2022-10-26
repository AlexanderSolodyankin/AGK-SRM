package com.agk.crm.model.usersModel;

import lombok.Data;

@Data
public class UserUpdateModelPassword {
    private String OldPassword;
    private String newPassword;
}
