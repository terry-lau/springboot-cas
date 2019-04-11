package com.easydatalink.tech.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.page.Page;

public interface IDao<T extends IdEntity> {

	public T get(final Long id);

	public T getByCode(final String code);
	
	public T getByCode(T entity);
	
	public T getCityCode(T entity);

	public List<T> getAll();
	
	public List<T> findBy(String propName, Object propValue);
	
	public T findUniqueBy(String propName, Object propValue);
	
	public List<T> findByIds(List<Long> ids) ;
	
	public List<T> find(final String sql, final Map<String, ?> values,User currentUser);
	
	public T findUnique(final String sql, final Map<String, ?> values,User currentUser);

	public void delete(final Long id);

	public void delete(final T entity);

	public int delete(String sql, Map<String, ?> values);
	
	public void deleteComplete(final Long id);
	
	public void deleteBy(String propName, Object propValue);
	
	public void deleteComplete(String propName, Object propValue);

	/**
	 * 保存新增的对象.
	 */
	public Long insert(final T entity);

	public boolean update(final T entity);

	public void batchInsert(List<T> entities);

	public void batchUpdate(List<T> entities);
	
	public void batchDelete(List<Long> ids);
	
	public <X> Page<X> findPage(final Page<X> page, String sql, final Map<String, ?> values,User currentUser);
	
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue);
	
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 */
	public boolean isPropertyUnique(String propertyName, Object value);
	
	public long count(final String hql, final Map<String, ?> values,User currentUser);
	
	/**
	 * <pre>
	 * 根据属性名批量查询.
	 * </pre>
	 *
	 * @param propName the prop name
	 * @param list the list
	 * @return the list
	 * @author 张涛  2013-1-5
	 */
	public List<T> findByLists(String propName,List<Object> list);
	public Page<T> findAllAfter(Page<T> page, Date date,User currentUser);
	public void execute(final String hql, final Map<String, ?> values,User currentUser);
	
}