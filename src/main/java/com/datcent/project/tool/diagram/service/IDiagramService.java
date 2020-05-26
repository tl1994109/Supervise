package com.datcent.project.tool.diagram.service;

import com.datcent.project.tool.diagram.domain.Diagram;

import java.util.List;

/**
 * 组图管理 服务层
 * 
 * @author datcent
 * @date 2018-11-29
 */
public interface IDiagramService 
{
	/**
     * 查询组图管理信息
     * 
     * @param diagramId 组图管理ID
     * @return 组图管理信息
     */
	public Diagram selectDiagramById(String diagramId);
	
	/**
     * 查询组图管理列表
     * 
     * @param diagram 组图管理信息
     * @return 组图管理集合
     */
	public List<Diagram> selectDiagramList(Diagram diagram);
	
	/**
     * 新增组图管理
     * 
     * @param diagram 组图管理信息
     * @return 结果
     */
	public int insertDiagram(Diagram diagram);
	
	/**
     * 修改组图管理
     * 
     * @param diagram 组图管理信息
     * @return 结果
     */
	public int updateDiagram(Diagram diagram);
		
	/**
     * 删除组图管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDiagramByIds(String ids);
	
}
