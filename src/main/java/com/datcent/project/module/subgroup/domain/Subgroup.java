package com.datcent.project.module.subgroup.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

import java.util.Arrays;

/**
 * 组件管理表 tool_subgroup
 * 
 * @author datcent
 * @date 2018-11-29
 */
public class Subgroup extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 分页条数 */
	private Integer pageSize;
	/** 组件ID */
	private String subgroupId;
	/** 组件名称 */
	private String subgroupName;
	/** 组件代码 */
	private String subgroupCode;
	/** 显示方式(0 数据表格 1表单2 查询条件 3 柱状图 4饼状图 5报表) */
	private String subgroupStyle;
	/** 是否分页(0 是 1否) */
	private String isPaging;
	/** 分页风格(0 普通分页 1 高级分页) */
	private String pageStyle;
	/*事件json*/
	private byte[] eventJson;
	/*条件初始化json*/
	private byte[] iniconditJson;

    /* 主键*/
	private String  primaryKey;

    /*排序字段*/
	private  String sortField;

	private String tableName;

	private String tableComment;

	private String tableColumn;

	private String isKey;

	private String isSortable;

	private String visibleCols;

    public String getVisibleCols() {
        return visibleCols;
    }

    public void setVisibleCols(String visibleCols) {
        this.visibleCols = visibleCols;
    }

    public String getIsSortable() {
        return isSortable;
    }

    public void setIsSortable(String isSortable) {
        this.isSortable = isSortable;
    }

    public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public byte[] getEventJson() {
		return eventJson;
	}

	public void setEventJson(byte[] eventJson) {
		this.eventJson = eventJson;
	}

	public byte[] getIniconditJson() {
		return iniconditJson;
	}

	public void setIniconditJson(byte[] iniconditJson) {
		this.iniconditJson = iniconditJson;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getPageSize() 
	{
		return pageSize;
	}
	public void setSubgroupId(String subgroupId) 
	{
		this.subgroupId = subgroupId;
	}

	public String getSubgroupId() 
	{
		return subgroupId;
	}
	public void setSubgroupName(String subgroupName) 
	{
		this.subgroupName = subgroupName;
	}

	public String getSubgroupName() 
	{
		return subgroupName;
	}
	public void setSubgroupCode(String subgroupCode) 
	{
		this.subgroupCode = subgroupCode;
	}

	public String getSubgroupCode() 
	{
		return subgroupCode;
	}
	public void setSubgroupStyle(String subgroupStyle) 
	{
		this.subgroupStyle = subgroupStyle;
	}

	public String getSubgroupStyle() 
	{
		return subgroupStyle;
	}
	public void setIsPaging(String isPaging) 
	{
		this.isPaging = isPaging;
	}

	public String getIsPaging() 
	{
		return isPaging;
	}
	public void setPageStyle(String pageStyle) 
	{
		this.pageStyle = pageStyle;
	}

	public String getPageStyle() 
	{
		return pageStyle;
	}

	public String getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}

    @Override
    public String toString() {
        return "Subgroup{" +
                "pageSize=" + pageSize +
                ", subgroupId='" + subgroupId + '\'' +
                ", subgroupName='" + subgroupName + '\'' +
                ", subgroupCode='" + subgroupCode + '\'' +
                ", subgroupStyle='" + subgroupStyle + '\'' +
                ", isPaging='" + isPaging + '\'' +
                ", pageStyle='" + pageStyle + '\'' +
                ", eventJson=" + Arrays.toString(eventJson) +
                ", iniconditJson=" + Arrays.toString(iniconditJson) +
                ", primaryKey='" + primaryKey + '\'' +
                ", sortField='" + sortField + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", tableColumn='" + tableColumn + '\'' +
                ", isKey='" + isKey + '\'' +
                ", isSortable='" + isSortable + '\'' +
                ", visibleCols=" + visibleCols +
                '}';
    }
}
