package com.datcent.project.tool.diagramSubgroup.service;

import java.util.List;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.tool.diagramSubgroup.mapper.DiagramSubgroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.tool.diagramSubgroup.domain.DiagramSubgroup;
import com.datcent.common.support.Convert;

/**
 * 组图组件关系 服务层实现
 * 
 * @author datcent
 * @date 2018-11-30
 */
@Service
public class DiagramSubgroupServiceImpl implements IDiagramSubgroupService 
{
	@Autowired
	private DiagramSubgroupMapper diagramSubgroupMapper;

	/**
     * 查询组图组件关系信息
     * 
     * @param id 组图组件关系ID
     * @return 组图组件关系信息
     */
    @Override
	public DiagramSubgroup selectDiagramSubgroupById(String id)
	{
	    return diagramSubgroupMapper.selectDiagramSubgroupById(id);
	}
	
	/**
     * 查询组图组件关系列表
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 组图组件关系集合
     */
	@Override
	public List<DiagramSubgroup> selectDiagramSubgroupList(DiagramSubgroup diagramSubgroup)
	{
	    return diagramSubgroupMapper.selectDiagramSubgroupList(diagramSubgroup);
	}
	
    /**
     * 新增组图组件关系
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 结果
     */
	@Override
	public int insertDiagramSubgroup(DiagramSubgroup diagramSubgroup)
	{
		diagramSubgroup.setId(StringUtils.getUUID());
		diagramSubgroup.setCreateTime(DateUtils.getNowDate());
	    return diagramSubgroupMapper.insertDiagramSubgroup(diagramSubgroup);
	}
	
	/**
     * 修改组图组件关系
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 结果
     */
	@Override
	public int updateDiagramSubgroup(DiagramSubgroup diagramSubgroup)
	{
	    return diagramSubgroupMapper.updateDiagramSubgroup(diagramSubgroup);
	}

	/**
     * 删除组图组件关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDiagramSubgroupByIds(String ids)
	{
		return diagramSubgroupMapper.deleteDiagramSubgroupByIds(Convert.toStrArray(ids));
	}
	
}
