package com.datcent.project.module.hiTaskinst.service;

import com.datcent.project.module.hiTaskinst.domain.HiTaskinst;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 历史任务 服务层
 * 
 * @author datcent
 * @date 2019-01-16
 */
public interface IHiTaskinstService 
{
	/**
     * 查询历史任务信息
     * 
     * @param id 历史任务ID
     * @return 历史任务信息
     */
	public HiTaskinst selectHiTaskinstById(String id);
	
	/**
     * 查询历史任务列表
     * 
     * @param hiTaskinst 历史任务信息
     * @return 历史任务集合
     */
	public List<HiTaskinst> selectHiTaskinstList(HiTaskinst hiTaskinst);
	
	/**
     * 新增历史任务
     * 
     * @param hiTaskinst 历史任务信息
     * @return 结果
     */
	public int insertHiTaskinst(HiTaskinst hiTaskinst);
	
	/**
     * 修改历史任务
     * 
     * @param hiTaskinst 历史任务信息
     * @return 结果
     */
	public int updateHiTaskinst(HiTaskinst hiTaskinst);
		
	/**
     * 删除历史任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteHiTaskinstByIds(String ids);

	/*查询流程部署*/
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId);
	
}
