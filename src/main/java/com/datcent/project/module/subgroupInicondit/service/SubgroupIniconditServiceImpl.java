package com.datcent.project.module.subgroupInicondit.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.subgroupInicondit.mapper.SubgroupIniconditMapper;
import com.datcent.project.module.subgroupInicondit.domain.SubgroupInicondit;
import com.datcent.project.module.subgroupInicondit.service.ISubgroupIniconditService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 初始条件 服务层实现
 * 
 * @author datcent
 * @date 2018-11-30
 */
@Service
public class SubgroupIniconditServiceImpl implements ISubgroupIniconditService 
{
	@Autowired
	private SubgroupIniconditMapper subgroupIniconditMapper;

	/**
     * 查询初始条件信息
     * 
     * @param iniconditId 初始条件ID
     * @return 初始条件信息
     */
    @Override
	public SubgroupInicondit selectSubgroupIniconditById(String iniconditId)
	{
	    return subgroupIniconditMapper.selectSubgroupIniconditById(iniconditId);
	}
	
	/**
     * 查询初始条件列表
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 初始条件集合
     */
	@Override
	public List<SubgroupInicondit> selectSubgroupIniconditList(SubgroupInicondit subgroupInicondit)
	{
	    return subgroupIniconditMapper.selectSubgroupIniconditList(subgroupInicondit);
	}
	
    /**
     * 新增初始条件
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 结果
     */
	@Override
	public int insertSubgroupInicondit(SubgroupInicondit subgroupInicondit)
	{
	    return subgroupIniconditMapper.insertSubgroupInicondit(subgroupInicondit);
	}
	
	/**
     * 修改初始条件
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 结果
     */
	@Override
	public int updateSubgroupInicondit(SubgroupInicondit subgroupInicondit)
	{
	    return subgroupIniconditMapper.updateSubgroupInicondit(subgroupInicondit);
	}

	/**
     * 删除初始条件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSubgroupIniconditByIds(String ids)
	{
		return subgroupIniconditMapper.deleteSubgroupIniconditByIds(Convert.toStrArray(ids));
	}
	
}
