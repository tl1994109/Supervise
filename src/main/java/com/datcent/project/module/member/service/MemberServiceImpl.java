package com.datcent.project.module.member.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.member.mapper.MemberMapper;
import com.datcent.project.module.member.domain.Member;
import com.datcent.project.module.member.service.IMemberService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 家庭成员情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class MemberServiceImpl implements IMemberService 
{
	@Autowired
	private MemberMapper memberMapper;

	/**
     * 查询家庭成员情况信息
     * 
     * @param id 家庭成员情况ID
     * @return 家庭成员情况信息
     */
    @Override
	public Member selectMemberById(String id)
	{
	    return memberMapper.selectMemberById(id);
	}
	
	/**
     * 查询家庭成员情况列表
     * 
     * @param member 家庭成员情况信息
     * @return 家庭成员情况集合
     */
	@Override
	public List<Member> selectMemberList(Member member)
	{
	    return memberMapper.selectMemberList(member);
	}
	
    /**
     * 新增家庭成员情况
     * 
     * @param member 家庭成员情况信息
     * @return 结果
     */
	@Override
	public int insertMember(Member member)
	{
	    return memberMapper.insertMember(member);
	}
	
	/**
     * 修改家庭成员情况
     * 
     * @param member 家庭成员情况信息
     * @return 结果
     */
	@Override
	public int updateMember(Member member)
	{
	    return memberMapper.updateMember(member);
	}

	/**
     * 删除家庭成员情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMemberByIds(String ids)
	{
		return memberMapper.deleteMemberByIds(Convert.toStrArray(ids));
	}
	
}
