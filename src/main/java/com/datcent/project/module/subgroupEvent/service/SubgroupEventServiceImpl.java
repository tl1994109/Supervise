package com.datcent.project.module.subgroupEvent.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.subgroupEvent.mapper.SubgroupEventMapper;
import com.datcent.project.module.subgroupEvent.domain.SubgroupEvent;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 添加事件 服务层实现
 * 
 * @author datcent
 * @date 2018-11-30
 */
@Service
public class SubgroupEventServiceImpl implements ISubgroupEventService 
{
	@Autowired
	private SubgroupEventMapper subgroupEventMapper;

	/**
     * 查询添加事件信息
     * 
     * @param eventId 添加事件ID
     * @return 添加事件信息
     */
    @Override
	public SubgroupEvent selectSubgroupEventById(String eventId)
	{
	    return subgroupEventMapper.selectSubgroupEventById(eventId);
	}
	
	/**
     * 查询添加事件列表
     * 
     * @param subgroupEvent 添加事件信息
     * @return 添加事件集合
     */
	@Override
	public List<SubgroupEvent> selectSubgroupEventList(SubgroupEvent subgroupEvent)
	{
	    return subgroupEventMapper.selectSubgroupEventList(subgroupEvent);
	}
	
    /**
     * 新增添加事件
     * 
     * @param subgroupEvent 添加事件信息
     * @return 结果
     */
	@Override
	public int insertSubgroupEvent(SubgroupEvent subgroupEvent)
	{
		subgroupEvent.setEventId(StringUtils.getUUID());
		subgroupEvent.setCreateTime(new Date());
	    return subgroupEventMapper.insertSubgroupEvent(subgroupEvent);
	}
	
	/**
     * 修改添加事件
     * 
     * @param subgroupEvent 添加事件信息
     * @return 结果
     */
	@Override
	public int updateSubgroupEvent(SubgroupEvent subgroupEvent)
	{
	    return subgroupEventMapper.updateSubgroupEvent(subgroupEvent);
	}

	/**
     * 删除添加事件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSubgroupEventByIds(String ids)
	{
		return subgroupEventMapper.deleteSubgroupEventByIds(Convert.toStrArray(ids));
	}
	
}
