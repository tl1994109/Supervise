package com.datcent.project.module.accessory.service;

import com.datcent.project.module.accessory.domain.Accessory;

import java.text.ParseException;
import java.util.List;

/**
 * 任务附件 服务层
 * 
 * @author datcent
 * @date 2019-01-22
 */
public interface IAccessoryService 
{
	/**
     * 查询任务附件信息
     * 
     * @param accessoryId 任务附件ID
     * @return 任务附件信息
     */
	public Accessory selectAccessoryById(String accessoryId);
	
	/**
     * 查询任务附件列表
     * 
     * @param accessory 任务附件信息
     * @return 任务附件集合
     */
	public List<Accessory> selectAccessoryList(Accessory accessory);
	
	/**
     * 新增任务附件
     * 
     * @param accessory 任务附件信息
     * @return 结果
     */
	public int insertAccessory(Accessory accessory);
	
	/**
     * 修改任务附件
     * 
     * @param accessory 任务附件信息
     * @return 结果
     */
	public int updateAccessory(Accessory accessory);
		
	/**
     * 删除任务附件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAccessoryByIds(String ids,String taskId) throws ParseException;
	
}
