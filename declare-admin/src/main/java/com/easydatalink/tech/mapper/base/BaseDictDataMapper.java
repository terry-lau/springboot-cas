package com.easydatalink.tech.mapper.base;

import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.orm.Mapper;

public interface BaseDictDataMapper extends Mapper<BaseDictData> {
    int insertSelective(BaseDictData record);
    int updateByPrimaryKeySelective(BaseDictData record);
    List<BaseDictData> findTaxStatus();
    List<BaseDictData> findReportStatus();
    List<BaseDictData> findAuditStatus();
    public BaseDictData getByDictTypeByValues(BaseDictData baseDictData);
	public List<BaseDictData> queryByDicttypeCode(Map<String, Object> value);
}
