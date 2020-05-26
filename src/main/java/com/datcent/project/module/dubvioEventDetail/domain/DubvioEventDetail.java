package com.datcent.project.module.dubvioEventDetail.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 案件详细表 busi_dubvio_event_detail
 * 
 * @author datcent
 * @date 2019-01-16
 */
public class DubvioEventDetail extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 案件编号 */
	private String jbxxAjbh;
	/** 当事人 */
	private String jbxxDsr;
	/** 新案件类别 */
	private String jbxxXajlb;
	/** 案件类别 */
	private String jbxxAjlb;
	/** 经办法院 */
	private String jbxxJbfy;
	/** 审判程序 */
	private String jbxxSpcx;
	/** 适用程序 */
	private String jbxxSycx;
	/** 收案意见 */
	private String jbxxSayj;
	/** 案号 */
	private String jbxxAh;
	/** 诉讼性质 */
	private String jbxxSsxz;
	/** 一审诉讼性质 */
	private String jbxxYsssxz;
	/** 最后修改时间 */
	private String jbxxZhxgsj;
	/** 案件名称 */
	private String jbxxAjmc;
	/** 案件进展阶段 */
	private String jbxxAjjzjd;
	/** 是否有电子卷宗 */
	private String jbxxSfydzjz;
	/** 是否职务犯罪 */
	private String jbxxSfzwfz;
	/** 审判流程公开 */
	private String jbxxSplcgk;



	/**违规事件id*/
	private String dubvioId;



	/** 收案登记人 */
	private String sadjSadjr;
	/** 收案日期 */
	private String sadjSasj;
	/** 敏感案件 */
	private String sadjMgaj;
	/** 地域涉及 */
	private String sadjDysj;
	/** 收到诉状日期 */
	private String sadjSdszsj;
	/** 立案案由 */
	private String sadjLaay;
	/** 管辖依据 */
	private String sadjGxyj;
	/** 立案部门 */
	private String sadjLabm;
	/** 收案庭室 */
	private String sadjSats;
	/** 立案审查人 */
	private String lascLascr;
	/** 立案审查日期 */
	private String lascLascrq;
	/** 立案审查意见 */
	private String lascLascyj;
	/** 立案审批人 */
	private String laspLaspr;
	/** 立案审批日期 */
	private String laspLasprq;
	/** 立案审批意见 */
	private String laspLaspyj;
	/** 立案日期 */
	private String laspLarq;
	/** 立案庭室 */
	private String laspLats;
	/** 立案人 */
	private String laspLar;
	/** 分案日期 */
	private String faFarq;
	/** 承办审判庭 */
	private String faCbspt;
	/** 承办人 */
	private String faCbr;
	/** 分案确认人 */
	private String faFaqrr;
	/** 分案确认日期 */
	private String faFaqrrq;
	/** 移交状态 */
	private String faYjzt;
	/** 排期状态 */
	private String blPqzt;
	/** 被告人同意认罪程序 */
	private String blBgrtyrzcx;
	/** 是否庭前证据交换 */
	private String blSftqzjjh;
	/** 是否法院收集调取证据 */
	private String blSffysjdqzj;
	/** 公开审理 */
	private String blGksl;
	/** 开庭审理 */
	private String blKysl;
	/** 诉讼代理人数(辩护人数) */
	private String blSsdlrs;
	/** 律师人数 */
	private String blLsrs;
	/** 法律援助律师人数 */
	private String blFlyzlsrs;
	/** 重新排期 */
	private String blCxpq;
	/** 是否独任审判 */
	private String blSfdrsp;
	/** 是否有人民陪审员 */
	private String blSfyrmpsy;
	/** 是否提起附带民事诉讼 */
	private String fdmsssSftqfdmsss;
	/** 移送状态 */
	private String jaYszt;
	/** 领导过问 */
	private String jaLdgw;
	/** 判决挽回损失 */
	private String jaPjwhss;
	/** 是否提请院长提交审委会 */
	private String jaSftqyztjswh;
	/** 审判长或独任审判员审签日期 */
	private String jaSpzhdrspysqrq;
	/** 结案日期 */
	private String jaJarq;
	/** 结案案由 */
	private String jaJaay;
	/** 新结案方式 */
	private String jaXjafs;
	/** 结案方式 */
	private String jaJafs;
	/** 当庭宣判 */
	private String jaDtxp;
	/** 是否上诉 */
	private String jaSfss;
	/** 待审批结案日期 */
	private String jaDspjarq;
	/** 有无结案文书 */
	private String jaYwjaws;
	/** 裁判文书是否具有可执行内容 */
	private String jaCpwssfjykzxnr;
	/** 审限总天数 */
	private String sxxxSxzts;
	/** 延长天数 */
	private String sxxxYcts;
	/** 扣除天数 */
	private String sxxxKcts;
	/** 审限起始日期 */
	private String sxxxSxqsrq;
	/** 审限届满日期 */
	private String sxxxSxjmrq;
	/** 法定审限天数 */
	private String sxxxFdsxts;
	/** 超审限天数 */
	private String sxxxCsxts;
	/** 超审限 */
	private String sxxxCsx;
	/** 审理天数 */
	private String sxxxSlts;

	public String getDubvioId() {
		return dubvioId;
	}

	public void setDubvioId(String dubvioId) {
		this.dubvioId = dubvioId;
	}

	public void setJbxxAjbh(String jbxxAjbh)
	{
		this.jbxxAjbh = jbxxAjbh;
	}

	public String getJbxxAjbh() 
	{
		return jbxxAjbh;
	}
	public void setJbxxDsr(String jbxxDsr) 
	{
		this.jbxxDsr = jbxxDsr;
	}

	public String getJbxxDsr() 
	{
		return jbxxDsr;
	}
	public void setJbxxXajlb(String jbxxXajlb) 
	{
		this.jbxxXajlb = jbxxXajlb;
	}

	public String getJbxxXajlb() 
	{
		return jbxxXajlb;
	}
	public void setJbxxAjlb(String jbxxAjlb) 
	{
		this.jbxxAjlb = jbxxAjlb;
	}

	public String getJbxxAjlb() 
	{
		return jbxxAjlb;
	}
	public void setJbxxJbfy(String jbxxJbfy) 
	{
		this.jbxxJbfy = jbxxJbfy;
	}

	public String getJbxxJbfy() 
	{
		return jbxxJbfy;
	}
	public void setJbxxSpcx(String jbxxSpcx) 
	{
		this.jbxxSpcx = jbxxSpcx;
	}

	public String getJbxxSpcx() 
	{
		return jbxxSpcx;
	}
	public void setJbxxSycx(String jbxxSycx) 
	{
		this.jbxxSycx = jbxxSycx;
	}

	public String getJbxxSycx() 
	{
		return jbxxSycx;
	}
	public void setJbxxSayj(String jbxxSayj) 
	{
		this.jbxxSayj = jbxxSayj;
	}

	public String getJbxxSayj() 
	{
		return jbxxSayj;
	}
	public void setJbxxAh(String jbxxAh) 
	{
		this.jbxxAh = jbxxAh;
	}

	public String getJbxxAh() 
	{
		return jbxxAh;
	}
	public void setJbxxSsxz(String jbxxSsxz) 
	{
		this.jbxxSsxz = jbxxSsxz;
	}

	public String getJbxxSsxz() 
	{
		return jbxxSsxz;
	}
	public void setJbxxYsssxz(String jbxxYsssxz) 
	{
		this.jbxxYsssxz = jbxxYsssxz;
	}

	public String getJbxxYsssxz() 
	{
		return jbxxYsssxz;
	}
	public void setJbxxZhxgsj(String jbxxZhxgsj) 
	{
		this.jbxxZhxgsj = jbxxZhxgsj;
	}

	public String getJbxxZhxgsj() 
	{
		return jbxxZhxgsj;
	}
	public void setJbxxAjmc(String jbxxAjmc) 
	{
		this.jbxxAjmc = jbxxAjmc;
	}

	public String getJbxxAjmc() 
	{
		return jbxxAjmc;
	}
	public void setJbxxAjjzjd(String jbxxAjjzjd) 
	{
		this.jbxxAjjzjd = jbxxAjjzjd;
	}

	public String getJbxxAjjzjd() 
	{
		return jbxxAjjzjd;
	}
	public void setJbxxSfydzjz(String jbxxSfydzjz) 
	{
		this.jbxxSfydzjz = jbxxSfydzjz;
	}

	public String getJbxxSfydzjz() 
	{
		return jbxxSfydzjz;
	}
	public void setJbxxSfzwfz(String jbxxSfzwfz) 
	{
		this.jbxxSfzwfz = jbxxSfzwfz;
	}

	public String getJbxxSfzwfz() 
	{
		return jbxxSfzwfz;
	}
	public void setJbxxSplcgk(String jbxxSplcgk) 
	{
		this.jbxxSplcgk = jbxxSplcgk;
	}

	public String getJbxxSplcgk() 
	{
		return jbxxSplcgk;
	}
	public void setSadjSadjr(String sadjSadjr) 
	{
		this.sadjSadjr = sadjSadjr;
	}

	public String getSadjSadjr() 
	{
		return sadjSadjr;
	}
	public void setSadjSasj(String sadjSasj) 
	{
		this.sadjSasj = sadjSasj;
	}

	public String getSadjSasj() 
	{
		return sadjSasj;
	}
	public void setSadjMgaj(String sadjMgaj) 
	{
		this.sadjMgaj = sadjMgaj;
	}

	public String getSadjMgaj() 
	{
		return sadjMgaj;
	}
	public void setSadjDysj(String sadjDysj) 
	{
		this.sadjDysj = sadjDysj;
	}

	public String getSadjDysj() 
	{
		return sadjDysj;
	}
	public void setSadjSdszsj(String sadjSdszsj) 
	{
		this.sadjSdszsj = sadjSdszsj;
	}

	public String getSadjSdszsj() 
	{
		return sadjSdszsj;
	}
	public void setSadjLaay(String sadjLaay) 
	{
		this.sadjLaay = sadjLaay;
	}

	public String getSadjLaay() 
	{
		return sadjLaay;
	}
	public void setSadjGxyj(String sadjGxyj) 
	{
		this.sadjGxyj = sadjGxyj;
	}

	public String getSadjGxyj() 
	{
		return sadjGxyj;
	}
	public void setSadjLabm(String sadjLabm) 
	{
		this.sadjLabm = sadjLabm;
	}

	public String getSadjLabm() 
	{
		return sadjLabm;
	}
	public void setSadjSats(String sadjSats) 
	{
		this.sadjSats = sadjSats;
	}

	public String getSadjSats() 
	{
		return sadjSats;
	}
	public void setLascLascr(String lascLascr) 
	{
		this.lascLascr = lascLascr;
	}

	public String getLascLascr() 
	{
		return lascLascr;
	}
	public void setLascLascrq(String lascLascrq) 
	{
		this.lascLascrq = lascLascrq;
	}

	public String getLascLascrq() 
	{
		return lascLascrq;
	}
	public void setLascLascyj(String lascLascyj) 
	{
		this.lascLascyj = lascLascyj;
	}

	public String getLascLascyj() 
	{
		return lascLascyj;
	}
	public void setLaspLaspr(String laspLaspr) 
	{
		this.laspLaspr = laspLaspr;
	}

	public String getLaspLaspr() 
	{
		return laspLaspr;
	}
	public void setLaspLasprq(String laspLasprq) 
	{
		this.laspLasprq = laspLasprq;
	}

	public String getLaspLasprq() 
	{
		return laspLasprq;
	}
	public void setLaspLaspyj(String laspLaspyj) 
	{
		this.laspLaspyj = laspLaspyj;
	}

	public String getLaspLaspyj() 
	{
		return laspLaspyj;
	}
	public void setLaspLarq(String laspLarq) 
	{
		this.laspLarq = laspLarq;
	}

	public String getLaspLarq() 
	{
		return laspLarq;
	}
	public void setLaspLats(String laspLats) 
	{
		this.laspLats = laspLats;
	}

	public String getLaspLats() 
	{
		return laspLats;
	}
	public void setLaspLar(String laspLar) 
	{
		this.laspLar = laspLar;
	}

	public String getLaspLar() 
	{
		return laspLar;
	}
	public void setFaFarq(String faFarq) 
	{
		this.faFarq = faFarq;
	}

	public String getFaFarq() 
	{
		return faFarq;
	}
	public void setFaCbspt(String faCbspt) 
	{
		this.faCbspt = faCbspt;
	}

	public String getFaCbspt() 
	{
		return faCbspt;
	}
	public void setFaCbr(String faCbr) 
	{
		this.faCbr = faCbr;
	}

	public String getFaCbr() 
	{
		return faCbr;
	}
	public void setFaFaqrr(String faFaqrr) 
	{
		this.faFaqrr = faFaqrr;
	}

	public String getFaFaqrr() 
	{
		return faFaqrr;
	}
	public void setFaFaqrrq(String faFaqrrq) 
	{
		this.faFaqrrq = faFaqrrq;
	}

	public String getFaFaqrrq() 
	{
		return faFaqrrq;
	}
	public void setFaYjzt(String faYjzt) 
	{
		this.faYjzt = faYjzt;
	}

	public String getFaYjzt() 
	{
		return faYjzt;
	}
	public void setBlPqzt(String blPqzt) 
	{
		this.blPqzt = blPqzt;
	}

	public String getBlPqzt() 
	{
		return blPqzt;
	}
	public void setBlBgrtyrzcx(String blBgrtyrzcx) 
	{
		this.blBgrtyrzcx = blBgrtyrzcx;
	}

	public String getBlBgrtyrzcx() 
	{
		return blBgrtyrzcx;
	}
	public void setBlSftqzjjh(String blSftqzjjh) 
	{
		this.blSftqzjjh = blSftqzjjh;
	}

	public String getBlSftqzjjh() 
	{
		return blSftqzjjh;
	}
	public void setBlSffysjdqzj(String blSffysjdqzj) 
	{
		this.blSffysjdqzj = blSffysjdqzj;
	}

	public String getBlSffysjdqzj() 
	{
		return blSffysjdqzj;
	}
	public void setBlGksl(String blGksl) 
	{
		this.blGksl = blGksl;
	}

	public String getBlGksl() 
	{
		return blGksl;
	}
	public void setBlKysl(String blKysl) 
	{
		this.blKysl = blKysl;
	}

	public String getBlKysl() 
	{
		return blKysl;
	}
	public void setBlSsdlrs(String blSsdlrs) 
	{
		this.blSsdlrs = blSsdlrs;
	}

	public String getBlSsdlrs() 
	{
		return blSsdlrs;
	}
	public void setBlLsrs(String blLsrs) 
	{
		this.blLsrs = blLsrs;
	}

	public String getBlLsrs() 
	{
		return blLsrs;
	}
	public void setBlFlyzlsrs(String blFlyzlsrs) 
	{
		this.blFlyzlsrs = blFlyzlsrs;
	}

	public String getBlFlyzlsrs() 
	{
		return blFlyzlsrs;
	}
	public void setBlCxpq(String blCxpq) 
	{
		this.blCxpq = blCxpq;
	}

	public String getBlCxpq() 
	{
		return blCxpq;
	}
	public void setBlSfdrsp(String blSfdrsp) 
	{
		this.blSfdrsp = blSfdrsp;
	}

	public String getBlSfdrsp() 
	{
		return blSfdrsp;
	}
	public void setBlSfyrmpsy(String blSfyrmpsy) 
	{
		this.blSfyrmpsy = blSfyrmpsy;
	}

	public String getBlSfyrmpsy() 
	{
		return blSfyrmpsy;
	}
	public void setFdmsssSftqfdmsss(String fdmsssSftqfdmsss) 
	{
		this.fdmsssSftqfdmsss = fdmsssSftqfdmsss;
	}

	public String getFdmsssSftqfdmsss() 
	{
		return fdmsssSftqfdmsss;
	}
	public void setJaYszt(String jaYszt) 
	{
		this.jaYszt = jaYszt;
	}

	public String getJaYszt() 
	{
		return jaYszt;
	}
	public void setJaLdgw(String jaLdgw) 
	{
		this.jaLdgw = jaLdgw;
	}

	public String getJaLdgw() 
	{
		return jaLdgw;
	}
	public void setJaPjwhss(String jaPjwhss) 
	{
		this.jaPjwhss = jaPjwhss;
	}

	public String getJaPjwhss() 
	{
		return jaPjwhss;
	}
	public void setJaSftqyztjswh(String jaSftqyztjswh) 
	{
		this.jaSftqyztjswh = jaSftqyztjswh;
	}

	public String getJaSftqyztjswh() 
	{
		return jaSftqyztjswh;
	}
	public void setJaSpzhdrspysqrq(String jaSpzhdrspysqrq) 
	{
		this.jaSpzhdrspysqrq = jaSpzhdrspysqrq;
	}

	public String getJaSpzhdrspysqrq() 
	{
		return jaSpzhdrspysqrq;
	}
	public void setJaJarq(String jaJarq) 
	{
		this.jaJarq = jaJarq;
	}

	public String getJaJarq() 
	{
		return jaJarq;
	}
	public void setJaJaay(String jaJaay) 
	{
		this.jaJaay = jaJaay;
	}

	public String getJaJaay() 
	{
		return jaJaay;
	}
	public void setJaXjafs(String jaXjafs) 
	{
		this.jaXjafs = jaXjafs;
	}

	public String getJaXjafs() 
	{
		return jaXjafs;
	}
	public void setJaJafs(String jaJafs) 
	{
		this.jaJafs = jaJafs;
	}

	public String getJaJafs() 
	{
		return jaJafs;
	}
	public void setJaDtxp(String jaDtxp) 
	{
		this.jaDtxp = jaDtxp;
	}

	public String getJaDtxp() 
	{
		return jaDtxp;
	}
	public void setJaSfss(String jaSfss) 
	{
		this.jaSfss = jaSfss;
	}

	public String getJaSfss() 
	{
		return jaSfss;
	}
	public void setJaDspjarq(String jaDspjarq) 
	{
		this.jaDspjarq = jaDspjarq;
	}

	public String getJaDspjarq() 
	{
		return jaDspjarq;
	}
	public void setJaYwjaws(String jaYwjaws) 
	{
		this.jaYwjaws = jaYwjaws;
	}

	public String getJaYwjaws() 
	{
		return jaYwjaws;
	}
	public void setJaCpwssfjykzxnr(String jaCpwssfjykzxnr) 
	{
		this.jaCpwssfjykzxnr = jaCpwssfjykzxnr;
	}

	public String getJaCpwssfjykzxnr() 
	{
		return jaCpwssfjykzxnr;
	}
	public void setSxxxSxzts(String sxxxSxzts) 
	{
		this.sxxxSxzts = sxxxSxzts;
	}

	public String getSxxxSxzts() 
	{
		return sxxxSxzts;
	}
	public void setSxxxYcts(String sxxxYcts) 
	{
		this.sxxxYcts = sxxxYcts;
	}

	public String getSxxxYcts() 
	{
		return sxxxYcts;
	}
	public void setSxxxKcts(String sxxxKcts) 
	{
		this.sxxxKcts = sxxxKcts;
	}

	public String getSxxxKcts() 
	{
		return sxxxKcts;
	}
	public void setSxxxSxqsrq(String sxxxSxqsrq) 
	{
		this.sxxxSxqsrq = sxxxSxqsrq;
	}

	public String getSxxxSxqsrq() 
	{
		return sxxxSxqsrq;
	}
	public void setSxxxSxjmrq(String sxxxSxjmrq) 
	{
		this.sxxxSxjmrq = sxxxSxjmrq;
	}

	public String getSxxxSxjmrq() 
	{
		return sxxxSxjmrq;
	}
	public void setSxxxFdsxts(String sxxxFdsxts) 
	{
		this.sxxxFdsxts = sxxxFdsxts;
	}

	public String getSxxxFdsxts() 
	{
		return sxxxFdsxts;
	}
	public void setSxxxCsxts(String sxxxCsxts) 
	{
		this.sxxxCsxts = sxxxCsxts;
	}

	public String getSxxxCsxts() 
	{
		return sxxxCsxts;
	}
	public void setSxxxCsx(String sxxxCsx) 
	{
		this.sxxxCsx = sxxxCsx;
	}

	public String getSxxxCsx() 
	{
		return sxxxCsx;
	}
	public void setSxxxSlts(String sxxxSlts) 
	{
		this.sxxxSlts = sxxxSlts;
	}

	public String getSxxxSlts() 
	{
		return sxxxSlts;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jbxxAjbh", getJbxxAjbh())
            .append("jbxxDsr", getJbxxDsr())
            .append("jbxxXajlb", getJbxxXajlb())
            .append("jbxxAjlb", getJbxxAjlb())
            .append("jbxxJbfy", getJbxxJbfy())
            .append("jbxxSpcx", getJbxxSpcx())
            .append("jbxxSycx", getJbxxSycx())
            .append("jbxxSayj", getJbxxSayj())
            .append("jbxxAh", getJbxxAh())
            .append("jbxxSsxz", getJbxxSsxz())
            .append("jbxxYsssxz", getJbxxYsssxz())
            .append("jbxxZhxgsj", getJbxxZhxgsj())
            .append("jbxxAjmc", getJbxxAjmc())
            .append("jbxxAjjzjd", getJbxxAjjzjd())
            .append("jbxxSfydzjz", getJbxxSfydzjz())
            .append("jbxxSfzwfz", getJbxxSfzwfz())
            .append("jbxxSplcgk", getJbxxSplcgk())
            .append("sadjSadjr", getSadjSadjr())
            .append("sadjSasj", getSadjSasj())
            .append("sadjMgaj", getSadjMgaj())
            .append("sadjDysj", getSadjDysj())
            .append("sadjSdszsj", getSadjSdszsj())
            .append("sadjLaay", getSadjLaay())
            .append("sadjGxyj", getSadjGxyj())
            .append("sadjLabm", getSadjLabm())
            .append("sadjSats", getSadjSats())
            .append("lascLascr", getLascLascr())
            .append("lascLascrq", getLascLascrq())
            .append("lascLascyj", getLascLascyj())
            .append("laspLaspr", getLaspLaspr())
            .append("laspLasprq", getLaspLasprq())
            .append("laspLaspyj", getLaspLaspyj())
            .append("laspLarq", getLaspLarq())
            .append("laspLats", getLaspLats())
            .append("laspLar", getLaspLar())
            .append("faFarq", getFaFarq())
            .append("faCbspt", getFaCbspt())
            .append("faCbr", getFaCbr())
            .append("faFaqrr", getFaFaqrr())
            .append("faFaqrrq", getFaFaqrrq())
            .append("faYjzt", getFaYjzt())
            .append("blPqzt", getBlPqzt())
            .append("blBgrtyrzcx", getBlBgrtyrzcx())
            .append("blSftqzjjh", getBlSftqzjjh())
            .append("blSffysjdqzj", getBlSffysjdqzj())
            .append("blGksl", getBlGksl())
            .append("blKysl", getBlKysl())
            .append("blSsdlrs", getBlSsdlrs())
            .append("blLsrs", getBlLsrs())
            .append("blFlyzlsrs", getBlFlyzlsrs())
            .append("blCxpq", getBlCxpq())
            .append("blSfdrsp", getBlSfdrsp())
            .append("blSfyrmpsy", getBlSfyrmpsy())
            .append("fdmsssSftqfdmsss", getFdmsssSftqfdmsss())
            .append("jaYszt", getJaYszt())
            .append("jaLdgw", getJaLdgw())
            .append("jaPjwhss", getJaPjwhss())
            .append("jaSftqyztjswh", getJaSftqyztjswh())
            .append("jaSpzhdrspysqrq", getJaSpzhdrspysqrq())
            .append("jaJarq", getJaJarq())
            .append("jaJaay", getJaJaay())
            .append("jaXjafs", getJaXjafs())
            .append("jaJafs", getJaJafs())
            .append("jaDtxp", getJaDtxp())
            .append("jaSfss", getJaSfss())
            .append("jaDspjarq", getJaDspjarq())
            .append("jaYwjaws", getJaYwjaws())
            .append("jaCpwssfjykzxnr", getJaCpwssfjykzxnr())
            .append("sxxxSxzts", getSxxxSxzts())
            .append("sxxxYcts", getSxxxYcts())
            .append("sxxxKcts", getSxxxKcts())
            .append("sxxxSxqsrq", getSxxxSxqsrq())
            .append("sxxxSxjmrq", getSxxxSxjmrq())
            .append("sxxxFdsxts", getSxxxFdsxts())
            .append("sxxxCsxts", getSxxxCsxts())
            .append("sxxxCsx", getSxxxCsx())
            .append("sxxxSlts", getSxxxSlts())
			.append("dubvioId", getDubvioId())
            .toString();
    }
}
