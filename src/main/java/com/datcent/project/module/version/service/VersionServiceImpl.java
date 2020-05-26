package com.datcent.project.module.version.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.version.mapper.VersionMapper;
import com.datcent.project.module.version.domain.Version;
import com.datcent.project.module.version.service.IVersionService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 项目版本管理 服务层实现
 * 
 * @author datcent
 * @date 2019-05-27
 */
@Service
public class VersionServiceImpl implements IVersionService 
{
	@Autowired
	private VersionMapper versionMapper;

	/**
     * 查询项目版本管理信息
     * 
     * @param versionId 项目版本管理ID
     * @return 项目版本管理信息
     */
    @Override
	public Version selectVersionById(Integer versionId)
	{
	    return versionMapper.selectVersionById(versionId);
	}
	
	/**
     * 查询项目版本管理列表
     * 
     * @param version 项目版本管理信息
     * @return 项目版本管理集合
     */
	@Override
	public List<Version> selectVersionList(Version version)
	{
	    return versionMapper.selectVersionList(version);
	}
	
    /**
     * 新增项目版本管理
     * 
     * @param version 项目版本管理信息
     * @return 结果
     */
	@Override
	public int insertVersion(Version version)
	{
	    return versionMapper.insertVersion(version);
	}
	
	/**
     * 修改项目版本管理
     * 
     * @param version 项目版本管理信息
     * @return 结果
     */
	@Override
	public int updateVersion(Version version)
	{
	    return versionMapper.updateVersion(version);
	}

	/**
     * 删除项目版本管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVersionByIds(String ids)
	{
		return versionMapper.deleteVersionByIds(Convert.toStrArray(ids));
	}
	
}
