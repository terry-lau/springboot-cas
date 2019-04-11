package com.easydatalink.tech.dao.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.easydatalink.tech.dao.base.IBaseDictTypeDao;
import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.mapper.base.BaseDictTypeMapper;
import com.easydatalink.tech.orm.MyBatisDao;
@Repository
public class BaseDictTypeDao extends MyBatisDao<BaseDictType> implements IBaseDictTypeDao {	
	private static Logger logger = LoggerFactory.getLogger(BaseDictTypeDao.class);
	
	@Autowired
	public BaseDictTypeMapper baseDictTypeMapper;
	
	@Override
	public BaseDictType queryListByCode(String code) {
		return baseDictTypeMapper.queryListByCode(code);
	}
}
