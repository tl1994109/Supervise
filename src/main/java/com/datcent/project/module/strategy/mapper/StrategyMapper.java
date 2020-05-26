package com.datcent.project.module.strategy.mapper;

import com.datcent.project.module.strategy.domain.Strategy;
import java.util.List;
import java.util.Map;

/**
 * 策略  数据层
 * 
 * @author datcent
 * @date 2019-01-16
 */
public interface StrategyMapper 
{
	/**
     * 查询策略 信息
     * 
     * @param strategyId 策略 ID
     * @return 策略 信息
     */
	public Strategy selectStrategyById(Integer strategyId);
	
	/**
     * 查询策略 列表
     * 
     * @param strategy 策略 信息
     * @return 策略 集合
     */
	public List<Strategy> selectStrategyList(Strategy strategy);
	
	/**
     * 新增策略 
     * 
     * @param strategy 策略 信息
     * @return 结果
     */
	public int insertStrategy(Strategy strategy);
	
	/**
     * 修改策略 
     * 
     * @param strategy 策略 信息
     * @return 结果
     */
	public int updateStrategy(Strategy strategy);
	
	/**
     * 删除策略 
     * 
     * @param strategyId 策略 ID
     * @return 结果
     */
	public int deleteStrategyById(Integer strategyId);
	
	/**
     * 批量删除策略 
     * 
     * @param strategyIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteStrategyByIds(String[] strategyIds);

	/**
	 * 查询所有的策略名字和策略id
	 * @return
	 */
	public List<Map>  queryStrategy();

	/**
	 * 根据策略id来查策略名
	 * @return
	 */
	public String queryStrategyById(int id);
	
}