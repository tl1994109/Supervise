package com.datcent.project.module.area.service;

import com.datcent.project.module.area.domain.Area;
import java.util.List;

/**
 * 省市区数据 服务层
 * 
 * @author datcent
 * @date 2018-11-06
 */
public interface IAreaService 
{
	/**
     * 查询省市区数据信息
     * 
     * @param id 省市区数据ID
     * @return 省市区数据信息
     */
	public Area selectAreaById(Integer id);
	
	/**
     * 根据Pid分类查询省市区数据信息
     * 
     * @param id 省市区数据ID
     * @return 省市区数据信息
     */
	public List<Area> selectProvinceByPid(long pid);
	
	/**
     * 查询省市区数据列表
     * 
     * @param area 省市区数据信息
     * @return 省市区数据集合
     */
	public List<Area> selectAreaList(Area area);
	
	/**
     * 新增省市区数据
     * 
     * @param area 省市区数据信息
     * @return 结果
     */
	public int insertArea(Area area);
	
	/**
     * 修改省市区数据
     * 
     * @param area 省市区数据信息
     * @return 结果
     */
	public int updateArea(Area area);
		
	/**
     * 删除省市区数据信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAreaByIds(String ids);
	
}
