package com.datcent.project.module.statistics.service;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import com.datcent.project.module.datashareFollow.service.IDatashareFollowService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.mapper.DubvioEventMapper;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.monitor.server.domain.Sys;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 统计汇总 服务层实现
 *
 * @author zhoushiji
 * @date 2019-11-29
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDatashareFollowService iDatashareFollowService;

    @Autowired
    private IDubvioEventService iDubvioEventService;

    @Autowired
    private DubvioEventMapper dubvioEventMapper;

    @Autowired
    private IClueService clueService;


    /**
     * 获取当前用户可查询法院数据的法院ID
     *
     * @return
     */
    public List<String> getCurrentUerSelectDept() {
        List<String> jbfyIdList = new ArrayList<String>();
        String parentId = deptService.selectParentDeptIdByUserId();
        DatashareFollow datashareFollow = new DatashareFollow();
        datashareFollow.setDatashareCourtid(parentId);
        datashareFollow.setStatus("0");
        List<DatashareFollow> DatashareList = iDatashareFollowService.selectDatashareFollowList(datashareFollow);
        for (DatashareFollow follow : DatashareList) {
            jbfyIdList.add(follow.getSharecourtId());
        }
        return jbfyIdList;
    }

    /**
     * 可疑事件中可疑点维度统计
     *
     * @param dubvioEvent
     * @return
     */
    public List<Map<String, Object>> getKysjBykyd(DubvioEvent dubvioEvent, PageDomain pageDomain) {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return iDubvioEventService.queryEventAjlbCountByStrategyName(dubvioEvent);
    }

    /**
     * 根据不同的维度条件查询案件列表
     *
     * @param dubvioEvent
     * @return
     */
    public List<DubvioEvent> getDimList(DubvioEvent dubvioEvent, PageDomain pageDomain) {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getDimList(dubvioEvent);
    }

    /**
     * 可疑被反映人数据统计
     *
     * @param dubvioEvent 可疑违纪事件
     * @return
     */
    public List<Map<String, Object>> getSuspectsByManData(DubvioEvent dubvioEvent, PageDomain pageDomain) {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.querySuspectsByManData(dubvioEvent);
    }

    /**
     * 可疑点线索统计数据
     *
     * @return
     */
    public List<Map<String, Object>> getSuspiciousCluesCount(DubvioEvent dubvioEvent, PageDomain pageDomain) {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspiciousCluesCount(dubvioEvent);
    }

    /**
     * 可疑点线索详情
     *
     * @return
     * @author zhang date:2019/12/10
     */
    public List<Map> getClueDetailList(String name, String id) {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        return dubvioEventMapper.getClueDetailList(name, id, jbfyIdList);
    }

    /**
     * 月度线索统计
     *
     * @return
     * @author zhang date:2019/12/11
     */
    public List<Map> getClueCountByMonth() {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        return dubvioEventMapper.getClueCountByMonth(jbfyIdList);
    }

    /**
     * 根据日期查询月度线索详情
     *
     * @return
     * @author zhang date:2019/12/11
     */
    public List<Map> getClueDetailListByDate(String name, String clueTime) {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        return dubvioEventMapper.getClueDetailListByDate(name, clueTime, jbfyIdList);
    }

    /**
     * 根据月，获取线索来源统计
     *
     * @return
     * @author zhang date:2019/12/12
     */
    public List<Map> getAllClueSourceByMonth() {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        return dubvioEventMapper.getAllClueSourceByMonth(jbfyIdList);
    }

    /**
     * 获取被反映人统计（线索：筛查部分）
     *
     * @return
     * @author zhang
     * @date 2019-12-13
     */
    public List<Map> getReflectedPeopleCount() {
		List<Map> reflectedPeopleCountList = new ArrayList<>();
		String courtId = deptService.selectParentDeptIdByUserId();
		reflectedPeopleCountList = dubvioEventMapper.getReflectedPeopleCountList(courtId);
        if (!StringUtils.isNull(reflectedPeopleCountList)) {
            for (int i = 0; i < reflectedPeopleCountList.size(); i++) {
				Object cbrId = reflectedPeopleCountList.get(i).get("jbxx_cbr_id");
				if (!StringUtils.isNull(cbrId)) {
					String dept = clueService.queryDeptByRyId(cbrId.toString());
					String fycs = clueService.queryFycs(cbrId.toString());
					String age = clueService.queryAgeById(cbrId.toString());
					reflectedPeopleCountList.get(i).put("fycs", fycs);
					reflectedPeopleCountList.get(i).put("dept", dept);
					reflectedPeopleCountList.get(i).put("age", age);
				} else {
					reflectedPeopleCountList.get(i).put("fycs", "0");
				}
            }
        }
        return reflectedPeopleCountList;
    }

    /**
     * 承办庭可疑点统计
     * @author zhang
     * @date 2019-12-17
     */
    public List<Map<String,Object>> getSuspectsCountByTribunal(DubvioEvent dubvioEvent, PageDomain pageDomain)
    {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspectsCountByTribunalList(dubvioEvent);
    }

    /**
     * 获取承办庭可疑点详情数据列表
     * @author zhang
     * @date 2019-12-18
     */
    public List<Map<String,Object>> getSuspectsDetailByCourtId(PageDomain pageDomain,String id)
    {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspectsDetailByCourtId(jbfyIdList, id);
    }

    /**
     * 获取月度可疑点统计
     * @author zhang
     * @date 2019-12-18
     */
    public List<Map> getSuspectsCountByMonth(String startDate, String endDate)
    {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
            startDate = startDate + "-01";
            endDate = endDate + "-31";
        }else{
            startDate = "2019-01-01"; //该日期为默认的最初日期。
            endDate = DateUtils.getDate();
        }
        return dubvioEventMapper.getAllSuspectsCountByMonth(startDate, endDate, jbfyIdList);
    }

    /**
     * 获取月度可疑点详细
     * @author zhang
     * @date 2019-12-19
     */
    public List<Map<String,Object>> getSuspectsDetailByMonth(PageDomain pageDomain, String suspectsTime, String caseTypeName)
    {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspectsDetailByMonth(suspectsTime,caseTypeName,jbfyIdList);
    }

    /**
     *院可疑点统计
     * @author zhang
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsCountByCourt(DubvioEvent dubvioEvent, PageDomain pageDomain)
    {
        dubvioEvent.setJbfyIdList(getCurrentUerSelectDept());
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspectsCountByCourt(dubvioEvent);
    }

    /**
     * 获取院可疑点详细数据接口
     * @author zhang
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsDetailByCourtIdAndName(PageDomain pageDomain,String id,String name)
    {
        List<String> jbfyIdList = new ArrayList<String>();
        jbfyIdList = getCurrentUerSelectDept();
        ShiroUtils.clearCachedPage(pageDomain);
        return dubvioEventMapper.getSuspectsDetailByCourtIdAndName(id,name,jbfyIdList);
    }

    /**
     * 院-承办庭统计数据接口
     * @param id 法院ID
     * @author zhang
     * @date 2019-12-30
     */
    public List<Map<String,Object>> getTribunalSuspectsCountByCourtId(PageDomain pageDomain, String id)
    {
        ShiroUtils.clearCachedPage(pageDomain);
        System.out.println(id + "imp");
        return dubvioEventMapper.getTribunalSuspectsCountByCourtId(id);
    }

    /**
     * 获取可疑点名称列表
     * @author zhang
     * @date 2019-12-31
     */
    public List<Map> getSuspectsNameList()
    {
        return dubvioEventMapper.getSuspectsNameList();
    }
}
