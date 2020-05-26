package com.datcent.project.module.usertestinfo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.usertestinfo.mapper.UsertestinfoMapper;
import com.datcent.project.module.usertestinfo.domain.Usertestinfo;
import com.datcent.project.module.usertestinfo.service.IUsertestinfoService;
import com.datcent.common.support.Convert;

/**
 * 测试 服务层实现
 * 
 * @author datcent
 * @date 2018-11-26
 */
@Service
public class UsertestinfoServiceImpl implements IUsertestinfoService 
{
	@Autowired
	private UsertestinfoMapper usertestinfoMapper;

	/**
     * 查询测试信息
     * 
     * @param id 测试ID
     * @return 测试信息
     */
    @Override
	public Usertestinfo selectUsertestinfoById(Integer id)
	{
	    return usertestinfoMapper.selectUsertestinfoById(id);
	}
	
	/**
     * 查询测试列表
     * 
     * @param usertestinfo 测试信息
     * @return 测试集合
     */
	@Override
	public List<Usertestinfo> selectUsertestinfoList(Usertestinfo usertestinfo)
	{
	    return usertestinfoMapper.selectUsertestinfoList(usertestinfo);
	}
	
    /**
     * 新增测试
     * 
     * @param usertestinfo 测试信息
     * @return 结果
     */
	@Override
	public int insertUsertestinfo(Usertestinfo usertestinfo)
	{
	    return usertestinfoMapper.insertUsertestinfo(usertestinfo);
	}
	
	/**
     * 修改测试
     * 
     * @param usertestinfo 测试信息
     * @return 结果
     */
	@Override
	public int updateUsertestinfo(Usertestinfo usertestinfo)
	{
	    return usertestinfoMapper.updateUsertestinfo(usertestinfo);
	}

	/**
     * 删除测试对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUsertestinfoByIds(String ids)
	{
		return usertestinfoMapper.deleteUsertestinfoByIds(Convert.toStrArray(ids));
	}
	
}
