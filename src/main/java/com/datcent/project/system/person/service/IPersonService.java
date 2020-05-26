package com.datcent.project.system.person.service;

import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.person.domain.Person;

import java.util.List;

/**
 * 
 * @描述: 人员  服务层
 * @创建人: zhou_shiji
 * @创建时间: 2018年10月19日下午3:16:31
 */
public interface IPersonService 
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
	public List<Person> selectPersonList(Person person, PageDomain pageDomain);
	
	/**
     * 查询人员ID Name 列表 添加权限
     * @return 人员 集合
     */
	public List<Person> selectPersonAll();
	
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
     * 删除人员 信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePersonByIds(String ids) throws Exception;


	/**
	 * 导入人员数据
	 *
	 * @param userList 用户数据列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
	 * @return 结果
	 */
	public String importPerson(List<Person> userList, Boolean isUpdateSupport);
	
}
