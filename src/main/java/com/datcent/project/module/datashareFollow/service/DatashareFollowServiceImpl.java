package com.datcent.project.module.datashareFollow.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.datashareFollow.mapper.DatashareFollowMapper;
import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import com.datcent.project.module.datashareFollow.service.IDatashareFollowService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 各法院查看权 服务层实现
 * 
 * @author datcent
 * @date 2019-05-27
 */
@Service
public class DatashareFollowServiceImpl implements IDatashareFollowService 
{
	@Autowired
	private DatashareFollowMapper datashareFollowMapper;

	/**
     * 查询各法院查看权信息
     * 
     * @param iD 各法院查看权ID
     * @return 各法院查看权信息
     */
    @Override
	public DatashareFollow selectDatashareFollowById(Integer iD)
	{
	    return datashareFollowMapper.selectDatashareFollowById(iD);
	}
	
	/**
     * 查询各法院查看权列表
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 各法院查看权集合
     */
	@Override
	public List<DatashareFollow> selectDatashareFollowList(DatashareFollow datashareFollow)
	{
	    return datashareFollowMapper.selectDatashareFollowList(datashareFollow);
	}
	
    /**
     * 新增各法院查看权
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 结果
     */
	@Override
	public int insertDatashareFollow(DatashareFollow datashareFollow)
	{
		datashareFollow.setCreateBy(ShiroUtils.getUser().getUserName());
	    return datashareFollowMapper.insertDatashareFollow(datashareFollow);
	}
	
	/**
     * 修改各法院查看权
     * 
     * @param datashareFollow 各法院查看权信息
     * @return 结果
     */
	@Override
	public int updateDatashareFollow(DatashareFollow datashareFollow)
	{
		datashareFollow.setUpdateBy(ShiroUtils.getUser().getUserName());
		datashareFollow.setUpdateTime(new Date());
	    return datashareFollowMapper.updateDatashareFollow(datashareFollow);
	}

	/**
     * 删除各法院查看权对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDatashareFollowByIds(String ids)
	{
		return datashareFollowMapper.deleteDatashareFollowByIds(Convert.toStrArray(ids));
	}
	
}
