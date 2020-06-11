package com.datcent.project.module.clue.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.RedisUtil;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.cbttj.domain.Cbttj;
import com.datcent.project.module.cbttj.service.ICbttjService;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.service.IDispositionHandleService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.project.module.fytj.domain.Fytj;
import com.datcent.project.module.fytj.service.IFytjService;
import com.datcent.project.module.information.controller.InformationController;
import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.ruTask.service.IRuTaskService;
import com.datcent.project.module.rytj.domain.Rytj;
import com.datcent.project.module.rytj.service.IRytjService;
import com.datcent.project.module.statistics.service.IStatisticsService;
import com.datcent.project.module.strategy.service.IStrategyService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;
import com.datcent.project.transaction.TaoTransaction;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 线索  信息操作处理
 *
 * @author datcent
 * @date 2019-01-09
 */
@Controller
@RequestMapping("/module/clue")
public class ClueController extends BaseController {

    private static final Logger log = Logger.getLogger(ClueController.class);
    private String prefix = "module/clue";

    @Resource
    RedisUtil redisUtil;

    @Autowired
    private IClueService clueService;

    @Autowired
    private IDispositionHandleService dispositionHandleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IRuTaskService ruTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IDubvioEventService dubvioEventService;

    @Autowired
    private IStrategyService strategyService;


    @Autowired
    private IDubvioEventDetailService dubvioEventDetailService;

    @Autowired
    private ICourtOrganService iCourtOrganService;

    @Autowired
    private IFytjService fytjService;

    @Autowired
    private ICbttjService cbttjService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRytjService rytjService;

    @Autowired
    private IStatisticsService iStatisticsService;

    @RequiresPermissions("module:clue:view")
    @GetMapping()
    public String clue(ModelMap mmap) {

        CourtOrgan   courtOrgan=new CourtOrgan();
        courtOrgan.setDeptType("1");
        courtOrgan.setStatus("0");
        List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
        mmap.put("deptList", thirdList);
        return prefix + "/clue";
    }

    /**
     * 查询线索 列表
     */
    @RequiresPermissions("module:clue:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Clue clue) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotEmpty(clue.getParams())) {
            if (StringUtils.isNotEmpty(clue.getParams().get("createTime").toString())) {
                clue.setCreateTime(sdf.parse(clue.getParams().get("createTime").toString()));
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Clue> list = clueService.selectClueList(clue, pageDomain);
        return getDataTable(list);
    }

    /**
     * 新增线索
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Map> list = strategyService.queryStrategy();
        List<Map> secondList = clueService.queryAllCbt();
        mmap.put("strategyList", list);
        mmap.put("cbtList", secondList);
        return prefix + "/add";
    }

    /**
     * 新增保存线索
     //     */
//    @RequiresPermissions("module:clue:add")
//    @Log(title = "线索 ", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ResponseBody
//    public AjaxResult addSave(Clue clue, @Param("createTimess") String createTimess, String jbxxAjbhs, String jbxxAhs, String dubvioStrategyIds, String dubvioIds, String jbxxBgrs, String jbxxCbtNames, String jbxxAjlbs, String risksLevels, String jbxxSarqs, String jbxxLarqs, String jbxxFarqs, String jbxxKtrqs, String jbxxJarqs, String jbxxGdrqs, String createTimes) throws ParseException {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        if (StringUtils.isEmpty(clue.getClueId())) {
//            clue.setClueId(StringUtils.getUUID());
//        }
//
//        if (StringUtils.isEmpty(createTimess)) {
//            clue.setCreateTime(DateUtils.getNowDate());
//        } else {
//            clue.setCreateTime(sdf.parse(createTimess));
//        }
//
//        if (StringUtils.isNotEmpty(jbxxAjbhs)) {
//            clue.setJbxxAjbh(jbxxAjbhs);
//            clue.setJbxxAh(jbxxAhs);
//            clue.setDubvioStrategyId(dubvioStrategyIds);
//            clue.setDubvioStrategyName(strategyService.queryStrategyById(Integer.parseInt(dubvioStrategyIds)));
//            clue.setJbxxBgr(jbxxBgrs);
//            clue.setDubvioId(dubvioIds);
//            clue.setJbxxCbtName(jbxxCbtNames);
//            if (StringUtils.isNotEmpty(jbxxCbtNames)) {
//                String id = clueService.queryCidByDeptName(jbxxCbtNames);
//                clue.setJbxxCbtId(id);
//            }
//            if (StringUtils.isNotEmpty(clue.getClueFydx())) {
//                String cbrId = clueService.queryCidByDeptName(clue.getClueFydx());
//                clue.setJbxxCbrId(cbrId);
//            }
//            clue.setJbxxCbrName(clue.getClueFydx());
//            clue.setJbxxAjlb(jbxxAjlbs);
//            clue.setRisksLevel(risksLevels);
//            clue.setJbxxSarq(jbxxSarqs);
//            clue.setJbxxLarq(jbxxLarqs);
//            clue.setJbxxFarq(jbxxFarqs);
//            clue.setJbxxKtrq(jbxxKtrqs);
//            clue.setJbxxJarq(jbxxJarqs);
//            clue.setJbxxGdrq(jbxxGdrqs);
//
//            if (StringUtils.isEmpty(createTimes)) {
//                clue.setCreateTime(DateUtils.getNowDate());
//            } else {
//                clue.setCreateTime(sdf.parse(createTimes));
//            }
//
//        }
//        clue.setClueSource("2");
//        clue.setClueStatus("1");
//
//        clue.setJbxxCbrName(clue.getClueFydx());
//        return toAjax(clueService.insertClue(clue));
//    }

    /**
     * 修改线索
     */
    @GetMapping("/edit/{clueId}")
    public String edit(@PathVariable("clueId") String clueId, ModelMap mmap) {
        Clue clue = clueService.selectClueById(clueId);
        DubvioEvent dubvioEvent = new DubvioEvent();
        dubvioEvent.setJbxxCbrName(clue.getClueFydx());
        List<DubvioEvent> dubvioList = dubvioEventService.selectDubvioEventList(dubvioEvent, null);

        mmap.put("dubvioList", dubvioList);
        List<Map> list = strategyService.queryStrategy();
        List<Map> secondList = clueService.queryAllCbt();
        mmap.put("strategyList", list);
        mmap.put("cbtList", secondList);

        mmap.put("clue", clue);
        return prefix + "/edit";
    }

//    /**
//     * 修改保存线索
//     */
//    @RequiresPermissions("module:clue:edit")
//    @Log(title = "线索 ", businessType = BusinessType.UPDATE)
//    @PostMapping("/edit")
//    @ResponseBody
//    public AjaxResult editSave(Clue clue, @Param("jbxxAjbhs") String jbxxAjbhs, @Param("jbxxAhs") String jbxxAhs, @Param("dubvioStrategyNames") String dubvioStrategyNames, @Param("jbxxCbrIds") String jbxxCbrIds, @Param("dubvioIds") String dubvioIds, @Param("dubvioStrategyIds") String dubvioStrategyIds, @Param("jbxxCbrNames") String jbxxCbrNames, @Param("jbxxBgrs") String jbxxBgrs, @Param("jbxxCbtNames") String jbxxCbtNames, @Param("jbxxCbtIds") String jbxxCbtIds, @Param("jbxxAjlbs") String jbxxAjlbs, @Param("risksLevels") String risksLevels, @Param("jbxxSarqs") String jbxxSarqs, @Param("jbxxLarqs") String jbxxLarqs, @Param("jbxxFarqs") String jbxxFarqs, @Param("jbxxKtrqs") String jbxxKtrqs, @Param("jbxxJarqs") String jbxxJarqs, @Param("jbxxGdrqs") String jbxxGdrqs) {
//
//
//        if (StringUtils.isEmpty(jbxxAjbhs)) {
//            String name = strategyService.queryStrategyById(Integer.parseInt(clue.getDubvioStrategyId()));
//            clue.setJbxxCbrName(clue.getClueFydx());
//            clue.setDubvioStrategyName(name);
//            if (StringUtils.isNotEmpty(clue.getJbxxCbtName())) {
//                String id = clueService.queryCidByDeptName(clue.getJbxxCbtName());
//                clue.setJbxxCbtId(id);
//                String cbrId = clueService.queryCidByDeptName(clue.getClueFydx());
//                clue.setJbxxCbrId(cbrId);
//            }
//        } else {
//            clue.setJbxxAjbh(jbxxAjbhs);
//            clue.setJbxxAh(jbxxAhs);
//            clue.setDubvioStrategyId(dubvioStrategyIds);
//            clue.setDubvioStrategyName(strategyService.queryStrategyById(Integer.parseInt(dubvioStrategyIds)));
//            clue.setJbxxBgr(jbxxBgrs);
//            clue.setDubvioId(dubvioIds);
//            clue.setJbxxCbtName(jbxxCbtNames);
//            if (StringUtils.isNotEmpty(jbxxCbtNames)allSecondData) {
//                String id = clueService.queryCidByDeptName(jbxxCbtNames);
//                clue.setJbxxCbtId(id);
//            }
//            if (StringUtils.isNotEmpty(clue.getClueFydx())) {
//                String cbrId = clueService.queryCidByDeptName(clue.getClueFydx());
//                clue.setJbxxCbrId(cbrId);
//            }
//            clue.setJbxxCbrName(clue.getClueFydx());
//            clue.setJbxxAjlb(jbxxAjlbs);
//            clue.setRisksLevel(risksLevels);
//            clue.setJbxxSarq(jbxxSarqs);
//            clue.setJbxxLarq(jbxxLarqs);
//            clue.setJbxxFarq(jbxxFarqs);
//            clue.setJbxxKtrq(jbxxKtrqs);
//            clue.setJbxxJarq(jbxxJarqs);
//            clue.setJbxxGdrq(jbxxGdrqs);
//
//        }
//
//        return toAjax(clueService.updateClue(clue));
//    }

    /**
     * 删除线索
     */
    @RequiresPermissions("module:clue:remove")
    @Log(title = "线索 ", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(clueService.deleteClueByIds(ids));
    }


    /**
     * 统计各个维度线索数量
     *
     * @return
     */
    @GetMapping("/queryAllCount")
    @ResponseBody
    public Map<String, Object> queryAllCount() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        int count = clueService.queryAllCount(fyId);
        int yearCount = clueService.queryAllCountYear(fyId);
        int dealCount = clueService.queryCountDeal(fyId);
        int xfCount = clueService.queryCountXf(fyId);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("allClueCount", count);
        map.put("yearCount", yearCount);
        map.put("dealCount", dealCount);
        map.put("xfCount", xfCount);
        return map;
    }


