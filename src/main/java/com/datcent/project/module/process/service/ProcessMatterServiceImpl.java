package com.datcent.project.module.process.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datcent.project.module.process.mapper.ProcessMatterMapper;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.service.IProcessMatterService;
import com.datcent.common.support.Convert;

/**
 * 流程事项  服务层实现
 * 
 * @author datcent
 * @date 2018-11-08
 */
@Service
public class ProcessMatterServiceImpl implements IProcessMatterService 
{
	@Autowired
	private ProcessMatterMapper processMatterMapper;

	/**
     * 查询流程事项 信息
     * 
     * @param matterId 流程事项 ID
     * @return 流程事项 信息
     */
    @Override
	public ProcessMatter selectProcessMatterById(String matterId)
	{
	    return processMatterMapper.selectProcessMatterById(matterId);
	}
	
	/**
     * 查询流程事项 列表
     * 
     * @param processMatter 流程事项 信息
     * @return 流程事项 集合
     */
	@Override
	public List<ProcessMatter> selectProcessMatterList(ProcessMatter processMatter)
	{
	    return processMatterMapper.selectProcessMatterList(processMatter);
	}
	
    /**
     * 新增流程事项 
     * 
     * @param processMatter 流程事项 信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertProcessMatter(ProcessMatter processMatter)
	{
	    return processMatterMapper.insertProcessMatter(processMatter);
	}
	
	/**
     * 修改流程事项 
     * 
     * @param processMatter 流程事项 信息
     * @return 结果
     */
	@Override
	public int updateProcessMatter(ProcessMatter processMatter)
	{
	    return processMatterMapper.updateProcessMatter(processMatter);
	}

	/**
     * 删除流程事项 对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProcessMatterByIds(String ids)
	{
		return processMatterMapper.deleteProcessMatterByIds(Convert.toStrArray(ids));
	}
	
}
