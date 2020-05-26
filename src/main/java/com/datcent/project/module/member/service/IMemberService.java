package com.datcent.project.module.member.service;

import com.datcent.project.module.member.domain.Member;
import java.util.List;

/**
 * 家庭成员情况 服务层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface IMemberService 
{
	/**
     * 查询家庭成员情况信息
     * 
     * @param id 家庭成员情况ID
     * @return 家庭成员情况信息
     */
	public Member selectMemberById(String id);
	
	/**
     * 查询家庭成员情况列表
     * 
     * @param member 家庭成员情况信息
     * @return 家庭成员情况集合
     */
	public List<Member> selectMemberList(Member member);
	
	/**
     * 新增家庭成员情况
     * 
     * @param member 家庭成员情况信息
     * @return 结果
     */
	public int insertMember(Member member);
	
	/**
     * 修改家庭成员情况
     * 
     * @param member 家庭成员情况信息
     * @return 结果
     */
	public int updateMember(Member member);
		
	/**
     * 删除家庭成员情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMemberByIds(String ids);
	
}
