package com.datcent.project.module.goingAbroad.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 出国情况登记表 arch_going_abroad
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class GoingAbroad extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String cgqkdjId;
	/** 法院组织表CID */
	private String cid;
	/** 出国种类 */
	private String cgqkdjCgzl;
	/** 批准单位 */
	private String cgqkdjPzdw;
	/** 目的地 */
	private String cgqkdjMdd;
	/** 起始时间 */
	private String cgqkdjQssj;
	/** 资金来源 */
	private String cgqkdjZjly;
	/** 出国(境)事由 */
	private String cgqkdjCgsy;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setCgqkdjId(String cgqkdjId) 
	{
		this.cgqkdjId = cgqkdjId;
	}

	public String getCgqkdjId() 
	{
		return cgqkdjId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setCgqkdjCgzl(String cgqkdjCgzl) 
	{
		this.cgqkdjCgzl = cgqkdjCgzl;
	}

	public String getCgqkdjCgzl() 
	{
		return cgqkdjCgzl;
	}
	public void setCgqkdjPzdw(String cgqkdjPzdw) 
	{
		this.cgqkdjPzdw = cgqkdjPzdw;
	}

	public String getCgqkdjPzdw() 
	{
		return cgqkdjPzdw;
	}
	public void setCgqkdjMdd(String cgqkdjMdd) 
	{
		this.cgqkdjMdd = cgqkdjMdd;
	}

	public String getCgqkdjMdd() 
	{
		return cgqkdjMdd;
	}
	public void setCgqkdjQssj(String cgqkdjQssj) 
	{
		this.cgqkdjQssj = cgqkdjQssj;
	}

	public String getCgqkdjQssj() 
	{
		return cgqkdjQssj;
	}
	public void setCgqkdjZjly(String cgqkdjZjly) 
	{
		this.cgqkdjZjly = cgqkdjZjly;
	}

	public String getCgqkdjZjly() 
	{
		return cgqkdjZjly;
	}
	public void setCgqkdjCgsy(String cgqkdjCgsy) 
	{
		this.cgqkdjCgsy = cgqkdjCgsy;
	}

	public String getCgqkdjCgsy() 
	{
		return cgqkdjCgsy;
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
            .append("cgqkdjId", getCgqkdjId())
            .append("cid", getCid())
            .append("cgqkdjCgzl", getCgqkdjCgzl())
            .append("cgqkdjPzdw", getCgqkdjPzdw())
            .append("cgqkdjMdd", getCgqkdjMdd())
            .append("cgqkdjQssj", getCgqkdjQssj())
            .append("cgqkdjZjly", getCgqkdjZjly())
            .append("cgqkdjCgsy", getCgqkdjCgsy())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
