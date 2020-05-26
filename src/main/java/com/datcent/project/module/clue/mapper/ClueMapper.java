package com.datcent.project.module.clue.mapper;

import com.datcent.project.module.clue.domain.Clue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 线索  数据层
 * 
 * @author datcent
 * @date 2019-01-09
 */
@Repository
public interface ClueMapper 
{
	/**
     * 查询线索 信息
     * 
     * @param clueId 线索 ID
     * @return 线索 信息
     */
	public Clue selectClueById(String clueId);
	
	/**
     * 查询线索 列表
     * 
     * @param clue 线索 信息
     * @return 线索 集合
     */
	public List<Clue> selectClueList(Clue clue);


	public List<Clue> selectSecondClueList(Clue clue);
	/**
     * 新增线索 
     * 
     * @param clue 线索 信息
     * @return 结果
     */
	public int insertClue(Clue clue);
	
	/**
     * 修改线索 
     * 
     * @param clue 线索 信息
     * @return 结果
     */
	public int updateClue(Clue clue);
	
	/**
     * 删除线索 
     * 
     * @param clueId 线索 ID
     * @return 结果
     */
	public int deleteClueById(String clueId);
	
	/**
     * 批量删除线索 
     * 
     * @param clueIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteClueByIds(String[] clueIds);


	/**
	 * 统计本年度线索数量
	 * @return
	 */
	public int queryAllCount(@Param("deptId")String deptId);

	/**
	 * 统计本月度线索数量
	 * @return
	 */
	public int queryAllCountYear(@Param("deptId")String deptId);


	/**
	 * 查询处理线索数量
	 * @return
	 */
	public int queryCountDeal(@Param("deptId")String deptId);

	/**
	 * 查询信访录入线索数量
	 * @return
	 */
	public int queryCountXf(@Param("deptId")String deptId);

	/**
	 * 按案件类别统计线索数量
	 * @return
	 */
	public List<Map<String,Object>> queryCountByAjlb(@Param("deptId")String deptId);

	/**
	 * 查询所有案件类别
	 * @return
	 */
	public List<Map<String,Object>> queryAllAjlb(@Param("deptId")String deptId);


	/**
	 * 按案件类别和承办庭统计线索数量
	 * @return
	 */
	public List<Map<String,Object>> queryCountByAjlbAndCbt(@Param("deptId")String deptId);


	/**
	 * 根据案件类别统计承办庭
	 * @return
	 */
	public List<Map<String,Object>> queryCbt(String ajlb);

	/**
	 * 根据日期统计各案件来源的线索数量
	 * @param time
	 * @return
	 */
	public  int queryCountBySourceAndMonth(@Param("deptId")String deptId,@Param("time")String time,@Param("source") String source);


	/**
	 * 根据日期统计各案件类别的线索数量
	 * @param time
	 * @return
	 */
	public  int queryCountByAjlbAndMonth(@Param("deptId")String deptId,@Param("time")String time,@Param("ajlb") String ajlb);

	/**
	 * 根据风险等级统计各个案件类别的数量
	 * @param riskLevel
	 * @return
	 */
	public int queryCountByRiskLevel(@Param("deptId")String deptId,@Param("riskLevel")String riskLevel,@Param("ajlb") String ajlb);

	/**
	 * 查出所有风险等级
	 * @return
	 */
	public List<String> queryAllRiskLevel(@Param("deptId")String deptId);

	/**
	 * 统计南阳市各个法院的线索数量
	 * @return
	 */
	public List<Map<String,Object>> queryAllCountByFy(@Param("deptId")String deptId);

	/**
	 * 统计南阳市各个法院的处理过线索数量
	 * @return
	 */
	public int queryDealCountByFy();

	/**
	 * 统计南阳市各个法院的当前年度新增线索数量
	 * @return
	 */
	public int queryYearAddCountByFy();


	/**
	 * 根据案件类别查询线索所有信息（分页）
	 * @return
	 */
	public List<Map<String,Object>> queryAllByAjlbAndPage(@Param("ajlb")String ajlb,@Param("pageNumber")int pageNumber,@Param("pageSize")int pageSize);

