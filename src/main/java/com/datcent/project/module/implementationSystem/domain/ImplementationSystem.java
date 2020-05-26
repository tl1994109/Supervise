package com.datcent.project.module.implementationSystem.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 落实规章制度情况表 arch_implementation_system
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class ImplementationSystem extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String lsgzzdqkId;
	/** 法院组织表CID */
	private String cid;
	/** 姓名 */
	private String lsgzzdqkXm;
	/** 单位 */
	private String lsgzzdqkDw;
	/** 职务 */
	private String lsgzzdqkZw;
	/** 落实庭审纪律情况 */
	private String lsgzzdqkLstsjl;
	/** 落实"十严禁"暂行规定情况 */
	private String lsgzzdqkLssyjzxgd;
	/** 执行回避制度情况 */
	private String lsgzzdqkZxhbzdqk;
	/** 过问职务外案件情况 */
	private String lsgzzdqkGwzwwajqk;
	/** 操办婚丧喜庆事宜情况 */
	private String lsgzzdqkZbhsxqsyqk;
	/** 其他蹲守法官职业道德情况 */
	private String lsgzzdqkQtdsfgzyddqk;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setLsgzzdqkId(String lsgzzdqkId) 
	{
		this.lsgzzdqkId = lsgzzdqkId;
	}

	public String getLsgzzdqkId() 
	{
		return lsgzzdqkId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setLsgzzdqkXm(String lsgzzdqkXm) 
	{
		this.lsgzzdqkXm = lsgzzdqkXm;
	}

	public String getLsgzzdqkXm() 
	{
		return lsgzzdqkXm;
	}
	public void setLsgzzdqkDw(String lsgzzdqkDw) 
	{
		this.lsgzzdqkDw = lsgzzdqkDw;
	}

	public String getLsgzzdqkDw() 
	{
		return lsgzzdqkDw;
	}
	public void setLsgzzdqkZw(String lsgzzdqkZw) 
	{
		this.lsgzzdqkZw = lsgzzdqkZw;
	}

	public String getLsgzzdqkZw() 
	{
		return lsgzzdqkZw;
	}
	public void setLsgzzdqkLstsjl(String lsgzzdqkLstsjl) 
	{
		this.lsgzzdqkLstsjl = lsgzzdqkLstsjl;
	}

	public String getLsgzzdqkLstsjl() 
	{
		return lsgzzdqkLstsjl;
	}
	public void setLsgzzdqkLssyjzxgd(String lsgzzdqkLssyjzxgd) 
	{
		this.lsgzzdqkLssyjzxgd = lsgzzdqkLssyjzxgd;
	}

	public String getLsgzzdqkLssyjzxgd() 
	{
		return lsgzzdqkLssyjzxgd;
	}
	public void setLsgzzdqkZxhbzdqk(String lsgzzdqkZxhbzdqk) 
	{
		this.lsgzzdqkZxhbzdqk = lsgzzdqkZxhbzdqk;
	}

	public String getLsgzzdqkZxhbzdqk() 
	{
		return lsgzzdqkZxhbzdqk;
	}
	public void setLsgzzdqkGwzwwajqk(String lsgzzdqkGwzwwajqk) 
	{
		this.lsgzzdqkGwzwwajqk = lsgzzdqkGwzwwajqk;
	}

	public String getLsgzzdqkGwzwwajqk() 
	{
		return lsgzzdqkGwzwwajqk;
	}
	public void setLsgzzdqkZbhsxqsyqk(String lsgzzdqkZbhsxqsyqk) 
	{
		this.lsgzzdqkZbhsxqsyqk = lsgzzdqkZbhsxqsyqk;
	}

	public String getLsgzzdqkZbhsxqsyqk() 
	{
		return lsgzzdqkZbhsxqsyqk;
	}
	public void setLsgzzdqkQtdsfgzyddqk(String lsgzzdqkQtdsfgzyddqk) 
	{
		this.lsgzzdqkQtdsfgzyddqk = lsgzzdqkQtdsfgzyddqk;
	}

	public String getLsgzzdqkQtdsfgzyddqk() 
	{
		return lsgzzdqkQtdsfgzyddqk;
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
            .append("lsgzzdqkId", getLsgzzdqkId())
            .append("cid", getCid())
            .append("lsgzzdqkXm", getLsgzzdqkXm())
            .append("lsgzzdqkDw", getLsgzzdqkDw())
            .append("lsgzzdqkZw", getLsgzzdqkZw())
            .append("lsgzzdqkLstsjl", getLsgzzdqkLstsjl())
            .append("lsgzzdqkLssyjzxgd", getLsgzzdqkLssyjzxgd())
            .append("lsgzzdqkZxhbzdqk", getLsgzzdqkZxhbzdqk())
            .append("lsgzzdqkGwzwwajqk", getLsgzzdqkGwzwwajqk())
            .append("lsgzzdqkZbhsxqsyqk", getLsgzzdqkZbhsxqsyqk())
            .append("lsgzzdqkQtdsfgzyddqk", getLsgzzdqkQtdsfgzyddqk())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
