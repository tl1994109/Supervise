package com.datcent.project.module.annualIncome.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 家庭全年收入情况表 arch_annual_income
 * 
 * @author datcent
 * @date 2019-04-03
 */
public class AnnualIncome extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String id;
	/** 法院组织表CID */
	private String cid;
	/** 工资收入 */
	private String jtqnsrGzsr;
	/** 工资外收入 */
	private String jtqnsrGzwsr;
	/** 家庭其他财产 */
	private String jtqnsrGqtcc;
	/** 备注 */
	private String remarks;
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
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setJtqnsrGzsr(String jtqnsrGzsr) 
	{
		this.jtqnsrGzsr = jtqnsrGzsr;
	}

	public String getJtqnsrGzsr() 
	{
		return jtqnsrGzsr;
	}
	public void setJtqnsrGzwsr(String jtqnsrGzwsr) 
	{
		this.jtqnsrGzwsr = jtqnsrGzwsr;
	}

	public String getJtqnsrGqtcc() {
		return jtqnsrGqtcc;
	}

	public void setJtqnsrGqtcc(String jtqnsrGqtcc) {
		this.jtqnsrGqtcc = jtqnsrGqtcc;
	}

	public String getJtqnsrGzwsr()
	{
		return jtqnsrGzwsr;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
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
		return "AnnualIncome{" +
				"id='" + id + '\'' +
				", cid='" + cid + '\'' +
				", jtqnsrGzsr='" + jtqnsrGzsr + '\'' +
				", jtqnsrGzwsr='" + jtqnsrGzwsr + '\'' +
				", jtqnsrGqtcc='" + jtqnsrGqtcc + '\'' +
				", remarks='" + remarks + '\'' +
				", createTime=" + createTime +
				", createBy='" + createBy + '\'' +
				", updateTime=" + updateTime +
				", updateBy='" + updateBy + '\'' +
				'}';
	}
}
