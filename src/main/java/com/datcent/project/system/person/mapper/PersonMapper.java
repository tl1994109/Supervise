package com.datcent.project.system.person.mapper;

import com.datcent.project.system.person.domain.Person;
import java.util.List;	

/**
 * 
 * @描述: 人员  数据层
 * @创建人: zhou_shiji
 * @创建时间: 2018年10月19日下午3:16:18
 */
public interface PersonMapper 
{
	/**
     * 查询人员 信息
     * 
     * @param personId 人员 ID
     * @return 人员 信息
     */
	public Person selectPersonById(String personId);
	
	/**
     * 查询人员 列表
     * 
     * @param person 人员 信息
     * @return 人员 集合
     */
	public List<Person> selectPersonList(Person person);
	
	/**
     * 查询人员Id Name
     * @return 人员 集合
     */
	public List<Person> selectPersonAll(Person person);
	
	/**
     * 新增人员 
     * 
     * @param person 人员 信息
     * @return 结果
     */
	public int insertPerson(Person person);
	
	/**
     * 修改人员 
     * 
     * @param person 人员 信息
     * @return 结果
     */
	public int updatePerson(Person person);
	
	/**
     * 删除人员 
     * 
     * @param personId 人员 ID
     * @return 结果
     */
	public int deletePersonById(String personId);
	
	/**
     * 批量删除人员 
     * 
     * @param personIds 需要删除的数据ID
     * @return 结果
     */
	public int deletePersonByIds(String[] personIds);
	
	
	/**
     * 逻辑删除人员 
     * 
     * @param personId 人员 ID
     * @return 结果
     */
	public int logicallyDelPersonById(String personId);
	
	/**
     * 逻辑批量删除人员 
     * 
     * @param personIds 需要删除的数据ID
     * @return 结果
     */
	public int logicallyDelPersonByIds(String[] personIds);
	
}