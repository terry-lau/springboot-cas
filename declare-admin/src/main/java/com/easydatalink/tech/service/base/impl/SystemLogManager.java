package com.easydatalink.tech.service.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.easydatalink.tech.dao.base.ISystemLogDao;
import com.easydatalink.tech.entity.base.SystemLog;
import com.easydatalink.tech.service.MybatisManager;
import com.easydatalink.tech.service.base.ISystemLogManager;

@Service
public class SystemLogManager extends MybatisManager<SystemLog, ISystemLogDao> implements ISystemLogManager {
	private static Logger logger = LoggerFactory.getLogger(SystemLogManager.class);
}
