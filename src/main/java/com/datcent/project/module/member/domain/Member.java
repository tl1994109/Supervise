package com.datcent.project.module.member.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 家庭成员情况表 arch_member
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class Member extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String id;
	/** 基本情况登记表ID */
	private String basicinforId;
	/** 与本人关系 */
	private String relationship;
	/** 姓名 */
	private String name;
	/** 出生年月 */
	private String birthday;
	/** 政治面貌 */
	private String politicalOutlook;
	/** 工作单位及职位 */
	private String unitPost;
	/** 单位性质 */
	private String unitProperty;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setBasicinforId(String basicinforId) 
	{
		this.basicinforId = basicinforId;
	}

	public String getBasicinforId() 
	{
		return basicinforId;
	}
	public void setRelationship(String relationship) 
	{
		this.relationship = relationship;
	}

	public String getRelationship() 
	{
		return relationship;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setBirthday(String birthday) 
	{
		this.birthday = birthday;
	}

	public String getBirthday() 
	{
		return birthday;
	}
	public void setPoliticalOutlook(String politicalOutlook) 
	{
		this.politicalOutlook = politicalOutlook;
	}

	public String getPoliticalOutlook() 
	{
		return politicalOutlook;
	}
	public void setUnitPost(String unitPost) 
	{
		this.unitPost = unitPost;
	}

	public String getUnitPost() 
	{
		return unitPost;
	}
	public void setUnitProperty(String unitProperty) 
	{
		this.unitProperty = unitProperty;
	}

	public String getUnitProperty() 
	{
		return unitProperty;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("basicinforId", getBasicinforId())
            .append("relationship", getRelationship())
            .append("name", getName())
            .append("birthday", getBirthday())
            .append("politicalOutlook", getPoliticalOutlook())
            .append("unitPost", getUnitPost())
            .append("unitProperty", getUnitProperty())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
