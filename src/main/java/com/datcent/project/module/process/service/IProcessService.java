package com.datcent.project.module.process.service;

import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;

import java.util.List;

/**
 * 流程 服务层
 * 
 * @author datcent
 * @date 2018-11-07
 */
public interface IProcessService 
{
	/**
     * 查询流程信息
     * 
     * @param processId 流程ID
     * @return 流程信息
     */
	public Process selectProcessById(String processId);
	
	/**
     * 查询流程列表
     * 
     * @param process 流程信息
     * @return 流程集合
     */
	public List<Process> selectProcessList(Process process);
	
	/**
     * 新增流程
     * 
     * @param process 流程信息
     * @return 结果
     */
	public int insertProcess(Process process);
	
	
	/**
     * 
     * 描述：获取当前节点执行信息
     * 时间：2018年11月12日 上午10:14:26
     * 作者：zhou_shiji
     * @return
     */
	public ProcessNodeDetail getLocalNodeInfo(String processId, String nodeId);
	
	/**
     * 
     * 描述：获取上一节点执行信息
     * 时间：2018年11月12日 上午10:14:26
     * 作者：zhou_shiji
     * @return
     */
	public ProcessNodeDetail getPrevNodeInfo(String processId, String nodeId);
	
	
	/**
     * 修改流程
     * 
     * @param process 流程信息
     * @return 结果
     */
	public int updateProcess(Process process);
		
	/**
     * 删除流程信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessByIds(String ids);
	
}
