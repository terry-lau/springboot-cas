package com.easydatalink.tech.dao.auth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.easydatalink.tech.dao.auth.IUserDao;
import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.mapper.auth.UserMapper;
import com.easydatalink.tech.orm.MyBatisDao;

@Repository
public class UserDao extends MyBatisDao<User> implements IUserDao {	
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private UserMapper userMapper;
	@Override
	public User findUserByLoginName(String username) {
		Map<String,Object> values=new HashMap<String,Object>();
		values.put("loginName", username);
		User user = (User) this.findUnique("getByCondition", values);
		return user;
	}

	@Override
	public List<User> getUsers(Map<String, Object> map) {
		List<User> users = userMapper.getUsers(map);
		return users;
	}
	
	@Override
	public void updateByPrimaryKeySelective(User user){
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	public User getUserByIdAndPassword(Map<String, Object> values){
		User user=userMapper.getUserByIdAndPassword(values);
		return user;
	}
	
	/**
	 * 根据登录名查询用户是否存在
	 * jie.qin
	 * 2017.12.13
	 * */
	public User getUserByLoginName(String loginName){
		return userMapper.getUserByLoginName(loginName);
	}
	/**
	 * 批量的获取用户列表
	 * @param list
	 * @return
	 */
	@Override
	public List<User> betchSelectUserById(List<Object> list) {
		return userMapper.betchSelectUserById(list);
	}

	@Override
	public List<User> findByCompanyCode(String companyCode) {
		return userMapper.findByCompanyCode(companyCode);
	}

	@Override
	public void deleteSubordinateUser(String userName) {
		userMapper.deleteSubordinateUser(userName);
	}

	@Override
	public User getUserByOpenId(Map<String, Object> values) {
		return userMapper.getUserByOpenId(values);
	}
}
