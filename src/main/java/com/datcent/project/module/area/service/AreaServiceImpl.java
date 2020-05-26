package com.datcent.project.module.area.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.area.mapper.AreaMapper;
import com.datcent.project.module.area.domain.Area;
import com.datcent.project.module.area.service.IAreaService;
import com.datcent.common.support.Convert;

/**
 * 省市区数据 服务层实现
 * 
 * @author datcent
 * @date 2018-11-06
 */
@Service
public class AreaServiceImpl implements IAreaService 
{
	@Autowired
	private AreaMapper areaMapper;

	/**
     * 查询省市区数据信息
     * 
     * @param id 省市区数据ID
     * @return 省市区数据信息
     */
    @Override
	public Area selectAreaById(Integer id)
	{
	    return areaMapper.selectAreaById(id);
	}
    
    /**
     * 查询省市区数据信息
     * 
     * @param id 省市区数据ID
     * @return 省市区数据信息
     */
    @Override
	public List<Area> selectProvinceByPid(long pid)
	{
	    return areaMapper.selectProvinceByPid(pid);
	}
	
	/**
     * 查询省市区数据列表
     * 
     * @param area 省市区数据信息
     * @return 省市区数据集合
     */
	@Override
	public List<Area> selectAreaList(Area area)
	{
	    return areaMapper.selectAreaList(area);
	}
	
    /**
     * 新增省市区数据
     * 
     * @param area 省市区数据信息
     * @return 结果
     */
	@Override
	public int insertArea(Area area)
	{
	    return areaMapper.insertArea(area);
	}
	
	/**
     * 修改省市区数据
     * 
     * @param area 省市区数据信息
     * @return 结果
     */
	@Override
	public int updateArea(Area area)
	{
	    return areaMapper.updateArea(area);
	}

	/**
     * 删除省市区数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAreaByIds(String ids)
	{
		return areaMapper.deleteAreaByIds(Convert.toStrArray(ids));
	}
	
}
