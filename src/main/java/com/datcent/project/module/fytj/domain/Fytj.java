package com.datcent.project.module.fytj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 法院统计表 sys_fytj
 * 
 * @author datcent
 * @date 2019-03-22
 */
public class Fytj extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 经办法院 */
	private String jbfyName;
	/** 经办法院编号 */
	private String jbfyId;
	/** 旧存数量 */
	private String jc;
	/** 新收数量 */
	private String xs;
	/** 未结数量 */
	private String wj;
	/** 已结数量 */
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
	public void setJbfyName(String jbfyName) 
	{
		this.jbfyName = jbfyName;
	}

	public String getJbfyName() 
	{
		return jbfyName;
	}
	public void setJbfyId(String jbfyId) 
	{
		this.jbfyId = jbfyId;
	}

	public String getJbfyId() 
	{
		return jbfyId;
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
            .append("jbfyName", getJbfyName())
            .append("jbfyId", getJbfyId())
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
