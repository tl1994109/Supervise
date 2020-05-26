package com.datcent.project.module.strategy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 策略 表 busi_strategy
 * 
 * @author datcent
 * @date 2019-01-16
 */
public class Strategy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 策略ID */
	private Integer strategyId;
	/** 策略类型 */
	private String strategyType;
	/** 任务编号 */
	private String strategyJob;
	/** 策略名称 */
	private String strategyName;
	/** 策略定义 */
	private String strategyDefine;
	/** 策略方法 */
	private String strategyCode;
	/** 适用案件类别 */
	private String strategyAjlb;
	/** 策略参数 */
	private String strategyParam;
	/** 策略状态 */
	private String strategyStatus;
	/** 备注 */
	private String remarks;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 修改者 */
	private String updateBy;
	/** 修改时间 */
	private Date updateTime;
	/** 删除者 */
	private String deleteBy;
	/** 删除时间 */
	private Date deleteTime;
	/** 删除标识 */
	private String delFlag;

	public String getStrategyDefine() {
		return strategyDefine;
	}

	public void setStrategyDefine(String strategyDefine) {
		this.strategyDefine = strategyDefine;
	}

	public void setStrategyId(Integer strategyId)
	{
		this.strategyId = strategyId;
	}

	public Integer getStrategyId() 
	{
		return strategyId;
	}
	public void setStrategyType(String strategyType) 
	{
		this.strategyType = strategyType;
	}

	public String getStrategyType() 
	{
		return strategyType;
	}
	public void setStrategyName(String strategyName) 
	{
		this.strategyName = strategyName;
	}

	public String getStrategyName() 
	{
		return strategyName;
	}
	public void setStrategyCode(String strategyCode) 
	{
		this.strategyCode = strategyCode;
	}

	public String getStrategyCode() 
	{
		return strategyCode;
	}
	public void setStrategyParam(String strategyParam) 
	{
		this.strategyParam = strategyParam;
	}

	public String getStrategyParam() 
	{
		return strategyParam;
	}
	public void setStrategyStatus(String strategyStatus) 
	{
		this.strategyStatus = strategyStatus;
	}

	public String getStrategyStatus() 
	{
		return strategyStatus;
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
	public void setUpdateBy(String updateBy)
	{
		this.updateBy = updateBy;
	}
	@Override
	public String getUpdateBy()
	{
		return updateBy;
	}
	@Override
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getStrategyJob() {
		return strategyJob;
	}

	public void setStrategyJob(String strategyJob) {
		this.strategyJob = strategyJob;
	}

	@Override
	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setDeleteBy(String deleteBy)
	{
		this.deleteBy = deleteBy;
	}

	public String getDeleteBy() 
	{
		return deleteBy;
	}
	public void setDeleteTime(Date deleteTime) 
	{
		this.deleteTime = deleteTime;
	}

	public Date getDeleteTime() 
	{
		return deleteTime;
	}
	public void setDelFlag(String delFlag) 
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag() 
	{
		return delFlag;
	}

	public String getStrategyAjlb() {
		return strategyAjlb;
	}

	public void setStrategyAjlb(String strategyAjlb) {
		this.strategyAjlb = strategyAjlb;
	}

	@Override
	public String toString() {
		return "Strategy{" +
				"strategyId=" + strategyId +
				", strategyType='" + strategyType + '\'' +
				", strategyJob='" + strategyJob + '\'' +
				", strategyName='" + strategyName + '\'' +
				", strategyCode='" + strategyCode + '\'' +
				", strategyAjlb='" + strategyAjlb + '\'' +
				", strategyParam='" + strategyParam + '\'' +
				", strategyStatus='" + strategyStatus + '\'' +
				", remarks='" + remarks + '\'' +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				", deleteBy='" + deleteBy + '\'' +
				", deleteTime=" + deleteTime +
				", delFlag='" + delFlag + '\'' +
				'}';
	}
}
