package com.easydatalink.tech.entity.base;

import com.easydatalink.tech.entity.IdEntity;

/**
 * 系统日志表
 * @author Terry
 *
 */
public class SystemLog  extends IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6876762652795645423L;
	
	public static final String TYPE_LOGIN="1";
	public static final String TYPE_LOGOFF="2";
	public static final String TYPE_CONTROLLER="3";
	public static final String TYPE_SERVICE="4";
	
	private String description;//操作描述
	private String method;//调用的方法
	private String type;//日志类型1：controller2：service
	private String requestIp;//请求ip
	private String exceptionCode;//异常编码
	private String exceptionDetail;//异常详情
	private String params;//传递的参数
	private String platformNo;//平台号

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

}