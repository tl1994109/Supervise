package com.datcent.project.module.colleinterface.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.colleinterface.mapper.ColleinterfaceMapper;
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.common.support.Convert;

/**
 * 采集接口 服务层实现
 * 
 * @author datcent
 * @date 2019-01-10
 */
@Service
public class ColleinterfaceServiceImpl implements IColleinterfaceService 
{
	@Autowired
	private ColleinterfaceMapper colleinterfaceMapper;

	/**
     * 查询采集接口信息
     * 
     * @param colleinterfaceId 采集接口ID
     * @return 采集接口信息
     */
    @Override
	public Colleinterface selectColleinterfaceById(String colleinterfaceId)
	{
	    return colleinterfaceMapper.selectColleinterfaceById(colleinterfaceId);
	}
	
	/**
     * 查询采集接口列表
     * 
     * @param colleinterface 采集接口信息
     * @return 采集接口集合
     */
	@Override
	public List<Colleinterface> selectColleinterfaceList(Colleinterface colleinterface)
	{
	    return colleinterfaceMapper.selectColleinterfaceList(colleinterface);
	}
	
    /**
     * 新增采集接口
     * 
     * @param colleinterface 采集接口信息
     * @return 结果
     */
	@Override
	public int insertColleinterface(Colleinterface colleinterface)
	{
	    return colleinterfaceMapper.insertColleinterface(colleinterface);
	}
	
	/**
     * 修改采集接口
     * 
     * @param colleinterface 采集接口信息
     * @return 结果
     */
	@Override
	public int updateColleinterface(Colleinterface colleinterface)
	{
	    return colleinterfaceMapper.updateColleinterface(colleinterface);
	}

	/**
     * 删除采集接口对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteColleinterfaceByIds(String ids)
	{
		return colleinterfaceMapper.deleteColleinterfaceByIds(Convert.toStrArray(ids));
	}
	
}
