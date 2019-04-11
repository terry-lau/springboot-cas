package com.easydatalink.tech.mapper.base;

import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.orm.Mapper;

public interface BaseDictTypeMapper extends Mapper<BaseDictType>{
    int insertSelective(BaseDictType record);
    int updateByPrimaryKeySelective(BaseDictType record);
    /**
	 * 根据dicttype_code查询字典表信息 qichao.duan 2018.04.10
	 * @param pAYMENT_STATE
	 * @return
	 */
	BaseDictType queryListByCode(String code);
}