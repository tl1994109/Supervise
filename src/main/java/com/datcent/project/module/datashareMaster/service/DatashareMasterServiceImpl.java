package com.datcent.project.module.datashareMaster.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.datashareMaster.mapper.DatashareMasterMapper;
import com.datcent.project.module.datashareMaster.domain.DatashareMaster;
import com.datcent.project.module.datashareMaster.service.IDatashareMasterService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 各法院数据权限 服务层实现
 * 
 * @author datcent
 * @date 2019-05-27
 */
@Service
public class DatashareMasterServiceImpl implements IDatashareMasterService 
{
	@Autowired
	private DatashareMasterMapper datashareMasterMapper;

	/**
     * 查询各法院数据权限信息
     * 
     * @param datashareId 各法院数据权限ID
     * @return 各法院数据权限信息
     */
    @Override
	public DatashareMaster selectDatashareMasterById(Integer datashareId)
	{
	    return datashareMasterMapper.selectDatashareMasterById(datashareId);
	}
	
	/**
     * 查询各法院数据权限列表
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 各法院数据权限集合
     */
	@Override
	public List<DatashareMaster> selectDatashareMasterList(DatashareMaster datashareMaster)
	{
	    return datashareMasterMapper.selectDatashareMasterList(datashareMaster);
	}
	
    /**
     * 新增各法院数据权限
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 结果
     */
	@Override
	public int insertDatashareMaster(DatashareMaster datashareMaster)
	{
		datashareMaster.setCreateBy(ShiroUtils.getUser().getUserName());
	    return datashareMasterMapper.insertDatashareMaster(datashareMaster);
	}
	
	/**
     * 修改各法院数据权限
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 结果
     */
	@Override
	public int updateDatashareMaster(DatashareMaster datashareMaster)
	{
		datashareMaster.setUpdateBy(ShiroUtils.getUser().getUserName());
		datashareMaster.setUpdateTime(new Date());
	    return datashareMasterMapper.updateDatashareMaster(datashareMaster);
	}

	/**
     * 删除各法院数据权限对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDatashareMasterByIds(String ids)
	{
		return datashareMasterMapper.deleteDatashareMasterByIds(Convert.toStrArray(ids));
	}
	
}
