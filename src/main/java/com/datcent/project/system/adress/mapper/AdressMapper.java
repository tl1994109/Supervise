package com.datcent.project.system.adress.mapper;

import com.datcent.project.system.adress.domain.Adress;

import java.util.List;
import java.util.Map;

/**
 * 通讯录 数据层
 * 
 * @author datcent
 * @date 2019-02-15
 */
public interface AdressMapper 
{
	/**
     * 查询通讯录信息
     * 
     * @param id 通讯录ID
     * @return 通讯录信息
     */
	public Adress selectAdressById(Integer id);
	
	/**
     * 查询通讯录列表
     * 
     * @param adress 通讯录信息
     * @return 通讯录集合
     */
	public List<Adress> selectAdressList(Adress adress);
	
	/**
     * 新增通讯录
     * 
     * @param adress 通讯录信息
     * @return 结果
     */
	public int insertAdress(Adress adress);
	
	/**
     * 修改通讯录
     * 
     * @param adress 通讯录信息
     * @return 结果
     */
	public int updateAdress(Adress adress);
	
	/**
     * 删除通讯录
     * 
     * @param id 通讯录ID
     * @return 结果
     */
	public int deleteAdressById(Integer id);
	
	/**
     * 批量删除通讯录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAdressByIds(String[] ids);

	/**
	 * 统计所有的通讯录
	 * @return
	 */
	public List<Map> selectAllAdressList(Adress adress);
	
}