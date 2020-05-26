package com.datcent.project.module.goingAbroad.mapper;

import com.datcent.project.module.goingAbroad.domain.GoingAbroad;
import java.util.List;	

/**
 * 出国情况登记 数据层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface GoingAbroadMapper 
{
	/**
     * 查询出国情况登记信息
     * 
     * @param cgqkdjId 出国情况登记ID
     * @return 出国情况登记信息
     */
	public GoingAbroad selectGoingAbroadById(String cgqkdjId);
	
	/**
     * 查询出国情况登记列表
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 出国情况登记集合
     */
	public List<GoingAbroad> selectGoingAbroadList(GoingAbroad goingAbroad);
	
	/**
     * 新增出国情况登记
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 结果
     */
	public int insertGoingAbroad(GoingAbroad goingAbroad);
	
	/**
     * 修改出国情况登记
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 结果
     */
	public int updateGoingAbroad(GoingAbroad goingAbroad);
	
	/**
     * 删除出国情况登记
     * 
     * @param cgqkdjId 出国情况登记ID
     * @return 结果
     */
	public int deleteGoingAbroadById(String cgqkdjId);
	
	/**
     * 批量删除出国情况登记
     * 
     * @param cgqkdjIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoingAbroadByIds(String[] cgqkdjIds);
	
}