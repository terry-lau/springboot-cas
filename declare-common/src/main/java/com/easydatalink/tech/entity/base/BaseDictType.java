package com.easydatalink.tech.entity.base;

import com.easydatalink.tech.entity.IdEntity;

/**
 * 字典主档
 * @author Terry
 *
 */
public class BaseDictType extends IdEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3003700120901602425L;

	private String dicttypeCode;

    private String dicttypeName;


    public String getDicttypeCode() {
        return dicttypeCode;
    }

    public void setDicttypeCode(String dicttypeCode) {
        this.dicttypeCode = dicttypeCode == null ? null : dicttypeCode.trim();
    }

    public String getDicttypeName() {
        return dicttypeName;
    }

    public void setDicttypeName(String dicttypeName) {
        this.dicttypeName = dicttypeName == null ? null : dicttypeName.trim();
    }
}