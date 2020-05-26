package com.datcent.project.module.existingProperty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 现有财产状况表 arch_existing_property
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class ExistingProperty extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String jtccqkId;
	/** 法院组织表CID */
	private String cid;
	/** 房产地址 */
	private String xycczkFcdz;
	/** 面积 */
	private String xycczkMj;
	/** 性质 */
	private String xycczkXz;
	/** 用途 */
	private String xycczkYt;
	/** 房产来源 */
	private String xycczkFcly;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	private String json_str;

	public String getJson_str() {
		return json_str;
	}

	public void setJson_str(String json_str) {
		this.json_str = json_str;
	}

	public void setJtccqkId(String jtccqkId)
	{
		this.jtccqkId = jtccqkId;
	}

	public String getJtccqkId() 
	{
		return jtccqkId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setXycczkFcdz(String xycczkFcdz) 
	{
		this.xycczkFcdz = xycczkFcdz;
	}

	public String getXycczkFcdz() 
	{
		return xycczkFcdz;
	}
	public void setXycczkMj(String xycczkMj) 
	{
		this.xycczkMj = xycczkMj;
	}

	public String getXycczkMj() 
	{
		return xycczkMj;
	}
	public void setXycczkXz(String xycczkXz) 
	{
		this.xycczkXz = xycczkXz;
	}

	public String getXycczkXz() 
	{
		return xycczkXz;
	}
	public void setXycczkYt(String xycczkYt) 
	{
		this.xycczkYt = xycczkYt;
	}

	public String getXycczkYt() 
	{
		return xycczkYt;
	}
	public void setXycczkFcly(String xycczkFcly) 
	{
		this.xycczkFcly = xycczkFcly;
	}

	public String getXycczkFcly() 
	{
		return xycczkFcly;
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
            .append("jtccqkId", getJtccqkId())
            .append("cid", getCid())
            .append("xycczkFcdz", getXycczkFcdz())
            .append("xycczkMj", getXycczkMj())
            .append("xycczkXz", getXycczkXz())
            .append("xycczkYt", getXycczkYt())
            .append("xycczkFcly", getXycczkFcly())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
