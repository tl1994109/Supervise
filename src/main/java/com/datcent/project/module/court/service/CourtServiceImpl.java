package com.datcent.project.module.court.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.project.module.court.mapper.CourtMapper;
import com.datcent.project.module.court.domain.Court;
import com.datcent.project.module.court.service.ICourtService;
import com.datcent.common.constant.UserConstants;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.security.ShiroUtils;

/**
 * 法院  服务层实现
 * 
 * @author datcent
 * @date 2018-11-06
 */
@Service
public class CourtServiceImpl implements ICourtService 
{
	@Autowired
	private CourtMapper courtMapper;

	/**
     * 查询法院 信息
     * 
     * @param courtId 法院 ID
     * @return 法院 信息
     */
    @Override
	public Court selectCourtById(Integer courtId)
	{
	    return courtMapper.selectCourtById(courtId);
	}
	
	/**
     * 查询法院 列表
     * 
     * @param court 法院 信息
     * @return 法院 集合
     */
	@Override
	public List<Court> selectCourtList(Court court)
	{
	    return courtMapper.selectCourtList(court);
	}
	
    /**
     * 新增法院 
     * 
     * @param court 法院 信息
     * @return 结果
     */
	@Override
	public int insertCourt(Court court)
	{
		court.setCreateBy(ShiroUtils.getUserId().toString());
	    return courtMapper.insertCourt(court);
	}
	
	/**
     * 修改法院 
     * 
     * @param court 法院 信息
     * @return 结果
     */
	@Override
	public int updateCourt(Court court)
	{
		court.setUpdateBy(ShiroUtils.getUserId().toString());
	    return courtMapper.updateCourt(court);
	}

	/**
     * 删除法院 对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourtByIds(String ids)
	{
		return courtMapper.deleteCourtByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 校验法院名称是否唯一
     * 
     * @param courtName 用户名
     * @return
     */
    @Override
    public String checkCourtNameUnique(String courtName)
    {
        int count = courtMapper.checkCourtNameUnique(courtName);
        if (count > 0)
        {
            return UserConstants.COURT_NAME_NOT_UNIQUE;
        }
        return UserConstants.COURT_NAME_UNIQUE;
    }
	
}
