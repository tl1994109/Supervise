package com.datcent.project.module.clue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import com.datcent.project.module.datashareFollow.service.IDatashareFollowService;
import com.datcent.project.module.information.domain.Information;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.clue.mapper.ClueMapper;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 线索  服务层实现
 *
 * @author datcent
 * @date 2019-01-09
 */
@Service
public class ClueServiceImpl implements IClueService
{
	@Autowired
	private ClueMapper clueMapper;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IUserService userService;


	@Autowired
	private IDatashareFollowService iDatashareFollowService;


	/**
     * 查询线索 信息
     *
     * @param clueId 线索 ID
     * @return 线索 信息
     */
    @Override
	public Clue selectClueById(String clueId)
	{
	    return clueMapper.selectClueById(clueId);
	}

	/**
     * 查询线索 列表
     *
     * @param clue 线索 信息
     * @return 线索 集合
     */
	@Override
	public List<Clue> selectClueList(Clue clue,PageDomain pageDomain)
	{
		// 用户的部门id
		String deptId = ShiroUtils.getUser().getDeptId().toString();
		//定义一个法院id集合
		List<String> jbfyIdList = new ArrayList<>();

		if (StringUtils.isNotEmpty(deptId)) {
			//查出部门的所属法院id
			Dept dept = deptService.selectDeptById(Long.parseLong(deptId));
			DatashareFollow datashareFollow = new DatashareFollow();
			datashareFollow.setDatashareCourtid(dept.getParentId() + "");
			datashareFollow.setStatus("0");
			//根据用户所属法院id 所有共享的法院id
			List<DatashareFollow> DatashareList = iDatashareFollowService.selectDatashareFollowList(datashareFollow);

			for (DatashareFollow follow : DatashareList) {
				jbfyIdList.add(follow.getSharecourtId());
			}
		}
		clue.setJbfyIdList(jbfyIdList);
		List<UserRole> roleList = userService.selectUserRoleByUserId(ShiroUtils.getUserId().toString());
		Boolean isok = false;
		Boolean QXisok = false;
		/*for (UserRole r:roleList) {
			if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
				isok = true;
				break;
			}
		}*/
		for (UserRole r:roleList) {
			if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey()) || ActivitiDefinftion.ROLE_KEY.equals(r.getRoleKey()) || ActivitiDefinftion.ROLE_Recorder_KEY.equals(r.getRoleKey())){
				QXisok = true;
				break;
			}
		}
		/*Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
		if("0".equals(dpt.getRank())){
			isok= true;
		}
		if(isok ==false){
			clue.setJbfyId(dpt.getParentId().toString());
		}*/
		if(QXisok!=true){
			clue.setClueCbrName(ShiroUtils.getUserId().toString());
		}
		ShiroUtils.clearCachedPage(pageDomain);
	    return clueMapper.selectClueList(clue);
	}

	public List<Clue> selectSecondClueList(Clue clue){
		return clueMapper.	selectSecondClueList(clue);
	}

    /**
     * 新增线索
     *
     * @param clue 线索 信息
     * @return 结果
     */
	@Override
	public int insertClue(Clue clue)
	{
	    return clueMapper.insertClue(clue);
	}

	/**
     * 修改线索
     *
     * @param clue 线索 信息
     * @return 结果
     */
	@Override
	public int updateClue(Clue clue)
	{
	    return clueMapper.updateClue(clue);
	}

	/**
     * 删除线索 对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClueByIds(String ids)
	{
		int  i =0;
		String[] arr = Convert.toStrArray(ids);
		for (String id:arr) {
			Clue clue =selectClueById(id);
			clue.setDeleteFlag("1");
			i+=updateClue(clue);
		}
		return i;
	}


	/**
	 * 统计全部线索数量
	 * @return
	 */
	public int queryAllCount(String deptId){
		return clueMapper.queryAllCount(deptId);
	}

	/**
	 * 统计本年度线索数量
	 * @return
	 */
	public int queryAllCountYear(String deptId){
		return clueMapper.queryAllCountYear(deptId);
	}


	/**
	 * 查询处理线索数量
	 * @return
	 */
	public int queryCountDeal(String deptId){
		return clueMapper.queryCountDeal(deptId)	;
	}

	/**
	 * 查询信访录入线索数量
	 * @return
	 */
	public int queryCountXf(String deptId){
		return clueMapper.queryCountXf(deptId);
	}


	/**
	 * 按案件类别统计线索数量
	 * @return
	 * @param fyId
	 */
	public List<Map<String,Object>> queryCountByAjlb(String fyId){
		return clueMapper.queryCountByAjlb(fyId);
	}


	/**
	 * 查询所有案件类别
	 * @return
	 * @param fyId
	 */
	public List<Map<String,Object>> queryAllAjlb(String fyId){
		return clueMapper.queryAllAjlb(fyId);
	}

	/**
	 * 按案件类别和承办庭统计线索数量
	 * @return
	 * @param fyId
	 */
	public List<Map<String,Object>> queryCountByAjlbAndCbt(String fyId){
		return clueMapper.queryCountByAjlbAndCbt(fyId);
	}


	/**
	 * 根据案件类别统计承办庭
	 * @return
	 */
	public List<Map<String,Object>> queryCbt(String ajlb){
		return clueMapper.queryCbt(ajlb);
	}


	/**
	 * 根据日期统计各案件来源的线索数量
	 *
	 * @param fyId
	 * @param time
	 * @return
	 */
	public int queryCountBySourceAndMonth(String fyId, String time, String source){
		return clueMapper.queryCountBySourceAndMonth(fyId,time,source);
	}


	/**
	 * 根据日期统计各案件类别的线索数量
	 *
	 * @param fyId
	 * @param time
	 * @return
	 */
	public  int queryCountByAjlbAndMonth(String fyId, String time, String ajlb){

		return clueMapper.queryCountByAjlbAndMonth(fyId,time,ajlb);
	}

	/**
	 * 根据风险等级统计各个案件类别的数量
	 *
	 * @param fyId
	 * @param riskLevel
	 * @return
	 */
	public int queryCountByRiskLevel(String fyId, String riskLevel, String ajlb){
		return clueMapper.queryCountByRiskLevel(fyId,riskLevel,ajlb);
	}

	/**
	 * 查出所有风险等级
	 * @return
	 * @param fyId
	 */
	public List<String> queryAllRiskLevel(String fyId){
		return clueMapper.queryAllRiskLevel(fyId);
	}


	/**
	 * 统计南阳市各个法院的线索数量
	 * @return
	 * @param fyId
	 */
	public List<Map<String,Object>> queryAllCountByFy(String fyId){
		return clueMapper.queryAllCountByFy(fyId);
	}


	/**
	 * 统计南阳市各个法院的处理过线索数量
	 * @return
	 */
	public int queryDealCountByFy(){
		return clueMapper.queryDealCountByFy();

	}

	/**
	 * 统计南阳市各个法院的当前年度新增线索数量
	 * @return
	 */
	public int queryYearAddCountByFy(){
		return clueMapper.queryYearAddCountByFy();
	}

	/**
	 * 根据案件类别查询线索所有信息（分页）
	 * @return
	 */
	public List<Map<String,Object>> queryAllByAjlbAndPage(String ajlb,int pageNumber,int pageSize){
		return clueMapper.queryAllByAjlbAndPage(ajlb, pageNumber, pageSize);
	}


	/**
	 * 统计各案件类别  可疑违规线索人员数量
	 * @return
	 * @param fyId
	 */
	public List<Map<String,Object>> queyCbrCountByAjlb(String fyId){
		return clueMapper.queyCbrCountByAjlb(fyId);
	}
	/**
	 * 统计各个年龄阶段相关责任人员数量
	 * @return
	 */
	public int queryCountByCardId(String fyId, int startAge, int endAge){
		return clueMapper.queryCountByCardId(fyId,startAge, endAge);
	}


	/**
	 * 统计60以上年龄阶段相关责任人员数量
	 * @return
	 */
	public int queryMaxCountByCardId(String fyId, int startAge){
		return clueMapper.queryMaxCountByCardId(fyId,startAge);
	}


	/**
	 * 统计当前时间前半年不同案件来源已处理的线索数量
	 *
	 * @param fyId
	 * @param time
	 * @param source
	 * @return
	 */
	public  int queryDealCountBySourceAndMonth(String fyId, String time, String source){
		return clueMapper.queryDealCountBySourceAndMonth(fyId,time,source);
	}

	/**
	 * 统计所有的策略的名称
	 * @return
	 */
	public List<Map<String,Object>> queryStrategyName(){
		return clueMapper.queryStrategyName( );
	}

    /**
     * 统计各案件类别各策略的数量
     * @return
     */
    public List<Map<String,Object>> queryAjlbCountByStrategyName(){
        return clueMapper.queryAjlbCountByStrategyName();
    }


	/**
	 * 查看所有承办庭
	 * @return
	 */
	public List<Map> queryAllCbt(){
		return clueMapper.queryAllCbt();
	}

    /**
     * 根据部门（人）名字查询出组织（人员）id
     * @return
     */
    public String queryCidByDeptName(String name){
        return clueMapper.queryCidByDeptName(name);
    }


	/**
	 * 根据部门（人）名字查询出组织（人员）id
	 * @return
	 */
	public List<Map> queryCidByDeptNames(String name){

		return  clueMapper.queryCidByDeptNames(name);
	}


    public  String queryDeptNameByCid(String id){
    	return  clueMapper.queryDeptNameByCid(id);
	}

	/**
////	 * 根据案件类别 和 策略id来查询线索
////	 * @param ajlb
////	 * @param id
////	 * @return
	 */
	public List<Map> selectClueListByAjlbAndStrategyId(@Param("ajlb") String ajlb,@Param("id") String id){
		return clueMapper.selectClueListByAjlbAndStrategyId(ajlb,id);
	}

	/**
	 * 根据 策略id来查询线索
	 * @param id
	 * @return
	 */
	public List<Map> selectClueListByStrategyId(@Param("id") String id){
		return clueMapper.selectClueListByStrategyId(id);
	}

	/**
	 * 根据 承办庭id来查询线索
	 * @param id
	 * @return
	 */
	public List<Map> selectClueListByCbtId( String id,String deptId){
		return clueMapper.selectClueListByCbtId(id,deptId);
	}

	/**
	 * 根据日期统计各案件类别的数量
	 * @param
	 * @return
	 */
	public List<Map> queryCountByMonth(){
		return clueMapper.queryCountByMonth();
	}


	/**
	 * 根据案件类别 和 日期来查询线索
	 * @param ajlb
	 * @param time
	 * @return
	 */
	public List<Map> queryClueByAjlbAndTime(@Param("ajlb") String ajlb,@Param("time") String time){
		return clueMapper.queryClueByAjlbAndTime(ajlb,time);
	}

	/**
	 * 根据日期来查询线索
	 * @param time
	 * @return
	 */
	public List<Map> queryClueByTime(String time){
		return clueMapper.queryClueByTime(time);
	}


	/**
	 * 根据日期来统计各线索来源数量
	 * @param
	 * @return
	 */
	public List<Map> queryAllCourceByDate( ){
		return clueMapper.queryAllCourceByDate();
	}

	/**
	 * 根据日期统计各线索来源详情
	 * @return
	 */
	public List<Map> querySourceByDate(String source, String time){
		return clueMapper.querySourceByDate(source,time);
	}

	/**
	 * 根据日期统计各线索来源处理详情
	 * @return
	 */
	public List<Map> querySourceDealByDate(@Param("source") String source,@Param("time") String time){
		return clueMapper.querySourceDealByDate(source,time);
	}


	/**
	 * 统计完成和处置中的线索总量
	 *
	 * @param fyId
	 * @param status
	 * @return
	 */
	public int queryEveCount(String fyId, @Param("status") int status){
		return clueMapper.queryEveCount(fyId,status);
	}

	/**
	 * 统计处置线索总量
	 * @return
	 * @param fyId
	 */
	public int queryAllCounts(String fyId){
		return clueMapper.queryAllCounts(fyId);
	}

	/**
	 * 统计完成各阶段线索数量
	 * @return
	 * @param fyId
	 */
	public List<Map> queryFinishCzjg(String fyId){
		return clueMapper.queryFinishCzjg(fyId);
	}


	/**
	 * 统计完成澄清线索数量
	 * @return
	 */
	public int queryCqCount(String fyId, String cbtName){
		return clueMapper.queryCqCount(fyId,cbtName);
	}

	/**
	 * 统计完成问题线索数量
	 * @return
	 */
	public int queryQuestionCount(String fyId, String cbtName){
		return clueMapper.queryQuestionCount(fyId,cbtName);
	}

	/**
	 * 根据日期统计处置中各阶段的线索数量
	 *
	 * @param fyId
	 * @param time
	 * @return
	 */
	public  int queryCzzCountByMonth(String fyId, @Param("time") String time, @Param("czjg") String czjg){
		return clueMapper.queryCzzCountByMonth(fyId,time,czjg);
	}


	/**
	 * 统计已处置各个年龄阶段和各个案件类别相关责任人员数量
	 * @return
	 */
	public int queryCzzCountByAge(String fyId, @Param("ajlb") String ajlb, @Param("startAge") int startAge, @Param("endAge") int endAge){
		return clueMapper.queryCzzCountByAge(fyId,ajlb,startAge,endAge);
	}

	/**
	 * 统计已处置60以上年龄阶段和各个案件类别相关责任人员数量
	 * @return
	 */
	public int queryMaxCountByAge(String fyId, @Param("ajlb") String ajlb, @Param("startAge") int startAge){
		return clueMapper.queryMaxCountByAge(fyId,ajlb,startAge);
	}


	/**
	 * 根据风险等级和时间统计完成线索数量
	 *
	 * @param fyId
	 * @param time
	 * @param riskLevel
	 * @return
	 */
	public int queryFinishRiskCount(String fyId, @Param("time") String time, @Param("riskLevel") String riskLevel){
		return clueMapper.queryFinishRiskCount(fyId,time,riskLevel);
	}

	/**
	 * 据案件类别和部门统计完成线索数量
	 * @return
	 */
	public int queryFinishClueByDeptAndAjlb(String fyId, @Param("cbtName") String cbtName, @Param("ajlb") String ajlb){
		return clueMapper.queryFinishClueByDeptAndAjlb(fyId,cbtName,ajlb);
	}


	/**
	 * 据案件类别和部门统计线索数量
	 * @return
	 */
	public int queryRiskCountByMonth(String fyId, @Param("time") String time, @Param("riskLevel") String riskLevel){
		return clueMapper.queryRiskCountByMonth(fyId,time,riskLevel);
	}

	@Override
	public List<Map<String, Object>> map() {
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> deptList = deptService.selectDeptList(dept);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (Dept p:deptList) {
			Map<String,Object> map = new HashMap<String,Object>();
			Information information = new Information();
			information.setParentDeptId(p.getDeptId().toString());
			map.put("name",p.getDeptName());
			map.put("count",clueMapper.queryEveCount(p.getDeptId().toString(),null));
			list.add(map);
		}
		return list;
	}

	/**
	 * 地图统计2
	 * @return
	 */
	public List<Map<String, Object>> map2() {
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> deptList = deptService.selectDeptList(dept);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (Dept p:deptList) {
			Map<String,Object> map = new HashMap<String,Object>();
			Information information = new Information();
			information.setParentDeptId(p.getDeptId().toString());
			map.put("name",p.getDeptName());
			map.put("count",clueMapper.queryAllCounts(p.getDeptId().toString()));
			list.add(map);
		}
		return list;
	}


	public List<Map<String,Object>> queryFyrInfoByFyy(String deptId ){

		return clueMapper.queryFyrInfoByFyy(deptId);
	}

	public String queryFycs(String id){

		return clueMapper.queryFycs(id);
	}

	public String queryDeptByRyId(String id){
		return clueMapper.queryDeptByRyId(id);
	}

	public String queryAgeById(String id){
		return clueMapper.queryAgeById(id);
	}

	public List<Map<String,Object>> queryCzjgByFyIds(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("deptId") String deptId){
		return clueMapper.queryCzjgByFyIds(startTime,endTime,deptId);
	}

	public String queryXsByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource ){
		return clueMapper.queryXsByFyId(fyId,startTime,endTime,clueSource);
	}

	public String queryJcByFyId(@Param("fyId") String fyId,@Param("time") String time,@Param("clueSource") String clueSource){
		return clueMapper.queryJcByFyId(fyId,time,clueSource);
	}

	public String queryYjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource){
		return clueMapper.queryYjByFyId(fyId,startTime,endTime,clueSource);
	}

	public String queryWjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource){
		return clueMapper.queryWjByFyId(fyId,startTime,endTime,clueSource);
	}

	/**
	 * 统计各法院各承办庭的线索处置结果
	 * @return
	 */
	public List<Map<String,Object>> queryCbtCzjgByCbtIds(String id,String startTime,String endTime){

		return clueMapper.queryCbtCzjgByCbtIds(id,startTime,endTime);
	}

	public String queryXsByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime ){
		return clueMapper.queryXsByCbtId(cbtId,fyId,startTime,endTime);
	}

	/**
	 * 统计各个法院线索旧存数量
	 * @param cbtId
	 * @param time
	 * @return
	 */
	public String queryJcByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("time") String time){
		return clueMapper.queryJcByCbtId(cbtId,fyId,time);
	}


	/**
	 * 统计各个法院线索已结数量
	 * @param cbtId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String queryYjByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime){
		return clueMapper.queryYjByCbtId(cbtId,fyId,startTime,endTime);

	}

	public String queryWjByCbtId(@Param("cbtId") String cbtId,@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime){
		return clueMapper.queryWjByCbtId(cbtId,fyId,startTime,endTime);
	}


	public List<Map<String,Object>> queryXfYjByFyId(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("deptId") String deptId){
		return clueMapper.queryXfYjByFyId(startTime,endTime,deptId);
	}

	public String queryXfWjByFyId(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime){
		return clueMapper.queryXfWjByFyId(fyId,startTime,endTime);
	}


	public List<Map> queryJcByFyIdAll(@Param("fyId") String fyId,@Param("time") String time,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId){

		return	clueMapper.queryJcByFyIdAll(fyId,time,clueSource,jbxxCbtId);

	}

	public List<Map> queryYjByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId){
		return	clueMapper.queryYjByFyIdAll(fyId,startTime,endTime,clueSource,jbxxCbtId);
	}

	public List<Map> queryWjByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId){
		return	clueMapper.queryWjByFyIdAll(fyId,startTime,endTime,clueSource,jbxxCbtId);
	}

	public List<Map> queryXsByFyIdAll(@Param("fyId") String fyId,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("clueSource") String clueSource,@Param("jbxxCbtId") String jbxxCbtId){
		return	clueMapper.queryXsByFyIdAll(fyId,startTime,endTime,clueSource,jbxxCbtId);
	}

	public List<Map> queryAllCzjgByFyId(@Param("czjg") String czjg,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("fyId") String fyId,@Param("jbxxCbtId") String jbxxCbtId){
		return	clueMapper.queryAllCzjgByFyId(czjg,startTime,endTime,fyId,jbxxCbtId);
	}


}
