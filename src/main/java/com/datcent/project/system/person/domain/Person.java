package com.datcent.project.system.person.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.datcent.project.system.post.domain.Post;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datcent.framework.web.domain.BaseEntity;
import com.datcent.project.system.dept.domain.Dept;

/**
 * 
 * @描述: 人员 表 sys_person
 * @创建人: zhou_shiji
 * @创建时间: 2018年10月19日下午3:16:00
 */
public class Person extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 人员ID */
	private String personId;
	/** 姓名 */
	private String personName;
	/** 性别 */
	private String sex;
	/** 年龄 */
	private Integer age;
	/** 手机号码 */
	private String mobilePhone;
	/** 办公电话 */
	private String officePhone;
	/** 邮箱 */
	private String email;
	/** 身份证号 */
	private String identityId;
	/** 家庭住址 */
	private String homeAddress;
	/** 办公地址 */
	private String officeAddress;
	/** 部门 */
	private String deptId;
	/** 职位 */
	private String officePosition;
	/** 工号 */
	private String officeId;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 删除状态 */
	private String delFlag;
	/** 删除时间 */
	private Date delTime;
	/** 备注 */
	private String remarks;
	
	/** 部门对象 */
    private Dept dept;
	
	/** 岗位组 */
    private Long[] postIds;



	/** 岗位组List */
	private List<Post> postList;

	/** 所属部门*/
	private String deptName;


	/** 所属单位id*/
	private String parentDeptId;
	/** 所属单位*/
	private String parentDeptName;

	public void setPersonId(String personId) 
	{
		this.personId = personId;
	}

	public String getPersonId() 
	{
		return personId;
	}
	public void setPersonName(String personName) 
	{
		this.personName = personName;
	}

	public String getPersonName() 
	{
		return personName;
	}
	public void setSex(String sex) 
	{
		this.sex = sex;
	}

	public String getSex() 
	{
		return sex;
	}
	public void setAge(Integer age) 
	{
		this.age = age;
	}

	public Integer getAge() 
	{
		return age;
	}
	public void setMobilePhone(String mobilePhone) 
	{
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhone() 
	{
		return mobilePhone;
	}
	public void setOfficePhone(String officePhone) 
	{
		this.officePhone = officePhone;
	}

	public String getOfficePhone() 
	{
		return officePhone;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}
	public void setIdentityId(String identityId) 
	{
		this.identityId = identityId;
	}

	public String getIdentityId() 
	{
		return identityId;
	}
	public void setHomeAddress(String homeAddress) 
	{
		this.homeAddress = homeAddress;
	}

	public String getHomeAddress() 
	{
		return homeAddress;
	}
	public void setOfficeAddress(String officeAddress) 
	{
		this.officeAddress = officeAddress;
	}

	public String getOfficeAddress() 
	{
		return officeAddress;
	}
	public void setDeptId(String deptId) 
	{
		this.deptId = deptId;
	}

	public String getDeptId() 
	{
		return deptId;
	}
	public void setOfficePosition(String officePosition) 
	{
		this.officePosition = officePosition;
	}

	public String getOfficePosition() 
	{
		return officePosition;
	}
	public void setOfficeId(String officeId) 
	{
		this.officeId = officeId;
	}

	public String getOfficeId() 
	{
		return officeId;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

    public Long[] getPostIds() {
		return postIds;
	}

	public void setPostIds(Long[] postIds) {
		this.postIds = postIds;
	}

	
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@Override
	public String toString() {
		return "Person{" +
				"personId='" + personId + '\'' +
				", personName='" + personName + '\'' +
				", sex='" + sex + '\'' +
				", age=" + age +
				", mobilePhone='" + mobilePhone + '\'' +
				", officePhone='" + officePhone + '\'' +
				", email='" + email + '\'' +
				", identityId='" + identityId + '\'' +
				", homeAddress='" + homeAddress + '\'' +
				", officeAddress='" + officeAddress + '\'' +
				", deptId='" + deptId + '\'' +
				", officePosition='" + officePosition + '\'' +
				", officeId='" + officeId + '\'' +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				", delFlag='" + delFlag + '\'' +
				", delTime=" + delTime +
				", remarks='" + remarks + '\'' +
				", dept=" + dept +
				", postIds=" + Arrays.toString(postIds) +
				", postList=" + postList +
				", deptName='" + deptName + '\'' +
				", parentDeptId='" + parentDeptId + '\'' +
				", parentDeptName='" + parentDeptName + '\'' +
				'}';
	}

	public Person(String deptId) {
		this.deptId = deptId;
	}

	public Person() {
	}
}
