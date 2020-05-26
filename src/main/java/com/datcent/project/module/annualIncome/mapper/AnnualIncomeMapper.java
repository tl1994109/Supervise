package com.datcent.project.module.annualIncome.mapper;

import com.datcent.project.module.annualIncome.domain.AnnualIncome;
import java.util.List;	

/**
 * 家庭全年收入情况 数据层
 * 
 * @author datcent
 * @date 2019-04-03
 */
public interface AnnualIncomeMapper 
{
	/**
     * 查询家庭全年收入情况信息
     * 
     * @param id 家庭全年收入情况ID
     * @return 家庭全年收入情况信息
     */
	public AnnualIncome selectAnnualIncomeById(String id);
	
	/**
     * 查询家庭全年收入情况列表
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 家庭全年收入情况集合
     */
	public List<AnnualIncome> selectAnnualIncomeList(AnnualIncome annualIncome);
	
	/**
     * 新增家庭全年收入情况
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 结果
     */
	public int insertAnnualIncome(AnnualIncome annualIncome);
	
	/**
     * 修改家庭全年收入情况
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 结果
     */
	public int updateAnnualIncome(AnnualIncome annualIncome);
	
	/**
     * 删除家庭全年收入情况
     * 
     * @param id 家庭全年收入情况ID
     * @return 结果
     */
	public int deleteAnnualIncomeById(String id);
	
	/**
     * 批量删除家庭全年收入情况
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAnnualIncomeByIds(String[] ids);
	
}