    /**
     * 按案件类别统计线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountByAjlb")
    @GetMapping("/queryCountByAjlb")
    @ResponseBody
    public List<Map<String, Object>> queryCountByAjlb() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        List<Map<String, Object>> map = clueService.queryCountByAjlb(fyId);
        return map;
    }

    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountByAjlbAndCbt")
    @GetMapping("/queryCountByAjlbAndCbt")
    @ResponseBody
    public List<Object> queryCountByAjlbAndCbt() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        Map<String, Object> map = new HashMap<>();
        //统计各个承办庭线索数量
        List<Map<String, Object>> countList = clueService.queryCountByAjlbAndCbt(fyId);

        List<Object> allList = new ArrayList<>();

        List<Object> firstList = new ArrayList<>();
        List<Object> secondList = new ArrayList<>();

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {
                long count = (long) countList.get(i).get("count");
                String cbtName = null;
                if (StringUtils.isNull(countList.get(i).get("jbxx_cbt_name"))) {
                    cbtName = "其他";
                } else {
                    cbtName = countList.get(i).get("jbxx_cbt_name").toString();
                }

                firstList.add(count);
                secondList.add(cbtName);
            }
        }
        allList.add(firstList);
        allList.add(secondList);

        return allList;
    }

    /**
     * 根据线索来源统计当前时间前半年各个月份的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountBySourceAndMonth")
    @GetMapping("/queryCountBySourceAndMonth")
    @ResponseBody
    public List<Object> queryCountBySourceAndMonth() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();
        List<Integer> fourCountList = new ArrayList<>();
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < dateList.size(); i++) {

            String time = dateList.get(i).toString();
            //根据月份参数和案件来源统计线索数量（1系统策略筛查 2信访录入）
            int count = clueService.queryCountBySourceAndMonth(fyId, time, "1");
            int secondCount = clueService.queryCountBySourceAndMonth(fyId, time, "2");
            //根据月份参数和案件来源统计已处理线索数量（1系统策略筛查 2信访录入）
            int thirdCount = clueService.queryDealCountBySourceAndMonth(fyId, time, "1");
            int fourCount = clueService.queryDealCountBySourceAndMonth(fyId, time, "2");
            countList.add(count);
            secondCountList.add(secondCount);
            thirdCountList.add(thirdCount);
            fourCountList.add(fourCount);
        }
        map.put("1", countList);
        map.put("2", secondCountList);
        map.put("3", thirdCountList);
        map.put("4", fourCountList);
        allList.add(map);
        allList.add(dateList);
        return allList;
    }


    /**
     * 根据案件类别统计当前时间前半年各个月份的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountByAjlbAndMonth")
    @GetMapping("/queryCountByAjlbAndMonth")
    @ResponseBody
    public List<Object> queryCountByAjlbAndMonth() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();
        List<Integer> fourCountList = new ArrayList<>();
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {

            String time = dateList.get(i).toString();
            //根据月份参数和案件类别统计线索数量（刑事  民事 行政 执行）
            int count = clueService.queryCountByAjlbAndMonth(fyId, time, "刑事");
            int secondCount = clueService.queryCountByAjlbAndMonth(fyId, time, "民事");
            int thirdCount = clueService.queryCountByAjlbAndMonth(fyId, time, "行政");
            int fourCount = clueService.queryCountByAjlbAndMonth(fyId, time, "执行");
            countList.add(count);
            secondCountList.add(secondCount);
            thirdCountList.add(thirdCount);
            fourCountList.add(fourCount);
        }
        map.put("xs", countList);
        map.put("ms", secondCountList);
        map.put("xz", thirdCountList);
        map.put("zx", fourCountList);
        allList.add(map);
        allList.add(dateList);
        return allList;
    }

    /**
     * 按风险等级统计各案件类别线索数量
     *
     * @return
     */
    @GetMapping("/queryCountByRiskLevel")
    @ResponseBody
    public List<Object> queryCountByRiskLevel() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        List<String> riskLevelList = clueService.queryAllRiskLevel(fyId);
        List<Object> allList = new ArrayList<>();

        List<Map<String, Object>> ajlbList = clueService.queryAllAjlb(fyId);

        allList.add(ajlbList);


