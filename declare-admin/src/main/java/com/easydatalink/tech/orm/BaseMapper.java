package com.easydatalink.tech.orm;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.easydatalink.tech.entity.IdEntity;

public interface BaseMapper<T extends IdEntity> {
	void insert(T entity);
	
	void delete(Long id);
	/**
	 * return 0 if fail
	 */
	int update(T entity);
	
	T get(Long id);
	
	public List<T> findPage(RowBounds bounds);
}
