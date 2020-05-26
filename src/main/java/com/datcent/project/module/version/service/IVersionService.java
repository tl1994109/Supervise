package com.datcent.project.module.version.service;

import com.datcent.project.module.version.domain.Version;
import java.util.List;

/**
 * 项目版本管理 服务层
 * 
 * @author datcent
 * @date 2019-05-27
 */
public interface IVersionService 
{
	/**
     * 查询项目版本管理信息
     * 
     * @param versionId 项目版本管理ID
     * @return 项目版本管理信息
     */
	public Version selectVersionById(Integer versionId);
	
	/**
     * 查询项目版本管理列表
     * 
     * @param version 项目版本管理信息
     * @return 项目版本管理集合
     */
	public List<Version> selectVersionList(Version version);
	
	/**
     * 新增项目版本管理
     * 
     * @param version 项目版本管理信息
     * @return 结果
     */
	public int insertVersion(Version version);
	
	/**
     * 修改项目版本管理
     * 
     * @param version 项目版本管理信息
     * @return 结果
     */
	public int updateVersion(Version version);
		
	/**
     * 删除项目版本管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVersionByIds(String ids);
	
}
