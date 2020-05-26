package com.datcent.project.module.cbttj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.cbttj.mapper.CbttjMapper;
import com.datcent.project.module.cbttj.domain.Cbttj;
import com.datcent.project.module.cbttj.service.ICbttjService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 承办庭统计 服务层实现
 * 
 * @author datcent
 * @date 2019-03-25
 */
@Service
public class CbttjServiceImpl implements ICbttjService
{
	@Autowired
	private CbttjMapper cbttjMapper;

	/**
     * 查询承办庭统计信息
     * 
     * @param id 承办庭统计ID
     * @return 承办庭统计信息
     */
    @Override
	public Cbttj selectCbttjById(Integer id)
	{
	    return cbttjMapper.selectCbttjById(id);
	}
	
	/**
     * 查询承办庭统计列表
     * 
     * @param cbttj 承办庭统计信息
     * @return 承办庭统计集合
     */
	@Override
	public List<Cbttj> selectCbttjList(Cbttj cbttj)
	{
	    return cbttjMapper.selectCbttjList(cbttj);
	}
	
    /**
     * 新增承办庭统计
     * 
     * @param cbttj 承办庭统计信息
     * @return 结果
     */
	@Override
	public int insertCbttj(Cbttj cbttj)
	{
	    return cbttjMapper.insertCbttj(cbttj);
	}
	
	/**
     * 修改承办庭统计
     * 
     * @param cbttj 承办庭统计信息
     * @return 结果
     */
	@Override
	public int updateCbttj(Cbttj cbttj)
	{
	    return cbttjMapper.updateCbttj(cbttj);
	}

	/**
     * 删除承办庭统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCbttjByIds(String ids)
	{
		return cbttjMapper.deleteCbttjByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据时间和法院编号查询各法院各类型案件数量
	 * @param time
	 * @param cbtId
	 * @param fyId
	 * @return
	 */
	@Override
	public List<Cbttj> queryByCbtAndMonth(String time, String cbtId, String fyId) {
		return cbttjMapper.queryByCbtAndMonth(time,cbtId,fyId);
	}

	/**
	 * 根据法院编号查询承办庭
	 * @param jbfyId
	 * @return
	 */
	public List<Map> queryCbtIdByFyId(String jbfyId,String time){

		return cbttjMapper.queryCbtIdByFyId(jbfyId,time);
	}


	/**
	 * 根据时间和承办庭编号查询各法院旧存数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth( String id,  String time){
		return cbttjMapper.queryJcByFyAndMonth(id,time);
	}

	/**
	 * 根据时间和承办庭编号查询各法院新收数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth( String id, String time){
		return cbttjMapper.queryXsByFyAndMonth(id,time);
	}


	/**
	 * 根据时间和承办庭编号查询各法院未结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth(@Param("id") String id,@Param("time") String time){
		return cbttjMapper.queryWjByFyAndMonth(id,time);
	}

	/**
	 * 根据时间和承办庭编号查询各法院已结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth(@Param("id") String id,@Param("time") String time){
		return cbttjMapper.queryYjByFyAndMonth(id,time);
	}

}
