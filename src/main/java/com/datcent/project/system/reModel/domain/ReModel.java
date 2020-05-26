package com.datcent.project.system.reModel.domain;

import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 工作流模型表 act_re_model
 * 
 * @author datcent
 * @date 2019-01-08
 */
public class ReModel extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private String id;
	/** $column.columnComment */
	private Integer rev;
	/** 模型名称 */
	private String name;
	/** 键值 */
	private String key;
	/** 分类 */
	private String category;
	/** 创建时间 */
	private Date createTime;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 版本 */
	private Integer version;
	/** 流程信息 */
	private String metaInfo;
	/** 部署ID */
	private String deploymentId;
	/** $column.columnComment */
	private boolean editorSourceValueId;
	/** $column.columnComment */
	private boolean editorSourceExtraValueId;
	/** $column.columnComment */
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
	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getCategory() 
	{
		return category;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) 
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastUpdateTime() 
	{
		return lastUpdateTime;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setMetaInfo(String metaInfo) 
	{
		this.metaInfo = metaInfo;
	}

	public String getMetaInfo() 
	{
		return metaInfo;
	}
	public void setDeploymentId(String deploymentId) 
	{
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() 
	{
		return deploymentId;
	}
	public void setEditorSourceValueId(boolean editorSourceValueId)
	{
		this.editorSourceValueId = editorSourceValueId;
	}

	public boolean getEditorSourceValueId()
	{
		return editorSourceValueId;
	}
	public void setEditorSourceExtraValueId(boolean editorSourceExtraValueId)
	{
		this.editorSourceExtraValueId = editorSourceExtraValueId;
	}

	public boolean getEditorSourceExtraValueId()
	{
		return editorSourceExtraValueId;
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
            .append("name", getName())
            .append("key", getKey())
            .append("category", getCategory())
            .append("createTime", getCreateTime())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("version", getVersion())
            .append("metaInfo", getMetaInfo())
            .append("deploymentId", getDeploymentId())
            .append("editorSourceValueId", getEditorSourceValueId())
            .append("editorSourceExtraValueId", getEditorSourceExtraValueId())
            .append("tenantId", getTenantId())
            .toString();
    }
	public ReModel(){}
	public ReModel(Model model) {
		this.id = model.getId();
		this.name = model.getName();
		this.key = model.getKey();
		this.category = model.getCategory();
		this.createTime = model.getCreateTime();
		this.lastUpdateTime = model.getLastUpdateTime();
		this.version = model.getVersion();
		this.metaInfo = model.getMetaInfo();
		this.deploymentId = model.getDeploymentId();
		this.tenantId = model.getTenantId();
		this.editorSourceValueId = model.hasEditorSource();
		this.editorSourceExtraValueId = model.hasEditorSourceExtra();
	}
}
