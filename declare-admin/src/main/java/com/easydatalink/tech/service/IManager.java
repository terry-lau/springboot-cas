package com.easydatalink.tech.service;

import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.page.Page;

public interface IManager<T extends IdEntity> {
	public T get(final Long id);

	public List<T> getAll();

	public void delete(final Long id);

	public void delete(final T entity);

	public void batchDelete(List<Long> ids);

	public void batchDeleteComplete(List<Long> ids);

	public void batchInsert(List<T> entities);

	public void deleteComplete(Long id);
	
	

	/**
	 * 保存新增的对象.
	 */
	public Long insert(T entity);

	public boolean update(T entity);

	public void batchUpdate(List<T> entities);

	public T saveOrUpdate(T entity);

	public Page<T> findPage(final Page<T> page, String sql,
			final Map<String, ?> values);
	

}
