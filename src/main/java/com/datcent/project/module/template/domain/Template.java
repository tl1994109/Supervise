package com.datcent.project.module.template.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 模板管理表 sys_template
 * 
 * @author datcent
 * @date 2019-02-15
 */
public class Template extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private String templateId;
	/** 父级 */
	private String parentId;
	/** 所有父级 */
	private String ancestors;
	/** 模板名称 */
	private String templateName;
	/** 转pdf地址 */
	private String templateUrl;

	/** doc地址 **/
	private String templateDocUrl;

	/** 排序 */
	private Integer orderNum;
	/**  0.使用中 1.删除 */
	private String status;
	/** 发布人 */
	private String createBy;
	/** 发布时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	/** 修改人 */
	private String updateBy;
	/** 修改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	/** 父级名称 **/
	private String parentName;

	private String remark;

	private String oldName;

	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTemplateDocUrl() {
		return templateDocUrl;
	}

	public void setTemplateDocUrl(String templateDocUrl) {
		this.templateDocUrl = templateDocUrl;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	public String getTemplateId() 
	{
		return templateId;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}

	public String getParentId() 
	{
		return parentId;
	}
	public void setAncestors(String ancestors) 
	{
		this.ancestors = ancestors;
	}

	public String getAncestors() 
	{
		return ancestors;
	}
	public void setTemplateName(String templateName) 
	{
		this.templateName = templateName;
	}

	public String getTemplateName() 
	{
		return templateName;
	}
	public void setTemplateUrl(String templateUrl) 
	{
		this.templateUrl = templateUrl;
	}

	public String getTemplateUrl() 
	{
		return templateUrl;
	}
	public void setOrderNum(Integer orderNum) 
	{
		this.orderNum = orderNum;
	}

	public Integer getOrderNum() 
	{
		return orderNum;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("templateName", getTemplateName())
            .append("templateUrl", getTemplateUrl())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
