package com.datcent.project.module.noticeClick.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.noticeClick.mapper.NoticeClickMapper;
import com.datcent.project.module.noticeClick.domain.NoticeClick;
import com.datcent.project.module.noticeClick.service.INoticeClickService;
import com.datcent.common.support.Convert;

/**
 * 公告点击记录 服务层实现
 * 
 * @author datcent
 * @date 2018-11-05
 */
@Service
public class NoticeClickServiceImpl implements INoticeClickService 
{
	@Autowired
	private NoticeClickMapper noticeClickMapper;

	/**
     * 查询公告点击记录信息
     * 
     * @param noticeClickrateId 公告点击记录ID
     * @return 公告点击记录信息
     */
    @Override
	public NoticeClick selectNoticeClickById(Integer noticeClickrateId)
	{
	    return noticeClickMapper.selectNoticeClickById(noticeClickrateId);
	}
	
	/**
     * 查询公告点击记录列表
     * 
     * @param noticeClick 公告点击记录信息
     * @return 公告点击记录集合
     */
	@Override
	public List<NoticeClick> selectNoticeClickList(NoticeClick noticeClick)
	{
	    return noticeClickMapper.selectNoticeClickList(noticeClick);
	}
	
    /**
     * 新增公告点击记录
     * 
     * @param noticeClick 公告点击记录信息
     * @return 结果
     */
	@Override
	public int insertNoticeClick(NoticeClick noticeClick)
	{
		noticeClick.setUserId(Integer.parseInt(ShiroUtils.getUserId().toString()));
		noticeClick.setClickTime(new Date());
		noticeClick.setUserName(ShiroUtils.getUser().getUserName());
	    return noticeClickMapper.insertNoticeClick(noticeClick);
	}
	
	/**
     * 修改公告点击记录
     * 
     * @param noticeClick 公告点击记录信息
     * @return 结果
     */
	@Override
	public int updateNoticeClick(NoticeClick noticeClick)
	{
	    return noticeClickMapper.updateNoticeClick(noticeClick);
	}

	/**
     * 删除公告点击记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteNoticeClickByIds(String ids)
	{
		return noticeClickMapper.deleteNoticeClickByIds(Convert.toStrArray(ids));
	}
	
}
