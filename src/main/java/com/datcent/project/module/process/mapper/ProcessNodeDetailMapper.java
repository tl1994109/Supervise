package com.datcent.project.module.process.mapper;

import com.datcent.project.module.process.domain.ProcessNodeDetail;

import java.util.List;

/**
 * 流程节点  数据层
 *
 * @author datcent
 * @date 2018-11-02
 */
public interface ProcessNodeDetailMapper {
    /**
     * 查询流程节点 信息
     *
     * @param processId 流程节点 ID
     * @return 流程节点 信息
     */
    public ProcessNodeDetail selectProcessNodeDetailById(String processId);

    /**
     * 查询流程节点 列表
     *
     * @param processNodeDetail 流程节点 信息
     * @return 流程节点 集合
     */
    public List<ProcessNodeDetail> selectProcessNodeDetailList(ProcessNodeDetail processNodeDetail);

    /**
     * 新增流程节点
     *
     * @param processNodeDetail 流程节点 信息
     * @return 结果
     */
    public int insertProcessNodeDetail(ProcessNodeDetail processNodeDetail);

    /**
     * 修改流程节点
     *
     * @param processNodeDetail 流程节点 信息
     * @return 结果
     */
    public int updateProcessNodeDetail(ProcessNodeDetail processNodeDetail);

    /**
     * 删除流程节点
     *
     * @param processId 流程节点 ID
     * @return 结果
     */
    public int deleteProcessNodeDetailById(String processId);

    /**
     * 批量删除流程节点
     *
     * @param processIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProcessNodeDetailByIds(String[] processIds);

}