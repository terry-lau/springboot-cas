package com.easydatalink.tech.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.easydatalink.tech.entity.auth.User;

public abstract class IdEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String isRemoved = "0";//是否删除0：使用1：删除
	protected Long version = 0l;
	protected Date createDate;//创建时间
	protected String createBy;//创建
	protected Date modifyDate;//修改时间
	protected String modifyBy;//修改人
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(String deleted) {
		this.isRemoved = deleted;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	
	/**
	 * 自动初始化属性 --- 根据场景设置 默认创建人，创建时间 修改人，修改时间
	 * @param user (当前操作用户对象)
	 */
	public void preInit(User user){
		if(null == this.id ){
			preInsert(user);
		}else{
			preUpdate(user);
		}
	}
	/**
	 * 新增 --- 设置 默认创建人，创建时间 修改人，修改时间
	 * @param user (当前操作用户对象)
	 */
	private void preInsert(User user){
        if (StringUtils.isBlank(this.createBy) && null != user){
            this.createBy = user.getLoginName();
            this.modifyBy = user.getLoginName();
        }
        this.createDate = new Date();
        this.modifyDate = this.createDate;
    }
	/**
	 * 修改 --- 设置 默认修改人，修改时间
	 * @param user (当前操作用户对象)
	 */
	private void preUpdate(User user){
        if (StringUtils.isBlank(this.modifyBy) && null != user){
            this.modifyBy = user.getLoginName();
        }
        this.modifyDate = new Date();
    }
}
