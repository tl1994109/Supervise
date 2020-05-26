package com.datcent.project.module.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 流程表 oa_process
 * 
 * @author datcent
 * @date 2018-11-07
 */
public class Process extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 流程ID */
	private String processId;
	/** 流程名称 */
	private String processName;
	/** 流程配置 */
	private String processConfig;
	/** 备注 */
	private String remarks;
	/** 状态 0启用 1禁用 2删除 */
	private String status;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 修改者 */
	private String updateBy;
	/** 修改时间 */
	private Date updateTime;
	/**  */
	private String deleteBy;
	/**  */
	private Date deleteTime;

	public void setProcessId(String processId) 
	{
		this.processId = processId;
	}

	public String getProcessId() 
	{
		return processId;
	}
	public void setProcessName(String processName) 
	{
		this.processName = processName;
	}

	public String getProcessName() 
	{
		return processName;
	}
	public void setProcessConfig(String processConfig) 
	{
		this.processConfig = processConfig;
	}

	public String getProcessConfig() 
	{
		return processConfig;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("processConfig", getProcessConfig())
            .append("remarks", getRemarks())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleteBy", getDeleteBy())
            .append("deleteTime", getDeleteTime())
            .toString();
    }
}
