package com.yawn.service;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yawn on 2018-01-08 13:55
 */
@Service
public class UserService {

    @Resource
    private IdentityService identityService;

    public boolean login(String userName, String password) {
        return identityService.checkPassword(userName, password);
    }

    public Object getAllUser() {
        List<User> userList = identityService.createUserQuery().list();
        List<com.yawn.entity.User> myUserList = new ArrayList<>();
        for (User user : userList) {
            com.yawn.entity.User myUser = new com.yawn.entity.User();
            myUser.setUserName(user.getFirstName() + "·" + user.getLastName());
            myUser.setPassword(user.getPassword());
            myUserList.add(myUser);
        }
        return myUserList;
    }
}
