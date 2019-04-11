package com.easydatalink.tech.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.orm.IMybatisDao;
import com.easydatalink.tech.page.Page;

@Transactional
public class MybatisManager<T extends IdEntity, D extends IMybatisDao<T>> implements IManager<T> {
    @Autowired
    protected D dao;

    public void setDao(D dao) {
        this.dao = dao;
    }

    @Override
    public T get(Long id) {
        return dao.get(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(T entity) {
        dao.delete(entity.getId());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Long insert(T entity) {
        dao.insert(entity);
        return entity.getId();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T saveOrUpdate(T entity) {
        if (entity.getId() == null || get(entity.getId()) == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    public List<T> find(final String hql, final Map<String, ?> values) {
        return dao.find(hql, values);
    }

    public Page<T> findPage(Page<T> page, String sql, Map<String, ?> values) {
        return dao.findPage(page, sql, values);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean update(T entity) {
        dao.update(entity);
        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteComplete(final Long id) {
        dao.deleteComplete(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            dao.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void batchDeleteComplete(List<Long> ids) {
        for (Long id : ids) {
            dao.deleteComplete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void batchInsert(List<T> entities) {
        for (T t : entities) {
            dao.insert(t);
        }

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void batchUpdate(List<T> entities) {
        for (T t : entities) {
            dao.update(t);
        }

    }

}
