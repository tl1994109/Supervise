package com.datcent.project.module.massesReport.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 群众举报情况表 arch_masses_report
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class MassesReport extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String xfjbqkId;
	/** 法院组织表CID */
	private String cid;
	/** 姓名 */
	private String xfjbqkXm;
	/** 单位 */
	private String xfjbqkDw;
	/** 职务 */
	private String xfjbqkZw;
	/** 举报人姓名 */
	private String xfjbqkJbrxm;
	/** 举报时间 */
	private String xfjbqkJbsj;
	/** 举报方式 */
	private String xfjbqkJbfs;
	/** 受理途径及受理人 */
	private String xfjbqkSltjjslr;
	/** 举报内容 */
	private String xfjbqkJbnr;
	/** 处理情况 */
	private String xfjbqkClqk;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setXfjbqkId(String xfjbqkId) 
	{
		this.xfjbqkId = xfjbqkId;
	}

	public String getXfjbqkId() 
	{
		return xfjbqkId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setXfjbqkXm(String xfjbqkXm) 
	{
		this.xfjbqkXm = xfjbqkXm;
	}

	public String getXfjbqkXm() 
	{
		return xfjbqkXm;
	}
	public void setXfjbqkDw(String xfjbqkDw) 
	{
		this.xfjbqkDw = xfjbqkDw;
	}

	public String getXfjbqkDw() 
	{
		return xfjbqkDw;
	}
	public void setXfjbqkZw(String xfjbqkZw) 
	{
		this.xfjbqkZw = xfjbqkZw;
	}

	public String getXfjbqkZw() 
	{
		return xfjbqkZw;
	}
	public void setXfjbqkJbrxm(String xfjbqkJbrxm) 
	{
		this.xfjbqkJbrxm = xfjbqkJbrxm;
	}

	public String getXfjbqkJbrxm() 
	{
		return xfjbqkJbrxm;
	}
	public void setXfjbqkJbsj(String xfjbqkJbsj) 
	{
		this.xfjbqkJbsj = xfjbqkJbsj;
	}

	public String getXfjbqkJbsj() 
	{
		return xfjbqkJbsj;
	}
	public void setXfjbqkJbfs(String xfjbqkJbfs) 
	{
		this.xfjbqkJbfs = xfjbqkJbfs;
	}

	public String getXfjbqkJbfs() 
	{
		return xfjbqkJbfs;
	}
	public void setXfjbqkSltjjslr(String xfjbqkSltjjslr) 
	{
		this.xfjbqkSltjjslr = xfjbqkSltjjslr;
	}

	public String getXfjbqkSltjjslr() 
	{
		return xfjbqkSltjjslr;
	}
	public void setXfjbqkJbnr(String xfjbqkJbnr) 
	{
		this.xfjbqkJbnr = xfjbqkJbnr;
	}

	public String getXfjbqkJbnr() 
	{
		return xfjbqkJbnr;
	}
	public void setXfjbqkClqk(String xfjbqkClqk) 
	{
		this.xfjbqkClqk = xfjbqkClqk;
	}

	public String getXfjbqkClqk() 
	{
		return xfjbqkClqk;
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
            .append("xfjbqkId", getXfjbqkId())
            .append("cid", getCid())
            .append("xfjbqkXm", getXfjbqkXm())
            .append("xfjbqkDw", getXfjbqkDw())
            .append("xfjbqkZw", getXfjbqkZw())
            .append("xfjbqkJbrxm", getXfjbqkJbrxm())
            .append("xfjbqkJbsj", getXfjbqkJbsj())
            .append("xfjbqkJbfs", getXfjbqkJbfs())
            .append("xfjbqkSltjjslr", getXfjbqkSltjjslr())
            .append("xfjbqkJbnr", getXfjbqkJbnr())
            .append("xfjbqkClqk", getXfjbqkClqk())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
