package com.datcent.project.system.notice.mapper;

import com.datcent.project.system.notice.domain.Notice;

import java.util.List;

/**
 * 公告 数据层
 * 
 * @author datcent
 */
public interface NoticeMapper
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public Notice selectNoticeById(String noticeId);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<Notice> selectNoticeList(Notice notice);

    public List<Notice> selectNoticeListOrderByprocessStatus(Notice notice);
    
    /**
     * 查询公告列表首页展示
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<Notice> selectNoticeMain(Notice notice);

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(Notice notice);

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(Notice notice);

    /**
     * 批量删除公告
     * 
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);
    
    /**
     * 获取用户未查看的公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public List<Notice> selectNoReadNotice(Notice notice);

    /**
     * 获取图片
     * @return
     */
    public List<Notice> selectNoticePhoto();

    /**
     * 根据流程id 获取notice
     * @param pid
     * @return
     */
    public Notice selectNoticeByPid(String pid);
}