        if (!StringUtils.isNull(riskLevelList)) {
            for (int i = 0; i < riskLevelList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                List<Object> firstList = new ArrayList<>();
                String riskLevel = riskLevelList.get(i);
                if (StringUtils.isNotEmpty(riskLevel)){
                    int xsCount = clueService.queryCountByRiskLevel(fyId, riskLevel, "刑事");
                    int msCount = clueService.queryCountByRiskLevel(fyId, riskLevel, "民事");
                    int xzCount = clueService.queryCountByRiskLevel(fyId, riskLevel, "行政");
                    int zxCount = clueService.queryCountByRiskLevel(fyId, riskLevel, "执行");

                    firstList.add(xsCount);
                    firstList.add(msCount);
                    firstList.add(xzCount);
                    firstList.add(zxCount);

                    map.put("countList", firstList);
                    map.put("riskLevel", riskLevel);
                    allList.add(map);
                }

            }
        }
        return allList;
    }


    /**
     * 统计南阳市各个法院的线索数量（地图）
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountByFy")
    @GetMapping("/queryCountByFy")
    @ResponseBody
    public Map<String, Object> queryCountByFy() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        List<Object> allList = new ArrayList<>();
        List<Map<String, Object>> list = clueService.queryAllCountByFy(fyId);
        Map<String, Object> map = null;
        if (!StringUtils.isNull(list)) {
            for (int i = 0; i < list.size(); i++) {
                map = new HashMap<>();
                long count = (long) list.get(i).get("count");

                //String name=list.get(i).get("name").toString();

                map.put("allCount", count);
                map.put("fyName", "监督管理平台");
            }
        }
        int dealCount = clueService.queryDealCountByFy();
        int addCount = clueService.queryYearAddCountByFy();

        map.put("dealCount", dealCount);
        map.put("addCount", addCount);

        Map<String, Object> firstMap = new HashMap<>();
        firstMap.put("dealCount", 56);
        firstMap.put("addCount", 124);
        firstMap.put("allCount", 521);
        firstMap.put("fyName", "镇平县");
        Map<String, Object> secondMap = new HashMap<>();
        secondMap.put("dealCount", 78);
        secondMap.put("addCount", 154);
        secondMap.put("allCount", 436);
        secondMap.put("fyName", "南召县");
        Map<String, Object> thirdMap = new HashMap<>();
        thirdMap.put("dealCount", 23);
        thirdMap.put("addCount", 148);
        thirdMap.put("allCount", 335);
        thirdMap.put("fyName", "卧龙区");

        Map<String, Object> fourMap = new HashMap<>();
        fourMap.put("dealCount", 31);
        fourMap.put("addCount", 121);
        fourMap.put("allCount", 233);
        fourMap.put("fyName", "桐柏县");

        allList.add(map);
        allList.add(firstMap);
        allList.add(secondMap);
        allList.add(thirdMap);
        allList.add(fourMap);

        Map<String, Object> allMap = new HashMap<>();
        allMap.put("data", allList);
        allMap.put("status", "ok");
        return allMap;
    }

    /**
     * 根据案件类别查询线索所有信息（分页）
     *
     * @return
     */
    @RequiresPermissions("module:clue:queryAllByAjlbAndPage")
    @GetMapping("/queryAllByAjlbAndPage")
    @ResponseBody
    public List<Map<String, Object>> queryAllByAjlbAndPage(String ajlb, String index, String pageSize) {

        List<Map<String, Object>> list = null;

        if (StringUtils.isNotEmpty(ajlb) && StringUtils.isNotEmpty(index) && StringUtils.isNotEmpty(pageSize)) {

            int allIndex = Integer.parseInt(index);
            int allPageSize = Integer.parseInt(pageSize);
            int pageNumber = (allIndex - 1) * allPageSize;

            list = clueService.queryAllByAjlbAndPage(ajlb, pageNumber, allPageSize);

        }
        return list;
    }


    /**
     * 统计各案件类别  可疑违规线索人员数量 和各年龄阶段人数
     *
     * @return
     */
    @GetMapping("/findCounByAgeAndAjlb")
    @ResponseBody
    public List<Map<String, Object>> findCounByAgeAndAjlb() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        List<Map<String, Object>> allMap = new ArrayList<>();
        List<Map<String, Object>> list = clueService.queyCbrCountByAjlb(fyId);

        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        //统计各年龄阶段的相关人员数量
        int firstCount = clueService.queryCountByCardId(fyId, 20, 30);
        int secondCount = clueService.queryCountByCardId(fyId, 31, 40);
        int thirdCount = clueService.queryCountByCardId(fyId, 41, 50);
        int fourCount = clueService.queryCountByCardId(fyId, 51, 60);
        int fiveCount = clueService.queryMaxCountByCardId(fyId, 60);

        Map<String, Object> firstMap = new HashMap<>();
        Map<String, Object> secondMap = new HashMap<>();
        Map<String, Object> thirdMap = new HashMap<>();
        Map<String, Object> fourMap = new HashMap<>();
        Map<String, Object> fiveMap = new HashMap<>();

        List<Object> nndList = new ArrayList<>();
        firstMap.put("20-30", firstCount);
        secondMap.put("31-40", secondCount);
        thirdMap.put("41-50", thirdCount);
        fourMap.put("51-60", fourCount);
        fiveMap.put("60以上", fiveCount);
        nndList.add(firstMap);
        nndList.add(secondMap);
        nndList.add(thirdMap);
        nndList.add(fourMap);
        nndList.add(fiveMap);
        map.put("secondData", nndList);
        allMap.add(map);
        return allMap;
    }


    /**
     * 线索转入问题处置流程
     */
    @RequiresPermissions("module:clue:release")
    @GetMapping("/release")
    public String release(String clueId, String pid, String taskId, ModelMap mmap) {
        Clue clue = clueService.selectClueById(clueId);
        Process process = new Process();
        process.setStatus("0");
        mmap.put("clue", clue);
        mmap.put("pid", pid);
        mmap.put("taskId", taskId);
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(clue.getDubvioId());
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId());
        mmap.put("dubvioEvent", dubvioEvent);
        mmap.put("localCourtPerson", getlocalCourtPerson());
        mmap.put("userList", userList);
        return prefix + "/release";
    }


    /**
     * 开始申报
     *
     * @param clueId
     * @param approverName
     * @param approver
     * @param content
     * @return
     */
    @RequestMapping("/releaseClue")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult releaseClue(String clueId, String approverName, String approver, String content, String clueClassify, String taskId, String pid,String clueCbrName) {
        try {
            //流程定义的key

            String processDefinitionKey = "classifyProcess";
            Map<String, Object> map = new HashMap<String, Object>();
            Clue clue = clueService.selectClueById(clueId);
            map.put("isok", "true");
            map.put("clueClassify", clueClassify);
            if (StringUtils.isEmpty(clue.getJbxxAh())){
                map.put("title", clue.getDubvioStrategyName());
            }else {
                map.put("title", clue.getJbxxAh());
            }
            map.put("type", "线索处置分类");
            map.put("clueId", clue.getClueId());
            //意见
            map.put("content", content);
            //提交时间
            map.put("proposerTime", DateUtils.getTime());
            //申请人
            map.put("proposerName", ShiroUtils.getUser().getUserName());
            map.put("proposer", ShiroUtils.getUserId());
            //审批人
            map.put("approverName", approverName);
            map.put("approver", approver);
            map.put("handleUrl", "module/problemdisposal/fpClassify");

            Task task = null;
            //去创建新流程
            if (pid == null || pid.equals("")) {
                //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
                ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);
                //查询当前流程实例任务对象
                pid = pi.getId();
                task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
                runtimeService.updateBusinessKey(pid, clue.getClueId());
            } else {
                task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("pid", pid);
                paramsMap.put("taskId", task.getId());
                map.put("paramsMap", paramsMap);
            }

            //设置批注人
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "user#" + ShiroUtils.getUserId());
            //设置审批标识
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "isok#" + true);
            //设置审批意见
            taskService.addComment(task.getId(), task.getProcessInstanceId(), content == null ? "content#提交申请" : "content#" + content.replaceAll("#", ""));
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "date#" + DateUtils.getTime());
            //设置当前任务
            taskService.setAssignee(task.getId(), ShiroUtils.getUserId().toString());
            //提交任务并将流程变量放到任务中
            ruTaskService.compleTask("true", content, ShiroUtils.getUser().getUserName(), task.getId(), content, pid, approver, map);


            //clue.setClueCzjg(clueClassify);
            clue.setJbxxCbrId(clueCbrName);
            clue.setProcessStatus("1");
            clueService.updateClue(clue);
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return success();
    }

    private void saveDispositionHandle(String DeptName, String NodeFlag, String way, String Result, String clueId, String content,
                                       String clueClassify, Clue clue, Task task, String status, String createBy, String createTime) {
        DispositionHandle dispositionHandle = new DispositionHandle();
        if (dispositionHandleService.selectDeptById(clueId) == null) {
            dispositionHandle.setParentId("0");
            dispositionHandle.setDeptId(clue.getClueId());
            dispositionHandle.setDeptName(clue.getJbxxAh());
            dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
            /*dispositionHandle.setCreateTime("");*/
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setNodeId(task.getId());
            dispositionHandle.setCount(1);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.insertDispositionHandle(dispositionHandle);
        }

        //判断节点是否在数据中存在
        dispositionHandle.setClueId(clueId);
        dispositionHandle.setNodeFlag(NodeFlag);
        List<DispositionHandle> dispositionHandles = dispositionHandleService.selectDeptList(dispositionHandle);
        if (dispositionHandles.size() > 0) {
            dispositionHandle = new DispositionHandle();
            dispositionHandle.setParentId(clue.getClueId());
            dispositionHandle.setSuggestion(content);
            dispositionHandle.setDeptName(DeptName);
            dispositionHandle.setNodeType(clueClassify);
            dispositionHandle.setNodeFlag(NodeFlag);
            dispositionHandle.setNodeId(NodeFlag);
            dispositionHandle.setDeptId(StringUtils.getUUID());
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setCreateBy(createBy);
            dispositionHandle.setWay(way);
            dispositionHandle.setCount(1);
            dispositionHandle.setResult(Result);
            dispositionHandle.setStatus(status);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.updateDispositionHandle(dispositionHandle);
        }else {
            dispositionHandle = new DispositionHandle();
            dispositionHandle.setParentId(clue.getClueId());
            dispositionHandle.setSuggestion(content);
            dispositionHandle.setDeptName(DeptName);
            dispositionHandle.setNodeType(clueClassify);
            dispositionHandle.setNodeFlag(NodeFlag);
            dispositionHandle.setNodeId(NodeFlag);
            dispositionHandle.setDeptId(StringUtils.getUUID());
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setCreateBy(createBy);
            dispositionHandle.setWay(way);
            dispositionHandle.setCount(1);
            dispositionHandle.setResult(Result);
            dispositionHandle.setStatus(status);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.insertDispositionHandle(dispositionHandle);
        }
    }

    /**
     * 查询策略详细
     */
    @RequiresPermissions("module:clue:detail")
    @GetMapping("/detail")
    public String detail() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/data";
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allData")
    @GetMapping("/allData")
    public String allData(ModelMap mmap) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = sdf.format(date);
        String startDate = "2019-01-01";
        String table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjAll", "session=123&startDate=" + startDate + "&endDate=" + endDate);
        mmap.put("table", table);
        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);
        return "module/clue/allData";
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allDataByDate")
    @GetMapping("/allDataByDate")
    @ResponseBody
    public String allDataByDate(ModelMap mmap, String startDate, String endDate) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(date);
        if (StringUtils.isEmpty(startDate)) {

            startDate = "2019-01-01";

        }
        if (StringUtils.isEmpty(endDate)) {

            endDate = nowDate;
        }

        String table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjAll", "session=123&startDate=" + startDate + "&endDate=" + endDate);
        return table;
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allSecondData")
    @GetMapping("/allSecondData")
    public String allSecondData(ModelMap mmap, String jbfyId, String fyName, String startDate, String endDate) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(date);
        if (StringUtils.isEmpty(startDate)) {

            startDate = "2019-01-01";

        }
        if (StringUtils.isEmpty(endDate)) {

            endDate = nowDate;
        }
        String param = null;
        try {
            param = "session=123&jbfyId=" + jbfyId + "&fyName=" + URLEncoder.encode(fyName, "UTF-8") + "&startDate=" + startDate + "&endDate=" + endDate;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjByCourt", param);
        mmap.put("table", table);
        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);
        mmap.put("endDate", endDate);
        //经办法院id
        mmap.put("jbfyId", jbfyId);
        String fyNames = fyName + "各承办庭收结存统计";
        mmap.put("fyNames", fyNames);
        return "module/clue/allSecondData";
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allThirdData")
    @GetMapping("/allThirdData")
    public String allThirdData(ModelMap mmap, String jbfyId, String fyName, String courtId, String startDate, String endDate) {

        String param = null;
        try {
            param = "session=123&jbfyId=" + jbfyId + "&fyName=" + URLEncoder.encode(fyName, "UTF-8") + "&courtId=" + courtId + "&startDate=" + startDate + "&endDate=" + endDate;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjByPeople", param);
        mmap.put("table", table);
        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);
        mmap.put("jbfyId", jbfyId);
        mmap.put("courtId", courtId);
        String fyNames = fyName + "各承办人收结存统计";
        mmap.put("fyNames", fyNames);
        return "module/clue/allThirdData";
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allNumberData")
    @GetMapping("/allNumberData")
    public String allNumberData(ModelMap mmap, String jbfyId, String status, String totalCount, String startDate, String endDate) {
        int tatalCounts = Integer.parseInt(totalCount);
        int totalPageNum = (tatalCounts + 10 - 1) / 10;
        mmap.put("tatalCounts", tatalCounts);
        mmap.put("totalPageNum", totalPageNum);
        mmap.put("jbfyId", jbfyId);
        mmap.put("status", status);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(date);
        if (StringUtils.isEmpty(startDate)) {

            startDate = "2019-01-01";

        }
        if (StringUtils.isEmpty(endDate)) {

            endDate = nowDate;
        }
        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);

        return "module/clue/allNumberData";
    }


    @RequiresPermissions("module:clue:allNumberDataPage")
    @GetMapping("/allNumberDataPage")
    @ResponseBody
    public String allNumberDataPage(ModelMap mmap, String jbfyId, String status, String page, String startDate, String endDate) {

        String fyName = null;

//        int tatalCounts=Integer.parseInt(totalCount);
//
//        int totalPageNum = (tatalCounts  +  10  - 1) / 10;
//        mmap.put("tatalCounts",tatalCounts);
//        mmap.put("totalPageNum",totalPageNum);
//        mmap.put("jbfyId",jbfyId);
//        mmap.put("status",status);
//        mmap.put("page",page);

        if (jbfyId.contains(",")) {
            fyName = "南阳市中级人民法院及所辖法院";

        } else {
            fyName = iCourtOrganService.queryDeptNameByCid(jbfyId, "1");
        }

        String table = null;
        try {
            table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjByNumber", "session=123&jbfyId=" + jbfyId + "&status=" + status + "&fyName=" + URLEncoder.encode(fyName, "UTF-8") + "&page=" + page + "&startDate=" + startDate + "&endDate=" + endDate);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return table;
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allSecondNumberData")
    @GetMapping("/allSecondNumberData")
    public String allSecondNumberData(ModelMap mmap, String jbfyId, String status, String courtId, String isAll, String startDate, String endDate, String totalCount) {


        int tatalCounts = Integer.parseInt(totalCount);

        int totalPageNum = (tatalCounts + 10 - 1) / 10;
        mmap.put("tatalCounts", tatalCounts);
        mmap.put("totalPageNum", totalPageNum);
        mmap.put("jbfyId", jbfyId);
        mmap.put("status", status);
        mmap.put("courtId", courtId);
        mmap.put("isAll", isAll);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(date);
        if (StringUtils.isEmpty(startDate)) {
            startDate = "2019-01-01";
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = nowDate;
        }

        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);

//        String fyName=iCourtOrganService.queryDeptNameByCid(jbfyId,"1");
//
//        String courtName=null;
//        if(isAll.equals("1")){
//            courtName=fyName;
//        }else {
//            courtName=iCourtOrganService.queryDeptNameByCid(courtId,"2");
//
//            if (StringUtils.isEmpty(courtName)){
//                courtName= fyName;
//            }
//        }
//        String table= null;
//        try {
//            table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchCounrtTjByNumber","session=123&jbfyId="+jbfyId+"&status="+status+"&courtId="+courtId+"&courtName="+ URLEncoder.encode(courtName,"UTF-8")+"&fyName="+URLEncoder.encode(fyName,"UTF-8")+"&isAll="+isAll+"&page="+page);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        mmap.put("table",table);
        return "module/clue/allSecondNumberData";
    }

    /**
     *
     */
    @RequiresPermissions("module:clue:allSecondNumberDataPage")
    @GetMapping("/allSecondNumberDataPage")
    @ResponseBody
    public String allSecondNumberDataPage(ModelMap mmap, String jbfyId, String status, String courtId, String isAll, String page, String startDate, String endDate) {


        String fyName = iCourtOrganService.queryDeptNameByCid(jbfyId, "1");

        String courtName = null;
        if (isAll.equals("1")) {
            courtName = fyName;
        } else {
            courtName = iCourtOrganService.queryDeptNameByCid(courtId, "2");

            if (StringUtils.isEmpty(courtName)) {
                courtName = fyName;
            }
        }
        String table = null;
        try {
            table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchCounrtTjByNumber", "session=123&jbfyId=" + jbfyId + "&status=" + status + "&courtId=" + courtId + "&courtName=" + URLEncoder.encode(courtName, "UTF-8") + "&fyName=" + URLEncoder.encode(fyName, "UTF-8") + "&isAll=" + isAll + "&page=" + page + "&startDate=" + startDate + "&endDate=" + endDate);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return table;
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allThirdNumberData")
    @GetMapping("/allThirdNumberData")
    public String allThirdNumberData(ModelMap mmap, String jbfyId, String status, String courtId, String ryId, String isAll, String totalCount, String startDate, String endDate) {


        String courtName = iCourtOrganService.queryDeptNameByCid(courtId, "2");
        String ryName = iCourtOrganService.queryDeptNameByCid(ryId, "3");

        int tatalCounts = Integer.parseInt(totalCount);

        int totalPageNum = (tatalCounts + 10 - 1) / 10;

        if (isAll.equals("1")) {
            ryName = courtName;
        }

        mmap.put("courtName", courtName);
        mmap.put("ryName", ryName);
        mmap.put("jbfyId", jbfyId);
        mmap.put("status", status);
        mmap.put("ryId", ryId);
        mmap.put("courtId", courtId);
        mmap.put("isAll", isAll);
        mmap.put("tatalCounts", tatalCounts);
        mmap.put("totalPageNum", totalPageNum);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(date);
        if (StringUtils.isEmpty(startDate)) {
            startDate = "2019-01-01";
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = nowDate;
        }

        mmap.put("startDate", startDate);
        mmap.put("endDate", endDate);


        return "module/clue/allThirdNumberData";
    }


    /**
     *
     */
    @RequiresPermissions("module:clue:allThirdNumberDataPage")
    @GetMapping("/allThirdNumberDataPage")
    @ResponseBody
    public String allThirdNumberDataPage(ModelMap mmap, String jbfyId, String status, String courtId, String ryId, String isAll, String page, String startDate, String endDate) {


        String courtName = iCourtOrganService.queryDeptNameByCid(courtId, "2");
        String ryName = iCourtOrganService.queryDeptNameByCid(ryId, "3");

        if (isAll.equals("1")) {
            ryName = courtName;
        }
        String table = null;
        try {
            table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchPeopleTjByNumber", "session=123&jbfyId=" + jbfyId + "&status=" + status + "&courtId=" + courtId + "&ryName=" + URLEncoder.encode(ryName, "UTF-8") + "&ryId=" + ryId + "&isAll=" + isAll + "&page=" + page + "&startDate=" + startDate + "&endDate=" + endDate);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 统计所有的策略的名称
     *
     * @return
     */
    @RequiresPermissions("module:clue:detail")
    @PostMapping("/queryCountStrategyName")
    @ResponseBody
    public TableDataInfo queryCountStrategyName() {
        startPage();
        List<Map<String, Object>> firstList = clueService.queryAjlbCountByStrategyName();

        return getDataTable(firstList);
    }

    /**
     * 可疑点线索统计数据接口
     *
     * @return
     * @author zhang 2019-12-09
     */
    @RequiresPermissions("module:clue:detail")
    @PostMapping("/getSuspiciousCluesCount")
    @ResponseBody
    public  TableDataInfo getSuspiciousCluesCount(DubvioEvent dubvioEvent){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Map<String,Object>> lists=iStatisticsService.getSuspiciousCluesCount(dubvioEvent,pageDomain);
        return getDataTable(lists);
    }


    /**
     * 查询线索详细
     */

    @GetMapping("/clueDetail")
    public String clueDetail(String id, String name, ModelMap mmap) {

        mmap.put("id", id);
        mmap.put("name", name);

        return "module/clue/clueDetail";
    }


    /**
     * 查询承办庭线索详细
     */

    @GetMapping("/cbtDetail")
    public String cbtDetail(String id, ModelMap mmap) {

        mmap.put("id", id);
        return "module/clue/cbtDetail";
    }

    /**
     * 查询日期线索详细ModelMap mmap
     */

    @GetMapping("/clueDateDetail")
    public String clueDateDetail(String time, String name, ModelMap mmap) {

        mmap.put("time", time);
        mmap.put("name", name);

        return "module/clue/clueDateDetail";
    }


    /**
     * 统计所有的策略的名称
     *
     * @return
     */
    //@RequiresPermissions("module:clue:clueDetailByIdAndAjlb")
    @PostMapping("/clueDetailByIdAndAjlb")
    @ResponseBody
    public TableDataInfo clueDetailByIdAndAjlb(String name, String id) {
        startPage();
        List<Map> firstList = null;
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(name)) {

            if (name.equals("1")) {
                name = "民事";
                firstList = clueService.selectClueListByAjlbAndStrategyId(name, id);
            } else if (name.equals("2")) {
                name = "刑事";
                firstList = clueService.selectClueListByAjlbAndStrategyId(name, id);
            } else if (name.equals("3")) {
                name = "行政";
                firstList = clueService.selectClueListByAjlbAndStrategyId(name, id);
            } else if (name.equals("4")) {
                name = "执行";
                firstList = clueService.selectClueListByAjlbAndStrategyId(name, id);
            } else if (name.equals("5")) {
                firstList = clueService.selectClueListByStrategyId(id);
            }
        }
        // = clueService.selectClueListByAjlbAndStrategyId(name, id);

        return getDataTable(firstList);
    }

    /**
     * 线索详情列表
     *
     * @author zhang 2019/12/10
     * @return
     */
    @PostMapping("/getClueDetail")
    @ResponseBody
    public TableDataInfo getClueDetail(String name, String id)
    {
        startPage();
        List<Map> clueDetailList = null;
        if(StringUtils.isNotEmpty(name)){
            switch (name){
                case "1":
                    name = "民事";
                    break;
                case "2" :
                    name = "刑事";
                    break;
                case "3":
                    name = "行政";
                    break;
                case "4" :
                    name = "执行";
                    break;
                default :
                    name = "";
                    break;
            }
        }

        if(StringUtils.isNotEmpty(id)){
            clueDetailList = iStatisticsService.getClueDetailList(name, id);
        }

        return getDataTable(clueDetailList);
    }


    /**
     * 根据承办庭id统计承办庭的线索详情
     *
     * @return
     */
   // @RequiresPermissions("module:clue:cbtDetailById")
    @PostMapping("/cbtDetailById")
    @ResponseBody
    public TableDataInfo cbtDetailById(String id) {
        startPage();
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        List<Map> firstList = null;
        if (StringUtils.isNotEmpty(id)) {
            firstList = clueService.selectClueListByCbtId(id,fyId);

        }
        // = clueService.selectClueListByAjlbAndStrategyId(name, id);
        return getDataTable(firstList);
    }


    /**
     * 修改可疑违纪事件
     */
    @GetMapping("/edits")
    public String edits(String clueId, ModelMap mmap) {

        Clue clue = clueService.selectClueById(clueId);
        //DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
        if (StringUtils.isNotEmpty(clue.getJbxxAjbh())) {
            DubvioEventDetail dubvioEventDetail = dubvioEventDetailService.selectDubvioEventDetailById(clue.getJbxxAjbh());
            if (dubvioEventDetail == null) {
                dubvioEventDetail = new DubvioEventDetail();
            }
            mmap.put("dubvioEventDetail", dubvioEventDetail);

        }
        return "module/dubvioEvent/edits";
    }

    /**
     * 承办庭线索统计
     */
    @RequiresPermissions("module:clue:cbtTj")
    @GetMapping("/cbtTj")
    public String cbtTj() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/cbtData";
    }


    /**
     * 被反映人情况分布情况统计
     */
    @RequiresPermissions("module:clue:fyrTj")
    @GetMapping("/fyrTj")
    public String fyrTj() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/fyrTj";
    }


    /**
     * 被反映人情况分布情况统计
     */
    @RequiresPermissions("module:clue:fyCzTj")
    @GetMapping("/fyCzTj")
    public String fyCzTj() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/fyCzTj";
    }


    /**
     * 线索来源统计
     */
    @RequiresPermissions("module:clue:clueSource")
    @GetMapping("/clueSource")
    public String clueSource() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/clueSource";
    }


    /**
     * 跳转页面
     */
    @RequiresPermissions("module:clue:clueMonthTj")
    @GetMapping("/clueMonthTj")
    public String clueMonthTj() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/clue/clueMonthData";
    }


    /**
     * 跳转页面
     */

    @GetMapping("/clueAllMonthTj")
    public String clueAllMonthTj(String clueTime, String name, ModelMap mmap) {
        mmap.put("clueTime", clueTime);
        mmap.put("name", name);
        return "module/clue/clueDateDetail";
    }


    /**
     * 跳转页面
     */
    //@RequiresPermissions("module:clue:clueSourceMonthTj")
    @GetMapping("/clueSourceMonthTj")
    public String clueSourceMonthTj(String clueTime, String name, ModelMap mmap) {
        mmap.put("clueTime", clueTime);
        mmap.put("name", name);
        return "module/clue/clueSourceDetail";
    }


    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    @RequiresPermissions("module:clue:cbtTj")
    @PostMapping("/queryCountByAjlbAndCbtDetail")
    @ResponseBody
    public TableDataInfo queryCountByAjlbAndCbtDetail() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        startPage();
        Map<String, Object> map = new HashMap<>();
        //统计各个承办庭线索数量
        List<Map<String, Object>> countList = clueService.queryCountByAjlbAndCbt(fyId);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {
                long count = (long) countList.get(i).get("count");
                String cbtName = null;
                if (StringUtils.isNull(countList.get(i).get("jbxx_cbt_name"))) {
                    cbtName = "其他";
                    map.put("jbxx_cbt_name", cbtName);
                    map.put("count", count);
                    map.put("jbxx_cbt_id", "1");
                    countList.add(map);
                }
//            firstList.add(count);
//            secondList.add(cbtName);
            }
        }
