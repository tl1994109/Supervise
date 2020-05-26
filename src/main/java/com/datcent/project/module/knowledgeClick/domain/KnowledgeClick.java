package com.datcent.project.module.knowledgeClick.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 知识点击纪录表 sys_knowledge_click
 * 
 * @author datcent
 * @date 2019-02-20
 */
public class KnowledgeClick extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private String id;
	/** 知识编号 */
	private String knowledgeId;
	/** 查看时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date clickDate;
	/** 查看人id */
	private String clickUser;
	/** 知识标题 **/
	private String knowledgeTitle;
	/** 查看人姓名 **/
	private String clickUserName;

	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getClickUserName() {
		return clickUserName;
	}

	public void setClickUserName(String clickUserName) {
		this.clickUserName = clickUserName;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setKnowledgeId(String knowledgeId) 
	{
		this.knowledgeId = knowledgeId;
	}

	public String getKnowledgeId() 
	{
		return knowledgeId;
	}
	public void setClickDate(Date clickDate) 
	{
		this.clickDate = clickDate;
	}

	public Date getClickDate() 
	{
		return clickDate;
	}
	public void setClickUser(String clickUser) 
	{
		this.clickUser = clickUser;
	}

	public String getClickUser() 
	{
		return clickUser;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("knowledgeId", getKnowledgeId())
            .append("clickDate", getClickDate())
            .append("clickUser", getClickUser())
			.append("knowledgeTitle", getKnowledgeTitle())
            .toString();
    }
}
