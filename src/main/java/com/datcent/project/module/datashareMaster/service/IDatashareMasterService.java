package com.datcent.project.module.datashareMaster.service;

import com.datcent.project.module.datashareMaster.domain.DatashareMaster;
import java.util.List;

/**
 * 各法院数据权限 服务层
 * 
 * @author datcent
 * @date 2019-05-27
 */
public interface IDatashareMasterService 
{
	/**
     * 查询各法院数据权限信息
     * 
     * @param datashareId 各法院数据权限ID
     * @return 各法院数据权限信息
     */
	public DatashareMaster selectDatashareMasterById(Integer datashareId);
	
	/**
     * 查询各法院数据权限列表
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 各法院数据权限集合
     */
	public List<DatashareMaster> selectDatashareMasterList(DatashareMaster datashareMaster);
	
	/**
     * 新增各法院数据权限
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 结果
     */
	public int insertDatashareMaster(DatashareMaster datashareMaster);
	
	/**
     * 修改各法院数据权限
     * 
     * @param datashareMaster 各法院数据权限信息
     * @return 结果
     */
	public int updateDatashareMaster(DatashareMaster datashareMaster);
		
	/**
     * 删除各法院数据权限信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDatashareMasterByIds(String ids);
	
}
