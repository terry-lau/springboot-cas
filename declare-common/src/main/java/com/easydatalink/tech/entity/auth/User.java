package com.easydatalink.tech.entity.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.utils.EncodeUtil;

/**
 * 用户信息
 * 
 * @author Terry
 * 
 */
public class User extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8253480111876133990L;

	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_LOCKED = "2";

	public static final String USER_TYPE_SUPER = "1";// 系统超级管理员
	public static final String USER_TYPE_NOMAL = "2";// 普通用户
	public static final String USER_TYPE_INTER = "3";// 接口用户
	// 登录用户访问Token
	private String token;
	private String userCode;// 用户编码
	private String userName;// 用户名称
	private String loginName;// 登陆名称
	private String password;// 登陆密码
	private String sex;// 性别
	private Long companyId;// 企业ID
	private String companyCode;// 企业编码
	private String companyName;// 企业名称
	private String birthday;// 生日
	private String email;// 邮箱
	private String cellphone;// 移动电话
	private String address;// 地址
	private String status;// 用户状态
	private String userRole;// 用户角色1：平台2：用户
	private String loginStatus;// 登陆状态
	private String userType;// 用户类型
	private String platformNo;// 平台编码
	private Long roleId;
	private String roleName;
	private String menuIds;
	private String BizField; // 业务字段
	private Long orgId;
	@SuppressWarnings("unused")
	private String jsonauthority;// 数据权限
	private Map<String, String> authorities;// 数据控制点

	public Map<String, String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Map<String, String> authorities) {
		this.authorities = authorities;
	}

	public String getJsonauthority() {
		Map<String, String> rmap = new HashMap<String, String>();
		if (this.authorities != null) {
			Iterator<String> it = authorities.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = authorities.get(key);
				if (!StringUtils.isEmpty(value.trim())) {
					rmap.put(key, value);
				}
			}
			if (rmap.isEmpty()) {
				return "{}";
			} else {
				return JSONObject.toJSONString(rmap);
			}
		}
		return "{}";
	}

	@SuppressWarnings("unchecked")
	public void setJsonauthority(String jsonauthority) {
		if (jsonauthority != null) {
			JSONObject obj = (JSONObject) JSONValue.parse(jsonauthority);
			authorities = new HashMap<String, String>();
			authorities.putAll(obj);
		} else {
			authorities = new HashMap<String, String>();
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String encodePassword(String pwd) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] b = messageDigest.digest(pwd.getBytes());
			return EncodeUtil.hexEncode(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pwd;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static void main(String[] args) {
		User user = new User();
		System.out.println(user.encodePassword("1"));
		;
	}

	public String getBizField() {
		return BizField;
	}

	public void setBizField(String bizField) {
		BizField = bizField;
	}
}