//        allList.add(firstList);
//        allList.add(secondList);
        return getDataTable(countList);
    }


    /**
     * 根据日期统计各案件类别的线索数量
     *
     * @return
     */
    @RequiresPermissions("module:clue:clueMonthTj")
    @PostMapping("/queryCountByMonth")
    @ResponseBody
    public TableDataInfo queryCountByMonth() {

        List<Map> list = clueService.queryCountByMonth();

        return getDataTable(list);

    }

    /**
     * 月度线索统计数据接口
     * @author zhang 2019/12/11
     * @return
     */
    @RequiresPermissions("module:clue:clueMonthTj")
    @PostMapping("/getClueCountByMonth")
    @ResponseBody
    public TableDataInfo getClueCountByMonth()
    {
        List<Map> clueCountList = iStatisticsService.getClueCountByMonth();
        return getDataTable(clueCountList);
    }


    /**
     * 统计所有日期的线索的名称
     *
     * @return
     */
    //@RequiresPermissions("module:clue:clueDateDetail")
    @PostMapping("/clueDateDetail")
    @ResponseBody
    public TableDataInfo clueDateDetail(String name, String clueTime) {
        startPage();
        List<Map> firstList = null;
        if (StringUtils.isNotEmpty(clueTime) && StringUtils.isNotEmpty(name)) {

            if (name.equals("1")) {
                name = "民事";
                firstList = clueService.queryClueByAjlbAndTime(name, clueTime);
            } else if (name.equals("2")) {
                name = "刑事";
                firstList = clueService.queryClueByAjlbAndTime(name, clueTime);
            } else if (name.equals("3")) {
                name = "行政";
                firstList = clueService.queryClueByAjlbAndTime(name, clueTime);
            } else if (name.equals("4")) {
                name = "执行";
                firstList = clueService.queryClueByAjlbAndTime(name, clueTime);
            } else if (name.equals("5")) {
                firstList = clueService.queryClueByTime(clueTime);
            }
        }

        return getDataTable(firstList);
    }

    /**
     * 根据日期和案件类别查看线索详情
     * @author zhang date:2019/12/11
     * @return
     */
    @PostMapping("/clueDetailByDate")
    @ResponseBody
    public TableDataInfo getClueDetailByDate(String name, String clueTime)
    {

        startPage();
        List<Map> clueDetailList = null;
        switch (name){
            case "1":
                name = "民事";
                break;
            case "2" :
                name = "刑事";
                break;
            case "3":
                name = "行政";
                break;
            case "4" :
                name = "执行";
                break;
            default :
                name = "";
                break;
        }
        if(StringUtils.isNotEmpty(clueTime)){
            clueDetailList = iStatisticsService.getClueDetailListByDate(name,clueTime);
        }

        return getDataTable(clueDetailList);

    }



    /**
     * 统计所有日期的线索的名称
     *
     * @return
     */
    @RequiresPermissions("module:clue:clueSource")
    @PostMapping("/queryAllCourceByDate")
    @ResponseBody
    public TableDataInfo queryAllCourceByDate() {
        startPage();
        List<Map> firstList = clueService.queryAllCourceByDate();


        return getDataTable(firstList);
    }

    /**
     * 根据每个月统计线索来源数据接口
     * @author zhang date:2019/12/12
     * @return
     */
    @RequiresPermissions("module:clue:clueSource")
    @PostMapping("getAllClueSourceByMonth")
    @ResponseBody
    public TableDataInfo getAllClueSourceByMonth()
    {
        startPage();
        List<Map> clueSourceList = null;
        clueSourceList = iStatisticsService.getAllClueSourceByMonth();
        return getDataTable(clueSourceList);
    }


    /**
     * 统计所有日期的线索的名称
     *
     * @return
     */
    @PostMapping("/clueSourceDetail")
    @ResponseBody
    public TableDataInfo clueSourceDetail(String name, String clueTime) {
        startPage();
        List<Map> firstList = null;
        if (StringUtils.isNotEmpty(clueTime) && StringUtils.isNotEmpty(name)) {

            if (name.equals("1") || name.equals("2")) {

                firstList = clueService.querySourceByDate(name, clueTime);
            } else if (name.equals("3")) {
                name = "1";
                firstList = clueService.querySourceDealByDate(name, clueTime);
            } else if (name.equals("4")) {
                name = "2";
                firstList = clueService.querySourceDealByDate(name, clueTime);
            } else if (name.equals("5")) {
                firstList = clueService.queryClueByTime(clueTime);
            }
        }
        return getDataTable(firstList);
    }

    /**
     * 统计各处置线索的量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryEveClueCount")
    @GetMapping("/queryEveClueCount")
    @ResponseBody
    public Map<String, Object> queryEveClueCount() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        Map<String, Object> map = new HashMap<>();
        int firstCount = clueService.queryEveCount(fyId, 0);
        int secondCount = clueService.queryEveCount(fyId, 1);
        int thirdCount = clueService.queryAllCounts(fyId);

        map.put("total", thirdCount);
        map.put("finishCount", firstCount);
        map.put("czzCount", secondCount);

        return map;
    }

    /**
     * 统计完成各阶段线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryFinishCzjg")
    @GetMapping("/queryFinishCzjg")
    @ResponseBody
    public List<Map> queryFinishCzjg() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        List<Map> list = clueService.queryFinishCzjg(fyId);

        List<Map> czjgList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> secondmap = new HashMap<>();
        Map<String, Object> thirdmap = new HashMap<>();
        Map<String, Object> forthmap = new HashMap<>();


        if (StringUtils.isEmpty(list)) {
            map.put("czjg", "予以了结");
            map.put("count", 0);
            secondmap.put("czjg", "谈话提醒");
            secondmap.put("count", 0);
            thirdmap.put("czjg", "移交司法");
            thirdmap.put("count", 0);
            forthmap.put("czjg", "暂存待查");
            forthmap.put("count", 0);
            czjgList.add(map);
            czjgList.add(secondmap);
            czjgList.add(thirdmap);
            czjgList.add(forthmap);
            return czjgList;
        }
        return list;
    }

    /**
     * 统计完成澄清和问题线索数量
     *
     * @return
     */
    @GetMapping("/queryFinishByCbtName")
    @ResponseBody
    public List<Map> queryFinishByCbtName() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        List<Map> list = new ArrayList<>();

        List<Object> firstList = new ArrayList<>();
        List<Object> secondList = new ArrayList<>();
        List<Map<String, Object>> cbtList = clueService.queryCountByAjlbAndCbt(fyId);

        List<Object> thirdList = new ArrayList<>();
        if (!StringUtils.isNull(cbtList)) {
            for (int i = 0; i < cbtList.size(); i++) {


                if (!StringUtils.isNull(cbtList.get(i).get("jbxx_cbt_name"))){
                    String cbtName = cbtList.get(i).get("jbxx_cbt_name").toString();

                    int count = clueService.queryCqCount(fyId, cbtName);
                    int secondCount = clueService.queryQuestionCount(fyId, cbtName);
                    firstList.add(count);
                    secondList.add(secondCount);
                    thirdList.add(cbtName);
                }


            }
        }
        Map<String, Object> map = new HashMap<>();

        map.put("cq", firstList);
        map.put("question", secondList);
        map.put("cbtList", thirdList);
        list.add(map);

        return list;
    }


    /**
     * 根据案件类别统计当前时间前半年各个月份的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCountByCzzAndMonth")
    @GetMapping("/queryCountByCzzAndMonth")
    @ResponseBody
    public List<Object> queryCountByCzzAndMonth() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();
        List<Integer> fourCountList = new ArrayList<>();
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {

            String time = dateList.get(i).toString();
            //根据月份参数和处置中各阶段统计线索数量（谈话函询  初步核实 立案调查 审理）
            int count = clueService.queryCzzCountByMonth(fyId, time, "4");
            int secondCount = clueService.queryCzzCountByMonth(fyId, time, "3");
            int thirdCount = clueService.queryCzzCountByMonth(fyId, time, "5");
            int fourCount = clueService.queryCzzCountByMonth(fyId, time, "6");
            countList.add(count);
            secondCountList.add(secondCount);
            thirdCountList.add(thirdCount);
            fourCountList.add(fourCount);
        }
        map.put("thhx", countList);
        map.put("cbhs", secondCountList);
        map.put("ladc", thirdCountList);
        map.put("sl", fourCountList);
        allList.add(map);
        allList.add(dateList);
        return allList;
    }

    /**
     * 根据案件类别统计已完成各年龄阶段的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:queryCzzCountByAge")
    @GetMapping("/queryCzzCountByAge")
    @ResponseBody
    public List<Object> queryCzzCountByAge() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        List<Map<String, Object>> list = clueService.queryAllAjlb(fyId);
        List<Object> allList = new ArrayList<>();
        if (!StringUtils.isNull(list)) {

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                List<Object> dataList = new ArrayList<>();
                String ajlb = list.get(i).get("jbxx_ajlb").toString();

                int firstCount = clueService.queryCzzCountByAge(fyId, ajlb, 20, 30);
                int secondCount = clueService.queryCzzCountByAge(fyId, ajlb, 31, 40);
                int thirdCount = clueService.queryCzzCountByAge(fyId, ajlb, 41, 50);
                int fourCount = clueService.queryCzzCountByAge(fyId, ajlb, 51, 60);
                int fiveCount = clueService.queryMaxCountByAge(fyId, ajlb, 60);
                dataList.add(firstCount);
                dataList.add(secondCount);
                dataList.add(thirdCount);
                dataList.add(fourCount);
                dataList.add(fiveCount);
                map.put("ajlb", ajlb);
                map.put("data", dataList);
                allList.add(map);
            }
        }
        return allList;

    }

    /**
     * 据风险等级和时间统计完成线索数量
     *
     * @return
     */
    @GetMapping("/queryFinishRiskCount")
    @ResponseBody
    public List<Object> queryFinishRiskCount() {


        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();

        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {

            String time = dateList.get(i).toString();
            //根据月份参数和风险统计线索数量（1低风险 2中风险 3高风险）
            int count = clueService.queryFinishRiskCount(fyId, time, "1");
            int secondCount = clueService.queryFinishRiskCount(fyId, time, "2");
            int thirdCount = clueService.queryFinishRiskCount(fyId, time, "3");

            countList.add(count);
            secondCountList.add(secondCount);
            thirdCountList.add(thirdCount);

        }
        map.put("lowRisk", countList);
        map.put("centerRisk", secondCountList);
        map.put("highRisk", thirdCountList);

        allList.add(map);
        allList.add(dateList);
        return allList;

    }

    /**
     * 据案件类别和部门统计完成线索数量
     *
     * @return
     */
    @GetMapping("/queryFinishClueByDeptAndAjlb")
    @ResponseBody
    public List<Object> queryFinishClueByDeptAndAjlb() {
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        List<Map<String, Object>> cbtList = clueService.queryCountByAjlbAndCbt(fyId);
        List<Object> allList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();
        List<Integer> fourCountList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        List<Object> cbtNameList = new ArrayList<>();

        if (!StringUtils.isNull(cbtList)) {

            for (int i = 0; i < cbtList.size(); i++) {

                if (!StringUtils.isNull(cbtList.get(i).get("jbxx_cbt_name"))){

                    String cbtName = cbtList.get(i).get("jbxx_cbt_name").toString();
                    cbtNameList.add(cbtName);
                    int count = clueService.queryFinishClueByDeptAndAjlb(fyId, cbtName, "刑事");
                    int secondCount = clueService.queryFinishClueByDeptAndAjlb(fyId, cbtName, "民事");
                    int thirdCount = clueService.queryFinishClueByDeptAndAjlb(fyId, cbtName, "行政");
                    int fourCount = clueService.queryFinishClueByDeptAndAjlb(fyId, cbtName, "执行");
                    countList.add(count);
                    secondCountList.add(secondCount);
                    thirdCountList.add(thirdCount);
                    fourCountList.add(fourCount);
                }
            }
        }
        map.put("xs", countList);
        map.put("ms", secondCountList);
        map.put("xz", thirdCountList);
        map.put("zx", fourCountList);
        map.put("cbt", cbtNameList);
        allList.add(map);
        return allList;
    }


    /**
     * 据风险等级和时间统计完成线索数量
     *
     * @return
     */
    @GetMapping("/queryRiskCountByMonth")
    @ResponseBody
    public List<Object> queryRiskCountByMonth() {


        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        List<Integer> thirdCountList = new ArrayList<>();

        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {

            String time = dateList.get(i).toString();
            //根据月份参数和风险统计线索数量（1低风险 2中风险 3高风险）
            int count = clueService.queryRiskCountByMonth(fyId, time, "1");
            int secondCount = clueService.queryRiskCountByMonth(fyId, time, "2");
            int thirdCount = clueService.queryRiskCountByMonth(fyId, time, "3");

            countList.add(count);
            secondCountList.add(secondCount);
            thirdCountList.add(thirdCount);

        }
        map.put("lowRisk", countList);
        map.put("centerRisk", secondCountList);
        map.put("highRisk", thirdCountList);

        allList.add(map);
        allList.add(dateList);
        return allList;

    }

    @RequiresPermissions("module:clue:getAddress")
    @GetMapping("/getAddress")
    @ResponseBody
    public String getAddress(String ajbhs, String ajlbs, String jbfyId) {
        String address = "http://143.0.10.23/ajxq/np/jsp/main.jsp?CBh=" + ajbhs + "&NAjlb=" + ajlbs + "&NLx=4&CBhLcsl=&sysName=tjfx&userId=144376309&corpId=" + jbfyId;
        return address;
    }



    /**
     * 各个法院页面解析接口
     */
    @GetMapping("/allTjData")
    public void allTjData() {

        System.out.println(DateUtils.getTime()+"------------获取法院页面开始-----------------");
        String table = null;
        List<String> arrayList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        String id = null;
        String value = null;
        List<String> idList = null;
        List<String> valueList = null;
        List<String> secondArrayList = null;
        try {
            table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjAllData", "session=123");
            if (StringUtils.isNotEmpty(table)) {
                JSONObject jsonObject = JSONObject.parseObject(table);
                Map map = (Map) jsonObject;
                id = map.get("id").toString();
                id = id.replace("[", "");
                id = id.replace("]", "");
                value = map.get("value").toString();
                idList = Arrays.asList(id.split(","));
                valueList = Arrays.asList(value.split(","));
                arrayList = new ArrayList(idList);
                secondArrayList = new ArrayList(valueList);
                Fytj tj = new Fytj();
                for (int i = 0; i < arrayList.size(); i++) {
                    int j = i * 9;
                    String jbfyName = secondArrayList.get(j + 1).substring(1, secondArrayList.get(j + 1).length() - 1);
                    String jbfyId = arrayList.get(i).substring(1, arrayList.get(i).length() - 1);
                    String jc = secondArrayList.get(j + 2).substring(1, secondArrayList.get(j + 2).length() - 1);
                    String xs = secondArrayList.get(j + 3).substring(1, secondArrayList.get(j + 3).length() - 1);
                    String wj = secondArrayList.get(j + 4).substring(1, secondArrayList.get(j + 4).length() - 1);
                    String yj = secondArrayList.get(j + 5).substring(1, secondArrayList.get(j + 5).length() - 1);
                    String count = secondArrayList.get(j + 6).substring(1, secondArrayList.get(j + 6).length() - 1);
                    tj.setJbfyId(jbfyId);
                    tj.setJbfyName(jbfyName);
                    tj.setJc(jc);
                    tj.setXs(xs);
                    tj.setWj(wj);
                    tj.setYj(yj);
                    tj.setCount(count);
                    tj.setCountTime(sdf.format(new Date()));
                    //tj.setCountTime("2019-06");
                    fytjService.insertFytj(tj);
                }
            }else {
                log.info("连接失败！！！");
            }
        } catch (Exception e) {
            log.info("*****数据提取失败*******");
            e.printStackTrace();
        }
        log.info("*****数据提取成功*******");
    }



    /**
     * 各个法院下各个庭页面解析接口
     */
    @GetMapping("/allTjCourtData")
    public void allTjCourtData() {

        String table = null;
        List<String> arrayList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Dept dept = new Dept();
        dept.setDeptType("0");
        List<Dept> thirdList = deptService.selectDeptList(dept);

        String id = null;
        String value = null;
        List<String> idList = null;
        List<String> valueList = null;
        List<String> secondArrayList = null;

        for (Dept dept1 : thirdList) {
            String jbfyId = dept1.getDeptId().toString();

            String fyName = iCourtOrganService.queryDeptNameByCid(jbfyId, "1");

            try {
                table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjByCourtData", "session=123&jbfyId=" + jbfyId);
                Cbttj cbttj = new Cbttj();
                if (StringUtils.isNotEmpty(table)) {
                    JSONObject jsonObject = JSONObject.parseObject(table);
                    Map map = (Map) jsonObject;
                    id = map.get("id").toString();
                    id = id.replace("[", "");
                    id = id.replace("]", "");
                    value = map.get("value").toString();
                    idList = Arrays.asList(id.split(","));
                    valueList = Arrays.asList(value.split(","));
                    arrayList = new ArrayList(idList);
                    secondArrayList = new ArrayList(valueList);
                    for (int i = 0; i < arrayList.size(); i++) {
                        int j = i * 9;
                        String cbtId = arrayList.get(i).substring(1, arrayList.get(i).length() - 1);

                        String cbtName = secondArrayList.get(j + 1).substring(1, secondArrayList.get(j + 1).length() - 1);
                        String jc = secondArrayList.get(j + 2).substring(1, secondArrayList.get(j + 2).length() - 1);
                        String xs = secondArrayList.get(j + 3).substring(1, secondArrayList.get(j + 3).length() - 1);
                        String wj = secondArrayList.get(j + 4).substring(1, secondArrayList.get(j + 4).length() - 1);
                        String yj = secondArrayList.get(j + 5).substring(1, secondArrayList.get(j + 5).length() - 1);
                        String count = secondArrayList.get(j + 6).substring(1, secondArrayList.get(j + 6).length() - 1);

                        cbttj.setCbtId(cbtId);
                        cbttj.setCbtName(cbtName);
                        cbttj.setJbfyName(fyName);
                        cbttj.setJc(jc);
                        cbttj.setXs(xs);
                        cbttj.setWj(wj);
                        cbttj.setYj(yj);
                        cbttj.setCount(count);
                        cbttj.setCountTime(sdf.format(new Date()));
                        //cbttj.setCountTime("2019-05");
                        cbttj.setJbfyId(jbfyId);
                        cbttjService.insertCbttj(cbttj);
                    }
                }
            } catch (Exception e) {
                System.out.println("*****数据提取失败*******");
                e.printStackTrace();
            }
        }
        System.out.println("*****数据提取成功*******");
    }


    /**
     * 各个法院下各个庭各个人页面解析接口
     */
    @GetMapping("/allTjPeopleData")
    public void allTjPeopleData() {

        String table = null;
        List<String> idList = null;
        List<String> valueList = null;
        List<String> arrayList = null;
        List<String> secondArrayList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Dept dept = new Dept();
        dept.setDeptType("0");
        List<Dept> thirdList = deptService.selectDeptList(dept);
        for (Dept dept1 : thirdList) {
            String jbfyId = dept1.getDeptId().toString();

            String fyName = iCourtOrganService.queryDeptNameByCid(jbfyId, "1");
            List<CourtOrgan> secondList = iCourtOrganService.selectCourtOrganByParentId(jbfyId);
            for (CourtOrgan courtOrgan : secondList) {
                String courtId = courtOrgan.getCid();
                String cbtName = iCourtOrganService.queryDeptNameByCid(courtId, "2");
                String id = null;
                String value = null;
                try {
                    table = HttpUtils.sendGet("http://localhost:8080/safety/ajtj/searchTjByPeopleData", "session=123&jbfyId=" + jbfyId + "&courtId=" + courtId);
                    if (StringUtils.isNotEmpty(table)) {
                        JSONObject jsonObject = JSONObject.parseObject(table);
                        Map map = (Map) jsonObject;
                        id = map.get("id").toString();
                        id = id.replace("[", "");
                        id = id.replace("]", "");
                        value = map.get("value").toString();
                        idList = Arrays.asList(id.split(","));
                        valueList = Arrays.asList(value.split(","));
                        arrayList = new ArrayList(idList);
                        secondArrayList = new ArrayList(valueList);
                        Rytj rytj = new Rytj();
                        for (int i = 0; i < arrayList.size(); i++) {
                            String ryId = arrayList.get(i).substring(1, arrayList.get(i).length() - 1);
                            rytj.setRyId(ryId);
                            int j = i * 9;
                            String name = secondArrayList.get(j + 1).substring(1, secondArrayList.get(j + 1).length() - 1);
                            String jc = secondArrayList.get(j + 2).substring(1, secondArrayList.get(j + 2).length() - 1);
                            String xs = secondArrayList.get(j + 3).substring(1, secondArrayList.get(j + 3).length() - 1);
                            String wj = secondArrayList.get(j + 4).substring(1, secondArrayList.get(j + 4).length() - 1);
                            String yj = secondArrayList.get(j + 5).substring(1, secondArrayList.get(j + 5).length() - 1);
                            String count = secondArrayList.get(j + 6).substring(1, secondArrayList.get(j + 6).length() - 1);
                            rytj.setRyName(name);
                            rytj.setJc(jc);
                            rytj.setXs(xs);
                            rytj.setWj(wj);
                            rytj.setYj(yj);
                            rytj.setCount(count);
                            rytj.setJbfyId(jbfyId);
                            rytj.setCbtId(courtId);
                            rytj.setJbfyName(fyName);
                            rytj.setCbtName(cbtName);
                            rytj.setCbtId(courtId);
                            rytj.setCountTime(sdf.format(new Date()));
                            //rytj.setCountTime("2019-05");
                            rytjService.insertRytj(rytj);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("*****数据提取失败*******");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("*****数据提取成功*******");
    }

    @PostMapping("/map")
    @ResponseBody
    public List<Map<String, Object>> map() {
        return clueService.map();
    }

    @PostMapping("/map_two")
    @ResponseBody
    public List<Map<String, Object>> map_two() {
        return clueService.map2();
    }


    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    @RequiresPermissions("module:clue:fyrTj")
    @PostMapping("/queryFyrInfo")
    @ResponseBody
    public TableDataInfo queryFyrInfo() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        startPage();
        Map<String, Object> map = new HashMap<>();
        //统计各个承办庭线索数量
        List<Map<String, Object>> countList = clueService.queryFyrInfoByFyy(fyId);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {

                Object cbrId = countList.get(i).get("jbxx_cbr_id");


                if (!StringUtils.isNull(cbrId)) {

                    String dept = clueService.queryDeptByRyId(cbrId.toString());
                    String fycs = clueService.queryFycs(cbrId.toString());
                    String age = clueService.queryAgeById(cbrId.toString());
                    countList.get(i).put("fycs", fycs);
                    countList.get(i).put("dept", dept);
                    countList.get(i).put("age", age);
                } else {
                    countList.get(i).put("fycs", "0");
                }


            }
        }

        return getDataTable(countList);
    }

    /**
     * 被反映人统计(线索；筛选部分)
     * @return
     * @author zhang
     * @date 2019-12-13
     */
    @RequiresPermissions("module:clue:fyrTj")
    @PostMapping("/getReflectedPeopleCount")
    @ResponseBody
    public TableDataInfo getReflectedPeopleCount()
    {
        String courtId = null;
        startPage();
        Map<String, Object> map = new HashMap<>();
        //统计各个承办庭线索数量
        List<Map> reflectedPeopleCountList = iStatisticsService.getReflectedPeopleCount();
        return getDataTable(reflectedPeopleCountList);
    }


    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:fyrTj")
    @PostMapping("/queryFyCzjg")
    @ResponseBody
    public TableDataInfo queryFyCzjg(Clue clue) {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        startPage();

        String startTime = null;
        String endTime = null;

        if (StringUtils.isNull(clue.getParams().get("startTime"))||clue.getParams().get("startTime").equals("")) {

            startTime = "2019-01-01";
        } else {
            startTime = clue.getParams().get("startTime").toString();
        }

        if (StringUtils.isNull(clue.getParams().get("endTime"))||clue.getParams().get("endTime").equals("")) {

            endTime = DateUtils.getDate();

        } else {
            endTime = clue.getParams().get("endTime").toString();
        }
        Map<String, Object> map = new HashMap<>();
        //统计各个法院线索数量
        List<Map<String, Object>> countList = clueService.queryCzjgByFyIds(startTime,endTime,fyId);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {

                Object jbfyId = countList.get(i).get("jbfy_id");


                if (!StringUtils.isNull(jbfyId)) {

                    String xs = clueService.queryXsByFyId(jbfyId.toString(), startTime, endTime, null);
                    String jc = clueService.queryJcByFyId(jbfyId.toString(), startTime, null);
                    String yj = clueService.queryYjByFyId(jbfyId.toString(), startTime, endTime,null);
                    String wj = clueService.queryWjByFyId(jbfyId.toString(), startTime, endTime,null);


                    countList.get(i).put("xs", xs);
                    countList.get(i).put("jc", jc);
                    countList.get(i).put("yj", yj);
                    countList.get(i).put("wj", wj);
                }


            }
        }

        return getDataTable(countList);
    }


    /**
     * 查询承办庭线索详细
     */

    @GetMapping("/cbtCzDetail")
    public String cbtCzDetail(String id,String startTime,String endTime, ModelMap mmap) {

        mmap.put("id", id);
        mmap.put("startTime", startTime);
        mmap.put("endTime", endTime);
        return "module/clue/cbtCzDetail";
    }


    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:fyrTj")
    @PostMapping("/queryCbtCzjg")
    @ResponseBody
    public TableDataInfo queryCbtCzjg(String startTime, String endTime, String id) {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        startPage();

        if (StringUtils.isEmpty(startTime)) {

            startTime = "2019-01-01";
        }

        if (StringUtils.isEmpty(endTime)) {

            endTime = DateUtils.getDate();

        }
        Map<String, Object> map = new HashMap<>();
        //统计各个法院线索数量
        List<Map<String, Object>> countList = clueService.queryCbtCzjgByCbtIds(id,startTime,endTime);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {

                Object cbtId = countList.get(i).get("jbxx_cbt_id");


                if (!StringUtils.isNull(cbtId)) {

                    String xs = clueService.queryXsByCbtId(cbtId.toString(), id, startTime, endTime);
                    String jc = clueService.queryJcByCbtId(cbtId.toString(), id, startTime);
                    String yj = clueService.queryYjByCbtId(cbtId.toString(), id, startTime, endTime);
                    String wj = clueService.queryWjByCbtId(cbtId.toString(), id, startTime, endTime);


                    countList.get(i).put("xs", xs);
                    countList.get(i).put("jc", jc);
                    countList.get(i).put("yj", yj);
                    countList.get(i).put("wj", wj);
                }


            }
        }

        return getDataTable(countList);
    }


    /**
     * 查询承办庭线索详细
     */
    @RequiresPermissions("module:clue:xfCzTj")
    @GetMapping("/xfCzTj")
    public String xfCzTj(String id, ModelMap mmap) {

        return "module/clue/xfCzTj";
    }


    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:clue:fyrTj")
    @PostMapping("/queryXfCzjg")
    @ResponseBody
    public TableDataInfo queryXfCzjg(Clue clue) {
        startPage();
        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }

        String startTime = null;
        String endTime = null;

        if (StringUtils.isNull(clue.getParams().get("startTime"))||clue.getParams().get("startTime").equals("")) {

            startTime = "2019-01-01";
        } else {
            startTime = clue.getParams().get("startTime").toString();
        }

        if (StringUtils.isNull(clue.getParams().get("endTime"))||clue.getParams().get("endTime").equals("")) {

            endTime = DateUtils.getDate();

        } else {
            endTime = clue.getParams().get("endTime").toString();
        }
        Map<String, Object> map = new HashMap<>();
        //统计各个法院线索数量
        List<Map<String, Object>> countList = clueService.queryXfYjByFyId(startTime, endTime,fyId);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {

                Object jbfyId = countList.get(i).get("jbfy_id");


                if (!StringUtils.isNull(jbfyId)) {

                    String wj = clueService.queryXfWjByFyId(jbfyId.toString(), startTime, endTime);
                    String jc = clueService.queryJcByFyId(jbfyId.toString(), startTime, "2");
                    String xs = clueService.queryXsByFyId(jbfyId.toString(), startTime, endTime, "2");

                    countList.get(i).put("xs", xs);
                    countList.get(i).put("jc", jc);
                    countList.get(i).put("wj", wj);
                }
            }
        }
        return getDataTable(countList);
    }


    /**
     * 查询承办庭线索详细
     */

    @GetMapping("/fyrTjDetail")
    public String fyrTjDetail(String id, String index, ModelMap mmap) {

        mmap.put("id", id);
        mmap.put("index", index);
        return "module/clue/fyrTjDetail";
    }


    /**
     * 根据承办庭id统计承办庭的线索详情
     *
     * @return
     */
