package com.datcent.project.module.datashareFollow.service;

import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import java.util.List;

/**
 * 各法院查看权 服务层
 * 
 * @author datcent
 * @date 2019-05-27
 */
public interface IDatashareFollowService 
{
	/**
     * 查询各法院查看权信息
     * 
     * @param iD 各法院查看权ID
     * @return 各法院查看权信息
     */
	public DatashareFollow selectDatashareFollowById(Integer iD);
	
	/**
     * 查询各法院查看权列表
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 各法院查看权集合
     */
	public List<DatashareFollow> selectDatashareFollowList(DatashareFollow datashareFollow);
	
	/**
     * 新增各法院查看权
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 结果
     */
	public int insertDatashareFollow(DatashareFollow datashareFollow);
	
	/**
     * 修改各法院查看权
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 结果
     */
	public int updateDatashareFollow(DatashareFollow datashareFollow);
		
	/**
     * 删除各法院查看权信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDatashareFollowByIds(String ids);
	
}
