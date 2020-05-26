package com.datcent.project.system.reProcdef.mapper;

import com.datcent.project.system.reProcdef.domain.ReProcdef;

import java.util.List;

/**
 * 业务流程定义 数据层
 * 
 * @author datcent
 * @date 2019-01-08
 */
public interface ReProcdefMapper 
{
	/**
     * 查询业务流程定义信息
     * 
     * @param id 业务流程定义ID
     * @return 业务流程定义信息
     */
	public ReProcdef selectReProcdefById(String id);
	
	/**
     * 查询业务流程定义列表
     * 
     * @param reProcdef 业务流程定义信息
     * @return 业务流程定义集合
     */
	public List<ReProcdef> selectReProcdefList(ReProcdef reProcdef);
	
	/**
     * 新增业务流程定义
     * 
     * @param reProcdef 业务流程定义信息
     * @return 结果
     */
	public int insertReProcdef(ReProcdef reProcdef);
	
	/**
     * 修改业务流程定义
     * 
     * @param reProcdef 业务流程定义信息
     * @return 结果
     */
	public int updateReProcdef(ReProcdef reProcdef);
	
	/**
     * 删除业务流程定义
     * 
     * @param id 业务流程定义ID
     * @return 结果
     */
	public int deleteReProcdefById(String id);
	
	/**
     * 批量删除业务流程定义
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteReProcdefByIds(String[] ids);

}