package com.easydatalink.tech.dao.base;

import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.orm.IMybatisDao;

public interface IBaseDictTypeDao extends IMybatisDao<BaseDictType>{

	BaseDictType queryListByCode(String code);
}