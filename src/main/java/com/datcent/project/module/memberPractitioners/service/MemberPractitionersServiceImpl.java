package com.datcent.project.module.memberPractitioners.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.memberPractitioners.mapper.MemberPractitionersMapper;
import com.datcent.project.module.memberPractitioners.domain.MemberPractitioners;
import com.datcent.project.module.memberPractitioners.service.IMemberPractitionersService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 配偶子女从业情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class MemberPractitionersServiceImpl implements IMemberPractitionersService 
{
	@Autowired
	private MemberPractitionersMapper memberPractitionersMapper;

	/**
     * 查询配偶子女从业情况信息
     * 
     * @param pozncyqkId 配偶子女从业情况ID
     * @return 配偶子女从业情况信息
     */
    @Override
	public MemberPractitioners selectMemberPractitionersById(String pozncyqkId)
	{
	    return memberPractitionersMapper.selectMemberPractitionersById(pozncyqkId);
	}
	
	/**
     * 查询配偶子女从业情况列表
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 配偶子女从业情况集合
     */
	@Override
	public List<MemberPractitioners> selectMemberPractitionersList(MemberPractitioners memberPractitioners)
	{
	    return memberPractitionersMapper.selectMemberPractitionersList(memberPractitioners);
	}
	
    /**
     * 新增配偶子女从业情况
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 结果
     */
	@Override
	public int insertMemberPractitioners(MemberPractitioners memberPractitioners)
	{
	    return memberPractitionersMapper.insertMemberPractitioners(memberPractitioners);
	}
	
	/**
     * 修改配偶子女从业情况
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 结果
     */
	@Override
	public int updateMemberPractitioners(MemberPractitioners memberPractitioners)
	{
	    return memberPractitionersMapper.updateMemberPractitioners(memberPractitioners);
	}

	/**
     * 删除配偶子女从业情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMemberPractitionersByIds(String ids)
	{
		return memberPractitionersMapper.deleteMemberPractitionersByIds(Convert.toStrArray(ids));
	}
	
}
