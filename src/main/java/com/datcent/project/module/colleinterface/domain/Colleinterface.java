package com.datcent.project.module.colleinterface.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 采集接口表 busi_colleinterface
 * 
 * @author datcent
 * @date 2019-01-14
 */
public class Colleinterface extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 采集接口ID */
	private String colleinterfaceId;
	/** 任务id */
	private String colleinterfaceJob;
	/** 采集接口名称 */
	private String colleinterfaceName;
	/** 采集接口方法名 */
	private String colleinterfaceMethed;
	/** 采集接口参数 */
	private String colleinterfaceParam;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;
	/** 删除标识 */
	private String deleteFlag;
	/** 删除时间 */
	private Date deleteTime;
	/** 删除人 */
	private String deleteBy;
	/** 备注 */
	private String remarks;


	public void setColleinterfaceId(String colleinterfaceId) 
	{
		this.colleinterfaceId = colleinterfaceId;
	}

	public String getColleinterfaceId() 
	{
		return colleinterfaceId;
	}
	public void setColleinterfaceName(String colleinterfaceName) 
	{
		this.colleinterfaceName = colleinterfaceName;
	}

	public String getColleinterfaceName() 
	{
		return colleinterfaceName;
	}
	public void setColleinterfaceMethed(String colleinterfaceMethed) 
	{
		this.colleinterfaceMethed = colleinterfaceMethed;
	}

	public String getColleinterfaceMethed() 
	{
		return colleinterfaceMethed;
	}
	public void setColleinterfaceParam(String colleinterfaceParam)
	{
		this.colleinterfaceParam = colleinterfaceParam;
	}

	public String getColleinterfaceJob() {
		return colleinterfaceJob;
	}

	public void setColleinterfaceJob(String colleinterfaceJob) {
		this.colleinterfaceJob = colleinterfaceJob;
	}

	public String getColleinterfaceParam()
	{
		return colleinterfaceParam;
	}
	@Override
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	@Override
	public Date getCreateTime() 
	{
		return createTime;
	}
	@Override
	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}
	@Override
	public String getCreateBy() 
	{
		return createBy;
	}
	@Override
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	@Override
	public Date getUpdateTime() 
	{
		return updateTime;
	}
	@Override
	public void setUpdateBy(String updateBy)
	{
		this.updateBy = updateBy;
	}
	@Override
	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setDeleteFlag(String deleteFlag) 
	{
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() 
	{
		return deleteFlag;
	}
	public void setDeleteTime(Date deleteTime) 
	{
		this.deleteTime = deleteTime;
	}

	public Date getDeleteTime() 
	{
		return deleteTime;
	}
	public void setDeleteBy(String deleteBy) 
	{
		this.deleteBy = deleteBy;
	}

	public String getDeleteBy() 
	{
		return deleteBy;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

	@Override
	public String toString() {
		return "Colleinterface{" +
				"colleinterfaceId='" + colleinterfaceId + '\'' +
				", colleinterfaceJob='" + colleinterfaceJob + '\'' +
				", colleinterfaceName='" + colleinterfaceName + '\'' +
				", colleinterfaceMethed='" + colleinterfaceMethed + '\'' +
				", colleinterfaceParam='" + colleinterfaceParam + '\'' +
				", createTime=" + createTime +
				", createBy='" + createBy + '\'' +
				", updateTime=" + updateTime +
				", updateBy='" + updateBy + '\'' +
				", deleteFlag='" + deleteFlag + '\'' +
				", deleteTime=" + deleteTime +
				", deleteBy='" + deleteBy + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}
