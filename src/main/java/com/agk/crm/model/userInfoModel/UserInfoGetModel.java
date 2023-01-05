package com.agk.crm.model.userInfoModel;


import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserInfoGetModel {
    private Long id;
    private String name;
    private String serName;
    private String patrol;
    private String phone;
    private Date dataBerth;
}
