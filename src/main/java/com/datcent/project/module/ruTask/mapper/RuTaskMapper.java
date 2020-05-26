package com.datcent.project.module.ruTask.mapper;

import com.datcent.project.module.ruTask.domain.RuTask;
import java.util.List;	

/**
 * 任务列 数据层
 * 
 * @author datcent
 * @date 2019-01-12
 */
public interface RuTaskMapper 
{
	/**
     * 查询任务列信息
     * 
     * @param id 任务列ID
     * @return 任务列信息
     */
	public RuTask selectRuTaskById(String id);
	
	/**
     * 查询任务列列表
     * 
     * @param ruTask 任务列信息
     * @return 任务列集合
     */
	public List<RuTask> selectRuTaskList(RuTask ruTask);
	
	/**
     * 新增任务列
     * 
     * @param ruTask 任务列信息
     * @return 结果
     */
	public int insertRuTask(RuTask ruTask);
	
	/**
     * 修改任务列
     * 
     * @param ruTask 任务列信息
     * @return 结果
     */
	public int updateRuTask(RuTask ruTask);
	
	/**
     * 删除任务列
     * 
     * @param id 任务列ID
     * @return 结果
     */
	public int deleteRuTaskById(String id);
	
	/**
     * 批量删除任务列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRuTaskByIds(String[] ids);
	
}