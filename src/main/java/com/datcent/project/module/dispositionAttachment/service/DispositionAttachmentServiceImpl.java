package com.datcent.project.module.dispositionAttachment.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.dispositionAttachment.mapper.DispositionAttachmentMapper;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 工作记录附件 服务层实现
 * 
 * @author datcent
 * @date 2019-01-25
 */
@Service
public class DispositionAttachmentServiceImpl implements IDispositionAttachmentService 
{
	@Autowired
	private DispositionAttachmentMapper dispositionAttachmentMapper;


	/**
     * 查询工作记录附件列表
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 工作记录附件集合
     */
	@Override
	public List<DispositionAttachment> selectDispositionAttachmentList(DispositionAttachment dispositionAttachment)
	{
	    return dispositionAttachmentMapper.selectDispositionAttachmentList(dispositionAttachment);
	}

	/**
	 * 查询有附件URL的集合
	 *
	 * @param dispositionAttachment 工作记录附件信息
	 * @return 工作记录附件集合
	 */
	@Override
	public List<DispositionAttachment> selectFile(DispositionAttachment dispositionAttachment)
	{
		return dispositionAttachmentMapper.selectFile(dispositionAttachment);
	}
	
    /**
     * 新增工作记录附件
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 结果
     */
	@Override
	public int insertDispositionAttachment(DispositionAttachment dispositionAttachment)
	{
	    return dispositionAttachmentMapper.insertDispositionAttachment(dispositionAttachment);
	}
	
	/**
     * 修改工作记录附件
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 结果
     */
	@Override
	public int updateDispositionAttachment(DispositionAttachment dispositionAttachment)
	{
	    return dispositionAttachmentMapper.updateDispositionAttachment(dispositionAttachment);
	}

	/**
	 * 查询问题处置单信息
	 *
	 * @param attachmentId
	 * @return 问题处置单信息
	 */
	public DispositionAttachment selectDispositionAttachmentById(String attachmentId){

		return dispositionAttachmentMapper.selectDispositionAttachmentById(attachmentId);
	};


}
