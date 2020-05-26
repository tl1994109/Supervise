package com.datcent.project.module.itemreport.service;

import com.datcent.project.module.itemreport.domain.Itemreport;
import java.util.List;

/**
 * 有关事件报告 服务层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface IItemreportService 
{
	/**
     * 查询有关事件报告信息
     * 
     * @param sxbgId 有关事件报告ID
     * @return 有关事件报告信息
     */
	public Itemreport selectItemreportById(String sxbgId);
	
	/**
     * 查询有关事件报告列表
     * 
     * @param itemreport 有关事件报告信息
     * @return 有关事件报告集合
     */
	public List<Itemreport> selectItemreportList(Itemreport itemreport);
	
	/**
     * 新增有关事件报告
     * 
     * @param itemreport 有关事件报告信息
     * @return 结果
     */
	public int insertItemreport(Itemreport itemreport);
	
	/**
     * 修改有关事件报告
     * 
     * @param itemreport 有关事件报告信息
     * @return 结果
     */
	public int updateItemreport(Itemreport itemreport);
		
	/**
     * 删除有关事件报告信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteItemreportByIds(String ids);
	
}
