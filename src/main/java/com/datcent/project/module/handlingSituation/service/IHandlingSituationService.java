package com.datcent.project.module.handlingSituation.service;

import com.datcent.project.module.handlingSituation.domain.HandlingSituation;
import java.util.List;

/**
 * 党政纪处理及四拒情况 服务层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface IHandlingSituationService 
{
	/**
     * 查询党政纪处理及四拒情况信息
     * 
     * @param cljsjqkId 党政纪处理及四拒情况ID
     * @return 党政纪处理及四拒情况信息
     */
	public HandlingSituation selectHandlingSituationById(String cljsjqkId);
	
	/**
     * 查询党政纪处理及四拒情况列表
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 党政纪处理及四拒情况集合
     */
	public List<HandlingSituation> selectHandlingSituationList(HandlingSituation handlingSituation);
	
	/**
     * 新增党政纪处理及四拒情况
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 结果
     */
	public int insertHandlingSituation(HandlingSituation handlingSituation);
	
	/**
     * 修改党政纪处理及四拒情况
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 结果
     */
	public int updateHandlingSituation(HandlingSituation handlingSituation);
		
	/**
     * 删除党政纪处理及四拒情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteHandlingSituationByIds(String ids);
	
}
