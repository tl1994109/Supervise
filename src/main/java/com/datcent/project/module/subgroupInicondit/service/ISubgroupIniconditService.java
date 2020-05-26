package com.datcent.project.module.subgroupInicondit.service;

import com.datcent.project.module.subgroupInicondit.domain.SubgroupInicondit;
import java.util.List;

/**
 * 初始条件 服务层
 * 
 * @author datcent
 * @date 2018-11-30
 */
public interface ISubgroupIniconditService 
{
	/**
     * 查询初始条件信息
     * 
     * @param iniconditId 初始条件ID
     * @return 初始条件信息
     */
	public SubgroupInicondit selectSubgroupIniconditById(String iniconditId);
	
	/**
     * 查询初始条件列表
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 初始条件集合
     */
	public List<SubgroupInicondit> selectSubgroupIniconditList(SubgroupInicondit subgroupInicondit);
	
	/**
     * 新增初始条件
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 结果
     */
	public int insertSubgroupInicondit(SubgroupInicondit subgroupInicondit);
	
	/**
     * 修改初始条件
     * 
     * @param subgroupInicondit 初始条件信息
     * @return 结果
     */
	public int updateSubgroupInicondit(SubgroupInicondit subgroupInicondit);
		
	/**
     * 删除初始条件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSubgroupIniconditByIds(String ids);
	
}
