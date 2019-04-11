package com.easydatalink.tech.http;

import java.util.HashMap;
import java.util.Map;

import com.easydatalink.tech.page.Page;


/**
 * 返回对象
 * 
 * @author Terry
 *
 */
public class HttpResult {
	/**
	 * 返回错误类型
	 */
	private String result;
	/**
	 * 返回编码
	 */
	private int code = 200;
	/**
	 * 返回提示
	 */
	private String infos;
	/**
	 * 返回数据
	 */
	private Object data;
	/**
	 * 分页参数
	 */
	private Object page;

	/**
	 * 默认返回系统级别错误500
	 * 
	 * @return
	 */
	public static HttpResult error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	/**
	 * 默认返回系统级别错误500，并制定错误原因
	 * 
	 * @param msg
	 * @return
	 */
	public static HttpResult error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	/**
	 * 返回错误并制定错误码和错误提示
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static HttpResult error(int code, String msg) {
		HttpResult r = new HttpResult();
		r.setResult(HttpStatus.RESULT_ERROR);
		r.setCode(code);
		r.setInfos(msg);
		return r;
	}

	/**
	 * 正常返回，但是信息需要自定义
	 * 
	 * @param msg
	 * @return
	 */
	public static HttpResult ok(String msg) {
		HttpResult r = new HttpResult();
		r.setResult(HttpStatus.RESULT_SUCCESS);
		r.setInfos(msg);
		return r;
	}

	/**
	 * 正常返回数据
	 * 
	 * @param data
	 * @return
	 */
	public static HttpResult ok(Object data) {
		HttpResult r = new HttpResult();
		r.setResult(HttpStatus.RESULT_SUCCESS);
		r.setData(data);
		return r;
	}

	/**
	 * 正常返回，只给正常码200
	 * 
	 * @return
	 */
	public static HttpResult ok() {
		HttpResult r = new HttpResult();
		r.setResult(HttpStatus.RESULT_SUCCESS);
		return r;
	}

	/**
	 * 正常返回数据和分页参数
	 * 
	 * @param data
	 * @return
	 */
	public static HttpResult ok(Page<Object> page) {
		HttpResult r = new HttpResult();
		r.setResult(HttpStatus.RESULT_SUCCESS);
		r.setData(page.getResult());
		Map<String, Object> pageResult=new HashMap<String, Object>();
		pageResult.put("page", page.getPageNum());
		pageResult.put("pages", page.getTotalPage());
		pageResult.put("per_page", page.getNumPerPage());
		pageResult.put("has_next",  page.getPageNum()==page.getTotalPage()?true:false);
		pageResult.put("has_prev", page.getPageNum()==1?false:true);
		pageResult.put("total", page.getTotalCount());
		r.setPage(pageResult);
		return r;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

//	public static void main(String[] args) {
//		Map<String, Object> data=new HashMap<String, Object>();
//		data.put("1111", "aaaa");
//		Map<String, Object> page=new HashMap<String, Object>();
//		page.put("page", "1");
//		page.put("pages", "3");
//		page.put("per_page", "10");
//		page.put("has_next", true);
//		page.put("has_prev", false);
//		page.put("total", 27);
//		System.out.println(JSON.toJSONString(HttpResult.ok(data, page)));
//	}
}
