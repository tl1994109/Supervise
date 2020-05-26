package com.datcent.project.module.noticeClick.mapper;

import com.datcent.project.module.noticeClick.domain.NoticeClick;
import java.util.List;	

/**
 * 公告点击记录 数据层
 * 
 * @author datcent
 * @date 2018-11-05
 */
public interface NoticeClickMapper 
{
	/**
     * 查询公告点击记录信息
     * 
     * @param noticeClickrateId 公告点击记录ID
     * @return 公告点击记录信息
     */
	public NoticeClick selectNoticeClickById(Integer noticeClickrateId);
	
	/**
     * 查询公告点击记录列表
     * 
     * @param noticeClick 公告点击记录信息
     * @return 公告点击记录集合
     */
	public List<NoticeClick> selectNoticeClickList(NoticeClick noticeClick);
	
	/**
     * 查询公告点击记录数
     * 
     * @param noticeClick 公告点击记录信息
     * @return 公告点击记录集合
     */
	public int selectNoticeClickCount(NoticeClick noticeClick);
	
	/**
     * 新增公告点击记录
     * 
     * @param noticeClick 公告点击记录信息
     * @return 结果
     */
	public int insertNoticeClick(NoticeClick noticeClick);
	
	/**
     * 修改公告点击记录
     * 
     * @param noticeClick 公告点击记录信息
     * @return 结果
     */
	public int updateNoticeClick(NoticeClick noticeClick);
	
	/**
     * 删除公告点击记录
     * 
     * @param noticeClickrateId 公告点击记录ID
     * @return 结果
     */
	public int deleteNoticeClickById(Integer noticeClickrateId);
	
	/**
     * 批量删除公告点击记录
     * 
     * @param noticeClickrateIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteNoticeClickByIds(String[] noticeClickrateIds);
	
}