package com.easydatalink.tech.dao.base;

import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.orm.IMybatisDao;

public interface IBaseDictDataDao extends IMybatisDao<BaseDictData> {

    List<BaseDictData> findTaxStatus();

    List<BaseDictData> findReportStatus();

    List<BaseDictData> findAuditStatus();

    public BaseDictData getByDictTypeByValues(BaseDictData baseDictData);

	public List<BaseDictData> queryByDicttypeCode(Map<String, Object> value);
}
