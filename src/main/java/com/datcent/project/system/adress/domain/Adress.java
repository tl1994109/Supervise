package com.datcent.project.system.adress.domain;

import com.datcent.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 通讯录表 sys_adress
 *
 * @author datcent
 * @date 2019-02-15
 */
public class Adress extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Integer id;
	/** 姓名 */
	private String name;
	/** 单位 */
	private String dept;
	/** 手机号码 */
	private String phone;
	/** 职务 */
	private String type;

	/*** qq/微信*/
	private  String qqWx;
	/*** 邮箱*/
	private String email;

	/**办公室电话*/
	private String officePhone;

	/**传真*/
	private String fax;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String createTimes;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String updateTimes;
	/** 删除状态(0 正常 1 已删除) */
	private String deleteFlag;

    /**共享状态（0 未共享 1 已共享 ）*/
	private String status;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 所属法院编号
	 */
	private String jbfyId;


	public String getJbfyId() {
		return jbfyId;
	}

	public void setJbfyId(String jbfyId) {
		this.jbfyId = jbfyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public String getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(String updateTimes) {
		this.updateTimes = updateTimes;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId()
	{
		return id;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getDept()
	{
		return dept;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPhone()
	{
		return phone;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public String getQqWx() {
		return qqWx;
	}

	public void setQqWx(String qqWx) {
		this.qqWx = qqWx;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("dept", getDept())
            .append("phone", getPhone())
            .append("type", getType())
            .append("createTimes", getCreateTimes())
            .append("updateTimes", getUpdateTimes())
            .append("deleteFlag", getDeleteFlag())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("qqWx", getQqWx())
			.append("email", getEmail())
			.append("officePhone", getOfficePhone())
			.append("fax", getFax())
			.append("jbfyId", getJbfyId())
            .toString();
    }
}