//    @RequiresPermissions("module:clue:fyrDetailById")
    @PostMapping("/fyrDetailById")
    @ResponseBody
    public TableDataInfo fyrDetailById(String id, String index) {
        startPage();
        Clue clue = new Clue();
        clue.setJbxxCbrId(id);
        clue.setClueCzjg(index);
        clue.setDeleteFlag("0");
        clue.setClueSource("1");

        List<Clue> list = clueService.selectSecondClueList(clue);

        return getDataTable(list);
    }



    @GetMapping("/xfTjDetail")
    public String xfTjDetail(String fyId, String startTime,String endTime,String  index, ModelMap mmap) {

        mmap.put("id", fyId);
        mmap.put("startTime", startTime);
        mmap.put("endTime", endTime);
        mmap.put("index", index);

        return "module/clue/xfTjDetail";
    }


    /**
     * 根据承办庭id统计承办庭的线索详情
     *
     * @return
     */
//    @RequiresPermissions("module:clue:xfDetailById")
    @PostMapping("/xfDetailById")
    @ResponseBody
    public TableDataInfo xfDetailById(String id, String index,String startTime,String endTime) {
        startPage();


        List<Map> list=null;


        if (index.equals("1")){

            list=clueService.queryJcByFyIdAll(id,startTime,"2",null);

        }else if (index.equals("2")){
            list=clueService.queryXsByFyIdAll(id,startTime,endTime,"2",null);
        }else if (index.equals("3")){
            list=clueService.queryWjByFyIdAll(id,startTime,endTime,"2",null);
        }else if (index.equals("4")){
            list=clueService.queryYjByFyIdAll(id,startTime,endTime,"2",null);
        }

        return getDataTable(list);
    }



    @GetMapping("/fyczTjDetail")
    public String fyczTjDetail(String fyId, String startTime,String endTime,String  index, ModelMap mmap) {

        mmap.put("id", fyId);
        mmap.put("startTime", startTime);
        mmap.put("endTime", endTime);
        mmap.put("index", index);

        return "module/clue/fyczTjDetail";
    }



    /**
     * 根据承办庭id统计承办庭的线索详情
     *
     * @return
     */

    @PostMapping("/fyczTjDetailById")
    @ResponseBody
    public TableDataInfo fyczTjDetailById(String id, String index,String startTime,String endTime) {
        startPage();


        List<Map> list=null;


        if (index.equals("1")){

            list=clueService.queryJcByFyIdAll(id,startTime,null,null);

        }else if (index.equals("2")){
            list=clueService.queryXsByFyIdAll(id,startTime,endTime,null,null);
        }else if (index.equals("3")){
            list=clueService.queryWjByFyIdAll(id,startTime,endTime,null,null);
        }else if (index.equals("4")){
            list=clueService.queryYjByFyIdAll(id,startTime,endTime,null,null);
        }else if(index.equals("5")){
            list=clueService.queryAllCzjgByFyId("1",startTime,endTime,id,null);
        }else if(index.equals("6")){
            list=clueService.queryAllCzjgByFyId("2",startTime,endTime,id,null);
        }else if(index.equals("7")){
            list=clueService.queryAllCzjgByFyId("4",startTime,endTime,id,null);
        }else if(index.equals("8")){
            list=clueService.queryAllCzjgByFyId("3",startTime,endTime,id,null);
        }else if(index.equals("9")){
            list=clueService.queryAllCzjgByFyId("5",startTime,endTime,id,null);
        }else if(index.equals("10")){
            list=clueService.queryAllCzjgByFyId("6",startTime,endTime,id,null);
        }

        return getDataTable(list);
    }


    @GetMapping("/cbtCzTjDetail")
    public String cbtCzTjDetail(String cbtId, String startTime,String endTime,String  index,String fyId, ModelMap mmap) {

        mmap.put("cbtId", cbtId);
        mmap.put("fyId", fyId);
        mmap.put("startTime", startTime);
        mmap.put("endTime", endTime);
        mmap.put("index", index);

        return "module/clue/cbtCzTjDetail";
    }




    /**
     * 根据承办庭id统计承办庭的线索详情
     *
     * @return
     */
    @RequiresPermissions("module:clue:cbtCzTjDetailById")
    @PostMapping("/cbtCzTjDetailById")
    @ResponseBody
    public TableDataInfo cbtCzTjDetailById(String fyId,String cbtId, String index,String startTime,String endTime) {
        startPage();


        List<Map> list=null;


        if (index.equals("1")){

            list=clueService.queryJcByFyIdAll(fyId,startTime,null,cbtId);

        }else if (index.equals("2")){
            list=clueService.queryXsByFyIdAll(fyId,startTime,endTime,null,cbtId);
        }else if (index.equals("3")){
            list=clueService.queryWjByFyIdAll(fyId,startTime,endTime,null,cbtId);
        }else if (index.equals("4")){
            list=clueService.queryYjByFyIdAll(fyId,startTime,endTime,null,cbtId);
        }else if(index.equals("5")){
            list=clueService.queryAllCzjgByFyId("1",startTime,endTime,fyId,cbtId);
        }else if(index.equals("6")){
            list=clueService.queryAllCzjgByFyId("2",startTime,endTime,fyId,cbtId);
        }else if(index.equals("7")){
            list=clueService.queryAllCzjgByFyId("4",startTime,endTime,fyId,cbtId);
        }else if(index.equals("8")){
            list=clueService.queryAllCzjgByFyId("3",startTime,endTime,fyId,cbtId);
        }else if(index.equals("9")){
            list=clueService.queryAllCzjgByFyId("5",startTime,endTime,fyId,cbtId);
        }else if(index.equals("10")){
            list=clueService.queryAllCzjgByFyId("6",startTime,endTime,fyId,cbtId);
        }

        return getDataTable(list);
    }


    @GetMapping("/insert")
    @ResponseBody
    @TaoTransaction
    public int insert(Clue clue) {
        int result=clueService.insertClue(clue);
        int j=1/Integer.parseInt(clue.getClueId());
        return result;
    }
}
