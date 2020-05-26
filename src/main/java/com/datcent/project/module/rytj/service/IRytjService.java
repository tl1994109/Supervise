package com.datcent.project.module.rytj.service;

import com.datcent.project.module.rytj.domain.Rytj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员统计 服务层
 * 
 * @author datcent
 * @date 2019-03-27
 */
public interface IRytjService 
{
	/**
     * 查询人员统计信息
     * 
     * @param id 人员统计ID
     * @return 人员统计信息
     */
	public Rytj selectRytjById(Integer id);
	
	/**
     * 查询人员统计列表
     * 
     * @param rytj 人员统计信息
     * @return 人员统计集合
     */
	public List<Rytj> selectRytjList(Rytj rytj);
	
	/**
     * 新增人员统计
     * 
     * @param rytj 人员统计信息
     * @return 结果
     */
	public int insertRytj(Rytj rytj);
	
	/**
     * 修改人员统计
     * 
     * @param rytj 人员统计信息
     * @return 结果
     */
	public int updateRytj(Rytj rytj);
		
	/**
     * 删除人员统计信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRytjByIds(String ids);



	/**
	 * 根据时间和承办庭编号查询各法院各承办庭 承办人旧存数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time);

	/**
	 * 根据时间和承办庭编号查询各法院新收数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time);


	/**
	 * 根据时间和承办庭编号查询各法院未结数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time);


	/**
	 * 根据时间和承办庭编号查询各法院已结数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time);
}
