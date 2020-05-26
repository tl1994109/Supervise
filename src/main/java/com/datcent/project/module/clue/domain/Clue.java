package com.datcent.project.module.clue.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 线索 表 busi_clue
 *
 * @author datcent
 * @date 2019-01-19
 */
public class Clue extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 线索ID */
	private String clueId;
	/** 线索来源(1系统策略筛查 2信访录入) */
	private String clueSource;
	/** 反映人 */
	private String clueFyr;
	/** 反映电话 */
	private String clueFyrdh;
	/** 反映对象 */
	private String clueFydx;
	/** 反映问题 */
	private String clueFywt;
	/** 线索状态 */
	private String clueStatus;
	/** 违纪事件ID */
	private String dubvioId;
	/** 策略ID */
	private String dubvioStrategyId;
	/** 策略名称 */
	private String dubvioStrategyName;
	/** 案件编号 */
	private String jbxxAjbh;
	/** 案号 */
	private String jbxxAh;
	/** 法官ID */
	private String jbxxCbrId;
	/** 法官姓名 */
	private String jbxxCbrName;
	/** 被告人 */
	private String jbxxBgr;
	/** 承办庭ID */
	private String jbxxCbtId;
	/** 承办庭名称 */
	private String jbxxCbtName;
	/** 案件类别 */
	private String jbxxAjlb;
	/** 收案日期 */
	private String jbxxSarq;
	/** 立案日期 */
	private String jbxxLarq;
	/** 分案日期 */
	private String jbxxFarq;
	/** 开庭日期 */
	private String jbxxKtrq;
	/** 结案日期 */
	private String jbxxJarq;
	/** 归档日期 */
	private String jbxxGdrq;
	/** 经办法院Id */
	private String jbfyId;
	/** 经办法院Name */
	private String jbfyName;
	/** 风险等级(1低风险 2中风险 3高风险) */
	private String risksLevel;
	/** 创建时间 */
	private Date createTime;
	/** 流程ID */
	private String processId;
	/** 流程状态 (审核状态（0通过 1 待审核 2 创建未发布）) */
	private String processStatus;
	/** 处置阶段 */
	private String clueCzjd;
	/** 处置结果*/
	private String clueCzjg;
	/** 线索承办人ID */
	private String clueCbrName;
	/** 备注 */
	private String clueRemarks;
	/** 更新时间 */
	private Date updateTime;
	/** 线索承办人中文名称 */
	private String CbrName;

	private String deleteFlag;

	//查询超期标识   不赋值为正常查询  1.查询暂存待查超期标识
	private String isCQ;

	public String getIsCQ() {
		return isCQ;
	}

	public void setIsCQ(String isCQ) {
		this.isCQ = isCQ;
	}

	private List<String> jbfyIdList;

	public List<String> getJbfyIdList() {
		return jbfyIdList;
	}

	public void setJbfyIdList(List<String> jbfyIdList) {
		this.jbfyIdList = jbfyIdList;
	}

	public String getCbrName() {
		return CbrName;
	}

	public void setCbrName(String cbrName) {
		CbrName = cbrName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setClueId(String clueId)
	{
		this.clueId = clueId;
	}

	public String getClueId()
	{
		return clueId;
	}
	public void setClueSource(String clueSource)
	{
		this.clueSource = clueSource;
	}

	public String getClueSource()
	{
		return clueSource;
	}
	public void setClueFyr(String clueFyr)
	{
		this.clueFyr = clueFyr;
	}

	public String getClueFyr()
	{
		return clueFyr;
	}
	public void setClueFyrdh(String clueFyrdh)
	{
		this.clueFyrdh = clueFyrdh;
	}

	public String getClueFyrdh()
	{
		return clueFyrdh;
	}
	public void setClueFydx(String clueFydx)
	{
		this.clueFydx = clueFydx;
	}

	public String getClueFydx()
	{
		return clueFydx;
	}
	public void setClueFywt(String clueFywt)
	{
		this.clueFywt = clueFywt;
	}

	public String getClueFywt()
	{
		return clueFywt;
	}
	public void setClueStatus(String clueStatus)
	{
		this.clueStatus = clueStatus;
	}

	public String getClueStatus()
	{
		return clueStatus;
	}
	public void setDubvioId(String dubvioId)
	{
		this.dubvioId = dubvioId;
	}

	public String getDubvioId()
	{
		return dubvioId;
	}
	public void setDubvioStrategyId(String dubvioStrategyId)
	{
		this.dubvioStrategyId = dubvioStrategyId;
	}

	public String getDubvioStrategyId()
	{
		return dubvioStrategyId;
	}
	public void setDubvioStrategyName(String dubvioStrategyName)
	{
		this.dubvioStrategyName = dubvioStrategyName;
	}

	public String getDubvioStrategyName()
	{
		return dubvioStrategyName;
	}
	public void setJbxxAjbh(String jbxxAjbh)
	{
		this.jbxxAjbh = jbxxAjbh;
	}

	public String getJbxxAjbh()
	{
		return jbxxAjbh;
	}
	public void setJbxxAh(String jbxxAh)
	{
		this.jbxxAh = jbxxAh;
	}

	public String getJbxxAh()
	{
		return jbxxAh;
	}
	public void setJbxxCbrId(String jbxxCbrId)
	{
		this.jbxxCbrId = jbxxCbrId;
	}

	public String getJbxxCbrId()
	{
		return jbxxCbrId;
	}
	public void setJbxxCbrName(String jbxxCbrName)
	{
		this.jbxxCbrName = jbxxCbrName;
	}

	public String getJbxxCbrName()
	{
		return jbxxCbrName;
	}
	public void setJbxxBgr(String jbxxBgr)
	{
		this.jbxxBgr = jbxxBgr;
	}

	public String getJbxxBgr()
	{
		return jbxxBgr;
	}
	public void setJbxxCbtId(String jbxxCbtId)
	{
		this.jbxxCbtId = jbxxCbtId;
	}

	public String getJbxxCbtId()
	{
		return jbxxCbtId;
	}
	public void setJbxxCbtName(String jbxxCbtName)
	{
		this.jbxxCbtName = jbxxCbtName;
	}

	public String getJbxxCbtName()
	{
		return jbxxCbtName;
	}
	public void setJbxxAjlb(String jbxxAjlb)
	{
		this.jbxxAjlb = jbxxAjlb;
	}

	public String getJbxxAjlb()
	{
		return jbxxAjlb;
	}
	public void setJbxxSarq(String jbxxSarq)
	{
		this.jbxxSarq = jbxxSarq;
	}

	public String getJbxxSarq()
	{
		return jbxxSarq;
	}
	public void setJbxxLarq(String jbxxLarq)
	{
		this.jbxxLarq = jbxxLarq;
	}

	public String getJbxxLarq()
	{
		return jbxxLarq;
	}
	public void setJbxxFarq(String jbxxFarq)
	{
		this.jbxxFarq = jbxxFarq;
	}

	public String getJbxxFarq()
	{
		return jbxxFarq;
	}
	public void setJbxxKtrq(String jbxxKtrq)
	{
		this.jbxxKtrq = jbxxKtrq;
	}

	public String getJbxxKtrq()
	{
		return jbxxKtrq;
	}
	public void setJbxxJarq(String jbxxJarq)
	{
		this.jbxxJarq = jbxxJarq;
	}

	public String getJbxxJarq()
	{
		return jbxxJarq;
	}
	public void setJbxxGdrq(String jbxxGdrq)
	{
		this.jbxxGdrq = jbxxGdrq;
	}

	public String getJbxxGdrq()
	{
		return jbxxGdrq;
	}
	public void setRisksLevel(String risksLevel)
	{
		this.risksLevel = risksLevel;
	}

	public String getRisksLevel()
	{
		return risksLevel;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getCreateTime()
	{
		return createTime;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}

	public String getProcessId()
	{
		return processId;
	}
	public void setProcessStatus(String processStatus)
	{
		this.processStatus = processStatus;
	}

	public String getProcessStatus()
	{
		return processStatus;
	}
	public void setClueCzjd(String clueCzjd)
	{
		this.clueCzjd = clueCzjd;
	}

	public String getClueCzjd()
	{
		return clueCzjd;
	}
	public void setClueCzjg(String clueCzjg)
	{
		this.clueCzjg = clueCzjg;
	}

	public String getClueCzjg()
	{
		return clueCzjg;
	}

	public void setClueRemarks(String clueRemarks)
	{
		this.clueRemarks = clueRemarks;
	}

	public String getClueRemarks()
	{
		return clueRemarks;
	}

	public String getClueCbrName() {
		return clueCbrName;
	}

	public void setClueCbrName(String clueCbrName) {
		this.clueCbrName = clueCbrName;
	}

	public String getJbfyId() {
		return jbfyId;
	}

	public void setJbfyId(String jbfyId) {
		this.jbfyId = jbfyId;
	}

	public String getJbfyName() {
		return jbfyName;
	}

	public void setJbfyName(String jbfyName) {
		this.jbfyName = jbfyName;
	}

	@Override
	public String toString() {
		return "Clue{" +
				"clueId='" + clueId + '\'' +
				", clueSource='" + clueSource + '\'' +
				", clueFyr='" + clueFyr + '\'' +
				", clueFyrdh='" + clueFyrdh + '\'' +
				", clueFydx='" + clueFydx + '\'' +
				", clueFywt='" + clueFywt + '\'' +
				", clueStatus='" + clueStatus + '\'' +
				", dubvioId='" + dubvioId + '\'' +
				", dubvioStrategyId='" + dubvioStrategyId + '\'' +
				", dubvioStrategyName='" + dubvioStrategyName + '\'' +
				", jbxxAjbh='" + jbxxAjbh + '\'' +
				", jbxxAh='" + jbxxAh + '\'' +
				", jbxxCbrId='" + jbxxCbrId + '\'' +
				", jbxxCbrName='" + jbxxCbrName + '\'' +
				", jbxxBgr='" + jbxxBgr + '\'' +
				", jbxxCbtId='" + jbxxCbtId + '\'' +
				", jbxxCbtName='" + jbxxCbtName + '\'' +
				", jbxxAjlb='" + jbxxAjlb + '\'' +
				", jbxxSarq='" + jbxxSarq + '\'' +
				", jbxxLarq='" + jbxxLarq + '\'' +
				", jbxxFarq='" + jbxxFarq + '\'' +
				", jbxxKtrq='" + jbxxKtrq + '\'' +
				", jbxxJarq='" + jbxxJarq + '\'' +
				", jbxxGdrq='" + jbxxGdrq + '\'' +
				", jbfyId='" + jbfyId + '\'' +
				", jbfyName='" + jbfyName + '\'' +
				", risksLevel='" + risksLevel + '\'' +
				", createTime=" + createTime +
				", processId='" + processId + '\'' +
				", processStatus='" + processStatus + '\'' +
				", clueCzjd='" + clueCzjd + '\'' +
				", clueCzjg='" + clueCzjg + '\'' +
				", clueCbrName='" + clueCbrName + '\'' +
				", clueRemarks='" + clueRemarks + '\'' +
				", updateTime=" + updateTime +
				", CbrName='" + CbrName + '\'' +
				", deleteFlag='" + deleteFlag + '\'' +
				'}';
	}
}
