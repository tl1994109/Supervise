package com.datcent.project.monitor.job.task;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.RedisUtil;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.framework.web.exception.DefaultExceptionHandler;
import com.datcent.project.module.cbttj.domain.Cbttj;
import com.datcent.project.module.cbttj.service.ICbttjService;
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.project.module.colleinterface.service.IColleinterfaceService;
import com.datcent.project.module.fytj.domain.Fytj;
import com.datcent.project.module.fytj.service.IFytjService;
import com.datcent.project.module.lists.domain.Lists;
import com.datcent.project.module.lists.service.IListsService;
import com.datcent.project.module.rytj.domain.Rytj;
import com.datcent.project.module.rytj.service.IRytjService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务，获取案件
 *
 * @author datcent
 */
@Component("getCase")
public class GetCase {

    @Resource
    RedisUtil redisUtil;


    @Autowired
    private IColleinterfaceService colleinterfaceService;

    @Autowired
    private IFytjService fytjService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ICourtOrganService iCourtOrganService;

    @Autowired
    private ICbttjService cbttjService;

    @Autowired
    private IRytjService rytjService;

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);


    /**
     * 执行案件 数据采集
     *
     * @param params
     */
    private void searchZxcase(String params) {
        String sr="";
        System.out.println("-------------调用执行接口开始-------------------" + DateUtils.getTime());
        List<String> colleinterIds = Arrays.asList(params.split(","));
        //获取所有接口列表
        List<Colleinterface> list =new ArrayList<Colleinterface>();
        for (String colleinterId : colleinterIds) {
            Colleinterface colleinterface = colleinterfaceService.selectColleinterfaceById(colleinterId);
            list.add(colleinterface);
        }

        for (Colleinterface c : list) {
            //检测 执行系统是否登录
            if("zxislogin".equals(c.getColleinterfaceMethed())){
                System.out.println("-------------开始检测登录-------------------");
                sr = HttpUtils.sendGet(c.getColleinterfaceParam().split("[?]")[0], c.getColleinterfaceParam().split("[?]")[1]);
                if (!"".equals(sr)&&sr!=null){
                    System.out.println("-----------结果----------------/n"+sr);
                    JSONObject json = JSONObject.parseObject(sr);
                    String resultCode = json.getString("resultCode");
                    if ("100000".equals(resultCode)) {
                        //开始调用登陆接口
                        for (Colleinterface d : list) {
                            if ("zxlogin".equals(d.getColleinterfaceMethed())) {
                                sr = HttpUtils.sendGet(d.getColleinterfaceParam().split("[?]")[0], d.getColleinterfaceParam().split("[?]")[1]);
                                if (!"".equals(sr)) {
                                    json = JSONObject.parseObject(sr);
                                    resultCode = json.getString("resultCode");
                                    if ("100000".equals(resultCode)) {
                                        log.debug("登陆失败");
                                    } else if ("200000".equals(resultCode)) {
                                        getZxcaseList(list);
                                        log.debug("--存入数据成功--");
                                    } else if ("100000".equals(resultCode)) {
                                        log.debug("登陆失败");
                                    }
                                } else {
                                    log.debug("登陆失败");
                                }
                            }
                        }
                    } else if ("200000".equals(resultCode)) {
                        getZxcaseList(list);
                        log.debug("登陆成功");
                    }
                }else{
                    log.debug("连接异常");
                }
            }
        }
    }


    private void getZxcaseList( List<Colleinterface> list){
        log.debug(DateUtils.getTime()+":：：开始获取列表");
        JSONObject json = null;
        Map<String, Object> redisMap = new HashMap<String, Object>();
        for (Colleinterface e : list) {
            if ("searchCase".equals(e.getColleinterfaceMethed())) {
                String sr = HttpUtils.sendGet(e.getColleinterfaceParam().split("[?]")[0], e.getColleinterfaceParam().split("[?]")[1]);
                if (!"".equals(sr)) {
                    json = JSONObject.parseObject(sr);
                    String resultCode = json.getString("resultCode");
                    if ("200000".equals(resultCode)) {
                        JSONArray result = json.getJSONArray("list");
                        JSONObject countjson = result.getJSONObject(0);
                        String totalCount = countjson.getString("totalnumber");
                        //先存入第一页数据
                        redisMap = getZxDetail(list, sr);
                        redisUtil.hmset("zxCase", redisMap);
                        redisMap.clear();
                        int tot = Integer.valueOf(totalCount);
                        //获取总页数
                        int page = tot % 100 == 0 ? tot / 100 : (tot / 100) + 1;
                        int start = 2;
                        //如果存在多页
                        if (page >=start) {
                            for (int i = start; i <= page; i++) {
                                String url = e.getColleinterfaceParam().replace("currPageNo=1", "currPageNo=" + i);
                                System.out.println(url+"----第"+i+"页");
                                sr = HttpUtils.sendGet(url.split("[?]")[0], url.split("[?]")[1]);
//                              //调用存入方法
                                redisMap.putAll(getZxDetail(list, sr));
                                redisUtil.hmset("zxCase", redisMap);
                                redisMap.clear();
                            }
                        }
                    } else {
                        log.info("获取数据失败！");
                    }
                } else {
                    log.info("连接失败！！！");
                }
            }
        }
    }


    /**
     * 获取执行系统 案件详细 流程
     * @param list
     * @param sr
     */
    private Map<String, Object> getZxDetail(List<Colleinterface> list, String sr){
        Map<String, Object> resmap =new HashMap<String, Object>();

        log.debug(DateUtils.getTime()+":------------------开始获取执行系统案件详细--------------------");
        try {
            System.out.println("线程休眠5秒执行");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject json=JSON.parseObject(sr);
        JSONArray result = json.getJSONArray("list");

        //获取 执行系统案件详细流程接
        //获取执行案件收案详情
        for (Colleinterface c: list) {
            if ("getZxCaseDetal".equals(c.getColleinterfaceMethed())){
                getZxDetail(resmap, result, c, "执行系统案件流程，获取数据失败！", "list");
            }
            if ("getZxSaxx".equals(c.getColleinterfaceMethed())){
                getZxDetail(resmap, result, c, "执行案件收案详细，获取数据失败！", "saxx");
            }
        }

        log.debug(DateUtils.getTime()+":-----------------获取执行系统案件详细  结束--------------------");

        return resmap;
    }

    private void getZxDetail(Map<String, Object> resmap, JSONArray result, Colleinterface c, String s, String list2) {
        for (int i = 1; i < result.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            //存放 执行系统案件流程
            List arrayList = new ArrayList();
            // 逐行获取执行案件列表 信息
            JSONObject rowjson = result.getJSONObject(i);
            map = rowjson;
            String AHDM = rowjson.getString("AHDM");
            String url = c.getColleinterfaceParam() + AHDM;
            String res = HttpUtils.sendGet(url.split("[?]")[0], url.split("[?]")[1]);
            JSONObject resjson = JSON.parseObject(res);
            String resultCode = resjson.getString("resultCode");
            if ("200000".equals(resultCode)) {
                JSONArray jsonArra = resjson.getJSONArray("list");
                arrayList = JSONObject.parseArray(jsonArra.toJSONString());
            } else {
                log.error(DateUtils.getTime() + s);
            }
            map.put(list2, arrayList);
            resmap.put(AHDM, map);
        }
    }

    private void searchCase(String params) {

        System.out.println("-------------调用案件接口开始-------------------" + DateUtils.getTime());

        String sr = "";

        //获取所有参数
        String[] param = params.split(",");
        List<Colleinterface> list=new ArrayList<Colleinterface>();
        for (String s : param) {
            Colleinterface colleinterface =colleinterfaceService.selectColleinterfaceById(s);
            list.add(colleinterface);
        }
        caseList(list);
        System.out.println("-------------调用接口结束-------------------" + DateUtils.getTime());
    }

    /**
     * 向redis 插入数据
     *
     * @param list
     * @return
     */
    private void caseList(List<Colleinterface> list) {
        JSONObject json = null;

        Map<String, Object> redisMap = new HashMap<String, Object>();
        for (Colleinterface e : list) {
            if ("getlist".equals(e.getColleinterfaceMethed())) {
                String sr = HttpUtils.sendGet(e.getColleinterfaceParam().split("[?]")[0], e.getColleinterfaceParam().split("[?]")[1]);
                if (!"".equals(sr)) {
                    json = JSONObject.parseObject(sr);
                    String resultCode = json.getString("resultCode");
                    if ("200000".equals(resultCode)) {
                        JSONArray result = json.getJSONArray("list");
                        JSONObject countjson = result.getJSONObject(0);
                        String totalCount = countjson.getString("totalCount");
                        //先存入第一页数据
                        redisMap = getDetail(list, sr);
                        redisUtil.hmset("case", redisMap);
                        redisMap.clear();
                        int tot = Integer.valueOf(totalCount);
                        //获取总页数
                        int page = tot % 20 == 0 ? tot / 20 : (tot / 20) + 1;
                        int start = 2;
                        //如果存在多页
                        if (page > start) {
                            for (int i = start; i <= page; i++) {
                                String url = e.getColleinterfaceParam().replace("currPageNo=1", "currPageNo=" + i);
                                System.out.println("获取案件列表链接：" + url+"----第"+i);
                                sr = HttpUtils.sendGet(url.split("[?]")[0], url.split("[?]")[1]);
//                              //调用存入方法
                                redisMap.putAll(getDetail(list, sr));
                                redisUtil.hmset("case", redisMap);
                                redisMap.clear();
                            }
                        }
                    } else {
                        log.info("获取数据失败！");
                    }
                } else {
                    log.info("连接失败！！！");
                }
            }
        }
    }

    /**
     * 向redis数据库存入数据
     *
     * @param list
     * @param sr
     */
    private Map<String, Object> getDetail(List<Colleinterface> list, String sr) {
        System.out.println("----------------------------开始获取案件列表详细-----------------------------------------------------");
        try {
            System.out.println("线程休眠5秒执行");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Object> redisMap = new HashMap<String, Object>();
        JSONObject json = JSONObject.parseObject(sr);
        JSONArray result = json.getJSONArray("list");
        Object caseJson = result.subList(1, 21);
        String jsonStr = JSONObject.toJSONString(caseJson);
        result = JSONArray.parseArray(jsonStr);
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            Map<String, Object> ajMap = new HashMap<String, Object>();
            JSONObject jsonObject = (JSONObject) iterator.next();
            String ahdm = jsonObject.getString("zhcxlist_StringAJBH");
            String ajlb = jsonObject.getString("zhcxlist_CodeNAjlb").substring(2,4);
            if ("刑事".equals(ajlb)) {
                ajlb = "1";
            }
            if ("民事".equals(ajlb)) {
                ajlb = "2";
            }
            if ("行政".equals(ajlb)) {
                ajlb = "6";
            }
            if ("执行".equals(ajlb)) {
                ajlb = "8";
            }
            if ("审查".equals(ajlb)) {
                ajlb = "18";
            }
            ajMap.put("ajlb", jsonObject);
            String url = "";
            for (Colleinterface e : list) {
                if ("getCaseDetal".equals(e.getColleinterfaceMethed())) {
                    url = e.getColleinterfaceParam();
                    break;
                }
            }
            String param = url + "&cBhAj=" + ahdm + "&nAjlb=" + ajlb + "&userId=144376309";
            String apiUrl = param.substring(0, param.indexOf("?"));
            String paramsUrl = param.substring(param.indexOf("?") + 1);
            sr = HttpUtils.sendGet(apiUrl, paramsUrl);
            if (!"".equals(sr)) {
                json = JSONObject.parseObject(sr);
                result = json.getJSONArray("list");
                ajMap.put("ajxx", result);
            } else {
                log.info("连接失败！！！");
            }
            redisMap.put(ahdm, ajMap);
        }
        return redisMap;
    }


    /**
     * 获取院庭人信息
     *
     * @param params
     */
    private void searchYtr(String params){
        System.out.println(DateUtils.getTime()+"------------获取院庭人信息 开始-----------------");
        String sr="";
        //获取所有参数
        String[] param = params.split(",");
        List<Colleinterface> list=new ArrayList<Colleinterface>();

        for (String s : param) {
            Colleinterface colleinterface =colleinterfaceService.selectColleinterfaceById(s);
            list.add(colleinterface);
        }


        for (Colleinterface c : list) {
            if ("islogin".equals(c.getColleinterfaceMethed())) {
                System.out.println("-------------开始检测登录-------------------");
                sr = HttpUtils.sendGet(c.getColleinterfaceParam().split("[?]")[0], c.getColleinterfaceParam().split("[?]")[1]);
                if (!"".equals(sr)) {
                    System.out.println("---结果----------------------:" + sr);
                    JSONObject json = JSONObject.parseObject(sr);
                    String resultCode = json.getString("resultCode");
                    if ("100000".equals(resultCode)) {
                        //开始调用登陆接口
                        for (Colleinterface d : list) {
                            if ("login".equals(d.getColleinterfaceMethed())) {
                                sr = HttpUtils.sendGet(d.getColleinterfaceParam().split("[?]")[0], d.getColleinterfaceParam().split("[?]")[1]);
                                if (!"".equals(sr)) {
                                    json = JSONObject.parseObject(sr);
                                    resultCode = json.getString("resultCode");
                                    if ("100000".equals(resultCode)) {
                                        log.debug("登陆失败");
                                    } else if ("200000".equals(resultCode)) {
                                        getYtr(list);
                                        log.debug("--存入数据成功--");
                                    } else if ("100000".equals(resultCode)) {
                                        log.debug("登陆失败");
                                    }
                                } else {
                                    log.debug("登陆失败");
                                    break;
                                }
                            }
                        }
                    } else if ("200000".equals(resultCode)) {
                        getYtr(list);
                        log.debug("登陆成功");
                    } else if ("100000".equals(resultCode)) {
                        log.debug("登陆失败");
                    }
                } else {
                    log.debug("连接异常");
                    break;
                }
            }
        }
        getYtr(list);


    }

    /**
     * 向redis 插入数据
     *
     * @param list
     * @return
     */
    private void getYtr(List<Colleinterface> list) {
        JSONObject json = null;

        Map<String, Object> redisMap = new HashMap<String, Object>();

        for (Colleinterface e : list) {
            if ("searchYtr".equals(e.getColleinterfaceMethed())) {
                String sr = HttpUtils.sendGet(e.getColleinterfaceParam().split("[?]")[0], e.getColleinterfaceParam().split("[?]")[1]);
                if (!"".equals(sr)) {
                    json = JSONObject.parseObject(sr);
                } else {
                    log.info("连接失败！！！");
                }
            }
        }
    }


    /**
     * 各个法院页面解析接口
     */

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
    public void allTjCourtData() {
        System.out.println(DateUtils.getTime()+"------------获取承办庭页面开始-----------------");
        String table = null;
        List<String> arrayList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Dept dept = new Dept();
        dept.setDeptType("0");
        List<Dept> thirdList = deptService.selectDeptLists(dept);

        String id = null;
        String value = null;
        List<String> idList = null;
        List<String> valueList = null;
        List<String> secondArrayList = null;

        for (Dept dept1 : thirdList) {
            String jbfyId = dept1.getDeptId().toString();

            String fyName=iCourtOrganService.queryDeptNameByCid(jbfyId,"1");

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
                        cbttj.setJbfyId(jbfyId);
                        cbttjService.insertCbttj(cbttj);
                    }
                }else{
                    log.info("无数据");
                }
            } catch (Exception e) {
                log.info("*****数据提取失败*******");
                e.printStackTrace();
            }
        }
        log.info("*****数据提取成功*******");
    }


    /**
     * 各个法院下各个庭各个人页面解析接口
     */

    public void allTjPeopleData() {
        System.out.println(DateUtils.getTime()+"------------获取承办人页面开始-----------------");
        String table = null;
        List<String> idList = null;
        List<String> valueList = null;
        List<String> arrayList = null;
        List<String> secondArrayList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Dept dept = new Dept();
        dept.setDeptType("0");
        List<Dept> thirdList = deptService.selectDeptLists(dept);
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
                            rytjService.insertRytj(rytj);
                        }
                    }else {
                        log.info("无数据");
                    }
                } catch (Exception e) {
                    log.info("*****数据提取失败*******");
                    e.printStackTrace();
                }
            }
        }
        log.info("*****数据提取成功*******");
    }

}
