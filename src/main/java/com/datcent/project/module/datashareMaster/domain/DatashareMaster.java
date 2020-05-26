package com.datcent.project.module.datashareMaster.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 各法院数据权限表 sys_datashare_master
 * 
 * @author datcent
 * @date 2019-05-27
 */
public class DatashareMaster extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Integer datashareId;
	/** 法院ID */
	private String datashareCourtid;
	/** 状态 */
	private String status;
	/** 序号 */
	private String number;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;
	/**法院名称*/
	private String courtName;

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public void setDatashareId(Integer datashareId)
	{
		this.datashareId = datashareId;
	}

	public Integer getDatashareId() 
	{
		return datashareId;
	}
	public void setDatashareCourtid(String datashareCourtid) 
	{
		this.datashareCourtid = datashareCourtid;
	}

	public String getDatashareCourtid() 
	{
		return datashareCourtid;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}

	public String getNumber() 
	{
		return number;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}

	@Override
	public String toString() {
		return "DatashareMaster{" +
				"datashareId=" + datashareId +
				", datashareCourtid='" + datashareCourtid + '\'' +
				", status='" + status + '\'' +
				", number='" + number + '\'' +
				", createTime=" + createTime +
				", createBy='" + createBy + '\'' +
				", updateTime=" + updateTime +
				", updateBy='" + updateBy + '\'' +
				", courtName='" + courtName + '\'' +
				'}';
	}
}
