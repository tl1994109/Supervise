package com.datcent.project.module.subgroupEvent.mapper;

import com.datcent.project.module.subgroupEvent.domain.SubgroupEvent;
import java.util.List;	

/**
 * 添加事件 数据层
 * 
 * @author datcent
 * @date 2018-11-30
 */
public interface SubgroupEventMapper 
{
	/**
     * 查询添加事件信息
     * 
     * @param eventId 添加事件ID
     * @return 添加事件信息
     */
	public SubgroupEvent selectSubgroupEventById(String eventId);
	
	/**
     * 查询添加事件列表
     * 
     * @param subgroupEvent 添加事件信息
     * @return 添加事件集合
     */
	public List<SubgroupEvent> selectSubgroupEventList(SubgroupEvent subgroupEvent);
	
	/**
     * 新增添加事件
     * 
     * @param subgroupEvent 添加事件信息
     * @return 结果
     */
	public int insertSubgroupEvent(SubgroupEvent subgroupEvent);
	
	/**
     * 修改添加事件
     * 
     * @param subgroupEvent 添加事件信息
     * @return 结果
     */
	public int updateSubgroupEvent(SubgroupEvent subgroupEvent);
	
	/**
     * 删除添加事件
     * 
     * @param eventId 添加事件ID
     * @return 结果
     */
	public int deleteSubgroupEventById(String eventId);
	
	/**
     * 批量删除添加事件
     * 
     * @param eventIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteSubgroupEventByIds(String[] eventIds);
	
}