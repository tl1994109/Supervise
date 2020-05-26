package com.datcent.project.module.knowledgeClick.mapper;

import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import java.util.List;	

/**
 * 知识点击纪录 数据层
 * 
 * @author datcent
 * @date 2019-02-20
 */
public interface KnowledgeClickMapper 
{
	/**
     * 查询知识点击纪录信息
     * 
     * @param id 知识点击纪录ID
     * @return 知识点击纪录信息
     */
	public KnowledgeClick selectKnowledgeClickById(String id);
	
	/**
     * 查询知识点击纪录列表
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 知识点击纪录集合
     */
	public List<KnowledgeClick> selectKnowledgeClickList(KnowledgeClick knowledgeClick);
	
	/**
     * 新增知识点击纪录
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 结果
     */
	public int insertKnowledgeClick(KnowledgeClick knowledgeClick);
	
	/**
     * 修改知识点击纪录
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 结果
     */
	public int updateKnowledgeClick(KnowledgeClick knowledgeClick);
	
	/**
     * 删除知识点击纪录
     * 
     * @param id 知识点击纪录ID
     * @return 结果
     */
	public int deleteKnowledgeClickById(String id);
	
	/**
     * 批量删除知识点击纪录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteKnowledgeClickByIds(String[] ids);

	/**
	 * 知识编号查询点击次数
	 * @param knowledgeId 需要查询指示编号
	 * @return 点击数
	 */
	public Integer selectKnowledgeClickByKnowledgeId(String knowledgeId);
}