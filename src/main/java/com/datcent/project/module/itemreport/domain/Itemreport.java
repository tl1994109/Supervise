package com.datcent.project.module.itemreport.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 有关事件报告表 arch_itemreport
 * 
 * @author datcent
 * @date 2019-04-01
 */
public class Itemreport extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 事项报告ID */
	private String sxbgId;
	/** 法院组织CID */
	private String sxbgCid;
	/** 姓名 */
	private String sxbgXm;
	/** 单位及工作部门 */
	private String sxbgDwjgzbm;
	/** 职务 */
	private String sxbgZw;
	/** 本人的婚姻变化情况 */
	private String sxbgHybhqk;
	/** 本人持有因私出国（境）证件的情况 */
	private String sxbgCyyscgzjqk;
	/** 因私出国情况 */
	private String sxbgYscgqk;
	/** 子女与外国人、港澳台居民通婚的情况 */
	private String sxbgZnywgthqk;
	/** 配偶、子女出国（境）定居及有关情况 */
	private String sxbgPozncgdjqk;
	/** 配偶、共同生活的子女（指同财共居的子女，下同）私人在国（境）外经商办企业的情况 */
	private String sxbgPoznjsqk;
	/** 配偶、共同生活的子女担任外国公司驻华、港澳台公司驻境内分支机构主管人员的情况 */
	private String sxbgPoznrcgzgryqk;
	/** 配偶、子女被司法机关追究刑事责任的情况 */
	private String sxbgPoznbsfzjqk;
	/** 计划生育、收养子女方面的情况 */
	private String sxbgJhsysyznqk;
	/** 本人认为应报告的其他事项 */
	private String sxbgQtsx;

	public void setSxbgId(String sxbgId) 
	{
		this.sxbgId = sxbgId;
	}

	public String getSxbgId() 
	{
		return sxbgId;
	}
	public void setSxbgCid(String sxbgCid) 
	{
		this.sxbgCid = sxbgCid;
	}

	public String getSxbgCid() 
	{
		return sxbgCid;
	}
	public void setSxbgXm(String sxbgXm) 
	{
		this.sxbgXm = sxbgXm;
	}

	public String getSxbgXm() 
	{
		return sxbgXm;
	}
	public void setSxbgDwjgzbm(String sxbgDwjgzbm) 
	{
		this.sxbgDwjgzbm = sxbgDwjgzbm;
	}

	public String getSxbgDwjgzbm() 
	{
		return sxbgDwjgzbm;
	}
	public void setSxbgZw(String sxbgZw) 
	{
		this.sxbgZw = sxbgZw;
	}

	public String getSxbgZw() 
	{
		return sxbgZw;
	}
	public void setSxbgHybhqk(String sxbgHybhqk) 
	{
		this.sxbgHybhqk = sxbgHybhqk;
	}

	public String getSxbgHybhqk() 
	{
		return sxbgHybhqk;
	}
	public void setSxbgCyyscgzjqk(String sxbgCyyscgzjqk) 
	{
		this.sxbgCyyscgzjqk = sxbgCyyscgzjqk;
	}

	public String getSxbgCyyscgzjqk() 
	{
		return sxbgCyyscgzjqk;
	}
	public void setSxbgYscgqk(String sxbgYscgqk) 
	{
		this.sxbgYscgqk = sxbgYscgqk;
	}

	public String getSxbgYscgqk() 
	{
		return sxbgYscgqk;
	}
	public void setSxbgZnywgthqk(String sxbgZnywgthqk) 
	{
		this.sxbgZnywgthqk = sxbgZnywgthqk;
	}

	public String getSxbgZnywgthqk() 
	{
		return sxbgZnywgthqk;
	}
	public void setSxbgPozncgdjqk(String sxbgPozncgdjqk) 
	{
		this.sxbgPozncgdjqk = sxbgPozncgdjqk;
	}

	public String getSxbgPozncgdjqk() 
	{
		return sxbgPozncgdjqk;
	}
	public void setSxbgPoznjsqk(String sxbgPoznjsqk) 
	{
		this.sxbgPoznjsqk = sxbgPoznjsqk;
	}

	public String getSxbgPoznjsqk() 
	{
		return sxbgPoznjsqk;
	}
	public void setSxbgPoznrcgzgryqk(String sxbgPoznrcgzgryqk) 
	{
		this.sxbgPoznrcgzgryqk = sxbgPoznrcgzgryqk;
	}

	public String getSxbgPoznrcgzgryqk() 
	{
		return sxbgPoznrcgzgryqk;
	}
	public void setSxbgPoznbsfzjqk(String sxbgPoznbsfzjqk) 
	{
		this.sxbgPoznbsfzjqk = sxbgPoznbsfzjqk;
	}

	public String getSxbgPoznbsfzjqk() 
	{
		return sxbgPoznbsfzjqk;
	}
	public void setSxbgJhsysyznqk(String sxbgJhsysyznqk) 
	{
		this.sxbgJhsysyznqk = sxbgJhsysyznqk;
	}

	public String getSxbgJhsysyznqk() 
	{
		return sxbgJhsysyznqk;
	}
	public void setSxbgQtsx(String sxbgQtsx) 
	{
		this.sxbgQtsx = sxbgQtsx;
	}

	public String getSxbgQtsx() 
	{
		return sxbgQtsx;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sxbgId", getSxbgId())
            .append("sxbgCid", getSxbgCid())
            .append("sxbgXm", getSxbgXm())
            .append("sxbgDwjgzbm", getSxbgDwjgzbm())
            .append("sxbgZw", getSxbgZw())
            .append("sxbgHybhqk", getSxbgHybhqk())
            .append("sxbgCyyscgzjqk", getSxbgCyyscgzjqk())
            .append("sxbgYscgqk", getSxbgYscgqk())
            .append("sxbgZnywgthqk", getSxbgZnywgthqk())
            .append("sxbgPozncgdjqk", getSxbgPozncgdjqk())
            .append("sxbgPoznjsqk", getSxbgPoznjsqk())
            .append("sxbgPoznrcgzgryqk", getSxbgPoznrcgzgryqk())
            .append("sxbgPoznbsfzjqk", getSxbgPoznbsfzjqk())
            .append("sxbgJhsysyznqk", getSxbgJhsysyznqk())
            .append("sxbgQtsx", getSxbgQtsx())
            .toString();
    }
}
