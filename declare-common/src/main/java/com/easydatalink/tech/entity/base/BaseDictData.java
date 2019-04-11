package com.easydatalink.tech.entity.base;

import com.easydatalink.tech.entity.IdEntity;

/**
 * 字典明细
 * @author Terry
 *
 */
public class BaseDictData extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347568199068299093L;
	
	private Long dictId;
	private String dictCode;
	private String dictName;
	private String sortNo;
	
	private String value;//非数据库字段值
	
	public BaseDictData(){
	}

	public BaseDictData(Long dictId,String dictCode,String dictName,String sortNo){
		this.dictId=dictId;
		this.dictCode=dictCode;
		this.dictName=dictName;
		this.sortNo=sortNo;
	}
	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode == null ? null : dictCode.trim();
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName == null ? null : dictName.trim();
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}