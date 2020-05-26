package com.datcent.project.module.process.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.project.module.process.mapper.ProcessMapper;
import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.process.domain.ProcessLineDetail;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.service.IProcessService;
import com.datcent.common.support.Convert;

/**
 * 流程 服务层实现
 * 
 * @author datcent
 * @date 2018-11-07
 */
@Service
public class ProcessServiceImpl implements IProcessService 
{
	@Autowired
	private ProcessMapper processMapper;
	
	@Autowired
    private IProcessNodeDetailService processNodeDetailService;
	
	@Autowired
    private IProcessLineDetailService processLineDetailService;
    
    @Autowired
    private IProcessResumeService processResumeService;
    
    @Autowired
    private IProcessMatterService processMatterService;

	/**
     * 查询流程信息
     * 
     * @param processId 流程ID
     * @return 流程信息
     */
    @Override
	public Process selectProcessById(String processId)
	{
	    return processMapper.selectProcessById(processId);
	}
	
	/**
     * 查询流程列表
     * 
     * @param process 流程信息
     * @return 流程集合
     */
	@Override
	public List<Process> selectProcessList(Process process)
	{
	    return processMapper.selectProcessList(process);
	}
	
    /**
     * 新增流程
     * 
     * @param process 流程信息
     * @return 结果
     */
	@Override
	public int insertProcess(Process process)
	{
	    return processMapper.insertProcess(process);
	}
	
	/**
     * 从当前节点查询下一个流程节点信息
     * 
     * @param process 流程信息
     * @return 结果
     */
	@Override
	public ProcessNodeDetail getLocalNodeInfo(String processId,String nodeId)
	{
		ProcessLineDetail processLineDetail=new ProcessLineDetail();
		processLineDetail.setProcessId(processId);
		processLineDetail.setFromNodeId(nodeId);
		List<ProcessLineDetail> processNodeDetailList = processLineDetailService.selectProcessLineDetailList(processLineDetail);
		if(processNodeDetailList.size()>0){
			ProcessNodeDetail detail=new ProcessNodeDetail();
			detail.setProcessId(processId);
			detail.setNodeId(processNodeDetailList.get(0).getToNodeId());
			return processNodeDetailService.selectProcessNodeDetailList(detail).get(0);
		}
		return null;
	}
	
	
	/**
     * 不通过从当前节点获取上一节点信息
     * 
     * @param process 流程信息
     * @return 结果
     */
	@Override
	public ProcessNodeDetail getPrevNodeInfo(String processId,String nodeId)
	{
		ProcessLineDetail processLineDetail=new ProcessLineDetail();
		processLineDetail.setProcessId(processId);
		processLineDetail.setToNodeId(nodeId);
		List<ProcessLineDetail> processNodeDetailList = processLineDetailService.selectProcessLineDetailList(processLineDetail);
		if(processNodeDetailList.size()>0){
			ProcessNodeDetail detail=new ProcessNodeDetail();
			detail.setProcessId(processId);
			detail.setNodeId(processNodeDetailList.get(0).getFromNodeId());
			return processNodeDetailService.selectProcessNodeDetailList(detail).get(0);
		}
		return null;
	}
	
	/**
     * 修改流程
     * 
     * @param process 流程信息
     * @return 结果
     */
	@Override
	public int updateProcess(Process process)
	{
	    return processMapper.updateProcess(process);
	}

	/**
     * 删除流程对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProcessByIds(String ids)
	{
		return processMapper.deleteProcessByIds(Convert.toStrArray(ids));
	}
	
}
