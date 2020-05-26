package com.datcent.project.module.subgroup.service;

import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.subgroup.domain.Subgroup;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 组件管理 服务层
 * 
 * @author datcent
 * @date 2018-12-06
 */
public interface ISubgroupService 
{
	/**
     * 查询组件管理信息
     * 
     * @param subgroupId 组件管理ID
     * @return 组件管理信息
     */
	public Subgroup selectSubgroupById(String subgroupId);
	
	/**
     * 查询组件管理列表
     * 
     * @param subgroup 组件管理信息
     * @return 组件管理集合
     */
	public List<Subgroup> selectSubgroupList(Subgroup subgroup);
	
	/**
     * 新增组件管理
     * 
     * @param subgroup 组件管理信息
     * @return 结果
     */
	public int insertSubgroup(Subgroup subgroup);
	
	/**
     * 修改组件管理
     * 
     * @param subgroup 组件管理信息
     * @return 结果
     */
	public int updateSubgroup(Subgroup subgroup);
		
	/**
     * 删除组件管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSubgroupByIds(String ids);

	/**
	 * @Author: 张梦圆
	 * @Email: zhangmengyuan@datcent.com
	 * @CreateDate: 2018/12/7 14:02
	 * @Copyright: © 2018 德讯科技 版权所有
	 * @param:  Subgroup
	 * @return: List<Subgroup>
	 * @exception:
	 * @Description: TODO
	 **/
	public List<Subgroup> selectSubgroup_information(Subgroup subgroup);

	public int addTableColumnAndSubgroup(String head_json,String contaniner_json);

	public int editTableColumnAndSubgroup(String jsonStr,String jsonTable);
}
