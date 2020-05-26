package com.datcent.project.module.lists.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 案件列表 case_lists
 * 
 * @author datcent
 * @date 2018-12-26
 */
public class Lists extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 立案人 */
	private String zhcxlistOrganlar;
	/** 立案人ID */
	private String zhcxlistOrganlarid;
	/** 案件编号 */
	private String zhcxlistStringajbh;
	/** 归档人 */
	private String zhcxlistStringgdr;
	/** 承办人 */
	private String zhcxlistOrganncbr;
	/** 承办人ID */
	private String zhcxlistOrganncbrid;
	/** 案号 */
	private String zhcxlistStringcah;
	/** 结案日期 */
	private String zhcxlistDatedjarq;
	/** 开庭地点 */
	private String zhcxlistStringktdd;
	/** 案件类别 */
	private String zhcxlistCodenajlb;
	/** 案件类别ID */
	private String zhcxlistCodenajlbid;
	/** 当事人 */
	private String zhcxlistStringcdsr;
	/** 审判程序 */
	private String zhcxlistCodenspcx;
	/** 审判程序ID */
	private String zhcxlistCodenspcxid;
	/** 是否涉自贸区 */
	private String zhcxlistCodesfszmq;
	/** 审限情况 */
	private String zhcxlistStringsxqk;
	/** 承办审判庭 */
	private String zhcxlistOrgancbspt;
	/** 承办审判庭ID */
	private String zhcxlistOrgancbsptid;
	/** 归档日期 */
	private String zhcxlistDategdrq;
	/** 已交费(元) */
	private String zhcxlistNumericnjfzje;
	/** 经办法院 */
	private String zhcxlistOrgan6aace;
	/** 经办法院ID */
	private String zhcxlistOrgan6aaceid;
	/** 结案案由 */
	private String zhcxlistStringcay;
	/** 立案日期 */
	private String zhcxlistDatedlarq;
	/** 评查状态 */
	private String zhcxlistCodenpczt;
	/** 立案案由 */
	private String zhcxlistStringlaay;
	/** 立案案由ID */
	private String zhcxlistStringlaayid;
	/** 提请报结日期 */
	private String zhcxlistDatedtqbjrq;
	/** 收案日期 */
	private String zhcxlistDatedsarq;
	/** 超审限天数 */
	private String zhcxlistNumericcsxts;
	/** 结案方式 */
	private String zhcxlistStringjafs;
	/** 是否超审限 */
	private String zhcxlistCodesfcsx;

	public void setZhcxlistOrganlarid(String zhcxlistOrganlarid) 
	{
		this.zhcxlistOrganlarid = zhcxlistOrganlarid;
	}

	public String getZhcxlistOrganlarid() 
	{
		return zhcxlistOrganlarid;
	}
	public void setZhcxlistStringajbh(String zhcxlistStringajbh) 
	{
		this.zhcxlistStringajbh = zhcxlistStringajbh;
	}

	public String getZhcxlistStringajbh() 
	{
		return zhcxlistStringajbh;
	}
	public void setZhcxlistStringgdr(String zhcxlistStringgdr) 
	{
		this.zhcxlistStringgdr = zhcxlistStringgdr;
	}

	public String getZhcxlistStringgdr() 
	{
		return zhcxlistStringgdr;
	}
	public void setZhcxlistOrganncbrid(String zhcxlistOrganncbrid) 
	{
		this.zhcxlistOrganncbrid = zhcxlistOrganncbrid;
	}

	public String getZhcxlistOrganncbrid() 
	{
		return zhcxlistOrganncbrid;
	}
	public void setZhcxlistStringcah(String zhcxlistStringcah) 
	{
		this.zhcxlistStringcah = zhcxlistStringcah;
	}

	public String getZhcxlistStringcah() 
	{
		return zhcxlistStringcah;
	}
	public void setZhcxlistDatedjarq(String zhcxlistDatedjarq) 
	{
		this.zhcxlistDatedjarq = zhcxlistDatedjarq;
	}

	public String getZhcxlistDatedjarq() 
	{
		return zhcxlistDatedjarq;
	}
	public void setZhcxlistStringktdd(String zhcxlistStringktdd) 
	{
		this.zhcxlistStringktdd = zhcxlistStringktdd;
	}

	public String getZhcxlistStringktdd() 
	{
		return zhcxlistStringktdd;
	}
	public void setZhcxlistCodenajlbid(String zhcxlistCodenajlbid) 
	{
		this.zhcxlistCodenajlbid = zhcxlistCodenajlbid;
	}

	public String getZhcxlistCodenajlbid() 
	{
		return zhcxlistCodenajlbid;
	}
	public void setZhcxlistOrganlar(String zhcxlistOrganlar) 
	{
		this.zhcxlistOrganlar = zhcxlistOrganlar;
	}

	public String getZhcxlistOrganlar() 
	{
		return zhcxlistOrganlar;
	}
	public void setZhcxlistStringcdsr(String zhcxlistStringcdsr) 
	{
		this.zhcxlistStringcdsr = zhcxlistStringcdsr;
	}

	public String getZhcxlistStringcdsr() 
	{
		return zhcxlistStringcdsr;
	}
	public void setZhcxlistCodenspcxid(String zhcxlistCodenspcxid) 
	{
		this.zhcxlistCodenspcxid = zhcxlistCodenspcxid;
	}

	public String getZhcxlistCodenspcxid() 
	{
		return zhcxlistCodenspcxid;
	}
	public void setZhcxlistCodesfszmq(String zhcxlistCodesfszmq) 
	{
		this.zhcxlistCodesfszmq = zhcxlistCodesfszmq;
	}

	public String getZhcxlistCodesfszmq() 
	{
		return zhcxlistCodesfszmq;
	}
	public void setZhcxlistStringsxqk(String zhcxlistStringsxqk) 
	{
		this.zhcxlistStringsxqk = zhcxlistStringsxqk;
	}

	public String getZhcxlistStringsxqk() 
	{
		return zhcxlistStringsxqk;
	}
	public void setZhcxlistOrgancbsptid(String zhcxlistOrgancbsptid) 
	{
		this.zhcxlistOrgancbsptid = zhcxlistOrgancbsptid;
	}

	public String getZhcxlistOrgancbsptid() 
	{
		return zhcxlistOrgancbsptid;
	}
	public void setZhcxlistDategdrq(String zhcxlistDategdrq) 
	{
		this.zhcxlistDategdrq = zhcxlistDategdrq;
	}

	public String getZhcxlistDategdrq() 
	{
		return zhcxlistDategdrq;
	}
	public void setZhcxlistNumericnjfzje(String zhcxlistNumericnjfzje) 
	{
		this.zhcxlistNumericnjfzje = zhcxlistNumericnjfzje;
	}

	public String getZhcxlistNumericnjfzje() 
	{
		return zhcxlistNumericnjfzje;
	}
	public void setZhcxlistCodenspcx(String zhcxlistCodenspcx) 
	{
		this.zhcxlistCodenspcx = zhcxlistCodenspcx;
	}

	public String getZhcxlistCodenspcx() 
	{
		return zhcxlistCodenspcx;
	}
	public void setZhcxlistOrgan6aaceid(String zhcxlistOrgan6aaceid) 
	{
		this.zhcxlistOrgan6aaceid = zhcxlistOrgan6aaceid;
	}

	public String getZhcxlistOrgan6aaceid() 
	{
		return zhcxlistOrgan6aaceid;
	}
	public void setZhcxlistCodenajlb(String zhcxlistCodenajlb) 
	{
		this.zhcxlistCodenajlb = zhcxlistCodenajlb;
	}

	public String getZhcxlistCodenajlb() 
	{
		return zhcxlistCodenajlb;
	}
	public void setZhcxlistStringcay(String zhcxlistStringcay) 
	{
		this.zhcxlistStringcay = zhcxlistStringcay;
	}

	public String getZhcxlistStringcay() 
	{
		return zhcxlistStringcay;
	}
	public void setZhcxlistOrgancbspt(String zhcxlistOrgancbspt) 
	{
		this.zhcxlistOrgancbspt = zhcxlistOrgancbspt;
	}

	public String getZhcxlistOrgancbspt() 
	{
		return zhcxlistOrgancbspt;
	}
	public void setZhcxlistDatedlarq(String zhcxlistDatedlarq) 
	{
		this.zhcxlistDatedlarq = zhcxlistDatedlarq;
	}

	public String getZhcxlistDatedlarq() 
	{
		return zhcxlistDatedlarq;
	}
	public void setZhcxlistCodenpczt(String zhcxlistCodenpczt) 
	{
		this.zhcxlistCodenpczt = zhcxlistCodenpczt;
	}

	public String getZhcxlistCodenpczt() 
	{
		return zhcxlistCodenpczt;
	}
	public void setZhcxlistStringlaay(String zhcxlistStringlaay) 
	{
		this.zhcxlistStringlaay = zhcxlistStringlaay;
	}

	public String getZhcxlistStringlaay() 
	{
		return zhcxlistStringlaay;
	}
	public void setZhcxlistDatedtqbjrq(String zhcxlistDatedtqbjrq) 
	{
		this.zhcxlistDatedtqbjrq = zhcxlistDatedtqbjrq;
	}

	public String getZhcxlistDatedtqbjrq() 
	{
		return zhcxlistDatedtqbjrq;
	}
	public void setZhcxlistOrgan6aace(String zhcxlistOrgan6aace) 
	{
		this.zhcxlistOrgan6aace = zhcxlistOrgan6aace;
	}

	public String getZhcxlistOrgan6aace() 
	{
		return zhcxlistOrgan6aace;
	}
	public void setZhcxlistDatedsarq(String zhcxlistDatedsarq) 
	{
		this.zhcxlistDatedsarq = zhcxlistDatedsarq;
	}

	public String getZhcxlistDatedsarq() 
	{
		return zhcxlistDatedsarq;
	}
	public void setZhcxlistNumericcsxts(String zhcxlistNumericcsxts) 
	{
		this.zhcxlistNumericcsxts = zhcxlistNumericcsxts;
	}

	public String getZhcxlistNumericcsxts() 
	{
		return zhcxlistNumericcsxts;
	}
	public void setZhcxlistOrganncbr(String zhcxlistOrganncbr) 
	{
		this.zhcxlistOrganncbr = zhcxlistOrganncbr;
	}

	public String getZhcxlistOrganncbr() 
	{
		return zhcxlistOrganncbr;
	}
	public void setZhcxlistStringjafs(String zhcxlistStringjafs) 
	{
		this.zhcxlistStringjafs = zhcxlistStringjafs;
	}

	public String getZhcxlistStringjafs() 
	{
		return zhcxlistStringjafs;
	}
	public void setZhcxlistStringlaayid(String zhcxlistStringlaayid) 
	{
		this.zhcxlistStringlaayid = zhcxlistStringlaayid;
	}

	public String getZhcxlistStringlaayid() 
	{
		return zhcxlistStringlaayid;
	}
	public void setZhcxlistCodesfcsx(String zhcxlistCodesfcsx) 
	{
		this.zhcxlistCodesfcsx = zhcxlistCodesfcsx;
	}

	public String getZhcxlistCodesfcsx() 
	{
		return zhcxlistCodesfcsx;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("zhcxlistOrganlarid", getZhcxlistOrganlarid())
            .append("zhcxlistStringajbh", getZhcxlistStringajbh())
            .append("zhcxlistStringgdr", getZhcxlistStringgdr())
            .append("zhcxlistOrganncbrid", getZhcxlistOrganncbrid())
            .append("zhcxlistStringcah", getZhcxlistStringcah())
            .append("zhcxlistDatedjarq", getZhcxlistDatedjarq())
            .append("zhcxlistStringktdd", getZhcxlistStringktdd())
            .append("zhcxlistCodenajlbid", getZhcxlistCodenajlbid())
            .append("zhcxlistOrganlar", getZhcxlistOrganlar())
            .append("zhcxlistStringcdsr", getZhcxlistStringcdsr())
            .append("zhcxlistCodenspcxid", getZhcxlistCodenspcxid())
            .append("zhcxlistCodesfszmq", getZhcxlistCodesfszmq())
            .append("zhcxlistStringsxqk", getZhcxlistStringsxqk())
            .append("zhcxlistOrgancbsptid", getZhcxlistOrgancbsptid())
            .append("zhcxlistDategdrq", getZhcxlistDategdrq())
            .append("zhcxlistNumericnjfzje", getZhcxlistNumericnjfzje())
            .append("zhcxlistCodenspcx", getZhcxlistCodenspcx())
            .append("zhcxlistOrgan6aaceid", getZhcxlistOrgan6aaceid())
            .append("zhcxlistCodenajlb", getZhcxlistCodenajlb())
            .append("zhcxlistStringcay", getZhcxlistStringcay())
            .append("zhcxlistOrgancbspt", getZhcxlistOrgancbspt())
            .append("zhcxlistDatedlarq", getZhcxlistDatedlarq())
            .append("zhcxlistCodenpczt", getZhcxlistCodenpczt())
            .append("zhcxlistStringlaay", getZhcxlistStringlaay())
            .append("zhcxlistDatedtqbjrq", getZhcxlistDatedtqbjrq())
            .append("zhcxlistOrgan6aace", getZhcxlistOrgan6aace())
            .append("zhcxlistDatedsarq", getZhcxlistDatedsarq())
            .append("zhcxlistNumericcsxts", getZhcxlistNumericcsxts())
            .append("zhcxlistOrganncbr", getZhcxlistOrganncbr())
            .append("zhcxlistStringjafs", getZhcxlistStringjafs())
            .append("zhcxlistStringlaayid", getZhcxlistStringlaayid())
            .append("zhcxlistCodesfcsx", getZhcxlistCodesfcsx())
            .toString();
    }
}
