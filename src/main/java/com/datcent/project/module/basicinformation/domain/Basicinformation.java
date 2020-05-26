package com.datcent.project.module.basicinformation.domain;


import com.datcent.framework.web.domain.BaseEntity;

/**
 * 基本情况登记表 arch_basicinformation
 * 
 * @author datcent
 * @date 2019-03-29
 */
public class Basicinformation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String jbqkId;
	/** 法院组织人员CID */
	private String jbqkCid;
	/** 姓名 */
	private String jbqkXm;
	/** 性别 */
	private String jbqkXb;
	/** 出生年月 */
	private String jbqkCsny;
	/** 曾用名 */
	private String jbqkCym;
	/** 民族 */
	private String jbqkMz;
	/** 身份证号 */
	private String jbqkSfzh;
	/** 籍贯 */
	private String jbqkJg;
	/** 婚姻状况 */
	private String jbqkHyzk;
	/** 文化程度 */
	private String jbqkWhcd;
	/** 政治面貌 */
	private String jbqkZzmm;
	/** 入党时间 */
	private String jbqkRdsj;
	/** 参加工作时间 */
	private String jbqkCjgzsj;
	/** 单位 */
	private String jbqkDw;
	/** 单位性质 */
	private String jbqkDwxz;
	/** 部门 */
	private String jbqkBm;
	/** 部门Id */
	private String jbqkBmd;
	/** 职位 */
	private String jbqkZw;
	/** 级别 */
	private String jbqkJb;
	/** 职称 */
	private String jbqkZc;
	/** 分管工作 */
	private String jbqkFggz;
	/** 任现职时间 */
	private String jbqkRxzsj;
	/** 住址 */
	private String jbqkZz;
	/** 邮编 */
	private String jbqkYb;
	/** 奖惩情况 */
	private String jbqkJcqk;

	/** 是否可编辑*/
	private String isEdit;

	/** 基本情况密码*/
	private String passWord;

	private String approvePerson;

	/**父级ID*/
	private String parentId;

    /**部门id */
	private String deptId;

	public String getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}

	public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setJbqkId(String jbqkId)
	{
		this.jbqkId = jbqkId;
	}

	public String getJbqkId() 
	{
		return jbqkId;
	}
	public void setJbqkCid(String jbqkCid) 
	{
		this.jbqkCid = jbqkCid;
	}

	public String getJbqkCid() 
	{
		return jbqkCid;
	}
	public void setJbqkXm(String jbqkXm) 
	{
		this.jbqkXm = jbqkXm;
	}

	public String getJbqkXm() 
	{
		return jbqkXm;
	}
	public void setJbqkXb(String jbqkXb) 
	{
		this.jbqkXb = jbqkXb;
	}

	public String getJbqkXb() 
	{
		return jbqkXb;
	}
	public void setJbqkCsny(String jbqkCsny) 
	{
		this.jbqkCsny = jbqkCsny;
	}

	public String getJbqkCsny() 
	{
		return jbqkCsny;
	}
	public void setJbqkCym(String jbqkCym) 
	{
		this.jbqkCym = jbqkCym;
	}

	public String getJbqkCym() 
	{
		return jbqkCym;
	}
	public void setJbqkMz(String jbqkMz) 
	{
		this.jbqkMz = jbqkMz;
	}

	public String getJbqkMz() 
	{
		return jbqkMz;
	}
	public void setJbqkSfzh(String jbqkSfzh) 
	{
		this.jbqkSfzh = jbqkSfzh;
	}

	public String getJbqkSfzh() 
	{
		return jbqkSfzh;
	}
	public void setJbqkJg(String jbqkJg) 
	{
		this.jbqkJg = jbqkJg;
	}

	public String getJbqkJg() 
	{
		return jbqkJg;
	}
	public void setJbqkHyzk(String jbqkHyzk) 
	{
		this.jbqkHyzk = jbqkHyzk;
	}

	public String getJbqkHyzk() 
	{
		return jbqkHyzk;
	}
	public void setJbqkWhcd(String jbqkWhcd) 
	{
		this.jbqkWhcd = jbqkWhcd;
	}

	public String getJbqkWhcd() 
	{
		return jbqkWhcd;
	}
	public void setJbqkZzmm(String jbqkZzmm) 
	{
		this.jbqkZzmm = jbqkZzmm;
	}

	public String getJbqkZzmm() 
	{
		return jbqkZzmm;
	}
	public void setJbqkRdsj(String jbqkRdsj) 
	{
		this.jbqkRdsj = jbqkRdsj;
	}

	public String getJbqkRdsj() 
	{
		return jbqkRdsj;
	}
	public void setJbqkCjgzsj(String jbqkCjgzsj) 
	{
		this.jbqkCjgzsj = jbqkCjgzsj;
	}

	public String getJbqkCjgzsj() 
	{
		return jbqkCjgzsj;
	}
	public void setJbqkDw(String jbqkDw) 
	{
		this.jbqkDw = jbqkDw;
	}

	public String getJbqkDw() 
	{
		return jbqkDw;
	}
	public void setJbqkDwxz(String jbqkDwxz) 
	{
		this.jbqkDwxz = jbqkDwxz;
	}

	public String getJbqkDwxz() 
	{
		return jbqkDwxz;
	}
	public void setJbqkZw(String jbqkZw) 
	{
		this.jbqkZw = jbqkZw;
	}

	public String getJbqkZw() 
	{
		return jbqkZw;
	}
	public void setJbqkJb(String jbqkJb) 
	{
		this.jbqkJb = jbqkJb;
	}

	public String getJbqkJb() 
	{
		return jbqkJb;
	}
	public void setJbqkZc(String jbqkZc) 
	{
		this.jbqkZc = jbqkZc;
	}

	public String getJbqkZc() 
	{
		return jbqkZc;
	}
	public void setJbqkFggz(String jbqkFggz) 
	{
		this.jbqkFggz = jbqkFggz;
	}

	public String getJbqkFggz() 
	{
		return jbqkFggz;
	}
	public void setJbqkRxzsj(String jbqkRxzsj) 
	{
		this.jbqkRxzsj = jbqkRxzsj;
	}

	public String getJbqkRxzsj() 
	{
		return jbqkRxzsj;
	}

	public String getJbqkBm() {
		return jbqkBm;
	}

	public void setJbqkBm(String jbqkBm) {
		this.jbqkBm = jbqkBm;
	}

	public String getJbqkBmd() {
		return jbqkBmd;
	}

	public void setJbqkBmd(String jbqkBmd) {
		this.jbqkBmd = jbqkBmd;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public void setJbqkZz(String jbqkZz)
	{
		this.jbqkZz = jbqkZz;
	}

	public String getJbqkZz() 
	{
		return jbqkZz;
	}
	public void setJbqkYb(String jbqkYb) 
	{
		this.jbqkYb = jbqkYb;
	}

	public String getJbqkYb() 
	{
		return jbqkYb;
	}
	public void setJbqkJcqk(String jbqkJcqk) 
	{
		this.jbqkJcqk = jbqkJcqk;
	}

	public String getJbqkJcqk() 
	{
		return jbqkJcqk;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "Basicinformation{" +
				"jbqkId='" + jbqkId + '\'' +
				", jbqkCid='" + jbqkCid + '\'' +
				", jbqkXm='" + jbqkXm + '\'' +
				", jbqkXb='" + jbqkXb + '\'' +
				", jbqkCsny='" + jbqkCsny + '\'' +
				", jbqkCym='" + jbqkCym + '\'' +
				", jbqkMz='" + jbqkMz + '\'' +
				", jbqkSfzh='" + jbqkSfzh + '\'' +
				", jbqkJg='" + jbqkJg + '\'' +
				", jbqkHyzk='" + jbqkHyzk + '\'' +
				", jbqkWhcd='" + jbqkWhcd + '\'' +
				", jbqkZzmm='" + jbqkZzmm + '\'' +
				", jbqkRdsj='" + jbqkRdsj + '\'' +
				", jbqkCjgzsj='" + jbqkCjgzsj + '\'' +
				", jbqkDw='" + jbqkDw + '\'' +
				", jbqkDwxz='" + jbqkDwxz + '\'' +
				", jbqkBm='" + jbqkBm + '\'' +
				", jbqkBmd='" + jbqkBmd + '\'' +
				", jbqkZw='" + jbqkZw + '\'' +
				", jbqkJb='" + jbqkJb + '\'' +
				", jbqkZc='" + jbqkZc + '\'' +
				", jbqkFggz='" + jbqkFggz + '\'' +
				", jbqkRxzsj='" + jbqkRxzsj + '\'' +
				", jbqkZz='" + jbqkZz + '\'' +
				", jbqkYb='" + jbqkYb + '\'' +
				", jbqkJcqk='" + jbqkJcqk + '\'' +
				", isEdit='" + isEdit + '\'' +
				", passWord='" + passWord + '\'' +
				", parentId='" + parentId + '\'' +
				", deptId='" + deptId + '\'' +
				'}';
	}

}
