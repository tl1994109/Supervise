package com.datcent.project.module.hiTaskinst.service;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.hiTaskinst.mapper.HiTaskinstMapper;
import com.datcent.project.module.hiTaskinst.domain.HiTaskinst;
import com.datcent.project.module.hiTaskinst.service.IHiTaskinstService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 历史任务 服务层实现
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Service
public class HiTaskinstServiceImpl implements IHiTaskinstService 
{
	@Autowired
	private HiTaskinstMapper hiTaskinstMapper;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RepositoryService repositoryService;

	/**
     * 查询历史任务信息
     * 
     * @param id 历史任务ID
     * @return 历史任务信息
     */
    @Override
	public HiTaskinst selectHiTaskinstById(String id)
	{
	    return hiTaskinstMapper.selectHiTaskinstById(id);
	}
	
	/**
     * 查询历史任务列表
     * 
     * @param hiTaskinst 历史任务信息
     * @return 历史任务集合
     */
	@Override
	public List<HiTaskinst> selectHiTaskinstList(HiTaskinst hiTaskinst)
	{
	    return hiTaskinstMapper.selectHiTaskinstList(hiTaskinst);
	}
	
    /**
     * 新增历史任务
     * 
     * @param hiTaskinst 历史任务信息
     * @return 结果
     */
	@Override
	public int insertHiTaskinst(HiTaskinst hiTaskinst)
	{
	    return hiTaskinstMapper.insertHiTaskinst(hiTaskinst);
	}
	
	/**
     * 修改历史任务
     * 
     * @param hiTaskinst 历史任务信息
     * @return 结果
     */
	@Override
	public int updateHiTaskinst(HiTaskinst hiTaskinst)
	{
	    return hiTaskinstMapper.updateHiTaskinst(hiTaskinst);
	}

	/**
     * 删除历史任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteHiTaskinstByIds(String ids)
	{
		return hiTaskinstMapper.deleteHiTaskinstByIds(Convert.toStrArray(ids));
	}

	/*
	* 查询流程部署
	*
	*
	* */
	@Override
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessDefinition processDefinition = null;
		if(task == null){
			HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicTaskInstance.getProcessDefinitionId()).singleResult();
		}else{
			processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
		}

		return processDefinition;
	}
}
