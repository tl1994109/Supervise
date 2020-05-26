package com.datcent.project.module.knowledgeClick.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.knowledgeClick.mapper.KnowledgeClickMapper;
import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import com.datcent.project.module.knowledgeClick.service.IKnowledgeClickService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 知识点击纪录 服务层实现
 * 
 * @author datcent
 * @date 2019-02-20
 */
@Service
public class KnowledgeClickServiceImpl implements IKnowledgeClickService 
{
	@Autowired
	private KnowledgeClickMapper knowledgeClickMapper;

	/**
     * 查询知识点击纪录信息
     * 
     * @param id 知识点击纪录ID
     * @return 知识点击纪录信息
     */
    @Override
	public KnowledgeClick selectKnowledgeClickById(String id)
	{
	    return knowledgeClickMapper.selectKnowledgeClickById(id);
	}
	
	/**
     * 查询知识点击纪录列表
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 知识点击纪录集合
     */
	@Override
	public List<KnowledgeClick> selectKnowledgeClickList(KnowledgeClick knowledgeClick)
	{
	    return knowledgeClickMapper.selectKnowledgeClickList(knowledgeClick);
	}
	
    /**
     * 新增知识点击纪录
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 结果
     */
	@Override
	public int insertKnowledgeClick(KnowledgeClick knowledgeClick)
	{
	    return knowledgeClickMapper.insertKnowledgeClick(knowledgeClick);
	}
	
	/**
     * 修改知识点击纪录
     * 
     * @param knowledgeClick 知识点击纪录信息
     * @return 结果
     */
	@Override
	public int updateKnowledgeClick(KnowledgeClick knowledgeClick)
	{
	    return knowledgeClickMapper.updateKnowledgeClick(knowledgeClick);
	}

	/**
     * 删除知识点击纪录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteKnowledgeClickByIds(String ids)
	{
		return knowledgeClickMapper.deleteKnowledgeClickByIds(Convert.toStrArray(ids));
	}

	@Override
	public Integer selectKnowledgeClickByKnowledgeId(String knowledgeId) {
		return knowledgeClickMapper.selectKnowledgeClickByKnowledgeId(knowledgeId);
	}
}
