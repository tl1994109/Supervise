package com.datcent.project.tool.diagram.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 组图管理表 tool_diagram
 * 
 * @author datcent
 * @date 2018-11-29
 */
public class Diagram extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 组图ID */
	private String diagramId;
	/** 组图名称 */
	private String diagramName;
	/** 组图代码 */
	private String diagramCode;
	/** 布局ID */
	private String layoutId;
	/** 备注 */
	private String remarks;

	public void setDiagramId(String diagramId) 
	{
		this.diagramId = diagramId;
	}

	public String getDiagramId() 
	{
		return diagramId;
	}
	public void setDiagramName(String diagramName) 
	{
		this.diagramName = diagramName;
	}

	public String getDiagramName() 
	{
		return diagramName;
	}
	public void setDiagramCode(String diagramCode) 
	{
		this.diagramCode = diagramCode;
	}

	public String getDiagramCode() 
	{
		return diagramCode;
	}
	public void setLayoutId(String layoutId) 
	{
		this.layoutId = layoutId;
	}

	public String getLayoutId() 
	{
		return layoutId;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("diagramId", getDiagramId())
            .append("diagramName", getDiagramName())
            .append("diagramCode", getDiagramCode())
            .append("layoutId", getLayoutId())
            .append("remarks", getRemarks())
            .toString();
    }
}
