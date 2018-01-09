package com.yawn.service;

import org.activiti.engine.IdentityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
