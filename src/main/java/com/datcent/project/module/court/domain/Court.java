package com.datcent.project.module.court.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 法院 表 b_court
 * 
 * @author datcent
 * @date 2018-11-06
 */
public class Court extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 法院ID */
	private Integer courtId;
	/** 法院名称 */
	private String courtName;
	/** 法院地址 */
	private String courtAddress;
	/** 法院负责人 */
	private String courtPersonName;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 修改者 */
	private String updateBy;
	/** 修改时间 */
	private Date updateTime;
	/** 删除状态 0正常 1已删除 */
	private String deleteFlay;
	/** 删除者 */
	private String deleteBy;
	/** 删除时间 */
	private Date daleteTime;

	public void setCourtId(Integer courtId) 
	{
		this.courtId = courtId;
	}

	public Integer getCourtId() 
	{
		return courtId;
	}
	public void setCourtName(String courtName) 
	{
		this.courtName = courtName;
	}

	public String getCourtName() 
	{
		return courtName;
	}
	public void setCourtAddress(String courtAddress) 
	{
		this.courtAddress = courtAddress;
	}

	public String getCourtAddress() 
	{
		return courtAddress;
	}
	public void setCourtPersonName(String courtPersonName) 
	{
		this.courtPersonName = courtPersonName;
	}

	public String getCourtPersonName() 
	{
		return courtPersonName;
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
	public void setDeleteFlay(String deleteFlay) 
	{
		this.deleteFlay = deleteFlay;
	}

	public String getDeleteFlay() 
	{
		return deleteFlay;
	}
	public void setDeleteBy(String deleteBy) 
	{
		this.deleteBy = deleteBy;
	}

	public String getDeleteBy() 
	{
		return deleteBy;
	}
	public void setDaleteTime(Date daleteTime) 
	{
		this.daleteTime = daleteTime;
	}

	public Date getDaleteTime() 
	{
		return daleteTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("courtId", getCourtId())
            .append("courtName", getCourtName())
            .append("courtAddress", getCourtAddress())
            .append("courtPersonName", getCourtPersonName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleteFlay", getDeleteFlay())
            .append("deleteBy", getDeleteBy())
            .append("daleteTime", getDaleteTime())
            .toString();
    }
}
