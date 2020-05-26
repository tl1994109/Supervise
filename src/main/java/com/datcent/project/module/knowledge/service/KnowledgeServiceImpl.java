package com.datcent.project.module.knowledge.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import com.datcent.project.module.knowledgeClick.mapper.KnowledgeClickMapper;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.datcent.project.module.knowledge.mapper.KnowledgeMapper;
import com.datcent.project.module.knowledge.domain.Knowledge;
import com.datcent.project.module.knowledge.service.IKnowledgeService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 知识管理 服务层实现
 * 
 * @author datcent
 * @date 2019-02-13
 */
@Service
public class KnowledgeServiceImpl implements IKnowledgeService 
{
	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private KnowledgeClickMapper knowledgeClickMapper;

	@Value("${datcent.profile}")
	private String profile;

	/**
     * 查询知识管理信息
     * 
     * @param knowledgeId 知识管理ID
     * @return 知识管理信息
     */
    @Override
	public Knowledge selectKnowledgeById(String knowledgeId)
	{
	    return knowledgeMapper.selectKnowledgeById(knowledgeId);
	}
	
	/**
     * 查询知识管理列表
     * 
     * @param knowledge 知识管理信息
     * @return 知识管理集合
     */
	@Override
	public List<Knowledge> selectKnowledgeList(Knowledge knowledge)
	{
	    return knowledgeMapper.selectKnowledgeList(knowledge);
	}
	
    /**
     * 新增知识管理
     * 
     * @param knowledge 知识管理信息
     * @return 结果
     */
	@Override
	public int insertKnowledge(Knowledge knowledge, MultipartFile file_pdf)
	{
		if("1".equals(knowledge.getFillWay())){
			if(file_pdf!=null){
				if(!file_pdf.isEmpty()){
					String fileName = file_pdf.getOriginalFilename();
					String prefix = fileName.substring(fileName.lastIndexOf("."));
					String uploadName = null;
					try {
						uploadName = FileUploadUtils.upload(profile,file_pdf,prefix);
					} catch (FileUploadBase.FileSizeLimitExceededException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (FileNameLengthLimitExceededException e) {
						e.printStackTrace();
					}
					knowledge.setKnowledgeContent(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
				}
			}
		}
		knowledge.setKnowledgeId(StringUtils.getUUID());
		knowledge.setKnowledgeCreateby(ShiroUtils.getUserId().toString());
		knowledge.setKnowledgeCreatetime(new Date());
	    return knowledgeMapper.insertKnowledge(knowledge);
	}
	
	/**
     * 修改知识管理
     * 
     * @param knowledge 知识管理信息
     * @return 结果
     */
	@Override
	public int updateKnowledge(Knowledge knowledge,MultipartFile file_pdf)
	{
		if("1".equals(knowledge.getFillWay())){
			if(file_pdf!=null){
				if(!file_pdf.isEmpty()){
					String fileName = file_pdf.getOriginalFilename();
					String prefix = fileName.substring(fileName.lastIndexOf("."));
					String uploadName = null;
					try {
						uploadName = FileUploadUtils.upload(profile,file_pdf,prefix);
					} catch (FileUploadBase.FileSizeLimitExceededException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (FileNameLengthLimitExceededException e) {
						e.printStackTrace();
					}
					knowledge.setKnowledgeContent(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
				}
			}
		}
	    return knowledgeMapper.updateKnowledge(knowledge);
	}

	/**
     * 删除知识管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteKnowledgeByIds(String ids)
	{
		return knowledgeMapper.deleteKnowledgeByIds(Convert.toStrArray(ids));
	}

}
