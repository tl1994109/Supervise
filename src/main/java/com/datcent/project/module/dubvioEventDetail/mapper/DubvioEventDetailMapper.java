package com.datcent.project.module.dubvioEventDetail.mapper;

import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import java.util.List;	

/**
 * 案件详细 数据层
 * 
 * @author datcent
 * @date 2019-01-16
 */
public interface DubvioEventDetailMapper 
{
	/**
     * 查询案件详细信息
     * 
     * @param jbxxAjbh 案件详细ID
     * @return 案件详细信息
     */
	public DubvioEventDetail selectDubvioEventDetailById(String jbxxAjbh);
	
	/**
     * 查询案件详细列表
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 案件详细集合
     */
	public List<DubvioEventDetail> selectDubvioEventDetailList(DubvioEventDetail dubvioEventDetail);
	
	/**
     * 新增案件详细
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 结果
     */
	public int insertDubvioEventDetail(DubvioEventDetail dubvioEventDetail);
	
	/**
     * 修改案件详细
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 结果
     */
	public int updateDubvioEventDetail(DubvioEventDetail dubvioEventDetail);
	
	/**
     * 删除案件详细
     * 
     * @param jbxxAjbh 案件详细ID
     * @return 结果
     */
	public int deleteDubvioEventDetailById(String jbxxAjbh);
	
	/**
     * 批量删除案件详细
     * 
     * @param jbxxAjbhs 需要删除的数据ID
     * @return 结果
     */
	public int deleteDubvioEventDetailByIds(String[] jbxxAjbhs);
	
}