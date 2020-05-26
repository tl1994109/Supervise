package com.datcent.project.module.datashareFollow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 各法院查看权表 sys_datashare_follow
 * 
 * @author datcent
 * @date 2019-05-27
 */
public class DatashareFollow extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Integer iD;
	/** 法院ID(关联主表的datashare) */
	private String datashareCourtid;
	/** 共享法院ID */
	private String sharecourtId;
	/** 状态(0启用 2关闭) */
	private String status;
	/** 序号 */
	private String number;
	/** 创建时间 */
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新时间 */
	private Date updateTime;
	/** 更新人 */
	private String updateBy;

	/**从表名称*/
	private String sharecourtName;

	/**主表名称*/
	private String datashareName;



	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	public String getDatashareName() {
		return datashareName;
	}

	public void setDatashareName(String datashareName) {
		this.datashareName = datashareName;
	}

	public String getSharecourtName() {
		return sharecourtName;
	}

	public void setSharecourtName(String sharecourtName) {
		this.sharecourtName = sharecourtName;
	}
	public void setDatashareCourtid(String datashareCourtid) 
	{
		this.datashareCourtid = datashareCourtid;
	}

	public String getDatashareCourtid() 
	{
		return datashareCourtid;
	}
	public void setSharecourtId(String sharecourtId) 
	{
		this.sharecourtId = sharecourtId;
	}

	public String getSharecourtId() 
	{
		return sharecourtId;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}

	public String getNumber() 
	{
		return number;
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

	@Override
	public String toString() {
		return "DatashareFollow{" +
				"iD=" + iD +
				", datashareCourtid='" + datashareCourtid + '\'' +
				", sharecourtId='" + sharecourtId + '\'' +
				", status='" + status + '\'' +
				", number='" + number + '\'' +
				", createTime=" + createTime +
				", createBy='" + createBy + '\'' +
				", updateTime=" + updateTime +
				", updateBy='" + updateBy + '\'' +
				", sharecourtName='" + sharecourtName + '\'' +
				", datashareName='" + datashareName + '\'' +
				'}';
	}
}
