package com.easydatalink.tech.service.auth.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.easydatalink.tech.dao.auth.IUserDao;
import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.service.MybatisManager;
import com.easydatalink.tech.service.auth.IUserManager;

@Service
public class UserManager extends MybatisManager<User, IUserDao> implements IUserManager {
    private static Logger logger = LoggerFactory.getLogger(UserManager.class);


    /**
     * 根据登录名查询用户是否存在 jie.qin 2017.12.13
     */
    @Override
    public User findUserByLoginName(String loginName) {
        return dao.getUserByLoginName(loginName);
    }
   
}
