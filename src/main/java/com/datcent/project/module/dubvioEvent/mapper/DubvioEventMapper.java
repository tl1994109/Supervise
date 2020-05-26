package com.datcent.project.module.dubvioEvent.mapper;

import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 可疑违纪事件 数据层
 *
 * @author datcent
 * @date 2019-01-12
 */
public interface DubvioEventMapper
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
	public List<DubvioEvent> selectDubvioEventList(DubvioEvent dubvioEvent);

	/**
	 * 查询可疑违纪事件列表
	 *
	 * @param dubvioEvent 可疑违纪事件信息
	 * @return 可疑违纪事件集合
	 */
	public List<DubvioEvent> selectNewDubvioEventList( DubvioEvent dubvioEvent);
	public List<DubvioEvent> selectPassDubvioEventList( DubvioEvent dubvioEvent);

	/**
	 * 查询可疑违纪事件列表
	 *
	 * @param dubvioEvent 可疑违纪事件信息
	 * @return 可疑违纪事件集合
	 */
	public List<DubvioEvent> selectSecondDubvioEventList(DubvioEvent dubvioEvent);

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
     * 删除可疑违纪事件
     *
     * @param dubvioId 可疑违纪事件ID
     * @return 结果
     */
	public int deleteDubvioEventById(String dubvioId);

	/**
     * 批量删除可疑违纪事件
     *
     * @param dubvioIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteDubvioEventByIds(String[] dubvioIds);

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
	public List<Map> selectEventListByAjlbAndStrategyId(@Param("ajlb") String ajlb,@Param("id") String id);


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

	/**
	 *统计被反映人 反映次数
	 * @return
	 */
	public String queryEventFycs(String id);

	/**
	 *根据不同的维度条件查询案件列表
	 * @return
	 */
	public List<DubvioEvent> getDimList(DubvioEvent dubvioEvent);

	/**
	 * 查询被反映人数据统计
	 * @param dubvioEvent 可疑违纪事件
	 * @return
	 */
	public List<Map<String,Object>> querySuspectsByManData(DubvioEvent dubvioEvent);

	/**
	 * 可疑点线索统计
	 * @param dubvioEvent
	 * @return
	 */
	public List<Map<String,Object>> getSuspiciousCluesCount(DubvioEvent dubvioEvent);

	/**
	 * 可疑点线索详情
	 * @author zhang date:2019/12/10
	 * @return
	 */
	public List<Map> getClueDetailList(String name, String id, List jbfyIdList);

	/**
	 * 月度线索统计
	 * @author zhang date:2019/12/11
	 * @return
	 */
	public List<Map> getClueCountByMonth(List jbfyIdList);

	/**
	 * 根据日期查询月度线索详情
	 * @author zhang date:2019/12/11
	 * @return
	 */
	public List<Map> getClueDetailListByDate(String name, String clueTime, List jbfyIdList);

	/**
	 * 根据月，获取线索来源统计
	 * @author zhang date:2019/12/12
	 * @return
	 */
	public List<Map> getAllClueSourceByMonth(List jbfyIdList);

	/**
	 * 获取被反映人统计（线索：筛查部分）
	 * @retun
	 * @author zhang
	 * @date 2019-12-13
	 */
	public List<Map> getReflectedPeopleCountList(@Param("deptId") String deptId);

	/**
	 * 承办庭可疑点统计
	 * @author zhang
	 * @date 2019-12-13
	 */
	public List<Map<String,Object>> getSuspectsCountByTribunalList(DubvioEvent dubvioEvent);

	/**
	 * 获取承办庭可疑点详情列表
	 * @author zhang
	 * @date 2019-12-18
	 */
	public List<Map<String,Object>> getSuspectsDetailByCourtId(List jbfyIdList, String id);

	/**
	 * 获取月度可疑点统计
	 * @author zhang
	 * @date 2019-12-18
	 */
	public List<Map> getAllSuspectsCountByMonth(String startDate, String endDate, List jbfyIdList);

	/**
	 * 获取月度可疑点详细
	 * @author zhang
	 * @date 2019-12-19
	 */
	public List<Map<String,Object>> getSuspectsDetailByMonth(String suspectsTime, String caseTypeName, List jbfyIdList);

    /**
     * 院可疑点统计
     * @author zhang
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsCountByCourt(DubvioEvent dubvioEvent);

    /**
     * 获取院可疑点详细
     * @author zhagn
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsDetailByCourtIdAndName(String id, String name, List jbfyIdList);

	/**
	 * 院-承办庭可疑点统计数据接口
	 * @param id 法院ID
	 * @author zhang
	 * @date 2019-12-30
	 */
	public List<Map<String, Object>> getTribunalSuspectsCountByCourtId(String id);

	/**
	 * 获取可疑点名称列表
	 * @author zhang
	 * @date 2019-12-31
	 */
	public List<Map> getSuspectsNameList();

}
