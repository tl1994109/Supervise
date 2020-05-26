package com.datcent.project.module.existingProperty.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.existingProperty.mapper.ExistingPropertyMapper;
import com.datcent.project.module.existingProperty.domain.ExistingProperty;
import com.datcent.project.module.existingProperty.service.IExistingPropertyService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 现有财产状况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class ExistingPropertyServiceImpl implements IExistingPropertyService 
{
	@Autowired
	private ExistingPropertyMapper existingPropertyMapper;

	/**
     * 查询现有财产状况信息
     * 
     * @param jtccqkId 现有财产状况ID
     * @return 现有财产状况信息
     */
    @Override
	public ExistingProperty selectExistingPropertyById(String jtccqkId)
	{
	    return existingPropertyMapper.selectExistingPropertyById(jtccqkId);
	}
	
	/**
     * 查询现有财产状况列表
     * 
     * @param existingProperty 现有财产状况信息
     * @return 现有财产状况集合
     */
	@Override
	public List<ExistingProperty> selectExistingPropertyList(ExistingProperty existingProperty)
	{
	    return existingPropertyMapper.selectExistingPropertyList(existingProperty);
	}
	
    /**
     * 新增现有财产状况
     * 
     * @param existingProperty 现有财产状况信息
     * @return 结果
     */
	@Override
	public int insertExistingProperty(ExistingProperty existingProperty) {
		return existingPropertyMapper.insertExistingProperty(existingProperty);
		}

	
	/**
     * 修改现有财产状况
     * 
     * @param existingProperty 现有财产状况信息
     * @return 结果
     */
	@Override
	public int updateExistingProperty(ExistingProperty existingProperty)
	{
	    return existingPropertyMapper.updateExistingProperty(existingProperty);
	}

	/**
     * 删除现有财产状况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteExistingPropertyByIds(String ids)
	{
		return existingPropertyMapper.deleteExistingPropertyByIds(Convert.toStrArray(ids));
	}
	
}
