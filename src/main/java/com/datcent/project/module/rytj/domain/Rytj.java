package com.datcent.project.module.rytj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 人员统计表 sys_rytj
 * 
 * @author datcent
 * @date 2019-03-27
 */
public class Rytj extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 人员编号 */
	private String ryId;
	/** 人员姓名 */
	private String ryName;
	/** 承办庭编号 */
	private String cbtId;
	/** 承办庭 */
	private String cbtName;
	/** 经办法院编号 */
	private String jbfyId;
	/** 经办法院 */
	private String jbfyName;
	/** 旧存 */
	private String jc;
	/** 新收 */
	private String xs;
	/** 未结 */
	private String wj;
	/** 已结 */
	private String yj;
	/** 总计数量 */
	private String count;
	/** 创建时间 */
	private Date createTime;
	/** 统计时间 */
	private String countTime;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setRyId(String ryId) 
	{
		this.ryId = ryId;
	}

	public String getRyId() 
	{
		return ryId;
	}
	public void setRyName(String ryName) 
	{
		this.ryName = ryName;
	}

	public String getRyName() 
	{
		return ryName;
	}
	public void setCbtId(String cbtId) 
	{
		this.cbtId = cbtId;
	}

	public String getCbtId() 
	{
		return cbtId;
	}
	public void setCbtName(String cbtName) 
	{
		this.cbtName = cbtName;
	}

	public String getCbtName() 
	{
		return cbtName;
	}
	public void setJbfyId(String jbfyId) 
	{
		this.jbfyId = jbfyId;
	}

	public String getJbfyId() 
	{
		return jbfyId;
	}
	public void setJbfyName(String jbfyName) 
	{
		this.jbfyName = jbfyName;
	}

	public String getJbfyName() 
	{
		return jbfyName;
	}
	public void setJc(String jc) 
	{
		this.jc = jc;
	}

	public String getJc() 
	{
		return jc;
	}
	public void setXs(String xs) 
	{
		this.xs = xs;
	}

	public String getXs() 
	{
		return xs;
	}
	public void setWj(String wj) 
	{
		this.wj = wj;
	}

	public String getWj() 
	{
		return wj;
	}
	public void setYj(String yj) 
	{
		this.yj = yj;
	}

	public String getYj() 
	{
		return yj;
	}
	public void setCount(String count) 
	{
		this.count = count;
	}

	public String getCount() 
	{
		return count;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setCountTime(String countTime) 
	{
		this.countTime = countTime;
	}

	public String getCountTime() 
	{
		return countTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ryId", getRyId())
            .append("ryName", getRyName())
            .append("cbtId", getCbtId())
            .append("cbtName", getCbtName())
            .append("jbfyId", getJbfyId())
            .append("jbfyName", getJbfyName())
            .append("jc", getJc())
            .append("xs", getXs())
            .append("wj", getWj())
            .append("yj", getYj())
            .append("count", getCount())
            .append("createTime", getCreateTime())
            .append("countTime", getCountTime())
            .toString();
    }
}
