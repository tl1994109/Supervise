package com.datcent.project.tool.layout.service;

import com.datcent.project.tool.layout.domain.Layout;
import java.util.List;

/**
 * 布局管理 服务层
 * 
 * @author datcent
 * @date 2018-11-22
 */
public interface ILayoutService 
{
	/**
     * 查询布局管理信息
     * 
     * @param layoutId 布局管理ID
     * @return 布局管理信息
     */
	public Layout selectLayoutById(String layoutId);
	
	/**
     * 查询布局管理列表
     * 
     * @param layout 布局管理信息
     * @return 布局管理集合
     */
	public List<Layout> selectLayoutList(Layout layout);
	
	/**
     * 新增布局管理
     * 
     * @param layout 布局管理信息
     * @return 结果
     */
	public int insertLayout(Layout layout);
	
	/**
     * 修改布局管理
     * 
     * @param layout 布局管理信息
     * @return 结果
     */
	public int updateLayout(Layout layout);
		
	/**
     * 删除布局管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteLayoutByIds(String ids);
	
}
