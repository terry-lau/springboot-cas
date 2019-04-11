package com.easydatalink.tech.service.auth;

import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.service.IManager;

public interface IUserManager extends IManager<User>{

	User findUserByLoginName(String username);
}