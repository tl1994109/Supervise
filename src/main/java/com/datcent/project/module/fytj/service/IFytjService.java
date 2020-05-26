package com.datcent.project.module.fytj.service;

import com.datcent.project.module.fytj.domain.Fytj;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 法院统计 服务层
 * 
 * @author datcent
 * @date 2019-03-22
 */
public interface IFytjService 
{
	/**
     * 查询法院统计信息
     * 
     * @param id 法院统计ID
     * @return 法院统计信息
     */
	public Fytj selectFytjById(Integer id);
	
	/**
     * 查询法院统计列表
     * 
     * @param fytj 法院统计信息
     * @return 法院统计集合
     */
	public List<Fytj> selectFytjList(Fytj fytj);
	
	/**
     * 新增法院统计
     * 
     * @param fytj 法院统计信息
     * @return 结果
     */
	public int insertFytj(Fytj fytj);
	
	/**
     * 修改法院统计
     * 
     * @param fytj 法院统计信息
     * @return 结果
     */
	public int updateFytj(Fytj fytj);
		
	/**
     * 删除法院统计信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFytjByIds(String ids);

	/**
	 * 查询所有的法院和法院编号
	 * @return
	 */
	public List<Map> queryAllFyAndId(String time);



	/**
	 * 根据时间和法院编号查询各法院旧存数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth(@Param(" id") String id, @Param("time") String time);

	/**
	 * 根据时间和法院编号查询各法院新收数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth(@Param(" id") String id,@Param("time") String time);


	/**
	 * 根据时间和法院编号查询各法院未结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth(@Param(" id") String id,@Param("time") String time);


	/**
	 * 根据时间和法院编号查询各法院已结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth(@Param(" id") String id,@Param("time") String time);
	
}
