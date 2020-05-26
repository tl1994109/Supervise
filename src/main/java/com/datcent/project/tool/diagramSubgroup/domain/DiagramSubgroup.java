package com.datcent.project.tool.diagramSubgroup.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 组图组件关系表 tool_diagram_subgroup
 * 
 * @author datcent
 * @date 2018-11-30
 */
public class DiagramSubgroup extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String id;
	/** 布局ID */
	private String diagramId;
	/** 组件ID */
	private String subgroupId;

	//无参构造
	public DiagramSubgroup() {
		super();
	}

	//参数构造
	public DiagramSubgroup(String diagramId, String subgroupId) {
		this.diagramId = diagramId;
		this.subgroupId = subgroupId;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setDiagramId(String diagramId) 
	{
		this.diagramId = diagramId;
	}

	public String getDiagramId() 
	{
		return diagramId;
	}
	public void setSubgroupId(String subgroupId) 
	{
		this.subgroupId = subgroupId;
	}

	public String getSubgroupId() 
	{
		return subgroupId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("diagramId", getDiagramId())
            .append("subgroupId", getSubgroupId())
            .toString();
    }
}
