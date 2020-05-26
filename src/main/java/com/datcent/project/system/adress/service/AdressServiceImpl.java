package com.datcent.project.system.adress.service;

import com.datcent.common.support.Convert;
import com.datcent.project.system.adress.domain.Adress;
import com.datcent.project.system.adress.mapper.AdressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 通讯录 服务层实现
 * 
 * @author datcent
 * @date 2019-02-15
 */
@Service
public class AdressServiceImpl implements IAdressService
{
	@Autowired
	private AdressMapper adressMapper;

	/**
     * 查询通讯录信息
     * 
     * @param id 通讯录ID
     * @return 通讯录信息
     */
    @Override
	public Adress selectAdressById(Integer id)
	{
	    return adressMapper.selectAdressById(id);
	}
	
	/**
     * 查询通讯录列表
     * 
     * @param adress 通讯录信息
     * @return 通讯录集合
     */
	@Override
	public List<Adress> selectAdressList(Adress adress)
	{
	    return adressMapper.selectAdressList(adress);
	}
	
    /**
     * 新增通讯录
     * 
     * @param adress 通讯录信息
     * @return 结果
     */
	@Override
	public int insertAdress(Adress adress)
	{
	    return adressMapper.insertAdress(adress);
	}
	
	/**
     * 修改通讯录
     * 
     * @param adress 通讯录信息
     * @return 结果
     */
	@Override
	public int updateAdress(Adress adress)
	{
	    return adressMapper.updateAdress(adress);
	}

	/**
     * 删除通讯录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdressByIds(String ids)
	{
		return adressMapper.deleteAdressByIds(Convert.toStrArray(ids));
	}


	/**
	 * 统计所有的通讯录
	 * @return
	 */
	public List<Map> selectAllAdressList(Adress adress){
		return adressMapper.selectAllAdressList(adress);
	}
	
}