	/**
	 * 统计各案件类别  可疑违规线索人员数量
	 * @return
	 */
	public List<Map<String,Object>> queyCbrCountByAjlb(@Param("deptId")String deptId);

	/**
	 * 统计各个年龄阶段相关责任人员数量
	 * @return
	 */
	public int queryCountByCardId(@Param("deptId")String deptId,@Param("startAge") int startAge,@Param("endAge") int endAge);

	/**
	 * 统计60以上年龄阶段相关责任人员数量
	 * @return
	 */
	public int queryMaxCountByCardId(@Param("deptId") String deptId,@Param("startAge") int startAge);


	/**
	 * 统计当前时间前半年不同案件来源已处理的线索数量
	 * @param time
	 * @param source
	 * @return
	 */
	public  int queryDealCountBySourceAndMonth(@Param("deptId")String deptId,@Param("time")String time,@Param("source") String source);


	/**
	 * 统计所有的策略的名称
	 * @return
	 */
	public List<Map<String,Object>> queryStrategyName( );

    /**
     * 统计各案件类别各策略的数量
     * @return
     */
	public List<Map<String,Object>> queryAjlbCountByStrategyName();

	/**
	 * 查看所有承办庭
	 * @return
	 */
	public List<Map> queryAllCbt();

	/**
	 * 根据部门（人）名字查询出组织（人员）id
	 * @return
	 */
	public String queryCidByDeptName(String name);



	/**
	 * 根据部门（人）名字查询出组织（人员）id
	 * @return
	 */
	public List<Map> queryCidByDeptNames(String name);

	/**
	 * 根据组织（人员）id查询出部门（人）名字
	 * @return
	 */
	public String queryDeptNameByCid(String id);

	/**
	 * 根据案件类别 和 策略id来查询线索
	 * @param ajlb
	 * @param id
	 * @return
	 */
	public List<Map> selectClueListByAjlbAndStrategyId(@Param("ajlb") String ajlb,@Param("id") String id);


	/**
	 * 根据 策略id来查询线索
	 * @param id
	 * @return
	 */
	public List<Map> selectClueListByStrategyId(@Param("id") String id);

	/**selectClueListByCbtId
	 * 根据 承办庭id来查询线索
	 * @param id
	 * @return
	 */
	public List<Map> selectClueListByCbtId(@Param("id") String id,@Param("deptId") String deptId);

	/**
	 * 根据日期统计各案件类别的数量
	 * @param
	 * @return
	 */
	public List<Map> queryCountByMonth();

	/**
	 * 根据案件类别 和 日期来查询线索
	 * @param ajlb
	 * @param time
	 * @return
	 */
	public List<Map> queryClueByAjlbAndTime(@Param("ajlb") String ajlb,@Param("time") String time);

	/**
	 * 根据日期来查询线索
	 * @param time
	 * @return
	 */
	public List<Map> queryClueByTime(String time);


	/**
	 * 根据日期来统计各线索来源数量
	 * @param
	 * @return
	 */
	public List<Map> queryAllCourceByDate( );


	/**
	 * 根据日期统计各线索来源详情
	 * @return
	 */
	public List<Map> querySourceByDate(@Param("source") String source,@Param("time") String time);



	/**
	 * 根据日期统计各线索来源处理详情
	 * @return
	 */
	public List<Map> querySourceDealByDate(@Param("source") String source,@Param("time") String time);

	/**
	 * 统计完成和处置中的线索总量
	 * @param status
	 * @return
	 */
	public int queryEveCount(@Param("deptId")String deptId,@Param("status") Integer status);

	/**
	 * 统计处置线索总量
	 * @return
	 */
	public int queryAllCounts(@Param("deptId")String deptId);

	/**
	 * 统计完成各阶段线索数量
	 * @return
	 */
	public List<Map> queryFinishCzjg(@Param("deptId")String deptId);

	/**
	 * 统计完成澄清线索数量
	 * @return
	 */
	public int queryCqCount(@Param("deptId") String deptId,@Param("cbtName") String cbtName);

	/**
	 * 统计完成问题线索数量
	 * @return
	 */
	public int queryQuestionCount(@Param("deptId")String deptId,@Param("cbtName") String cbtName);


