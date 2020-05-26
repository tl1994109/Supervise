package com.datcent.project.module.lists.mapper;

import com.datcent.project.module.lists.domain.Lists;
import java.util.List;	

/**
 * 案件列 数据层
 * 
 * @author datcent
 * @date 2018-12-26
 */
public interface ListsMapper 
{
	/**
     * 查询案件列信息
     * 
     * @param zhcxlistStringajbh 案件列ID
     * @return 案件列信息
     */
	public Lists selectListsById(String zhcxlistStringajbh);
	
	/**
     * 查询案件列列表
     * 
     * @param lists 案件列信息
     * @return 案件列集合
     */
	public List<Lists> selectListsList(Lists lists);
	
	/**
     * 新增案件列
     * 
     * @param lists 案件列信息
     * @return 结果
     */
	public int insertLists(Lists lists);
	
	/**
     * 修改案件列
     * 
     * @param lists 案件列信息
     * @return 结果
     */
	public int updateLists(Lists lists);
	
	/**
     * 删除案件列
     * 
     * @param zhcxlistStringajbh 案件列ID
     * @return 结果
     */
	public int deleteListsById(String zhcxlistStringajbh);
	
	/**
     * 批量删除案件列
     * 
     * @param zhcxlistStringajbhs 需要删除的数据ID
     * @return 结果
     */
	public int deleteListsByIds(String[] zhcxlistStringajbhs);
	
}