package com.datcent.project.module.statistics.service;


import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;

import java.util.List;
import java.util.Map;

/**
 * 统计汇总 服务层
 *
 * @author zhoushiji
 * @date 2019-11-29
 */
public interface IStatisticsService
{
    public List<Map<String,Object>> getKysjBykyd(DubvioEvent dubvioEvent,PageDomain pageDomain);

    public List<DubvioEvent> getDimList(DubvioEvent dubvioEvent,PageDomain pageDomain);

    public List<String> getCurrentUerSelectDept();

    /**
     * 可疑点被反映人统计数据
     * @param dubvioEvent 可疑违纪事件
     * @return 统计数据
     */
    public List<Map<String,Object>> getSuspectsByManData(DubvioEvent dubvioEvent,PageDomain pageDomain);

    /**
     * 可疑线点线索统计
     * @return
     */
    public List<Map<String,Object>> getSuspiciousCluesCount(DubvioEvent dubvioEvent,PageDomain pageDomain);

    /**
     * 可疑点线索详情
     * @author zhang date:2019/12/10
     * @return
     */
    public List<Map> getClueDetailList(String name, String id);

    /**
     * 月度线索统计
     * @author zhang date:2019/12/11
     * @return
     */
    public List<Map> getClueCountByMonth();

    /**
     * 根据日期查询月度线索详情
     * @author zhang date:2019/12/11
     * @return
     */
    public List<Map> getClueDetailListByDate(String name, String clueTime);

    /**
     * 根据月，查询线索来源统计
     * @author zhang date:2019/12/12
     * @return
     */
    public List<Map> getAllClueSourceByMonth();

    /**
     * 获取被反映人统计（线索：筛查部分）
     * @return
     * @author zhang
     * @date 2019-12-13
     */
    public List<Map> getReflectedPeopleCount();

    /**
     * 承办庭可疑点统计
     * @author zhang
     * @date 2019-12-17
     */
    public List<Map<String,Object>> getSuspectsCountByTribunal(DubvioEvent dubvioEvent,PageDomain pageDomain);

    /**
     * 获取承办庭可疑点详情数据列表
     * @author zhang
     * @date 2019-12-18
     */
    public List<Map<String,Object>> getSuspectsDetailByCourtId(PageDomain pageDomain,String id);

    /**
     * 获取月度可疑点统计
     * @author zhang
     * @date 2019-12-18
     */
    public List<Map> getSuspectsCountByMonth(String startDate, String endDate);

    /**
     * 获取月度可疑点详细
     * @author zhang
     * @date 2019-12-18
     */
    public List<Map<String,Object>> getSuspectsDetailByMonth(PageDomain pageDomain, String suspectsTime, String caseTypeName);

    /**
     * 院可疑点统计
     * @author zhang
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsCountByCourt(DubvioEvent dubvioEvent, PageDomain pageDomain);

    /**
     * 获取院可疑点详细数据接口
     * @author zhang
     * @date 2019-12-20
     */
    public List<Map<String,Object>> getSuspectsDetailByCourtIdAndName(PageDomain pageDomain,String id,String name);

    /**
     * 院-承办庭可疑点统计数据接口
     * @param id 法院ID
     * @param pageDomain 分页
     * @author zhang
     * @date 2019-12-30
     */
    public List<Map<String, Object>> getTribunalSuspectsCountByCourtId(PageDomain pageDomain, String id);

    /**
     * 获取可疑点名称列表
     * @author zhang
     * @date 2019-12-31
     */
    public List<Map> getSuspectsNameList();

}
