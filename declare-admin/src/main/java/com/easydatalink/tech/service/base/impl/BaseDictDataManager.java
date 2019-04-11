package com.easydatalink.tech.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easydatalink.tech.dao.base.IBaseDictDataDao;
import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.service.MybatisManager;
import com.easydatalink.tech.service.base.IBaseDictDataManager;

@Service
public class BaseDictDataManager extends MybatisManager<BaseDictData, IBaseDictDataDao> implements  IBaseDictDataManager {
    private static Logger logger = LoggerFactory.getLogger(BaseDictDataManager.class);
    @Autowired
    private IBaseDictDataDao baseDictDataDao;

    @Override
    public List<BaseDictData> getByDictTypeId(Long id) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("dictId", id);
        return dao.find("getByDictTypeId", values);
    }

    @Override
    public List<BaseDictData> findTaxStatus() {
        return dao.findTaxStatus();
    }

    @Override
    public List<BaseDictData> findReportStatus() {
        return dao.findReportStatus();
    }

    @Override
    public List<BaseDictData> findAuditStatus() {
        return dao.findAuditStatus();
    }

    @Override
    public BaseDictData getByDictTypeByValues(BaseDictData baseDictData) {
        return baseDictDataDao.getByDictTypeByValues(baseDictData);
    }
    
    @Override
    public List<BaseDictData> queryByDicttypeCode(Map<String, Object> value){
    	return baseDictDataDao.queryByDicttypeCode(value);
    }

	@Override
	public List<BaseDictData> findByCondtion(Map<String, Object> values) {
		return dao.find("getByDictTypeByValues", values);
	}
}
