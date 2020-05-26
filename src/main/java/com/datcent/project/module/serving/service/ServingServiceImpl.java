package com.datcent.project.module.serving.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.serving.mapper.ServingMapper;
import com.datcent.project.module.serving.domain.Serving;
import com.datcent.project.module.serving.service.IServingService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 主要任职情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class ServingServiceImpl implements IServingService 
{
	@Autowired
	private ServingMapper servingMapper;

	/**
     * 查询主要任职情况信息
     * 
     * @param id 主要任职情况ID
     * @return 主要任职情况信息
     */
    @Override
	public Serving selectServingById(String id)
	{
	    return servingMapper.selectServingById(id);
	}
	
	/**
     * 查询主要任职情况列表
     * 
     * @param serving 主要任职情况信息
     * @return 主要任职情况集合
     */
	@Override
	public List<Serving> selectServingList(Serving serving)
	{
	    return servingMapper.selectServingList(serving);
	}
	
    /**
     * 新增主要任职情况
     * 
     * @param serving 主要任职情况信息
     * @return 结果
     */
	@Override
	public int insertServing(Serving serving)
	{
	    return servingMapper.insertServing(serving);
	}
	
	/**
     * 修改主要任职情况
     * 
     * @param serving 主要任职情况信息
     * @return 结果
     */
	@Override
	public int updateServing(Serving serving)
	{
	    return servingMapper.updateServing(serving);
	}

	/**
     * 删除主要任职情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteServingByIds(String ids)
	{
		return servingMapper.deleteServingByIds(Convert.toStrArray(ids));
	}
	
}
