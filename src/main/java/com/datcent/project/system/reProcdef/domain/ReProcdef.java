package com.datcent.project.system.reProcdef.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 业务流程定义表 act_re_procdef
 * 
 * @author datcent
 * @date 2019-01-08
 */
public class ReProcdef extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 流程编号 */
	private String id;
	/** rev */
	private Integer rev;
	/** 流程命名空间 */
	private String category;
	/** 流程名称 */
	private String name;
	/** 流程KEY */
	private String key;
	/** 流程版本号 */
	private Integer version;
	/** 部署编号 */
	private String deploymentId;
	/** 资源文件名称 */
	private String resourceName;
	/** 图片资源文件名称 */
	private String dgrmResourceName;
	/** 备注 */
	private String description;
	/** 是否有Start From Key */
	private boolean hasStartFormKey;
	/** HAS_GRAPHICAL_NOTATION_ */
	private boolean hasGraphicalNotation;
	/** 是否挂起，1 激活 2挂起 */
	private boolean suspensionState;
	/** 租户id */
	private String tenantId;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setRev(Integer rev) 
	{
		this.rev = rev;
	}

	public Integer getRev() 
	{
		return rev;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getCategory() 
	{
		return category;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getKey() 
	{
		return key;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setDeploymentId(String deploymentId) 
	{
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() 
	{
		return deploymentId;
	}
	public void setResourceName(String resourceName) 
	{
		this.resourceName = resourceName;
	}

	public String getResourceName() 
	{
		return resourceName;
	}
	public void setDgrmResourceName(String dgrmResourceName) 
	{
		this.dgrmResourceName = dgrmResourceName;
	}

	public String getDgrmResourceName() 
	{
		return dgrmResourceName;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
	public void setHasStartFormKey(boolean hasStartFormKey)
	{
		this.hasStartFormKey = hasStartFormKey;
	}

	public boolean getHasStartFormKey()
	{
		return hasStartFormKey;
	}
	public void setHasGraphicalNotation(boolean hasGraphicalNotation)
	{
		this.hasGraphicalNotation = hasGraphicalNotation;
	}

	public boolean getHasGraphicalNotation()
	{
		return hasGraphicalNotation;
	}
	public void setSuspensionState(boolean suspensionState)
	{
		this.suspensionState = suspensionState;
	}

	public boolean getSuspensionState()
	{
		return suspensionState;
	}
	public void setTenantId(String tenantId) 
	{
		this.tenantId = tenantId;
	}

	public String getTenantId() 
	{
		return tenantId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rev", getRev())
            .append("category", getCategory())
            .append("name", getName())
            .append("key", getKey())
            .append("version", getVersion())
            .append("deploymentId", getDeploymentId())
            .append("resourceName", getResourceName())
            .append("dgrmResourceName", getDgrmResourceName())
            .append("description", getDescription())
            .append("hasStartFormKey", getHasStartFormKey())
            .append("hasGraphicalNotation", getHasGraphicalNotation())
            .append("suspensionState", getSuspensionState())
            .append("tenantId", getTenantId())
            .toString();
    }

	public ReProcdef() {
	}
	public ReProcdef(org.activiti.engine.repository.ProcessDefinition p) {
		this.id=p.getId();
		this.category=p.getCategory();
		this.name=p.getName();
		this.key=p.getKey();
		this.description=p.getDescription();
		this.version=p.getVersion();
		this.resourceName=p.getResourceName();
		this.deploymentId=p.getDeploymentId();
		this.dgrmResourceName=p.getDiagramResourceName();
		this.hasStartFormKey=p.hasStartFormKey();
		this.hasGraphicalNotation=p.hasGraphicalNotation();
		this.suspensionState=p.isSuspended();
		this.tenantId=p.getTenantId();

	}
}
