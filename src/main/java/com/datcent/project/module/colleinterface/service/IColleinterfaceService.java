package com.datcent.project.module.colleinterface.service;

import com.datcent.project.module.colleinterface.domain.Colleinterface;
import java.util.List;

/**
 * 采集接口 服务层
 * 
 * @author datcent
 * @date 2019-01-10
 */
public interface IColleinterfaceService 
{
	/**
     * 查询采集接口信息
     * 
     * @param colleinterfaceId 采集接口ID
     * @return 采集接口信息
     */
	public Colleinterface selectColleinterfaceById(String colleinterfaceId);
	
	/**
     * 查询采集接口列表
     * 
     * @param colleinterface 采集接口信息
     * @return 采集接口集合
     */
	public List<Colleinterface> selectColleinterfaceList(Colleinterface colleinterface);
	
	/**
     * 新增采集接口
     * 
     * @param colleinterface 采集接口信息
     * @return 结果
     */
	public int insertColleinterface(Colleinterface colleinterface);
	
	/**
     * 修改采集接口
     * 
     * @param colleinterface 采集接口信息
     * @return 结果
     */
	public int updateColleinterface(Colleinterface colleinterface);
		
	/**
     * 删除采集接口信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteColleinterfaceByIds(String ids);
	
}
