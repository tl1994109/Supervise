package com.datcent.project.module.process.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.process.mapper.ProcessLineDetailMapper;
import com.datcent.project.module.process.domain.ProcessLineDetail;
import com.datcent.common.support.Convert;

/**
 * 流程线条  服务层实现
 *
 * @author datcent
 * @date 2018-11-02
 */
@Service
public class ProcessLineDetailServiceImpl implements IProcessLineDetailService {
    @Autowired
    private ProcessLineDetailMapper processLineDetailMapper;

    /**
     * 查询流程线条 信息
     *
     * @param processId 流程线条 ID
     * @return 流程线条 信息
     */
    @Override
    public ProcessLineDetail selectProcessLineDetailById(String processId) {
        return processLineDetailMapper.selectProcessLineDetailById(processId);
    }

    /**
     * 查询流程线条 列表
     *
     * @param processLineDetail 流程线条 信息
     * @return 流程线条 集合
     */
    @Override
    public List<ProcessLineDetail> selectProcessLineDetailList(ProcessLineDetail processLineDetail) {
        return processLineDetailMapper.selectProcessLineDetailList(processLineDetail);
    }

    /**
     * 新增流程线条
     *
     * @param processLineDetail 流程线条 信息
     * @return 结果
     */
    @Override
    public int insertProcessLineDetail(ProcessLineDetail processLineDetail) {
        return processLineDetailMapper.insertProcessLineDetail(processLineDetail);
    }

    /**
     * 修改流程线条
     *
     * @param processLineDetail 流程线条 信息
     * @return 结果
     */
    @Override
    public int updateProcessLineDetail(ProcessLineDetail processLineDetail) {
        return processLineDetailMapper.updateProcessLineDetail(processLineDetail);
    }

    /**
     * 删除流程线条 对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProcessLineDetailByIds(String ids) {
        return processLineDetailMapper.deleteProcessLineDetailByIds(Convert.toStrArray(ids));
    }

}
