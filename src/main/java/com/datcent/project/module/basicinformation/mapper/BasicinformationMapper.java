package com.datcent.project.module.basicinformation.mapper;

import com.datcent.project.module.basicinformation.domain.Basicinformation;
import java.util.List;	

/**
 * 基本情况登记 数据层
 * 
 * @author datcent
 * @date 2019-03-29
 */
public interface BasicinformationMapper 
{
	/**
     * 查询基本情况登记信息
     * 
     * @param jbqkId 基本情况登记ID
     * @return 基本情况登记信息
     */
	public Basicinformation selectBasicinformationById(String jbqkId);
	
	/**
     * 查询基本情况登记列表
     * 
     * @param basicinformation 基本情况登记信息
     * @return 基本情况登记集合
     */
	public List<Basicinformation> selectBasicinformationList(Basicinformation basicinformation);
	
	/**
     * 新增基本情况登记
     * 
     * @param basicinformation 基本情况登记信息
     * @return 结果
     */
	public int insertBasicinformation(Basicinformation basicinformation);
	
	/**
     * 修改基本情况登记
     * 
     * @param basicinformation 基本情况登记信息
     * @return 结果
     */
	public int updateBasicinformation(Basicinformation basicinformation);
	
	/**
     * 删除基本情况登记
     * 
     * @param jbqkId 基本情况登记ID
     * @return 结果
     */
	public int deleteBasicinformationById(String jbqkId);
	
	/**
     * 批量删除基本情况登记
     * 
     * @param jbqkIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteBasicinformationByIds(String[] jbqkIds);

	public Integer selectBasicinformationByCardNo(String cardNo);

	public List<Basicinformation> selectBasicinformationByApproveAndCardNo(Basicinformation basicinformation);
}