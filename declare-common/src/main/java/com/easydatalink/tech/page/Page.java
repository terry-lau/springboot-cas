/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Page.java 838 2010-01-06 13:47:36Z calvinxiu $
 */
package com.easydatalink.tech.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装. 注意所有序号从1开始.
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 * @author calvin
 */
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342867522736889413L;

	public final static String ORDER_DIRECTION_ASC = "ASC";
	public final static String ORDER_DIRECTION_DESC = "DESC";

	/**
	 * 默认每页记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 原始页码
	 */
	private int plainPageNum = 1;
	/**
	 * 当前页码
	 */
	protected int pageNum = 1;
	/**
	 * 每页记录数
	 */
	protected int numPerPage = DEFAULT_PAGE_SIZE;

	// 默认按照id倒序排列
	private String orderField = "";
	private String orderDirection = "";
	/**
	 * 总页数
	 */
	private int totalPage = 1;

	/**
	 * 前一页
	 */
	private int prePage = 1;

	/**
	 * 下一页
	 */
	private int nextPage = 1;
	/**
	 * 总记录数
	 */
	protected long totalCount = 0;
	protected boolean autoCount = true;
	protected List<T> result = new ArrayList<T>();
	private String sEcho;

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNum - 1) * numPerPage) + 1;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * 返回 pageNum 的值
	 * 
	 * @return pageNum
	 */
	public int getPageNum() {
		if (pageNum > totalPage) {
			pageNum = totalPage;
		}
		return pageNum;
	}

	/**
	 * 设置 pageNum 的值
	 * 
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum > 0 ? pageNum : 1;
		this.plainPageNum = this.pageNum;
	}

	/**
	 * 返回 numPerPage 的值
	 * 
	 * @return numPerPage
	 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/**
	 * 设置 numPerPage 的值
	 * 
	 * @param numPerPage
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage > 0 ? numPerPage : 10;
	}

	/**
	 * 返回 orderField 的值
	 * 
	 * @return orderField
	 */
	public String getOrderField() {
		return orderField;
	}

	/**
	 * 设置 orderField 的值
	 * 
	 * @param orderField
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**
	 * 返回 orderDirection 的值
	 * 
	 * @return orderDirection
	 */
	public String getOrderDirection() {
		return orderDirection;
	}

	/**
	 * 设置 orderDirection 的值
	 * 
	 * @param orderDirection
	 */
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * 返回 totalPage 的值
	 * 
	 * @return totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置 totalPage 的值
	 * 
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 返回 prePage 的值
	 * 
	 * @return prePage
	 */
	public int getPrePage() {
		prePage = pageNum - 1;
		if (prePage < 1) {
			prePage = 1;
		}
		return prePage;
	}

	/**
	 * 设置 prePage 的值
	 * 
	 * @param prePage
	 */
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	/**
	 * 返回 nextPage 的值
	 * 
	 * @return nextPage
	 */
	public int getNextPage() {
		nextPage = pageNum + 1;
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}

		return nextPage;
	}

	/**
	 * 设置 nextPage 的值
	 * 
	 * @param nextPage
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * 返回 totalCount 的值
	 * 
	 * @return totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置 totalCount 的值
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		totalPage = (int) (totalCount - 1) / this.numPerPage + 1;
	}

	/**
	 * 返回 plainPageNum 的值
	 * 
	 * @return plainPageNum
	 */
	public int getPlainPageNum() {
		return plainPageNum;
	}

	/**
	 * 设置 plainPageNum 的值
	 * 
	 * @param plainPageNum
	 */
	public void setPlainPageNum(int plainPageNum) {
		this.plainPageNum = plainPageNum;
	}

	public int getStartIndex() {
		int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
		return pageNum * this.numPerPage;
	}

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}
}
