package com.datcent.project.module.process.service;

import com.datcent.project.module.process.domain.ProcessResume;
import java.util.List;

/**
 * 流程履历  服务层
 * 
 * @author datcent
 * @date 2018-11-08
 */
public interface IProcessResumeService 
{
	/**
     * 查询流程履历 信息
     * 
     * @param id 流程履历 ID
     * @return 流程履历 信息
     */
	public ProcessResume selectProcessResumeById(String id);
	
	/**
     * 查询流程履历 列表
     * 
     * @param processResume 流程履历 信息
     * @return 流程履历 集合
     */
	public List<ProcessResume> selectProcessResumeList(ProcessResume processResume);
	
	/**
     * 新增流程履历 
     * 
     * @param processResume 流程履历 信息
     * @return 结果
     */
	public int insertProcessResume(ProcessResume processResume);
	
	/**
     * 修改流程履历 
     * 
     * @param processResume 流程履历 信息
     * @return 结果
     */
	public int updateProcessResume(ProcessResume processResume);
		
	/**
     * 删除流程履历 信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessResumeByIds(String ids);
	
}
