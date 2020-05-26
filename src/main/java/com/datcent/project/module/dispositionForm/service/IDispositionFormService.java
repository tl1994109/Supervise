package com.datcent.project.module.dispositionForm.service;

import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import java.util.List;

/**
 * 问题处置单 服务层
 * 
 * @author datcent
 * @date 2019-01-18
 */
public interface IDispositionFormService 
{
	/**
     * 查询问题处置单信息
     * 
     * @param formId 问题处置单ID
     * @return 问题处置单信息
     */
	public DispositionForm selectDispositionFormById(String formId);
	
	/**
     * 查询问题处置单列表
     * 
     * @param dispositionForm 问题处置单信息
     * @return 问题处置单集合
     */
	public List<DispositionForm> selectDispositionFormList(DispositionForm dispositionForm);
	
	/**
     * 新增问题处置单
     * 
     * @param dispositionForm 问题处置单信息
     * @return 结果
     */
	public int insertDispositionForm(DispositionForm dispositionForm);
	
	/**
     * 修改问题处置单
     * 
     * @param dispositionForm 问题处置单信息
     * @return 结果
     */
	public int updateDispositionForm(DispositionForm dispositionForm);
		
	/**
     * 删除问题处置单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDispositionFormByIds(String ids);
	
}
