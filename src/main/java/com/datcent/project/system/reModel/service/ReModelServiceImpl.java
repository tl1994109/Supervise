package com.datcent.project.system.reModel.service;

import java.util.List;

import com.datcent.project.system.reModel.domain.ReModel;
import com.datcent.project.system.reModel.mapper.ReModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 工作流模型 服务层实现
 * 
 * @author datcent
 * @date 2019-01-08
 */
@Service
public class ReModelServiceImpl implements IReModelService 
{
	@Autowired
	private ReModelMapper reModelMapper;

	/**
     * 查询工作流模型信息
     * 
     * @param id 工作流模型ID
     * @return 工作流模型信息
     */
    @Override
	public ReModel selectReModelById(String id)
	{
	    return reModelMapper.selectReModelById(id);
	}
	
	/**
     * 查询工作流模型列表
     * 
     * @param reModel 工作流模型信息
     * @return 工作流模型集合
     */
	@Override
	public List<ReModel> selectReModelList(ReModel reModel)
	{
	    return reModelMapper.selectReModelList(reModel);
	}
	
    /**
     * 新增工作流模型
     * 
     * @param reModel 工作流模型信息
     * @return 结果
     */
	@Override
	public int insertReModel(ReModel reModel)
	{
	    return reModelMapper.insertReModel(reModel);
	}
	
	/**
     * 修改工作流模型
     * 
     * @param reModel 工作流模型信息
     * @return 结果
     */
	@Override
	public int updateReModel(ReModel reModel)
	{
	    return reModelMapper.updateReModel(reModel);
	}

	/**
     * 删除工作流模型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteReModelByIds(String ids)
	{
		return reModelMapper.deleteReModelByIds(Convert.toStrArray(ids));
	}
	
}
