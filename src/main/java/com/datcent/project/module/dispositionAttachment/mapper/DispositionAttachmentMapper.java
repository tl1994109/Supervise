package com.datcent.project.module.dispositionAttachment.mapper;

import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import java.util.List;	

/**
 * 工作记录附件 数据层
 * 
 * @author datcent
 * @date 2019-01-25
 */
public interface DispositionAttachmentMapper 
{
	/**
     * 查询工作记录附件列表
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 工作记录附件集合
     */
	public List<DispositionAttachment> selectDispositionAttachmentList(DispositionAttachment dispositionAttachment);


	/**
	 * 查询有附件URL的集合
	 * @param dispositionAttachment
	 * @return
	 */
	public List<DispositionAttachment> selectFile(DispositionAttachment dispositionAttachment);

	
	/**
     * 新增工作记录附件
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 结果
     */
	public int insertDispositionAttachment(DispositionAttachment dispositionAttachment);
	
	/**
     * 修改工作记录附件
     * 
     * @param dispositionAttachment 工作记录附件信息
     * @return 结果
     */
	public int updateDispositionAttachment(DispositionAttachment dispositionAttachment);



	/**
	 * 查询问题处置单信息
	 *
	 * @param attachmentId
	 * @return 问题处置单信息
	 */
	public DispositionAttachment selectDispositionAttachmentById(String attachmentId);


}