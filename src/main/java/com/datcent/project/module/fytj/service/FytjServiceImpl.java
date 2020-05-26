package com.datcent.project.module.fytj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.fytj.mapper.FytjMapper;
import com.datcent.project.module.fytj.domain.Fytj;
import com.datcent.project.module.fytj.service.IFytjService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 法院统计 服务层实现
 * 
 * @author datcent
 * @date 2019-03-22
 */
@Service
public class FytjServiceImpl implements IFytjService 
{
	@Autowired
	private FytjMapper fytjMapper;

	/**
     * 查询法院统计信息
     * 
     * @param id 法院统计ID
     * @return 法院统计信息
     */
    @Override
	public Fytj selectFytjById(Integer id)
	{
	    return fytjMapper.selectFytjById(id);
	}
	
	/**
     * 查询法院统计列表
     * 
     * @param fytj 法院统计信息
     * @return 法院统计集合
     */
	@Override
	public List<Fytj> selectFytjList(Fytj fytj)
	{
	    return fytjMapper.selectFytjList(fytj);
	}
	
    /**
     * 新增法院统计
     * 
     * @param fytj 法院统计信息
     * @return 结果
     */
	@Override
	public int insertFytj(Fytj fytj)
	{
	    return fytjMapper.insertFytj(fytj);
	}
	
	/**
     * 修改法院统计
     * 
     * @param fytj 法院统计信息
     * @return 结果
     */
	@Override
	public int updateFytj(Fytj fytj)
	{
	    return fytjMapper.updateFytj(fytj);
	}

	/**
     * 删除法院统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFytjByIds(String ids)
	{
		return fytjMapper.deleteFytjByIds(Convert.toStrArray(ids));
	}



	/**
	 * 查询所有的法院和法院编号
	 * @return
	 */
	public List<Map> queryAllFyAndId(String time){
		return fytjMapper.queryAllFyAndId(time);
	}

	/**
	 * 根据时间和法院编号查询各法院旧存数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryJcByFyAndMonth( String id, String time){

		return fytjMapper.queryJcByFyAndMonth(id,time);
	}

	/**
	 * 根据时间和法院编号查询各法院新收数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryXsByFyAndMonth( String id, String time){
		return fytjMapper.queryXsByFyAndMonth(id,time);
	}


	/**
	 * 根据时间和法院编号查询各法院未结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryWjByFyAndMonth( String id, String time){
		return fytjMapper.queryWjByFyAndMonth(id,time);
	}


	/**
	 * 根据时间和法院编号查询各法院已结数量
	 * @param id
	 * @param time
	 * @return
	 */
	public  String queryYjByFyAndMonth( String id, String time){
		return fytjMapper.queryYjByFyAndMonth(id,time);
	}
}
