package com.easydatalink.tech.service.base;

import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.service.IManager;

public interface IBaseDictTypeManager extends IManager<BaseDictType>{
	/**
	 * 根据dicttype_code查询字典表信息 qichao.duan 2018.04.10
	 * @param 
	 * @return
	 */
	BaseDictType queryListByCode(String code);

	void delByIds(String ids);
}