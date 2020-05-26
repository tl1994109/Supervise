package com.datcent.project.module.strategy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.strategy.mapper.StrategyMapper;
import com.datcent.project.module.strategy.domain.Strategy;
import com.datcent.project.module.strategy.service.IStrategyService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 策略  服务层实现
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Service
public class StrategyServiceImpl implements IStrategyService 
{
	@Autowired
	private StrategyMapper strategyMapper;

	/**
     * 查询策略 信息
     * 
     * @param strategyId 策略 ID
     * @return 策略 信息
     */
    @Override
	public Strategy selectStrategyById(Integer strategyId)
	{
	    return strategyMapper.selectStrategyById(strategyId);
	}
	
	/**
     * 查询策略 列表
     * 
     * @param strategy 策略 信息
     * @return 策略 集合
     */
	@Override
	public List<Strategy> selectStrategyList(Strategy strategy)
	{
	    return strategyMapper.selectStrategyList(strategy);
	}
	
    /**
     * 新增策略 
     * 
     * @param strategy 策略 信息
     * @return 结果
     */
	@Override
	public int insertStrategy(Strategy strategy)
	{
	    return strategyMapper.insertStrategy(strategy);
	}
	
	/**
     * 修改策略 
     * 
     * @param strategy 策略 信息
     * @return 结果
     */
	@Override
	public int updateStrategy(Strategy strategy)
	{
	    return strategyMapper.updateStrategy(strategy);
	}

	/**
     * 删除策略 对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStrategyByIds(String ids)
	{
		return strategyMapper.deleteStrategyByIds(Convert.toStrArray(ids));
	}

	/**
	 * 查询所有的策略名字和策略id
	 * @return
	 */
	public List<Map>  queryStrategy(){
		return strategyMapper.queryStrategy();
	}

	/**
	 * 根据策略id来查策略名
	 * @return
	 */
	public String queryStrategyById(int id){
		return strategyMapper.queryStrategyById(id);
	}
	
}
