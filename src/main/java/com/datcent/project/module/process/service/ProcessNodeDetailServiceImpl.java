package com.datcent.project.module.process.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.process.mapper.ProcessNodeDetailMapper;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.common.support.Convert;

/**
 * 流程节点  服务层实现
 * 
 * @author datcent
 * @date 2018-11-08
 */
@Service
public class ProcessNodeDetailServiceImpl implements IProcessNodeDetailService 
{
	@Autowired
	private ProcessNodeDetailMapper processNodeDetailMapper;

	/**
     * 查询流程节点 信息
     * 
     * @param processId 流程节点 ID
     * @return 流程节点 信息
     */
    @Override
	public ProcessNodeDetail selectProcessNodeDetailById(String processId)
	{
	    return processNodeDetailMapper.selectProcessNodeDetailById(processId);
	}
	
	/**
     * 查询流程节点 列表
     * 
     * @param processNodeDetail 流程节点 信息
     * @return 流程节点 集合
     */
	@Override
	public List<ProcessNodeDetail> selectProcessNodeDetailList(ProcessNodeDetail processNodeDetail)
	{
	    return processNodeDetailMapper.selectProcessNodeDetailList(processNodeDetail);
	}
	
    /**
     * 新增流程节点 
     * 
     * @param processNodeDetail 流程节点 信息
     * @return 结果
     */
	@Override
	public int insertProcessNodeDetail(ProcessNodeDetail processNodeDetail)
	{
	    return processNodeDetailMapper.insertProcessNodeDetail(processNodeDetail);
	}
	
	/**
     * 修改流程节点 
     * 
     * @param processNodeDetail 流程节点 信息
     * @return 结果
     */
	@Override
	public int updateProcessNodeDetail(ProcessNodeDetail processNodeDetail)
	{
	    return processNodeDetailMapper.updateProcessNodeDetail(processNodeDetail);
	}

	/**
     * 删除流程节点 对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProcessNodeDetailByIds(String ids)
	{
		return processNodeDetailMapper.deleteProcessNodeDetailByIds(Convert.toStrArray(ids));
	}
	
}
