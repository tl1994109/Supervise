package com.datcent.project.tool.diagram.service;

import java.util.List;

import com.datcent.project.tool.diagram.domain.Diagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.tool.diagram.mapper.DiagramMapper;
import com.datcent.common.support.Convert;

/**
 * 组图管理 服务层实现
 * 
 * @author datcent
 * @date 2018-11-29
 */
@Service
public class DiagramServiceImpl implements IDiagramService 
{
	@Autowired
	private DiagramMapper diagramMapper;

	/**
     * 查询组图管理信息
     * 
     * @param diagramId 组图管理ID
     * @return 组图管理信息
     */
    @Override
	public Diagram selectDiagramById(String diagramId)
	{
	    return diagramMapper.selectDiagramById(diagramId);
	}
	
	/**
     * 查询组图管理列表
     * 
     * @param diagram 组图管理信息
     * @return 组图管理集合
     */
	@Override
	public List<Diagram> selectDiagramList(Diagram diagram)
	{
	    return diagramMapper.selectDiagramList(diagram);
	}
	
    /**
     * 新增组图管理
     * 
     * @param diagram 组图管理信息
     * @return 结果
     */
	@Override
	public int insertDiagram(Diagram diagram)
	{
		return diagramMapper.insertDiagram(diagram);
	}
	
	/**
     * 修改组图管理
     * 
     * @param diagram 组图管理信息
     * @return 结果
     */
	@Override
	public int updateDiagram(Diagram diagram)
	{
	    return diagramMapper.updateDiagram(diagram);
	}

	/**
     * 删除组图管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDiagramByIds(String ids)
	{
		return diagramMapper.deleteDiagramByIds(Convert.toStrArray(ids));
	}
	
}
