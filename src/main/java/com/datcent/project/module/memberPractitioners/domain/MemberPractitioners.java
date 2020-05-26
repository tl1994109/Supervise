package com.datcent.project.module.memberPractitioners.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 配偶子女从业情况表 arch_member_practitioners
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class MemberPractitioners extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String pozncyqkId;
	/** 法院组织表关联CID */
	private String cid;
	/** 姓名 */
	private String pozncyqkXm;
	/** 单位 */
	private String pozncyqkDw;
	/** 职务 */
	private String pozncyqkZw;
	/** 本人分管业务范围 */
	private String pozncyqkBrfgywfw;
	/** 兼职单位 */
	private String pozncyqkJzdw;
	/** 兼职职务 */
	private String pozncyqkJzzw;
	/** 与填表人关系 */
	private String jsbqyqkYtbrgx;
	/** 姓名 */
	private String jsbqyqkXm;
	/** 企业名称及地址 */
	private String jsbqyqkQymcjdz;
	/** 经商办企业形式 */
	private String jsbqyqkJsbqyxs;
	/** 企业登记注册机关 */
	private String jsbqyqkQydjzcjg;
	/** 经营范围 */
	private String jsbqyqkJyfw;
	/** 与填表人关系 */
	private String zwqyrzqkYtbrgx;
	/** 姓名 */
	private String zwqyrzqkXm;
	/** 企业名称及地址 */
	private String zwqyrzqkQymcjdz;
	/** 企业组织形式 */
	private String zwqyrzqkQyzzxs;
	/** 受何方委派聘任 */
	private String zwqyrzqkShfwppr;
	/** 经营范围 */
	private String zwqyrzqkJyfw;
	/** 备注 */
	private String remarks;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	public void setPozncyqkId(String pozncyqkId) 
	{
		this.pozncyqkId = pozncyqkId;
	}

	public String getPozncyqkId() 
	{
		return pozncyqkId;
	}
	public void setCid(String cid) 
	{
		this.cid = cid;
	}

	public String getCid() 
	{
		return cid;
	}
	public void setPozncyqkXm(String pozncyqkXm) 
	{
		this.pozncyqkXm = pozncyqkXm;
	}

	public String getPozncyqkXm() 
	{
		return pozncyqkXm;
	}
	public void setPozncyqkDw(String pozncyqkDw) 
	{
		this.pozncyqkDw = pozncyqkDw;
	}

	public String getPozncyqkDw() 
	{
		return pozncyqkDw;
	}
	public void setPozncyqkZw(String pozncyqkZw) 
	{
		this.pozncyqkZw = pozncyqkZw;
	}

	public String getPozncyqkZw() 
	{
		return pozncyqkZw;
	}
	public void setPozncyqkBrfgywfw(String pozncyqkBrfgywfw) 
	{
		this.pozncyqkBrfgywfw = pozncyqkBrfgywfw;
	}

	public String getPozncyqkBrfgywfw() 
	{
		return pozncyqkBrfgywfw;
	}
	public void setPozncyqkJzdw(String pozncyqkJzdw) 
	{
		this.pozncyqkJzdw = pozncyqkJzdw;
	}

	public String getPozncyqkJzdw() 
	{
		return pozncyqkJzdw;
	}
	public void setPozncyqkJzzw(String pozncyqkJzzw) 
	{
		this.pozncyqkJzzw = pozncyqkJzzw;
	}

	public String getPozncyqkJzzw() 
	{
		return pozncyqkJzzw;
	}
	public void setJsbqyqkYtbrgx(String jsbqyqkYtbrgx) 
	{
		this.jsbqyqkYtbrgx = jsbqyqkYtbrgx;
	}

	public String getJsbqyqkYtbrgx() 
	{
		return jsbqyqkYtbrgx;
	}
	public void setJsbqyqkXm(String jsbqyqkXm) 
	{
		this.jsbqyqkXm = jsbqyqkXm;
	}

	public String getJsbqyqkXm() 
	{
		return jsbqyqkXm;
	}
	public void setJsbqyqkQymcjdz(String jsbqyqkQymcjdz) 
	{
		this.jsbqyqkQymcjdz = jsbqyqkQymcjdz;
	}

	public String getJsbqyqkQymcjdz() 
	{
		return jsbqyqkQymcjdz;
	}
	public void setJsbqyqkJsbqyxs(String jsbqyqkJsbqyxs) 
	{
		this.jsbqyqkJsbqyxs = jsbqyqkJsbqyxs;
	}

	public String getJsbqyqkJsbqyxs() 
	{
		return jsbqyqkJsbqyxs;
	}
	public void setJsbqyqkQydjzcjg(String jsbqyqkQydjzcjg) 
	{
		this.jsbqyqkQydjzcjg = jsbqyqkQydjzcjg;
	}

	public String getJsbqyqkQydjzcjg() 
	{
		return jsbqyqkQydjzcjg;
	}
	public void setJsbqyqkJyfw(String jsbqyqkJyfw) 
	{
		this.jsbqyqkJyfw = jsbqyqkJyfw;
	}

	public String getJsbqyqkJyfw() 
	{
		return jsbqyqkJyfw;
	}
	public void setZwqyrzqkYtbrgx(String zwqyrzqkYtbrgx) 
	{
		this.zwqyrzqkYtbrgx = zwqyrzqkYtbrgx;
	}

	public String getZwqyrzqkYtbrgx() 
	{
		return zwqyrzqkYtbrgx;
	}
	public void setZwqyrzqkXm(String zwqyrzqkXm) 
	{
		this.zwqyrzqkXm = zwqyrzqkXm;
	}

	public String getZwqyrzqkXm() 
	{
		return zwqyrzqkXm;
	}
	public void setZwqyrzqkQymcjdz(String zwqyrzqkQymcjdz) 
	{
		this.zwqyrzqkQymcjdz = zwqyrzqkQymcjdz;
	}

	public String getZwqyrzqkQymcjdz() 
	{
		return zwqyrzqkQymcjdz;
	}
	public void setZwqyrzqkQyzzxs(String zwqyrzqkQyzzxs) 
	{
		this.zwqyrzqkQyzzxs = zwqyrzqkQyzzxs;
	}

	public String getZwqyrzqkQyzzxs() 
	{
		return zwqyrzqkQyzzxs;
	}
	public void setZwqyrzqkShfwppr(String zwqyrzqkShfwppr) 
	{
		this.zwqyrzqkShfwppr = zwqyrzqkShfwppr;
	}

	public String getZwqyrzqkShfwppr() 
	{
		return zwqyrzqkShfwppr;
	}
	public void setZwqyrzqkJyfw(String zwqyrzqkJyfw) 
	{
		this.zwqyrzqkJyfw = zwqyrzqkJyfw;
	}

	public String getZwqyrzqkJyfw() 
	{
		return zwqyrzqkJyfw;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
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
            .append("pozncyqkId", getPozncyqkId())
            .append("cid", getCid())
            .append("pozncyqkXm", getPozncyqkXm())
            .append("pozncyqkDw", getPozncyqkDw())
            .append("pozncyqkZw", getPozncyqkZw())
            .append("pozncyqkBrfgywfw", getPozncyqkBrfgywfw())
            .append("pozncyqkJzdw", getPozncyqkJzdw())
            .append("pozncyqkJzzw", getPozncyqkJzzw())
            .append("jsbqyqkYtbrgx", getJsbqyqkYtbrgx())
            .append("jsbqyqkXm", getJsbqyqkXm())
            .append("jsbqyqkQymcjdz", getJsbqyqkQymcjdz())
            .append("jsbqyqkJsbqyxs", getJsbqyqkJsbqyxs())
            .append("jsbqyqkQydjzcjg", getJsbqyqkQydjzcjg())
            .append("jsbqyqkJyfw", getJsbqyqkJyfw())
            .append("zwqyrzqkYtbrgx", getZwqyrzqkYtbrgx())
            .append("zwqyrzqkXm", getZwqyrzqkXm())
            .append("zwqyrzqkQymcjdz", getZwqyrzqkQymcjdz())
            .append("zwqyrzqkQyzzxs", getZwqyrzqkQyzzxs())
            .append("zwqyrzqkShfwppr", getZwqyrzqkShfwppr())
            .append("zwqyrzqkJyfw", getZwqyrzqkJyfw())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
