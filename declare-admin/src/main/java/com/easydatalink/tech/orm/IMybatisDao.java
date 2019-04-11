package com.easydatalink.tech.orm;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.page.Page;

/**
 * FOR MYBATIS
 * @author wei.liu
 * @param <T>
 */
@Repository
public interface IMybatisDao<T extends IdEntity> {

    public T get(final Long id);

    public T getByCode(final String code);

    public T getByCode(T entity);

    public T getCityCode(T entity);

    public List<T> getAll();

    public List<T> findBy(String propName, Object propValue);

    public T findUniqueBy(String propName, Object propValue);

    public List<T> findByIds(List<Long> ids);

    public List<T> find(final String sql, final Map<String, ?> values);

    public Object findUnique(final String sql, final Map<String, ?> values);

    public void delete(final Long id);

    public void delete(final T entity);

    public void delete(String sql, Map<String, ?> values);

    public void deleteComplete(final Long id);

    public void deleteBy(String propName, Object propValue);

    /**
     * 保存新增的对象.
     */
    public Long insert(final T entity);

    public boolean update(final T entity);

    public void batchInsert(List<T> entities);

    public void deleteAll();

    public void batchUpdate(List<T> entities);

    public void batchDelete(List<Long> ids);

    public <X> Page<X> findPage(final Page<X> page, String sql, final Map<String, ?> values);

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue);

    public long count(final String hql, final Map<String, ?> values);
}
