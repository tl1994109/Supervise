package com.datcent.project.module.serving.service;

import com.datcent.project.module.serving.domain.Serving;
import java.util.List;

/**
 * 主要任职情况 服务层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface IServingService 
{
	/**
     * 查询主要任职情况信息
     * 
     * @param id 主要任职情况ID
     * @return 主要任职情况信息
     */
	public Serving selectServingById(String id);
	
	/**
     * 查询主要任职情况列表
     * 
     * @param serving 主要任职情况信息
     * @return 主要任职情况集合
     */
	public List<Serving> selectServingList(Serving serving);
	
	/**
     * 新增主要任职情况
     * 
     * @param serving 主要任职情况信息
     * @return 结果
     */
	public int insertServing(Serving serving);
	
	/**
     * 修改主要任职情况
     * 
     * @param serving 主要任职情况信息
     * @return 结果
     */
	public int updateServing(Serving serving);
		
	/**
     * 删除主要任职情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteServingByIds(String ids);
	
}
