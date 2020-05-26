package com.datcent.project.tool.layout.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 布局管理表 sys_layout
 * 
 * @author datcent
 * @date 2018-11-22
 */
public class Layout extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 布局ID */
	private String layoutId;
	/** 布局名称 */
	private String layoutName;
	/** 渲染方式 */
	private String romanceType;
	/** 布局handsontable--Json(row col) */
	private String romanceTable;
	/** 备注 */
	private String remark;

	public void setLayoutId(String layoutId) 
	{
		this.layoutId = layoutId;
	}

	public String getLayoutId() 
	{
		return layoutId;
	}
	public void setLayoutName(String layoutName) 
	{
		this.layoutName = layoutName;
	}

	public String getLayoutName() 
	{
		return layoutName;
	}
	public void setRomanceType(String romanceType) 
	{
		this.romanceType = romanceType;
	}

	public String getRomanceType() 
	{
		return romanceType;
	}
	public void setRomanceTable(String romanceTable) 
	{
		this.romanceTable = romanceTable;
	}

	public String getRomanceTable() 
	{
		return romanceTable;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("layoutId", getLayoutId())
            .append("layoutName", getLayoutName())
            .append("romanceType", getRomanceType())
            .append("romanceTable", getRomanceTable())
            .append("remark", getRemark())
            .toString();
    }
}
