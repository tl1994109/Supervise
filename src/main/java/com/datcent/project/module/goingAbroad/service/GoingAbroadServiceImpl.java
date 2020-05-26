package com.datcent.project.module.goingAbroad.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.goingAbroad.mapper.GoingAbroadMapper;
import com.datcent.project.module.goingAbroad.domain.GoingAbroad;
import com.datcent.project.module.goingAbroad.service.IGoingAbroadService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 出国情况登记 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class GoingAbroadServiceImpl implements IGoingAbroadService 
{
	@Autowired
	private GoingAbroadMapper goingAbroadMapper;

	/**
     * 查询出国情况登记信息
     * 
     * @param cgqkdjId 出国情况登记ID
     * @return 出国情况登记信息
     */
    @Override
	public GoingAbroad selectGoingAbroadById(String cgqkdjId)
	{
	    return goingAbroadMapper.selectGoingAbroadById(cgqkdjId);
	}
	
	/**
     * 查询出国情况登记列表
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 出国情况登记集合
     */
	@Override
	public List<GoingAbroad> selectGoingAbroadList(GoingAbroad goingAbroad)
	{
	    return goingAbroadMapper.selectGoingAbroadList(goingAbroad);
	}
	
    /**
     * 新增出国情况登记
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 结果
     */
	@Override
	public int insertGoingAbroad(GoingAbroad goingAbroad) {
	return goingAbroadMapper.insertGoingAbroad(goingAbroad);
	}
	
	/**
     * 修改出国情况登记
     * 
     * @param goingAbroad 出国情况登记信息
     * @return 结果
     */
	@Override
	public int updateGoingAbroad(GoingAbroad goingAbroad)
	{
	    return goingAbroadMapper.updateGoingAbroad(goingAbroad);
	}

	/**
     * 删除出国情况登记对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoingAbroadByIds(String ids)
	{
		return goingAbroadMapper.deleteGoingAbroadByIds(Convert.toStrArray(ids));
	}
	
}
