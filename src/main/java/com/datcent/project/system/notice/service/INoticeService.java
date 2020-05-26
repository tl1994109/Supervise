package com.datcent.project.system.notice.service;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.project.system.notice.domain.Notice;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 公告 服务层
 * 
 * @author datcent
 */
public interface INoticeService
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public Notice selectNoticeById(String noticeId);

    public List<Notice> selectNoticeListOrderByprocessStatus(Notice notice);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<Notice> selectNoticeList(Notice notice);
    
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
    public int insertNotice(Notice notice, MultipartFile file,MultipartFile file_pdf,String profile) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException;

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(Notice notice,MultipartFile multipartFile,MultipartFile file_pdf,String profile) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException;
    
    /**
     * 获取用户未查看的公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public List<Notice> selectNoReadNotice(Notice notice);
    
    /**
     * 
     * 描述：公告审核并修改保存
     * 时间：2018年11月13日 下午1:57:40
     * 作者：zhou_shiji
     * @param notice
     * @return
     */
    public int saveOrAuditing(Notice notice);

    /**
     * 删除公告信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String ids);

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
