package com.datcent.project.module.court.service;

import com.datcent.project.module.court.domain.Court;

import java.util.List;

/**
 * 法院  服务层
 * 
 * @author datcent
 * @date 2018-11-06
 */
public interface ICourtService 
{
	/**
     * 查询法院 信息
     * 
     * @param courtId 法院 ID
     * @return 法院 信息
     */
	public Court selectCourtById(Integer courtId);
	
	/**
     * 查询法院 列表
     * 
     * @param court 法院 信息
     * @return 法院 集合
     */
	public List<Court> selectCourtList(Court court);
	
	/**
     * 新增法院 
     * 
     * @param court 法院 信息
     * @return 结果
     */
	public int insertCourt(Court court);
	
	/**
     * 修改法院 
     * 
     * @param court 法院 信息
     * @return 结果
     */
	public int updateCourt(Court court);
		
	/**
     * 删除法院 信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourtByIds(String ids);
	
	/**
     * 校验法院名称是否唯一
     * 
     * @param courtName 法院名称
     * @return 结果
     */
    public String checkCourtNameUnique(String courtName);
	
}
