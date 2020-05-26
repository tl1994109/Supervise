package com.datcent.project.module.dispositionForm.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.dispositionForm.mapper.DispositionFormMapper;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 问题处置单 服务层实现
 * 
 * @author datcent
 * @date 2019-01-18
 */
@Service
public class DispositionFormServiceImpl implements IDispositionFormService 
{
	@Autowired
	private DispositionFormMapper dispositionFormMapper;

	/**
     * 查询问题处置单信息
     * 
     * @param formId 问题处置单ID
     * @return 问题处置单信息
     */
    @Override
	public DispositionForm selectDispositionFormById(String formId)
	{
	    return dispositionFormMapper.selectDispositionFormById(formId);
	}
	
	/**
     * 查询问题处置单列表
     * 
     * @param dispositionForm 问题处置单信息
     * @return 问题处置单集合
     */
	@Override
	public List<DispositionForm> selectDispositionFormList(DispositionForm dispositionForm)
	{
	    return dispositionFormMapper.selectDispositionFormList(dispositionForm);
	}
	
    /**
     * 新增问题处置单
     * 
     * @param dispositionForm 问题处置单信息
     * @return 结果
     */
	@Override
	public int insertDispositionForm(DispositionForm dispositionForm)
	{
		dispositionForm.setFormId(StringUtils.getUUID());
		dispositionForm.setCreateBy(ShiroUtils.getUser().getUserName());
		dispositionForm.setCreateTime(new Date());
	    return dispositionFormMapper.insertDispositionForm(dispositionForm);
	}
	
	/**
     * 修改问题处置单
     * 
     * @param dispositionForm 问题处置单信息
     * @return 结果
     */
	@Override
	public int updateDispositionForm(DispositionForm dispositionForm)
	{
		dispositionForm.setUpdateBy(ShiroUtils.getUser().getUserName());
		dispositionForm.setUpdateTime(new Date());
	    return dispositionFormMapper.updateDispositionForm(dispositionForm);
	}

	/**
     * 删除问题处置单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDispositionFormByIds(String ids)
	{
		return dispositionFormMapper.deleteDispositionFormByIds(Convert.toStrArray(ids));
	}
	
}
