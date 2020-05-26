package com.datcent.project.module.implementationSystem.mapper;

import com.datcent.project.module.implementationSystem.domain.ImplementationSystem;
import java.util.List;	

/**
 * 落实规章制度情况 数据层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface ImplementationSystemMapper 
{
	/**
     * 查询落实规章制度情况信息
     * 
     * @param lsgzzdqkId 落实规章制度情况ID
     * @return 落实规章制度情况信息
     */
	public ImplementationSystem selectImplementationSystemById(String lsgzzdqkId);
	
	/**
     * 查询落实规章制度情况列表
     * 
     * @param implementationSystem 落实规章制度情况信息
     * @return 落实规章制度情况集合
     */
	public List<ImplementationSystem> selectImplementationSystemList(ImplementationSystem implementationSystem);
	
	/**
     * 新增落实规章制度情况
     * 
     * @param implementationSystem 落实规章制度情况信息
     * @return 结果
     */
	public int insertImplementationSystem(ImplementationSystem implementationSystem);
	
	/**
     * 修改落实规章制度情况
     * 
     * @param implementationSystem 落实规章制度情况信息
     * @return 结果
     */
	public int updateImplementationSystem(ImplementationSystem implementationSystem);
	
	/**
     * 删除落实规章制度情况
     * 
     * @param lsgzzdqkId 落实规章制度情况ID
     * @return 结果
     */
	public int deleteImplementationSystemById(String lsgzzdqkId);
	
	/**
     * 批量删除落实规章制度情况
     * 
     * @param lsgzzdqkIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteImplementationSystemByIds(String[] lsgzzdqkIds);
	
}