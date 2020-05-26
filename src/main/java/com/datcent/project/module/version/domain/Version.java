package com.datcent.project.module.version.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 项目版本管理表 sys_version
 * 
 * @author datcent
 * @date 2019-05-27
 */
public class Version extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 版本id */
	private Integer versionId;
	/** 升级包名称 */
	private String versionName;
	/** 类型 */
	private String  versionType;
	/** 版本号 */
	private String versionNumber;
	/**
	 * 版本日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date versionCreattime;
	/**
	 * 版本上传日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date versionUploadtime;
	/** 版本上传路径 */
	private String versionPath;
	/** 版本说明 */
	private String versionInfo;

	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Date getVersionCreattime() {
		return versionCreattime;
	}

	public void setVersionCreattime(Date versionCreattime) {
		this.versionCreattime = versionCreattime;
	}

	public Date getVersionUploadtime() {
		return versionUploadtime;
	}

	public void setVersionUploadtime(Date versionUploadtime) {
		this.versionUploadtime = versionUploadtime;
	}

	public String getVersionPath() {
		return versionPath;
	}

	public void setVersionPath(String versionPath) {
		this.versionPath = versionPath;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("versionId", getVersionId())
            .append("versionName", getVersionName())
            .append("versionType", getVersionType())
            .append("versionNumber", getVersionNumber())
            .append("versionCreattime", getVersionCreattime())
            .append("versionUploadtime", getVersionUploadtime())
            .append("versionPath", getVersionPath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("versionInfo", getVersionInfo())
            .toString();
    }
}
