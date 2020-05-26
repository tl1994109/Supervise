package com.datcent.project.module.knowledge.mapper;

import com.datcent.project.module.knowledge.domain.Knowledge;
import java.util.List;	

/**
 * 知识管理 数据层
 * 
 * @author datcent
 * @date 2019-02-13
 */
public interface KnowledgeMapper 
{
	/**
     * 查询知识管理信息
     * 
     * @param knowledgeId 知识管理ID
     * @return 知识管理信息
     */
	public Knowledge selectKnowledgeById(String knowledgeId);
	
	/**
     * 查询知识管理列表
     * 
     * @param knowledge 知识管理信息
     * @return 知识管理集合
     */
	public List<Knowledge> selectKnowledgeList(Knowledge knowledge);
	
	/**
     * 新增知识管理
     * 
     * @param knowledge 知识管理信息
     * @return 结果
     */
	public int insertKnowledge(Knowledge knowledge);
	
	/**
     * 修改知识管理
     * 
     * @param knowledge 知识管理信息
     * @return 结果
     */
	public int updateKnowledge(Knowledge knowledge);
	
	/**
     * 删除知识管理
     * 
     * @param knowledgeId 知识管理ID
     * @return 结果
     */
	public int deleteKnowledgeById(String knowledgeId);
	
	/**
     * 批量删除知识管理
     * 
     * @param knowledgeIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteKnowledgeByIds(String[] knowledgeIds);
	
}