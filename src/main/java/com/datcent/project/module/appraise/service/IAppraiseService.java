package com.datcent.project.module.appraise.service;

import com.datcent.project.module.appraise.domain.Appraise;
import java.util.List;

/**
 * 任务评价 服务层
 * 
 * @author datcent
 * @date 2019-01-24
 */
public interface IAppraiseService 
{
	/**
     * 查询任务评价信息
     * 
     * @param appraiseId 任务评价ID
     * @return 任务评价信息
     */
	public Appraise selectAppraiseById(String appraiseId);
	
	/**
     * 查询任务评价列表
     * 
     * @param appraise 任务评价信息
     * @return 任务评价集合
     */
	public List<Appraise> selectAppraiseList(Appraise appraise);
	
	/**
     * 新增任务评价
     * 
     * @param appraise 任务评价信息
     * @return 结果
     */
	public int insertAppraise(Appraise appraise);
	
	/**
     * 修改任务评价
     * 
     * @param appraise 任务评价信息
     * @return 结果
     */
	public int updateAppraise(Appraise appraise);
		
	/**
     * 删除任务评价信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAppraiseByIds(String ids);

	/*
	 *
	 * 根据任务编号查询
	 *
	 * */
	public Appraise selectAppraiseByTaskId(String taskId);
}
