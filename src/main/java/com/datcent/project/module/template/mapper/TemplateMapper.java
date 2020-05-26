package com.datcent.project.module.template.mapper;

import com.datcent.project.module.template.domain.Template;
import java.util.List;	

/**
 * 模板管理 数据层
 * 
 * @author datcent
 * @date 2019-02-15
 */
public interface TemplateMapper 
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
	public int insertTemplate(Template template);
	
	/**
     * 修改模板管理
     * 
     * @param template 模板管理信息
     * @return 结果
     */
	public int updateTemplate(Template template);
	
	/**
     * 删除模板管理
     * 
     * @param templateId 模板管理ID
     * @return 结果
     */
	public int deleteTemplateById(String templateId);
	
	/**
     * 批量删除模板管理
     * 
     * @param templateIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteTemplateByIds(String[] templateIds);

	/**
	 * 查询所有二级
	 * @return
	 */
	public List<Template> selectSecondTree();
}