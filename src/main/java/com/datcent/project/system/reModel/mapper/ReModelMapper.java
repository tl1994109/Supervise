package com.datcent.project.system.reModel.mapper;

import com.datcent.project.system.reModel.domain.ReModel;

import java.util.List;

/**
 * 工作流模型 数据层
 * 
 * @author datcent
 * @date 2019-01-08
 */
public interface ReModelMapper 
{
	/**
     * 查询工作流模型信息
     * 
     * @param id 工作流模型ID
     * @return 工作流模型信息
     */
	public ReModel selectReModelById(String id);
	
	/**
     * 查询工作流模型列表
     * 
     * @param reModel 工作流模型信息
     * @return 工作流模型集合
     */
	public List<ReModel> selectReModelList(ReModel reModel);
	
	/**
     * 新增工作流模型
     * 
     * @param reModel 工作流模型信息
     * @return 结果
     */
	public int insertReModel(ReModel reModel);
	
	/**
     * 修改工作流模型
     * 
     * @param reModel 工作流模型信息
     * @return 结果
     */
	public int updateReModel(ReModel reModel);
	
	/**
     * 删除工作流模型
     * 
     * @param id 工作流模型ID
     * @return 结果
     */
	public int deleteReModelById(String id);
	
	/**
     * 批量删除工作流模型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteReModelByIds(String[] ids);
	
}