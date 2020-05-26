package com.datcent.project.module.handlingSituation.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.handlingSituation.mapper.HandlingSituationMapper;
import com.datcent.project.module.handlingSituation.domain.HandlingSituation;
import com.datcent.project.module.handlingSituation.service.IHandlingSituationService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 党政纪处理及四拒情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class HandlingSituationServiceImpl implements IHandlingSituationService 
{
	@Autowired
	private HandlingSituationMapper handlingSituationMapper;

	/**
     * 查询党政纪处理及四拒情况信息
     * 
     * @param cljsjqkId 党政纪处理及四拒情况ID
     * @return 党政纪处理及四拒情况信息
     */
    @Override
	public HandlingSituation selectHandlingSituationById(String cljsjqkId)
	{
	    return handlingSituationMapper.selectHandlingSituationById(cljsjqkId);
	}
	
	/**
     * 查询党政纪处理及四拒情况列表
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 党政纪处理及四拒情况集合
     */
	@Override
	public List<HandlingSituation> selectHandlingSituationList(HandlingSituation handlingSituation)
	{
	    return handlingSituationMapper.selectHandlingSituationList(handlingSituation);
	}
	
    /**
     * 新增党政纪处理及四拒情况
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 结果
     */
	@Override
	public int insertHandlingSituation(HandlingSituation handlingSituation)
	{
			return handlingSituationMapper.insertHandlingSituation(handlingSituation);

	}
	
	/**
     * 修改党政纪处理及四拒情况
     * 
     * @param handlingSituation 党政纪处理及四拒情况信息
     * @return 结果
     */
	@Override
	public int updateHandlingSituation(HandlingSituation handlingSituation)
	{
	    return handlingSituationMapper.updateHandlingSituation(handlingSituation);
	}

	/**
     * 删除党政纪处理及四拒情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteHandlingSituationByIds(String ids)
	{
		return handlingSituationMapper.deleteHandlingSituationByIds(Convert.toStrArray(ids));
	}
	
}
