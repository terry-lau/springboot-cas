package com.easydatalink.tech.dao.auth;

import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.orm.IMybatisDao;

public interface IUserDao extends IMybatisDao<User>{

	User findUserByLoginName(String username);

	List<User> getUsers(Map<String, Object> map);
	
	void updateByPrimaryKeySelective(User user);
	
	User getUserByIdAndPassword(Map<String, Object> values);
	
	/**
	 * 根据登录名查询用户是否存在
	 * jie.qin
	 * 2017.12.13
	 * */
	public User getUserByLoginName(String loginName);
	/**
	 * 批量的获取用户列表
	 * @param list
	 * @return
	 */
	public List<User> betchSelectUserById(List<Object> list);


	public List<User> findByCompanyCode(String companyCode);

	void deleteSubordinateUser(String userName);

    public User getUserByOpenId(Map<String, Object> values);
}