package com.datcent.project.module.process.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datcent.project.module.process.mapper.ProcessResumeMapper;
import com.datcent.project.module.process.domain.ProcessResume;
import com.datcent.project.module.process.service.IProcessResumeService;
import com.datcent.common.support.Convert;

/**
 * 流程履历  服务层实现
 * 
 * @author datcent
 * @date 2018-11-08
 */
@Service
public class ProcessResumeServiceImpl implements IProcessResumeService 
{
	@Autowired
	private ProcessResumeMapper processResumeMapper;

	/**
     * 查询流程履历 信息
     * 
     * @param id 流程履历 ID
     * @return 流程履历 信息
     */
    @Override
	public ProcessResume selectProcessResumeById(String id)
	{
	    return processResumeMapper.selectProcessResumeById(id);
	}
	
	/**
     * 查询流程履历 列表
     * 
     * @param processResume 流程履历 信息
     * @return 流程履历 集合
     */
	@Override
	public List<ProcessResume> selectProcessResumeList(ProcessResume processResume)
	{
	    return processResumeMapper.selectProcessResumeList(processResume);
	}
	
    /**
     * 新增流程履历 
     * 
     * @param processResume 流程履历 信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertProcessResume(ProcessResume processResume)
	{
	    return processResumeMapper.insertProcessResume(processResume);
	}
	
	/**
     * 修改流程履历 
     * 
     * @param processResume 流程履历 信息
     * @return 结果
     */
	@Override
	public int updateProcessResume(ProcessResume processResume)
	{
	    return processResumeMapper.updateProcessResume(processResume);
	}

	/**
     * 删除流程履历 对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProcessResumeByIds(String ids)
	{
		return processResumeMapper.deleteProcessResumeByIds(Convert.toStrArray(ids));
	}
	
}