	/**
	 * 根据日期统计处置中各阶段的线索数量
	 * @param time
	 * @return
	 */
	public  int queryCzzCountByMonth(@Param("deptId")String deptId,@Param("time")String time,@Param("czjg") String czjg);


	/**
	 * 统计已处置各个年龄阶段和各个案件类别相关责任人员数量
	 * @return
	 */
	public int queryCzzCountByAge(@Param("deptId")String deptId,@Param("ajlb") String ajlb,@Param("startAge") int startAge,@Param("endAge") int endAge);

	/**
	 * 统计已处置60以上年龄阶段和各个案件类别相关责任人员数量
	 * @return
	 */
	public int queryMaxCountByAge(@Param("deptId")String deptId,@Param("ajlb") String ajlb,@Param("startAge") int startAge);

	/**
	 * 根据风险等级和时间统计完成线索数量
	 * @param time
	 * @param riskLevel
	 * @return
	 */
	public int queryFinishRiskCount(@Param("deptId")String deptId,@Param("time")String time,@Param("riskLevel") String riskLevel);

	/**
	 * 据案件类别和部门统计完成线索数量
	 * @return
	 */
	public int queryFinishClueByDeptAndAjlb(@Param("deptId")String deptId,@Param("cbtName")String cbtName,@Param("ajlb") String ajlb);

	/**
	 * 据案件类别和部门统计线索数量
	 * @return
	 */
	public int queryRiskCountByMonth(@Param("deptId")String deptId,@Param("time")String time,@Param("riskLevel") String riskLevel);


	/**
	 * 根据反映人编号  统计反映人情况
	 * @return
	 */
	public List<Map<String,Object>> queryFyrInfoByFyy(@Param("deptId") String deptId );

	/**
	 *统计被反映人 反映次数
	 * @return
	 */
	public String queryFycs(String id);

	/**
	 * 根据人员id查询部门
	 * @param id
	 * @return
	 */
	public String queryDeptByRyId(String id);

	/**
	 * 根据人员id统计年龄
	 * @param id
	 * @return
	 */
	public String queryAgeById(String id);

	/**
	 * 统计各法院的线索处置结果
	 * @return
	 */
	public List<Map<String,Object>> queryCzjgByFyIds(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("deptId") String deptId);


	/**
	 * 统计各个法院线索新收数量
	 * @param fyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */

	public String queryXsByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource );

	/**
	 * 统计各个法院线索旧存数量
	 * @param fyId
	 * @param time
	 * @return
	 */
	public String queryJcByFyId(@Param("fyId") String fyId,@Param("time") String time,@Param("clueSource") String clueSource);


	/**
	 * 统计各个法院线索已结数量
	 * @param fyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String queryYjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource);

	public String queryWjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource);

	/**
	 * 统计各法院各承办庭的线索处置结果
	 * @return
	 */
	public List<Map<String,Object>> queryCbtCzjgByCbtIds(@Param("id") String id,@Param("startTime") String startTime,@Param("endTime") String endTime);


    /**
     * 统计各个法院线索新收数量
     * @param fyId
     * @param startTime
     * @param endTime
     * @return
     */
    public String queryXsByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime );

    /**
     * 统计各个法院线索旧存数量
     * @param fyId
     * @param time
     * @return
     */
    public String queryJcByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("time") String time);


    /**
     * 统计各个法院线索已结数量
     * @param fyId
     * @param startTime
     * @param endTime
     * @return
     */
    public String queryYjByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    public String queryWjByCbtId(@Param("cbtId") String cbtId, @Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime);



    public List<Map<String,Object>> queryXfYjByFyId(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("deptId") String deptId);


    public String queryXfWjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime);



    public   List<Map> queryJcByFyIdAll(@Param("fyId") String fyId,@Param("time") String time,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId);

	public List<Map> queryYjByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId);

	public List<Map> queryWjByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId);

	public List<Map> queryXsByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId);


	public List<Map> queryAllCzjgByFyId(@Param("czjg") String czjg,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("fyId") String fyId,@Param("jbxxCbtId") String jbxxCbtId);


}