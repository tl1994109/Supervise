package com.datcent.project.tool.diagramSubgroup.service;

import com.datcent.project.tool.diagramSubgroup.domain.DiagramSubgroup;
import java.util.List;

/**
 * 组图组件关系 服务层
 * 
 * @author datcent
 * @date 2018-11-30
 */
public interface IDiagramSubgroupService 
{
	/**
     * 查询组图组件关系信息
     * 
     * @param id 组图组件关系ID
     * @return 组图组件关系信息
     */
	public DiagramSubgroup selectDiagramSubgroupById(String id);
	
	/**
     * 查询组图组件关系列表
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 组图组件关系集合
     */
	public List<DiagramSubgroup> selectDiagramSubgroupList(DiagramSubgroup diagramSubgroup);
	
	/**
     * 新增组图组件关系
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 结果
     */
	public int insertDiagramSubgroup(DiagramSubgroup diagramSubgroup);
	
	/**
     * 修改组图组件关系
     * 
     * @param diagramSubgroup 组图组件关系信息
     * @return 结果
     */
	public int updateDiagramSubgroup(DiagramSubgroup diagramSubgroup);
		
	/**
     * 删除组图组件关系信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDiagramSubgroupByIds(String ids);
	
}
