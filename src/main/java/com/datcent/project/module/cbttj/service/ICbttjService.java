package com.datcent.project.module.cbttj.service;

import com.datcent.project.module.cbttj.domain.Cbttj;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 承办庭统计 服务层
 * 
 * @author datcent
 * @date 2019-03-25
 */
public interface ICbttjService 
{
	/**
     * 查询承办庭统计信息
     * 
     * @param id 承办庭统计ID
     * @return 承办庭统计信息
     */
	public Cbttj selectCbttjById(Integer id);
	
	/**
     * 查询承办庭统计列表
     * 
     * @param cbttj 承办庭统计信息
     * @return 承办庭统计集合
     */
	public List<Cbttj> selectCbttjList(Cbttj cbttj);
	
	/**
     * 新增承办庭统计
     * 
     * @param cbttj 承办庭统计信息
     * @return 结果
     */
	public int insertCbttj(Cbttj cbttj);
	
	/**
     * 修改承办庭统计
     * 
     * @param cbttj 承办庭统计信息
     * @return 结果
     */
	public int updateCbttj(Cbttj cbttj);
		
	/**
     * 删除承办庭统计信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCbttjByIds(String ids);


	/**
	 * 根据时间和法院编号查询各法院已结数量
	 * @param time
	 * @param cbtId
	 * @return
	 */
	public  List<Cbttj> queryByCbtAndMonth(@Param("time") String time, @Param("cbtId") String cbtId, @Param("fyId") String fyId);



	/**
	 * 根据法院编号查询承办庭
	 * @param jbfyId
	 * @return
	 */
	public List<Map> queryCbtIdByFyId(String jbfyId,String time);

	/**
	 * 根据时间和承办庭编号查询各法院旧存数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth(@Param("id") String id,@Param("time") String time);

	/**
	 * 根据时间和承办庭编号查询各法院新收数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth(@Param("id") String id,@Param("time") String time);


	/**
	 * 根据时间和承办庭编号查询各法院未结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth(@Param("id") String id,@Param("time") String time);

	/**
	 * 根据时间和承办庭编号查询各法院已结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth(@Param("id") String id,@Param("time") String time);
}
