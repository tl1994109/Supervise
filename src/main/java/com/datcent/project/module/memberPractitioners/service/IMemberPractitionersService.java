package com.datcent.project.module.memberPractitioners.service;

import com.datcent.project.module.memberPractitioners.domain.MemberPractitioners;
import java.util.List;

/**
 * 配偶子女从业情况 服务层
 * 
 * @author datcent
 * @date 2019-04-01
 */
public interface IMemberPractitionersService 
{
	/**
     * 查询配偶子女从业情况信息
     * 
     * @param pozncyqkId 配偶子女从业情况ID
     * @return 配偶子女从业情况信息
     */
	public MemberPractitioners selectMemberPractitionersById(String pozncyqkId);
	
	/**
     * 查询配偶子女从业情况列表
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 配偶子女从业情况集合
     */
	public List<MemberPractitioners> selectMemberPractitionersList(MemberPractitioners memberPractitioners);
	
	/**
     * 新增配偶子女从业情况
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 结果
     */
	public int insertMemberPractitioners(MemberPractitioners memberPractitioners);
	
	/**
     * 修改配偶子女从业情况
     * 
     * @param memberPractitioners 配偶子女从业情况信息
     * @return 结果
     */
	public int updateMemberPractitioners(MemberPractitioners memberPractitioners);
		
	/**
     * 删除配偶子女从业情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMemberPractitionersByIds(String ids);
	
}
