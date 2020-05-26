package com.datcent.project.module.serving.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 主要任职情况表 arch_serving
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class Serving extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String id;
	/** 基本情况登记表ID */
	private String basicinforId;
	/** 起始时间 */
	private String startTime;
	/** 终止时间 */
	private String endTime;
	/** 所在单位 */
	private String toUnit;
	/** 担任职务 */
	private String assumeOffice;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setBasicinforId(String basicinforId) 
	{
		this.basicinforId = basicinforId;
	}

	public String getBasicinforId() 
	{
		return basicinforId;
	}
	public void setStartTime(String startTime) 
	{
		this.startTime = startTime;
	}

	public String getStartTime() 
	{
		return startTime;
	}
	public void setEndTime(String endTime) 
	{
		this.endTime = endTime;
	}

	public String getEndTime() 
	{
		return endTime;
	}
	public void setToUnit(String toUnit) 
	{
		this.toUnit = toUnit;
	}

	public String getToUnit() 
	{
		return toUnit;
	}
	public void setAssumeOffice(String assumeOffice) 
	{
		this.assumeOffice = assumeOffice;
	}

	public String getAssumeOffice() 
	{
		return assumeOffice;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("basicinforId", getBasicinforId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("toUnit", getToUnit())
            .append("assumeOffice", getAssumeOffice())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
