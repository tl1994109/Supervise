package com.datcent.project.module.process.mapper;

import com.datcent.project.module.process.domain.ProcessMatter;
import java.util.List;	

/**
 * 流程事项  数据层
 * 
 * @author datcent
 * @date 2018-11-08
 */
public interface ProcessMatterMapper 
{
	/**
     * 查询流程事项 信息
     * 
     * @param matterId 流程事项 ID
     * @return 流程事项 信息
     */
	public ProcessMatter selectProcessMatterById(String matterId);
	
	/**
     * 查询流程事项 列表
     * 
     * @param processMatter 流程事项 信息
     * @return 流程事项 集合
     */
	public List<ProcessMatter> selectProcessMatterList(ProcessMatter processMatter);
	
	/**
     * 新增流程事项 
     * 
     * @param processMatter 流程事项 信息
     * @return 结果
     */
	public int insertProcessMatter(ProcessMatter processMatter);
	
	/**
     * 修改流程事项 
     * 
     * @param processMatter 流程事项 信息
     * @return 结果
     */
	public int updateProcessMatter(ProcessMatter processMatter);
	
	/**
     * 删除流程事项 
     * 
     * @param matterId 流程事项 ID
     * @return 结果
     */
	public int deleteProcessMatterById(String matterId);
	
	/**
     * 批量删除流程事项 
     * 
     * @param matterIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessMatterByIds(String[] matterIds);
	
}