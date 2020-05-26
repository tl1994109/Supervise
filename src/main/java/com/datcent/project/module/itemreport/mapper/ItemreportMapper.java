package com.datcent.project.module.itemreport.mapper;

import com.datcent.project.module.itemreport.domain.Itemreport;
import java.util.List;	

/**
 * 有关事件报告 数据层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface ItemreportMapper 
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
     * 删除有关事件报告
     * 
     * @param sxbgId 有关事件报告ID
     * @return 结果
     */
	public int deleteItemreportById(String sxbgId);
	
	/**
     * 批量删除有关事件报告
     * 
     * @param sxbgIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteItemreportByIds(String[] sxbgIds);
	
}