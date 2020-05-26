package com.datcent.project.module.template.service;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.project.module.template.domain.Template;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板管理 服务层
 * 
 * @author datcent
 * @date 2019-02-15
 */
public interface ITemplateService 
{
	/**
     * 查询模板管理信息
     * 
     * @param templateId 模板管理ID
     * @return 模板管理信息
     */
	public Template selectTemplateById(String templateId);
	
	/**
     * 查询模板管理列表
     * 
     * @param template 模板管理信息
     * @return 模板管理集合
     */
	public List<Template> selectTemplateList(Template template);
	
	/**
     * 新增模板管理
     * 
     * @param template 模板管理信息
     * @return 结果
     */
	public int insertTemplate(Template template, MultipartFile file, String profile) throws Exception;
	
	/**
     * 修改模板管理
     * 
     * @param template 模板管理信息
     * @return 结果
     */
	public int updateTemplate(Template template,MultipartFile file,String profile) throws Exception;
		
	/**
     * 删除模板管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTemplateByIds(String ids);

	/*
	* 查询模板管理树
	*
	* */
	public List<Map<String, Object>> selectTempTree();

	public List<Map<String, Object>> getTrees(List<Template> templatesList);

	/**
	 * 查询所有二级
	 * @return
	 */
	public List<Template> selectSecondTree();
}


