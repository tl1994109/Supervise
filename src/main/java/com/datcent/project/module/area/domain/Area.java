package com.datcent.project.module.area.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 省市区数据表 sys_area
 * 
 * @author datcent
 * @date 2018-11-06
 */
public class Area extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private long id;
	/**  */
	private String name;
	/**  */
	private Long pid;

	public void setId(long id) 
	{
		this.id = id;
	}

	public long getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setPid(long pid) 
	{
		this.pid = pid;
	}

	public long getPid() 
	{
		return pid;
	}


    @Override
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("pid", getPid())
            .toString();
    }
}
