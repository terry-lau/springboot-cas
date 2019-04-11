package com.easydatalink.tech.dao.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.easydatalink.tech.dao.base.ISystemLogDao;
import com.easydatalink.tech.entity.base.SystemLog;
import com.easydatalink.tech.orm.MyBatisDao;

@Repository
public class SystemLogDao extends MyBatisDao<SystemLog> implements ISystemLogDao {	
	private static Logger logger = LoggerFactory.getLogger(SystemLogDao.class);
}
