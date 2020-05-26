package com.datcent.project.module.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 知识管理表 sys_knowledge
 * 
 * @author datcent
 * @date 2019-02-13
 */
public class Knowledge extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private String knowledgeId;
	/** 标题 */
	private String knowledgeTitle;
	/** 内容 */
	private String knowledgeContent;
	/** 类型 0.政策法规 1.经验分享 */
	private String knowledgeType;
	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date knowledgeCreatetime;
	/** 创建人 */
	private String knowledgeCreateby;
	/* 姓名 */
	private String userName;

	/** 备注 **/
	private String remark;
	//类型一
	private String typeOne;
	//类型二
	private String typeTwo;

	private String fillWay;

	public String getFillWay() {
		return fillWay;
	}

	public void setFillWay(String fillWay) {
		this.fillWay = fillWay;
	}

	public String getTypeOne() {
		return typeOne;
	}

	public void setTypeOne(String typeOne) {
		this.typeOne = typeOne;
	}

	public String getTypeTwo() {
		return typeTwo;
	}

	public void setTypeTwo(String typeTwo) {
		this.typeTwo = typeTwo;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setKnowledgeId(String knowledgeId)
	{
		this.knowledgeId = knowledgeId;
	}

	public String getKnowledgeId() 
	{
		return knowledgeId;
	}
	public void setKnowledgeTitle(String knowledgeTitle) 
	{
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getKnowledgeTitle() 
	{
		return knowledgeTitle;
	}
	public void setKnowledgeContent(String knowledgeContent) 
	{
		this.knowledgeContent = knowledgeContent;
	}

	public String getKnowledgeContent() 
	{
		return knowledgeContent;
	}
	public void setKnowledgeType(String knowledgeType) 
	{
		this.knowledgeType = knowledgeType;
	}

	public String getKnowledgeType() 
	{
		return knowledgeType;
	}
	public void setKnowledgeCreatetime(Date knowledgeCreatetime) 
	{
		this.knowledgeCreatetime = knowledgeCreatetime;
	}

	public Date getKnowledgeCreatetime() 
	{
		return knowledgeCreatetime;
	}
	public void setKnowledgeCreateby(String knowledgeCreateby) 
	{
		this.knowledgeCreateby = knowledgeCreateby;
	}

	public String getKnowledgeCreateby() 
	{
		return knowledgeCreateby;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("knowledgeId", getKnowledgeId())
            .append("knowledgeTitle", getKnowledgeTitle())
            .append("knowledgeContent", getKnowledgeContent())
            .append("knowledgeType", getKnowledgeType())
            .append("knowledgeCreatetime", getKnowledgeCreatetime())
            .append("knowledgeCreateby", getKnowledgeCreateby())
            .toString();
    }
}
