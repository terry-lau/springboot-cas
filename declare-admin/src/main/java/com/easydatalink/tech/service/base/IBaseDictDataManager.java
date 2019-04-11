package com.easydatalink.tech.service.base;

import java.util.List;
import java.util.Map;

import com.easydatalink.tech.entity.base.BaseDictData;
import com.easydatalink.tech.service.IManager;

public interface IBaseDictDataManager extends IManager<BaseDictData> {

    List<BaseDictData> getByDictTypeId(Long id);

    List<BaseDictData> findTaxStatus();

    List<BaseDictData> findReportStatus();

    List<BaseDictData> findAuditStatus();

    public BaseDictData getByDictTypeByValues(BaseDictData baseDictData);
    
    public List<BaseDictData> queryByDicttypeCode(Map<String, Object> value);

	List<BaseDictData> findByCondtion(Map<String, Object> values);
}
