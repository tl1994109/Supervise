package com.datcent.project.module.dispositionForm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 问题处置单表 busi_disposition_form
 * 
 * @author datcent
 * @date 2019-01-18
 */
public class DispositionForm extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 表单ID */
	private String formId;
	/** 公告标题 */
	private String formTitle;
	/** 表单类型（1初步核实 2谈话函询 3立案调查 4审理） */
	private String formType;
	/** 表单内容 */
	private String formContent;
	/** 发布人 */
	private String releaseBy;
	/** 表单状态（0正常 1关闭） */
	private String status;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 删除标识（0正常 1关闭） */
	private String deleteFlag;
	/** 删除人 */
	private String deleteBy;
	/** 删除时间 */
	private Date deleteTime;
	/** 备注 */
	private String remark;

	public void setFormId(String formId) 
	{
		this.formId = formId;
	}

	public String getFormId() 
	{
		return formId;
	}
	public void setFormTitle(String formTitle) 
	{
		this.formTitle = formTitle;
	}

	public String getFormTitle() 
	{
		return formTitle;
	}
	public void setFormType(String formType) 
	{
		this.formType = formType;
	}

	public String getFormType() 
	{
		return formType;
	}
	public void setFormContent(String formContent) 
	{
		this.formContent = formContent;
	}

	public String getFormContent() 
	{
		return formContent;
	}
	public void setReleaseBy(String releaseBy) 
	{
		this.releaseBy = releaseBy;
	}

	public String getReleaseBy() 
	{
		return releaseBy;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setDeleteFlag(String deleteFlag) 
	{
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() 
	{
		return deleteFlag;
	}
	public void setDeleteBy(String deleteBy) 
	{
		this.deleteBy = deleteBy;
	}

	public String getDeleteBy() 
	{
		return deleteBy;
	}
	public void setDeleteTime(Date deleteTime) 
	{
		this.deleteTime = deleteTime;
	}

	public Date getDeleteTime() 
	{
		return deleteTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("formId", getFormId())
            .append("formTitle", getFormTitle())
            .append("formType", getFormType())
            .append("formContent", getFormContent())
            .append("releaseBy", getReleaseBy())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleteFlag", getDeleteFlag())
            .append("deleteBy", getDeleteBy())
            .append("deleteTime", getDeleteTime())
            .append("remark", getRemark())
            .toString();
    }
}
