package com.datcent.project.module.existingProperty.mapper;

import com.datcent.project.module.existingProperty.domain.ExistingProperty;
import java.util.List;	

/**
 * 现有财产状况 数据层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface ExistingPropertyMapper 
{
	/**
     * 查询现有财产状况信息
     * 
     * @param jtccqkId 现有财产状况ID
     * @return 现有财产状况信息
     */
	public ExistingProperty selectExistingPropertyById(String jtccqkId);
	
	/**
     * 查询现有财产状况列表
     * 
     * @param existingProperty 现有财产状况信息
     * @return 现有财产状况集合
     */
	public List<ExistingProperty> selectExistingPropertyList(ExistingProperty existingProperty);
	
	/**
     * 新增现有财产状况
     * 
     * @param existingProperty 现有财产状况信息
     * @return 结果
     */
	public int insertExistingProperty(ExistingProperty existingProperty);
	
	/**
     * 修改现有财产状况
     * 
     * @param existingProperty 现有财产状况信息
     * @return 结果
     */
	public int updateExistingProperty(ExistingProperty existingProperty);
	
	/**
     * 删除现有财产状况
     * 
     * @param jtccqkId 现有财产状况ID
     * @return 结果
     */
	public int deleteExistingPropertyById(String jtccqkId);
	
	/**
     * 批量删除现有财产状况
     * 
     * @param jtccqkIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteExistingPropertyByIds(String[] jtccqkIds);
	
}