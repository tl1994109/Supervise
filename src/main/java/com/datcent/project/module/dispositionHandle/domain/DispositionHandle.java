package com.datcent.project.module.dispositionHandle.domain;

import com.datcent.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 处置过程记录表 busi_disposition_handle
 * 
 * @author datcent
 */
public class DispositionHandle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录表id */
    private String deptId;

    /** 父节点id */
    private String parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 当前节点ID */
    private String nodeId;

    /** 线索ID */
    private String clueId;

    /** 节点名称 */
    private String deptName;

    /** 大节点类型 */
    private String nodeType;

    /** 节点标识 */
    private String nodeFlag;

    /** 意见 */
    private String suggestion;

    /** 处置结果 */
    private String result;
    /** 处置方式 */
    private String way;

    /** 状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父部门名称 */
    private String parentName;

    /** 创建时间*/
    private String createTimes;

    private Integer count;


    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeFlag() {
        return nodeFlag;
    }

    public void setNodeFlag(String nodeFlag) {
        this.nodeFlag = nodeFlag;
    }

    public String getCreateTimes() {
        return createTimes;
    }

    public void setCreateTimes(String createTimes) {
        this.createTimes = createTimes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DispositionHandle{" +
                "deptId='" + deptId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", ancestors='" + ancestors + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", clueId='" + clueId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", nodeFlag='" + nodeFlag + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", result='" + result + '\'' +
                ", way='" + way + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", parentName='" + parentName + '\'' +
                ", createTimes='" + createTimes + '\'' +
                ", count=" + count +
                '}';
    }
}
