package com.datcent.project.tool.layout.service;

import java.util.List;

import com.datcent.project.system.dict.domain.DictData;
import com.datcent.project.system.dict.mapper.DictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.project.tool.layout.mapper.LayoutMapper;
import com.datcent.project.tool.layout.domain.Layout;
import com.datcent.project.tool.layout.service.ILayoutService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 布局管理 服务层实现
 * 
 * @author datcent
 * @date 2018-11-22
 */
@Service
public class LayoutServiceImpl implements ILayoutService 
{
	@Autowired
	private LayoutMapper layoutMapper;
	@Autowired
	private DictDataMapper dictDataMapper;

	/**
     * 查询布局管理信息
     * 
     * @param layoutId 布局管理ID
     * @return 布局管理信息
     */
    @Override
	public Layout selectLayoutById(String layoutId)
	{
	    return layoutMapper.selectLayoutById(layoutId);
	}
	
	/**
     * 查询布局管理列表
     * 
     * @param layout 布局管理信息
     * @return 布局管理集合
     */
	@Override
	public List<Layout> selectLayoutList(Layout layout)
	{
	    return layoutMapper.selectLayoutList(layout);
	}
	
    /**
     * 新增布局管理
     * 
     * @param layout 布局管理信息
     * @return 结果
     */
	@Override
	public int insertLayout(Layout layout)
	{
		layout.setLayoutId(StringUtils.getUUID());

		//同时向 字典表（tool_layout）添加数
		DictData dictData=new DictData();
		dictData.setDictType("tool_layout");
		dictData.setDictLabel(layout.getLayoutName());
		dictData.setDictSort((long) 1);
		dictData.setIsDefault("Y");
		dictData.setStatus("0");
		dictData.setDictValue(layout.getLayoutId());
		dictDataMapper.insertDictData(dictData);
		return layoutMapper.insertLayout(layout);
	}
	
	/**
     * 修改布局管理
     * 
     * @param layout 布局管理信息
     * @return 结果
     */
	@Override
	public int updateLayout(Layout layout)
	{
	    return layoutMapper.updateLayout(layout);
	}

	/**
     * 删除布局管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteLayoutByIds(String ids)
	{
		return layoutMapper.deleteLayoutByIds(Convert.toStrArray(ids));
	}
	
}
