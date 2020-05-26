package com.datcent.project.system.notice.service;

import java.beans.Transient;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.domain.ProcessResume;
import com.datcent.project.module.process.service.IProcessMatterService;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.project.module.process.service.IProcessResumeService;
import com.datcent.project.module.process.service.IProcessService;
import com.datcent.project.system.notice.mapper.NoticeMapper;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.notice.service.INoticeService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公告 服务层实现
 * 
 * @author datcent
 * @date 2018-06-25
 */
@Service
public class NoticeServiceImpl implements INoticeService
{
    @Autowired
    private NoticeMapper noticeMapper;
    
    @Autowired
    private IProcessService processService;
    
    @Autowired
    private IProcessNodeDetailService processNodeDetailService;
    
    @Autowired
    private IProcessResumeService processResumeService;
    
    @Autowired
    private IProcessMatterService processMatterService;

    @Autowired
	private IUserService userService;

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public Notice selectNoticeById(String noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
    	notice.setProcessStatus("0");
    	notice.setDeleteFlag("0");
        return noticeMapper.selectNoticeList(notice);
    }
    
    /**
     * 查询公告列表首页展示
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<Notice> selectNoticeMain(Notice notice)
    {
        return noticeMapper.selectNoticeMain(notice);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice, MultipartFile file,MultipartFile file_pdf,String profile) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
		if(file!=null && "0".equals(notice.getFillWay())){
			if(!file.isEmpty()){
				String fileName = file.getOriginalFilename();
				String prefix = fileName.substring(fileName.lastIndexOf("."));
				String uploadName = FileUploadUtils.upload(profile,file,prefix);
				notice.setNoticePhoto(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
			}
		}
		if(file_pdf!=null && "1".equals(notice.getFillWay())){
			if(!file_pdf.isEmpty()){
				String fileName = file_pdf.getOriginalFilename();
				String prefix = fileName.substring(fileName.lastIndexOf("."));
				String uploadName = FileUploadUtils.upload(profile,file_pdf,prefix);
				notice.setNoticePhoto(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
			}
		}
		List<UserRole> roleList = userService.selectUserRoleByUserId(ShiroUtils.getUserId().toString());
		String isok = "false";
		for (UserRole role:roleList) {
			if(ActivitiDefinftion.ROLE_KEY.equals(role.getRoleKey())|| ActivitiDefinftion.ROLE_ADMIN_KEY.equals(role.getRoleKey())){
				isok = "true";
				break;
			}
		}
		if("true".equals(isok)){
			notice.setProcessStatus("0");
		}
		notice.setCreateBy(ShiroUtils.getUserId().toString());
        notice.setNoticeId(StringUtils.getUUID());
        notice.setCreateTime(new Date());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice,MultipartFile file,MultipartFile file_pdf,String profile) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
		if(file!=null && "0".equals(notice.getFillWay())){
			if(!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String prefix = fileName.substring(fileName.lastIndexOf("."));
				String uploadName = FileUploadUtils.upload(profile, file, prefix);
				notice.setNoticePhoto(ActivitiDefinftion.VIRTUAL_PATH + uploadName);
			}
		}
		if(file_pdf!=null && "1".equals(notice.getFillWay())){
			if(!file_pdf.isEmpty()){
				String fileName = file_pdf.getOriginalFilename();
				String prefix = fileName.substring(fileName.lastIndexOf("."));
				String uploadName = FileUploadUtils.upload(profile,file_pdf,prefix);
				notice.setNoticePhoto(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
			}
		}
		Notice lastNotice = noticeMapper.selectNoticeById(notice.getNoticeId());
		if("1".equals(lastNotice.getFillWay()) && "0".equals(notice.getFillWay()) && file == null){
			notice.setNoticePhoto("");
		}
        notice.setUpdateBy(ShiroUtils.getLoginName());
        notice.setUpdateTime(new Date());
        return noticeMapper.updateNotice(notice);
    }
    
    @Transient
    public int saveOrAuditing(Notice notice){
    	int row=0;
    	ProcessNodeDetail node=null;
    	ProcessMatter matter = processMatterService.selectProcessMatterById(notice.getNoticeId());
    	node= processService.getLocalNodeInfo(matter.getProcessId(),matter.getNodeId());
    	if(notice.getProcessStatus().equals("0")){
    		//通过
    		if(node.getNodeType().equals("task capsule")){
    			notice.setProcessStatus("1");
    			matter.setStatus("0");
    			matter.setAllowBy(node.getAllowBy());
    		}else{
    			matter.setStatus("1");
    		}
    		if(notice.getNoticeId()!=null && notice.getNoticeId()!=""){
    			row=noticeMapper.updateNotice(notice);
    		}else{
    			Notice noEditNotice = new Notice();
    			noEditNotice.setNoticeId(notice.getNoticeId());
    			noEditNotice.setProcessStatus(notice.getProcessStatus());
    			row=noticeMapper.updateNotice(noEditNotice);
    		}
    		row+=saveProcessResume(matter,"通过");
        	matter.setMatterName(node.getNodeName());
        	matter.setNodeId(node.getNodeId());
        	matter.setLaunchTime(new Date());
        	row+=processMatterService.updateProcessMatter(matter);
    	}else if(notice.getProcessStatus().equals("1")){
    		//不通过
    		ProcessNodeDetail detail = processService.getPrevNodeInfo(matter.getProcessId(), matter.getNodeId());
    		if(detail.getNodeType().equals("task capsule")){
    			notice.setProcessStatus("1");
    			matter.setStatus("0");
    			matter.setAllowBy(detail.getAllowBy());
    		}else{
    			matter.setStatus("1");
    			notice.setProcessStatus("2");
    		}
    		if(notice.getNoticeId()!=null && notice.getNoticeId()!=""){
	    		row=noticeMapper.updateNotice(notice);
    		}else{
    			Notice noEditNotice = new Notice();
    			noEditNotice.setNoticeId(notice.getNoticeId());
    			row=noticeMapper.updateNotice(noEditNotice);
    		}
    		row+=saveProcessResume(matter,"不通过");
        	matter.setMatterName(detail.getNodeName());
        	matter.setNodeId(detail.getNodeId());
        	matter.setLaunchTime(new Date());
        	row+=processMatterService.updateProcessMatter(matter);
    	}else{
    		//结束流程
    		if(notice.getNoticeId()!=null && notice.getNoticeId()!=""){
	    		notice.setProcessStatus("0");
	    		row=noticeMapper.updateNotice(notice);
    		}else{
    			Notice noEditNotice = new Notice();
    			noEditNotice.setNoticeId(notice.getNoticeId());
    			noEditNotice.setProcessStatus("0");
    			row=noticeMapper.updateNotice(noEditNotice);
    		}
    		row+=saveProcessResume(matter,"结束");
        	ProcessNodeDetail processNodeDetail=new ProcessNodeDetail();
        	processNodeDetail.setProcessId(matter.getProcessId());
        	processNodeDetail.setNodeType("end round");
        	List<ProcessNodeDetail> processNodeDetailList = processNodeDetailService.selectProcessNodeDetailList(processNodeDetail);
        	matter.setNodeId(processNodeDetailList.get(0).getNodeId());
        	matter.setMatterName(processNodeDetailList.get(0).getNodeName());
        	matter.setStatus("1");
        	matter.setLaunchTime(new Date());
        	row+=processMatterService.updateProcessMatter(matter);
    	}
    	return row;
    }
    
    public int saveProcessResume(ProcessMatter matter,String context){
    	ProcessResume processResume=new ProcessResume();
		processResume.setId(StringUtils.getUUID());
		processResume.setProcessId(matter.getProcessId());
		processResume.setNodeId(matter.getNodeId());
		processResume.setNodeName(matter.getMatterName());
		processResume.setMatterId(matter.getMatterId());
		processResume.setOperationContent(context);
		processResume.setOperationBy(ShiroUtils.getUserId().toString());
		processResume.setOperationTime(new Date());
		return processResumeService.insertProcessResume(processResume);
    }

    /**
     * 删除公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids)
    {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }

	@Override
	public List<Notice> selectNoReadNotice(Notice notice) {
		return noticeMapper.selectNoReadNotice(notice);
	}

	@Override
	public List<Notice> selectNoticePhoto() {
		return noticeMapper.selectNoticePhoto();
	}

	@Override
	public List<Notice> selectNoticeListOrderByprocessStatus(Notice notice) {
		return noticeMapper.selectNoticeListOrderByprocessStatus(notice);
	}

	@Override
	public Notice selectNoticeByPid(String pid) {
		return noticeMapper.selectNoticeByPid(pid);
	}
}
