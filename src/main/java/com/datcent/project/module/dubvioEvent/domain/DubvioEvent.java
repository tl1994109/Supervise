package com.datcent.project.module.dubvioEvent.domain;

import com.datcent.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 可疑违纪事件表 busi_dubvio_event
 *
 * @author datcent
 * @date 2019-01-12
 */
public class DubvioEvent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 违纪事件ID
     */
    private String dubvioId;
    /**
     * 策略ID
     */
    private String dubvioStrategyId;

    /**
     * 案件编号
     */
    private String jbxxAjbh;
    /**
     * 案号
     */
    private String jbxxAh;
    /**
     * 经办法院Id
     */
    private String jbfyId;
    /**
     * 法官ID
     */
    private String jbxxCbrId;
    /**
     * 法官姓名
     */
    private String jbxxCbrName;
    /**
     * 被告人
     */
    private String jbxxBgr;
    /**
     * 承办庭ID
     */
    private String jbxxCbtId;
    /**
     * 承办庭名称
     */
    private String jbxxCbtName;
    /**
     * 案件类别
     */
    private String jbxxAjlb;
    /**
     * 收案日期
     */
    private String jbxxSarq;
    /**
     * 立案日期
     */
    private String jbxxLarq;
    /**
     * 分案日期
     */
    private String jbxxFarq;
    /**
     * 开庭日期
     */
    private String jbxxKtrq;
    /**
     * 结案日期
     */
    private String jbxxJarq;
    /**
     * 归档日期
     */
    private String jbxxGdrq;
    /**
     * 风险等级(1低风险 2中风险 3高风险)
     */
    private String risksLevel;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 可疑违纪事件状态
     */
    private String status;
    /**
     * 删除状态(0 正常 1 已删除 2 待删除)
     */
    private String deleteFlag;

    /**信访*/
    /**信访序号*/
    @Excel(name = "序号")
    private String jbxxXfxh;
    /** 信访反映时间*/
    @Excel(name = "反映时间")
    private String jbxxXffysj;
    /**信访来源*/
    @Excel(name = "线索来源")
    private String jbxxXfly;
    /**反映人*/
    @Excel(name = "反映人")
    private String dubvioFyr;
    /**反映电话*/
    @Excel(name = "联系方式")
    private String dubvioFyrdh;
    /**反映对象*/
    @Excel(name = "反映对象")
    private String dubvioFydx;
    /**策略名称*/
    @Excel(name = "反映问题")
    private String dubvioStrategyName;
    /**信访处置情况*/
    @Excel(name = "处置情况")
    private String jbxxXfczqk;
    /**信访承办人*/
    @Excel(name = "承办人")
    private String jbxxXfcbr;
    /**信访处置结果*/
    @Excel(name = "处置结果")
    private String jbxxXfczjg;
    /**信访备注*/
    @Excel(name = "备注")
    private String jbxxXfbz;

    /*流程ID*/
    private String pid;
    /**删除人*/
    private String deleteBy;
    /**可疑事件来源(1系统策略筛查 2信访录入)*/
    private String dubvioSource;
    /**反映问题*/
    private String dubvioFywt;
    /**更新时间*/
    private Date updateTime;
    private String cause;
    /**经办法院名*/
    private String jbfyName;
    /**可疑违纪处置结果*/
    private String dubvioCzjg;
    /**案件名称*/
    private String ajmc;
    /**信访相似度*/
    private String similer;
    /** 信访录入人部门Id*/
    private String xfDeptId;

    private String dubvioStatus;


    private List<String> jbfyIdList;

    public List<String> getJbfyIdList() {
        return jbfyIdList;
    }

    public void setJbfyIdList(List<String> jbfyIdList) {
        this.jbfyIdList = jbfyIdList;
    }

    public String getDubvioStatus() {
        return dubvioStatus;
    }

    public void setDubvioStatus(String dubvioStatus) {
        this.dubvioStatus = dubvioStatus;
    }

    public String getXfDeptId() {
        return xfDeptId;
    }

    public void setXfDeptId(String xfDeptId) {
        this.xfDeptId = xfDeptId;
    }

    public String getJbxxXfxh() {
        return jbxxXfxh;
    }

    public void setJbxxXfxh(String jbxxXfxh) {
        this.jbxxXfxh = jbxxXfxh;
    }

    public String getJbxxXfly() {
        return jbxxXfly;
    }

    public void setJbxxXfly(String jbxxXfly) {
        this.jbxxXfly = jbxxXfly;
    }

    public String getJbxxXffysj() {
        return jbxxXffysj;
    }

    public void setJbxxXffysj(String jbxxXffysj) {
        this.jbxxXffysj = jbxxXffysj;
    }

    public String getJbxxXfczqk() {
        return jbxxXfczqk;
    }

    public void setJbxxXfczqk(String jbxxXfczqk) {
        this.jbxxXfczqk = jbxxXfczqk;
    }

    public String getJbxxXfcbr() {
        return jbxxXfcbr;
    }

    public void setJbxxXfcbr(String jbxxXfcbr) {
        this.jbxxXfcbr = jbxxXfcbr;
    }

    public String getJbxxXfczjg() {
        return jbxxXfczjg;
    }

    public void setJbxxXfczjg(String jbxxXfczjg) {
        this.jbxxXfczjg = jbxxXfczjg;
    }

    public String getJbxxXfbz() {
        return jbxxXfbz;
    }

    public void setJbxxXfbz(String jbxxXfbz) {
        this.jbxxXfbz = jbxxXfbz;
    }

    public String getDubvioCzjg() {
        return dubvioCzjg;
    }

    public void setDubvioCzjg(String dubvioCzjg) {
        this.dubvioCzjg = dubvioCzjg;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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

    public DubvioEvent() {
        super();
    }

    public String getSimiler() {
        return similer;
    }

    public void setSimiler(String similer) {
        this.similer = similer;
    }

    @Override
    public String toString() {
        return "DubvioEvent{" +
                "dubvioId='" + dubvioId + '\'' +
                ", dubvioStrategyId='" + dubvioStrategyId + '\'' +
                ", dubvioStrategyName='" + dubvioStrategyName + '\'' +
                ", jbxxAjbh='" + jbxxAjbh + '\'' +
                ", jbxxAh='" + jbxxAh + '\'' +
                ", jbfyId='" + jbfyId + '\'' +
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
                ", risksLevel='" + risksLevel + '\'' +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", pid='" + pid + '\'' +
                ", deleteBy='" + deleteBy + '\'' +
                ", dubvioSource='" + dubvioSource + '\'' +
                ", dubvioFyr='" + dubvioFyr + '\'' +
                ", dubvioFyrdh='" + dubvioFyrdh + '\'' +
                ", dubvioFydx='" + dubvioFydx + '\'' +
                ", dubvioFywt='" + dubvioFywt + '\'' +
                ", updateTime=" + updateTime +
                ", cause='" + cause + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", jbfyName='" + jbfyName + '\'' +
                ", dubvioCzjg='" + dubvioCzjg + '\'' +
                ", jbxxXfxh='" + jbxxXfxh + '\'' +
                ", jbxxXfly='" + jbxxXfly + '\'' +
                ", jbxxXffysj='" + jbxxXffysj + '\'' +
                ", jbxxXfczqk='" + jbxxXfczqk + '\'' +
                ", jbxxXfcbr='" + jbxxXfcbr + '\'' +
                ", jbxxXfbz='" + jbxxXfbz + '\'' +
                ", jbxxXfczjg='" + jbxxXfczjg + '\'' +
                ", xfDeptId='" + xfDeptId + '\'' +
                ", dubvioStatus='" + dubvioStatus + '\'' +
                '}';
    }

    public DubvioEvent(String jbxxAjbh, String xfDeptId, String dubvioStatus, String jbxxXfxh, String jbxxXfly, String jbxxXffysj, String jbxxXfczqk, String jbxxXfcbr, String dubvioCzjg, String jbxxXfbz, String jbxxXfczjg, String jbfyName, String ajmc, String jbxxAh, String jbxxCbrId, String jbxxCbrName, String jbxxBgr, String jbxxCbtId, String jbxxCbtName, String jbxxAjlb, String jbxxSarq, String jbxxLarq, String jbxxFarq, String jbxxKtrq, String jbxxJarq, String jbxxGdrq, String risksLevel, Date createTime, Date updateTime, String status, String pid, String deleteFlag, String deleteBy, String dubvioSource, String dubvioFyr, String dubvioFyrdh, String dubvioFydx, String dubvioFywt) {
        this.jbxxAjbh = jbxxAjbh;
        this.jbxxAh = jbxxAh;
        this.jbxxCbrId = jbxxCbrId;
        this.jbxxCbrName = jbxxCbrName;
        this.jbxxBgr = jbxxBgr;
        this.jbxxCbtId = jbxxCbtId;
        this.jbxxCbtName = jbxxCbtName;
        this.jbxxAjlb = jbxxAjlb;
        this.jbxxSarq = jbxxSarq;
        this.jbxxLarq = jbxxLarq;
        this.jbxxFarq = jbxxFarq;
        this.jbxxKtrq = jbxxKtrq;
        this.jbxxJarq = jbxxJarq;
        this.jbxxGdrq = jbxxGdrq;
        this.risksLevel = risksLevel;
        this.createTime = createTime;
        this.status = status;
        this.pid = pid;
        this.deleteFlag = deleteFlag;
        this.deleteBy = deleteBy;
        this.dubvioSource = dubvioSource;
        this.dubvioFyr = dubvioFyr;
        this.dubvioFyrdh = dubvioFyrdh;
        this.dubvioFydx = dubvioFydx;
        this.dubvioFywt = dubvioFywt;
        this.updateTime = updateTime;
        this.ajmc = ajmc;
        this.jbfyName = jbfyName;
        this.dubvioCzjg = dubvioCzjg;
        this.jbxxXfxh = jbxxXfxh;
        this.jbxxXfly = jbxxXfly;
        this.jbxxXffysj = jbxxXffysj;
        this.jbxxXfczqk = jbxxXfczqk;
        this.jbxxXfcbr = jbxxXfcbr;
        this.jbxxXfbz = jbxxXfbz;
        this.jbxxXfczjg = jbxxXfczjg;
        this.xfDeptId = xfDeptId;
        this.dubvioStatus = dubvioStatus;
    }


    public DubvioEvent(String jbxxAjbh, String jbxxAh, String jbxxCbrId, String jbxxCbrName, String jbxxBgr, String jbxxCbtId, String jbxxCbtName, String jbxxAjlb, String jbxxSarq, String jbxxLarq, String jbxxFarq, String jbxxKtrq, String jbxxJarq, String jbxxGdrq, String risksLevel, Date createTime, String status) {
        this.jbxxAjbh = jbxxAjbh;
        this.jbxxAh = jbxxAh;
        this.jbxxCbrId = jbxxCbrId;
        this.jbxxCbrName = jbxxCbrName;
        this.jbxxBgr = jbxxBgr;
        this.jbxxCbtId = jbxxCbtId;
        this.jbxxCbtName = jbxxCbtName;
        this.jbxxAjlb = jbxxAjlb;
        this.jbxxSarq = jbxxSarq;
        this.jbxxLarq = jbxxLarq;
        this.jbxxFarq = jbxxFarq;
        this.jbxxKtrq = jbxxKtrq;
        this.jbxxJarq = jbxxJarq;
        this.jbxxGdrq = jbxxGdrq;
        this.risksLevel = risksLevel;
        this.createTime = createTime;
        this.status = status;

    }

    public String getDubvioSource() {
        return dubvioSource;
    }

    public void setDubvioSource(String dubvioSource) {
        this.dubvioSource = dubvioSource;
    }

    public String getDubvioFyr() {
        return dubvioFyr;
    }

    public void setDubvioFyr(String dubvioFyr) {
        this.dubvioFyr = dubvioFyr;
    }

    public String getDubvioFyrdh() {
        return dubvioFyrdh;
    }

    public void setDubvioFyrdh(String dubvioFyrdh) {
        this.dubvioFyrdh = dubvioFyrdh;
    }

    public String getDubvioFydx() {
        return dubvioFydx;
    }

    public void setDubvioFydx(String dubvioFydx) {
        this.dubvioFydx = dubvioFydx;
    }

    public String getDubvioFywt() {
        return dubvioFywt;
    }

    public void setDubvioFywt(String dubvioFywt) {
        this.dubvioFywt = dubvioFywt;
    }


    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDubvioId(String dubvioId) {
        this.dubvioId = dubvioId;
    }

    public String getDubvioId() {
        return dubvioId;
    }

    public void setDubvioStrategyId(String dubvioStrategyId) {
        this.dubvioStrategyId = dubvioStrategyId;
    }

    public String getDubvioStrategyId() {
        return dubvioStrategyId;
    }

    public void setDubvioStrategyName(String dubvioStrategyName) {
        this.dubvioStrategyName = dubvioStrategyName;
    }

    public String getDubvioStrategyName() {
        return dubvioStrategyName;
    }

    public void setJbxxAjbh(String jbxxAjbh) {
        this.jbxxAjbh = jbxxAjbh;
    }

    public String getJbxxAjbh() {
        return jbxxAjbh;
    }

    public void setJbxxAh(String jbxxAh) {
        this.jbxxAh = jbxxAh;
    }

    public String getJbxxAh() {
        return jbxxAh;
    }

    public void setJbxxCbrId(String jbxxCbrId) {
        this.jbxxCbrId = jbxxCbrId;
    }

    public String getJbxxCbrId() {
        return jbxxCbrId;
    }

    public void setJbxxCbrName(String jbxxCbrName) {
        this.jbxxCbrName = jbxxCbrName;
    }

    public String getJbxxCbrName() {
        return jbxxCbrName;
    }

    public void setJbxxBgr(String jbxxBgr) {
        this.jbxxBgr = jbxxBgr;
    }

    public String getJbxxBgr() {
        return jbxxBgr;
    }

    public void setJbxxCbtId(String jbxxCbtId) {
        this.jbxxCbtId = jbxxCbtId;
    }

    public String getJbxxCbtId() {
        return jbxxCbtId;
    }

    public void setJbxxCbtName(String jbxxCbtName) {
        this.jbxxCbtName = jbxxCbtName;
    }

    public String getJbxxCbtName() {
        return jbxxCbtName;
    }

    public void setJbxxAjlb(String jbxxAjlb) {
        this.jbxxAjlb = jbxxAjlb;
    }

    public String getJbxxAjlb() {
        return jbxxAjlb;
    }

    public void setJbxxSarq(String jbxxSarq) {
        this.jbxxSarq = jbxxSarq;
    }

    public String getJbxxSarq() {
        return jbxxSarq;
    }

    public void setJbxxLarq(String jbxxLarq) {
        this.jbxxLarq = jbxxLarq;
    }

    public String getJbxxLarq() {
        return jbxxLarq;
    }

    public void setJbxxFarq(String jbxxFarq) {
        this.jbxxFarq = jbxxFarq;
    }

    public String getJbxxFarq() {
        return jbxxFarq;
    }

    public void setJbxxKtrq(String jbxxKtrq) {
        this.jbxxKtrq = jbxxKtrq;
    }

    public String getJbxxKtrq() {
        return jbxxKtrq;
    }

    public void setJbxxJarq(String jbxxJarq) {
        this.jbxxJarq = jbxxJarq;
    }

    public String getJbxxJarq() {
        return jbxxJarq;
    }

    public void setJbxxGdrq(String jbxxGdrq) {
        this.jbxxGdrq = jbxxGdrq;
    }

    public String getJbxxGdrq() {
        return jbxxGdrq;
    }

    public void setRisksLevel(String risksLevel) {
        this.risksLevel = risksLevel;
    }

    public String getRisksLevel() {
        return risksLevel;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
