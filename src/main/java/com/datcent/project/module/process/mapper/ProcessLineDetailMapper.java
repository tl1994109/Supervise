package com.datcent.project.module.process.mapper;

import com.datcent.project.module.process.domain.ProcessLineDetail;
import java.util.List;	

/**
 * 流程线条  数据层
 * 
 * @author datcent
 * @date 2018-11-02
 */
public interface ProcessLineDetailMapper 
{
	/**
     * 查询流程线条 信息
     * 
     * @param processId 流程线条 ID
     * @return 流程线条 信息
     */
	public ProcessLineDetail selectProcessLineDetailById(String processId);
	
	/**
     * 查询流程线条 列表
     * 
     * @param processLineDetail 流程线条 信息
     * @return 流程线条 集合
     */
	public List<ProcessLineDetail> selectProcessLineDetailList(ProcessLineDetail processLineDetail);
	
	/**
     * 新增流程线条 
     * 
     * @param processLineDetail 流程线条 信息
     * @return 结果
     */
	public int insertProcessLineDetail(ProcessLineDetail processLineDetail);
	
	/**
     * 修改流程线条 
     * 
     * @param processLineDetail 流程线条 信息
     * @return 结果
     */
	public int updateProcessLineDetail(ProcessLineDetail processLineDetail);
	
	/**
     * 删除流程线条 
     * 
     * @param processId 流程线条 ID
     * @return 结果
     */
	public int deleteProcessLineDetailById(String processId);
	
	/**
     * 批量删除流程线条 
     * 
     * @param processIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessLineDetailByIds(String[] processIds);
	
}