package com.datcent.project.module.handlingSituation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 党政纪处理及四拒情况表 arch_handling_situation
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class HandlingSituation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String cljsjqkId;
	/** 法院组织表CID */
	private String cid;
	/** 姓名 */
	private String cljsjqkXm;
	/** 单位 */
	private String cljsjqkDw;
	/** 职务 */
	private String cljsjqkZw;
	/** 党政纪处理-违纪违法事实 */
	private String dzjclWjwfss;
	/** 党政纪处理-处理情况 */
	private String dzjclClqk;
	/** 非党政纪处理-违纪违法事实 */
	private String fdzjclWjwfss;
	/** 非党政纪处理-处理情况 */
	private String fdzjclClqk;
	/** "四拒"情况登记-上交礼品礼金情况 */
	private String sjqkdjSjlpljqk;
	/** "四拒"情况登记-处置结果 */
	private String sjqkdjCzjg;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setCljsjqkId(String cljsjqkId) 
	{
		this.cljsjqkId = cljsjqkId;
	}

	public String getCljsjqkId() 
	{
		return cljsjqkId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setCljsjqkXm(String cljsjqkXm) 
	{
		this.cljsjqkXm = cljsjqkXm;
	}

	public String getCljsjqkXm() 
	{
		return cljsjqkXm;
	}
	public void setCljsjqkDw(String cljsjqkDw) 
	{
		this.cljsjqkDw = cljsjqkDw;
	}

	public String getCljsjqkDw() 
	{
		return cljsjqkDw;
	}
	public void setCljsjqkZw(String cljsjqkZw) 
	{
		this.cljsjqkZw = cljsjqkZw;
	}

	public String getCljsjqkZw() 
	{
		return cljsjqkZw;
	}
	public void setDzjclWjwfss(String dzjclWjwfss) 
	{
		this.dzjclWjwfss = dzjclWjwfss;
	}

	public String getDzjclWjwfss() 
	{
		return dzjclWjwfss;
	}
	public void setDzjclClqk(String dzjclClqk) 
	{
		this.dzjclClqk = dzjclClqk;
	}

	public String getDzjclClqk() 
	{
		return dzjclClqk;
	}
	public void setFdzjclWjwfss(String fdzjclWjwfss) 
	{
		this.fdzjclWjwfss = fdzjclWjwfss;
	}

	public String getFdzjclWjwfss() 
	{
		return fdzjclWjwfss;
	}
	public void setFdzjclClqk(String fdzjclClqk) 
	{
		this.fdzjclClqk = fdzjclClqk;
	}

	public String getFdzjclClqk() 
	{
		return fdzjclClqk;
	}
	public void setSjqkdjSjlpljqk(String sjqkdjSjlpljqk) 
	{
		this.sjqkdjSjlpljqk = sjqkdjSjlpljqk;
	}

	public String getSjqkdjSjlpljqk() 
	{
		return sjqkdjSjlpljqk;
	}
	public void setSjqkdjCzjg(String sjqkdjCzjg) 
	{
		this.sjqkdjCzjg = sjqkdjCzjg;
	}

	public String getSjqkdjCzjg() 
	{
		return sjqkdjCzjg;
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
            .append("cljsjqkId", getCljsjqkId())
            .append("cid", getCid())
            .append("cljsjqkXm", getCljsjqkXm())
            .append("cljsjqkDw", getCljsjqkDw())
            .append("cljsjqkZw", getCljsjqkZw())
            .append("dzjclWjwfss", getDzjclWjwfss())
            .append("dzjclClqk", getDzjclClqk())
            .append("fdzjclWjwfss", getFdzjclWjwfss())
            .append("fdzjclClqk", getFdzjclClqk())
            .append("sjqkdjSjlpljqk", getSjqkdjSjlpljqk())
            .append("sjqkdjCzjg", getSjqkdjCzjg())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
