package com.datcent.project.module.dubvioEvent.service;

import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 可疑违纪事件 服务层
 *
 * @author datcent
 * @date 2019-01-12
 */
public interface IDubvioEventService
{
	/**
     * 查询可疑违纪事件信息
     *
     * @param dubvioId 可疑违纪事件ID
     * @return 可疑违纪事件信息
     */
	public DubvioEvent selectDubvioEventById(String dubvioId);

	/**
     * 查询可疑违纪事件列表
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 可疑违纪事件集合
     */
	public List<DubvioEvent> selectDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain);

	public List<DubvioEvent> selectPassDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain);

	/**
	 * 查询可疑违纪事件列表
	 *
	 * @param dubvioEvent 可疑违纪事件信息
	 * @return 可疑违纪事件集合
	 */
	public List<DubvioEvent> selectSecondDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain);

	/**
     * 新增可疑违纪事件
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 结果
     */
	public int insertDubvioEvent(DubvioEvent dubvioEvent);

	/**
     * 修改可疑违纪事件
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 结果
     */
	public int updateDubvioEvent(DubvioEvent dubvioEvent);

	/**
     * 删除可疑违纪事件信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDubvioEventByIds(String ids);

	/**
	 * 根据名字查询人员相关的案号和案件编号
	 * @param name
	 * @return
	 */
	public List<Map> queyAhAndAjbhByAjbh(String name);

	/**
	 * 查出所有的案号
	 * @return
	 */
	public List<String> queryAllAh();



	/**
	 * 查询可疑违纪事件列表
	 *
	 * @return 可疑违纪事件集合
	 */
	public List<DubvioEvent> selectList(DubvioEvent dubvioEven);

	/**
	 * 根据用户id查出 用户所在部门
	 * @param userId
	 * @return
	 */
	public String queryDeptNameByUserId(String userId);

	/**
	 * 统计各案件类别各策略的数量
	 * @return
	 */
	public List<Map<String,Object>> queryEventAjlbCountByStrategyName(DubvioEvent dubvioEvent);


	/**
	 * 根据案件类别 和 策略id来查询线索
	 * @param ajlb
	 * @param id
	 * @return
	 */
	public List<Map> selectEventListByAjlbAndStrategyId(@Param("ajlb") String ajlb, @Param("id") String id);


	/**
	 * 根据 策略id来查询线索
	 * @param id
	 * @return
	 */
	public List<Map> selectEventListByStrategyId(@Param("id") String id);


	/**
	 * 根据反映人编号  统计反映人情况
	 * @return
	 */
	public List<Map<String,Object>> queryEventFyrInfoByFyy(@Param("deptId") String deptId );


	public String queryEventFycs(String id);

	public String importDubvioEvent(List<DubvioEvent> dubvioEventList);

	/**
	 * 查询被反映人数据统计
	 * @param dubvioEvent 可疑违纪事件
	 * @return
	 */
	public List<Map<String,Object>> querySuspectsByManData(DubvioEvent dubvioEvent);
}
