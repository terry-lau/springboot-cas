package com.easydatalink.tech.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easydatalink.tech.dao.base.IBaseDictDataDao;
import com.easydatalink.tech.dao.base.IBaseDictTypeDao;
import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.entity.base.BaseDictType;
import com.easydatalink.tech.service.MybatisManager;
import com.easydatalink.tech.service.base.IBaseDictTypeManager;

@Service
public class BaseDictTypeManager extends MybatisManager<BaseDictType, IBaseDictTypeDao> implements IBaseDictTypeManager {
	private static Logger logger = LoggerFactory.getLogger(BaseDictTypeManager.class);
	
	@Autowired
	private IBaseDictTypeDao ibaseDictTypeDao;
	@Autowired
	private IBaseDictDataDao baseDictDataDao;
	
	@Override
	public BaseDictType queryListByCode(String code) {
		return ibaseDictTypeDao.queryListByCode(code);
	}

	@Override
	public void delByIds(String ids) {
		Map<String,Object> values=new HashMap<String,Object>();
		String[] str=ids.split(",");
		for (String id : str) {
			dao.deleteComplete(Long.parseLong(id));
			values.put("dictId", Long.parseLong(id));
			List<BaseDictData> items=baseDictDataDao.find("getByDictTypeId", values);
			if(items!=null&&items.size()>0){
				for (BaseDictData baseDictData : items) {
					baseDictDataDao.deleteComplete(baseDictData.getId());
				}
			}
		}
	}
}
