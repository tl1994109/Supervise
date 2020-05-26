package com.datcent.project.system.reProcdef.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.datcent.project.system.reProcdef.domain.ReProcdef;
import com.datcent.project.system.reProcdef.mapper.ReProcdefMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 业务流程定义 服务层实现
 * 
 * @author datcent
 * @date 2019-01-08
 */
@Service
public class ReProcdefServiceImpl implements IReProcdefService 
{
	@Autowired
	private ReProcdefMapper reProcdefMapper;

	@Autowired
    private RepositoryService repositoryService;

	/**
     * 查询业务流程定义信息
     * 
     * @param id 业务流程定义ID
     * @return 业务流程定义信息
     */
    @Override
	public ReProcdef selectReProcdefById(String id)
	{
	    return reProcdefMapper.selectReProcdefById(id);
	}
	
	/**
     * 查询业务流程定义列表
     * 
     * @param reProcdef 业务流程定义信息
     * @return 业务流程定义集合
     */
	@Override
	public List<ReProcdef> selectReProcdefList(ReProcdef reProcdef)
	{
	    return reProcdefMapper.selectReProcdefList(reProcdef);
	}
	
    /**
     * 新增业务流程定义
     * 
     * @param reProcdef 业务流程定义信息
     * @return 结果
     */
	@Override
	public int insertReProcdef(ReProcdef reProcdef)
	{
	    return reProcdefMapper.insertReProcdef(reProcdef);
	}
	
	/**
     * 修改业务流程定义
     * 
     * @param reProcdef 业务流程定义信息
     * @return 结果
     */
	@Override
	public int updateReProcdef(ReProcdef reProcdef)
	{
	    return reProcdefMapper.updateReProcdef(reProcdef);
	}

	/**
     * 删除业务流程定义对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteReProcdefByIds(String ids)
	{
		return reProcdefMapper.deleteReProcdefByIds(Convert.toStrArray(ids));
	}

}
