package com.datcent.project.module.rytj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.rytj.mapper.RytjMapper;
import com.datcent.project.module.rytj.domain.Rytj;
import com.datcent.project.module.rytj.service.IRytjService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 人员统计 服务层实现
 * 
 * @author datcent
 * @date 2019-03-27
 */
@Service
public class RytjServiceImpl implements IRytjService 
{
	@Autowired
	private RytjMapper rytjMapper;

	/**
     * 查询人员统计信息
     * 
     * @param id 人员统计ID
     * @return 人员统计信息
     */
    @Override
	public Rytj selectRytjById(Integer id)
	{
	    return rytjMapper.selectRytjById(id);
	}
	
	/**
     * 查询人员统计列表
     * 
     * @param rytj 人员统计信息
     * @return 人员统计集合
     */
	@Override
	public List<Rytj> selectRytjList(Rytj rytj)
	{
	    return rytjMapper.selectRytjList(rytj);
	}
	
    /**
     * 新增人员统计
     * 
     * @param rytj 人员统计信息
     * @return 结果
     */
	@Override
	public int insertRytj(Rytj rytj)
	{
	    return rytjMapper.insertRytj(rytj);
	}
	
	/**
     * 修改人员统计
     * 
     * @param rytj 人员统计信息
     * @return 结果
     */
	@Override
	public int updateRytj(Rytj rytj)
	{
	    return rytjMapper.updateRytj(rytj);
	}

	/**
     * 删除人员统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRytjByIds(String ids)
	{
		return rytjMapper.deleteRytjByIds(Convert.toStrArray(ids));
	}


	/**
	 * 根据时间和承办庭编号查询各法院各承办庭 承办人旧存数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time){
		return rytjMapper.queryJcByFyAndMonth(fyId,cbtId,ryId,time);
	}

	/**
	 * 根据时间和承办庭编号查询各法院新收数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time){
		return rytjMapper.queryXsByFyAndMonth(fyId,cbtId,ryId,time);
	}


	/**
	 * 根据时间和承办庭编号查询各法院未结数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time){
		return rytjMapper.queryWjByFyAndMonth(fyId,cbtId,ryId,time);
	}


	/**
	 * 根据时间和承办庭编号查询各法院已结数量
	 * @param ryId
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth(@Param("fyId") String fyId,@Param("cbtId") String cbtId,@Param("ryId") String ryId, @Param("time") String time){
		return rytjMapper.queryYjByFyAndMonth(fyId,cbtId,ryId,time);
	}

}
