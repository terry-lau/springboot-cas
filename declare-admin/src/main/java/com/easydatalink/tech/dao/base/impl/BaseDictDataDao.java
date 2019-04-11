package com.easydatalink.tech.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.easydatalink.tech.dao.base.IBaseDictDataDao;
import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.mapper.base.BaseDictDataMapper;
import com.easydatalink.tech.orm.MyBatisDao;

@Repository
public class BaseDictDataDao extends MyBatisDao<BaseDictData> implements IBaseDictDataDao {
    private static Logger logger = LoggerFactory.getLogger(BaseDictDataDao.class);

    @Autowired
    private BaseDictDataMapper baseDictDataMapper;

    @Override
    public List<BaseDictData> findTaxStatus() {
        return baseDictDataMapper.findTaxStatus();
    }

    @Override
    public List<BaseDictData> findReportStatus() {
        return baseDictDataMapper.findReportStatus();
    }

    @Override
    public List<BaseDictData> findAuditStatus() {
        return baseDictDataMapper.findAuditStatus();
    }

    @Override
    public BaseDictData getByDictTypeByValues(BaseDictData baseDictData) {
        return baseDictDataMapper.getByDictTypeByValues(baseDictData);
    }

	@Override
	public List<BaseDictData> queryByDicttypeCode(Map<String, Object> value) {
		return baseDictDataMapper.queryByDicttypeCode(value);
	}
}
