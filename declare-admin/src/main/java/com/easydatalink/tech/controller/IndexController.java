package com.easydatalink.tech.controller;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easydatalink.tech.annotation.SystemControllerLog;
import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.http.HttpResult;
import com.easydatalink.tech.service.auth.IUserManager;

@RestController
public class IndexController {
	@Autowired
	private IUserManager userManager;
	
	@ApiOperation(value="登陆接口")
	@SystemControllerLog(description="来来来登陆一下")
	@RequestMapping(value ="/login", method = {RequestMethod.POST,RequestMethod.GET})
	public HttpResult index(HttpServletRequest request,String name) {
		User user = userManager.findUserByLoginName("admin");
		System.out.println(user.getUserCode());
		return HttpResult.ok(user);
	}

}
