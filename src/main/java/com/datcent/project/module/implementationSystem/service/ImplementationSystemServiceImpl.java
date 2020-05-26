package com.datcent.project.module.implementationSystem.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.implementationSystem.mapper.ImplementationSystemMapper;
import com.datcent.project.module.implementationSystem.domain.ImplementationSystem;
import com.datcent.project.module.implementationSystem.service.IImplementationSystemService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 落实规章制度情况 服务层实现
 *
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class ImplementationSystemServiceImpl implements IImplementationSystemService {
    @Autowired
    private ImplementationSystemMapper implementationSystemMapper;

    /**
     * 查询落实规章制度情况信息
     *
     * @param lsgzzdqkId 落实规章制度情况ID
     * @return 落实规章制度情况信息
     */
    @Override
    public ImplementationSystem selectImplementationSystemById(String lsgzzdqkId) {
        return implementationSystemMapper.selectImplementationSystemById(lsgzzdqkId);
    }

    /**
     * 查询落实规章制度情况列表
     *
     * @param implementationSystem 落实规章制度情况信息
     * @return 落实规章制度情况集合
     */
    @Override
    public List<ImplementationSystem> selectImplementationSystemList(ImplementationSystem implementationSystem) {
        return implementationSystemMapper.selectImplementationSystemList(implementationSystem);
    }

    /**
     * 新增落实规章制度情况
     *
     * @param implementationSystem 落实规章制度情况信息
     * @return 结果
     */
    @Override
    public int insertImplementationSystem(ImplementationSystem implementationSystem) {
         return implementationSystemMapper.insertImplementationSystem(implementationSystem);
    }

    /**
     * 修改落实规章制度情况
     *
     * @param implementationSystem 落实规章制度情况信息
     * @return 结果
     */
    @Override
    public int updateImplementationSystem(ImplementationSystem implementationSystem) {
        return implementationSystemMapper.updateImplementationSystem(implementationSystem);
    }

    /**
     * 删除落实规章制度情况对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteImplementationSystemByIds(String ids) {
        return implementationSystemMapper.deleteImplementationSystemByIds(Convert.toStrArray(ids));
    }

}
