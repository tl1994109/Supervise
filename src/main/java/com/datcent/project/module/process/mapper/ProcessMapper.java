package com.datcent.project.module.process.mapper;

import com.datcent.project.module.process.domain.Process;
import java.util.List;	

/**
 * 流程 数据层
 * 
 * @author datcent
 * @date 2018-11-07
 */
public interface ProcessMapper 
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
     * 修改流程
     * 
     * @param process 流程信息
     * @return 结果
     */
	public int updateProcess(Process process);
	
	/**
     * 删除流程
     * 
     * @param processId 流程ID
     * @return 结果
     */
	public int deleteProcessById(String processId);
	
	/**
     * 批量删除流程
     * 
     * @param processIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessByIds(String[] processIds);
	
}