package com.datcent.project.monitor.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.RedisUtil;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.project.module.colleinterface.service.IColleinterfaceService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.project.module.strategy.domain.Strategy;
import com.datcent.project.module.strategy.service.IStrategyService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务调度测试
 * 
 * @author datcent
 */
@Component("CheckTask")
public class CheckTask
{

    @Resource
    RedisUtil redisUtil;


    @Autowired
    private IColleinterfaceService colleinterfaceService;

    @Autowired
    private IStrategyService strategyService;


    @Autowired
    private IDubvioEventService dubvioEventService;

    @Autowired
    private IDubvioEventDetailService dubvioEventDetailService;

    public void checkCase(String params) throws UnsupportedEncodingException {

        System.out.println("-----------------------获取redis 数据begin---------------------------------------");
        System.out.println("-----------------------获取所有key---------------------------------------");

        redisUtil.hdel("case","22038191800002200000000000000000");

        //获取所有策略
        Strategy strategy=new Strategy();
        strategy.setStrategyJob(params);
        List<Strategy> strategyList = strategyService.selectStrategyList(strategy);
        Map<String,Strategy> stategys=strategyList.stream().collect(Collectors.toMap(Strategy::getStrategyCode, a -> a,(k1,k2)->k1));

        //获取案件列表
        Set<String> result = redisUtil.hmgetkeys("case");
        for (String s : result) {
            //s="00C66C4538FA6342D8590A053C08D2A8";
            Object objec = redisUtil.hget("case", s);
            JSONObject caseJson = new JSONObject((Map<String, Object>) objec);
            System.out.println(s+"-----"+caseJson);
            JSONObject ajlbJson = caseJson.getJSONObject("ajlb");
            JSONArray ajxxArra = caseJson.getJSONArray("ajxx");

            //案件类别Id
            String ajlbId=ajlbJson.getString("zhcxlist_CodeNAjlbId").replace("\\n","");

            //创建可以违纪事件（列表）实体
            DubvioEvent dubvioEvent=getDubvioEvent(ajlbJson,ajxxArra);
            dubvioEvent.setDubvioSource("1");
            //创建可以危及时间（详细）实体
            DubvioEventDetail dubvioEventDetail=getDubvioEventDetai(ajlbJson,ajxxArra);
            //策略执行方法
            JSONObject zxcaseJson=null;
            System.out.println(dubvioEvent.getJbxxJarq().equals(" "));
            //从redis（全部） 筛选未结案的
            if(dubvioEvent.getJbxxJarq().equals(" ")){
                dostategy(stategys, ajlbId, dubvioEvent, dubvioEventDetail,zxcaseJson);
            }
        }


        //获取执行系统案件所有Key值
        Map<String, Object> redisMap = new HashMap<String, Object>();

        Set<String> zxResult = redisUtil.hmgetkeys("zxCase");
        for (String s : zxResult) {
            JSONObject zxajlbJson;
            JSONArray zxajxxArra = null;
            Object objec = redisUtil.hget("zxCase", s);
            JSONObject zxcaseJson = new JSONObject((Map<String, Object>) objec);
            //获取执行案号
            String AH = zxcaseJson.getString("AH");
            AH = URLEncoder.encode(AH, "UTF-8");
            //获取查询案件接口
            Colleinterface coll=new Colleinterface();
            coll.setColleinterfaceMethed("getlist");
            List<Colleinterface> colls = colleinterfaceService.selectColleinterfaceList(coll);
            for (Colleinterface c : colls) {
                if ("getlist".equals(c.getColleinterfaceMethed())){
                    coll=c;
                    break;
                }
            }
            String paramUrl=coll.getColleinterfaceParam();
            //将案号补充到查询链接 结案参数改为空
            paramUrl=paramUrl.replace("NSfja=2","NSfja=");
            paramUrl=paramUrl.replace("CAhInclude=","CAhInclude="+AH);
            String sr = HttpUtils.sendGet(paramUrl.split("[?]")[0], paramUrl.split("[?]")[1]);
            JSONObject json = JSONObject.parseObject(sr);
            JSONArray zxList = json.getJSONArray("list");
            zxajlbJson = zxList.getJSONObject(1);
            String ahdm = zxajlbJson.getString("zhcxlist_StringAJBH");
            String ajlb = zxajlbJson.getString("zhcxlist_CodeNAjlb").substring(2,4);
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
                //获取查询案件接口
                coll=new Colleinterface();
                coll.setColleinterfaceMethed("getCaseDetal");
                colls = colleinterfaceService.selectColleinterfaceList(coll);
                for (Colleinterface c : colls) {
                    if ("getCaseDetal".equals(c.getColleinterfaceMethed())){
                        coll=c;
                        break;
                    }
                }
                String url=coll.getColleinterfaceParam();
                String param = url + "&cBhAj=" + ahdm + "&nAjlb=" + ajlb + "&userId=144376309";
                String apiUrl = param.substring(0, param.indexOf("?"));
                String paramsUrl = param.substring(param.indexOf("?") + 1);
                sr = HttpUtils.sendGet(apiUrl, paramsUrl);
                if (!"".equals(sr)) {
                    json = JSONObject.parseObject(sr);
                    zxajxxArra = json.getJSONArray("list");
                } else {
                    System.out.println("系统链接失败！！");
                }


            //创建可以违纪事件（列表）实体
            DubvioEvent dubvioEvent=getDubvioEvent(zxajlbJson,zxajxxArra);
            dubvioEvent.setDubvioSource("1");
            //创建可以危及时间（详细）实体
            DubvioEventDetail dubvioEventDetail=getDubvioEventDetai(zxajlbJson,zxajxxArra);
            //策略执行方法
            dostategy(stategys, ajlb, dubvioEvent, dubvioEventDetail,zxcaseJson);

        }



        System.out.println("------------结束------------");
    }


    private Map<String, Object> getDetail( String sr) {
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
            Colleinterface coll=new Colleinterface();
            coll.setColleinterfaceMethed("getCaseDetal");
            List<Colleinterface> colls = colleinterfaceService.selectColleinterfaceList(coll);
            for (Colleinterface c : colls) {
                if ("getCaseDetal".equals(c.getColleinterfaceMethed())){
                    url=c.getColleinterfaceParam();
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
                System.out.println("连接失败!!!");
            }
            redisMap.put(ahdm, ajMap);
        }
        return redisMap;
    }



    /**
     *
     *
     * 筛查策略执行总方法
     *
     * @param stategys
     * @param ajlbId
     * @param dubvioEvent
     * @param dubvioEventDetail
     */
    private void dostategy(Map<String, Strategy> stategys,
                           String ajlbId, DubvioEvent dubvioEvent,
                           DubvioEventDetail dubvioEventDetail,
                           JSONObject zxcaseJson) {
        System.out.println("案件id："+dubvioEvent.getJbxxAjbh()+"--------------------开始策略筛选------------------------------");
        System.out.println("案号："+dubvioEvent.getJbxxAh());
        if (zxcaseJson!=null){
            System.out.println(DateUtils.getTime()+"执行案件超审限 begin--------------------------");
            Strategy zxcsxZX=stategys.get("zxcsxZX");
            zxcsxZxMethod(zxcsxZX, dubvioEvent,ajlbId,dubvioEventDetail,zxcaseJson);
            System.out.println(DateUtils.getTime()+"执行案件超审限 end--------------------------");


            System.out.println(DateUtils.getTime()+"执行案件超审限（严重） begin--------------------------");
            Strategy zxcsxYZX=stategys.get("zxcsxYZX");
            zxcsxYZXMethod(zxcsxYZX, dubvioEvent,ajlbId,dubvioEventDetail,zxcaseJson);
            System.out.println(DateUtils.getTime()+"执行案件超审限（严重） end--------------------------");


            System.out.println(DateUtils.getTime()+"长期未执行低 begin--------------------------");
            Strategy cqwzxZZX=stategys.get("cqwzxZZX");
            cqwzxZZXMethod(cqwzxZZX, dubvioEvent,ajlbId,dubvioEventDetail,zxcaseJson);
            System.out.println(DateUtils.getTime()+"长期未执行低 end--------------------------");


            System.out.println(DateUtils.getTime()+"长期未执行中 begin--------------------------");
            Strategy cqwzxDZX=stategys.get("cqwzxDZX");
            cqwzxDZXMethod(cqwzxDZX, dubvioEvent,ajlbId,dubvioEventDetail,zxcaseJson);
            System.out.println(DateUtils.getTime()+"长期未执行中 end--------------------------");



            System.out.println(DateUtils.getTime()+"长期未执行高 begin--------------------------");
            Strategy cqwzxGZX=stategys.get("cqwzxGZX");
            cqwzxGZXMethod(cqwzxGZX, dubvioEvent,ajlbId,dubvioEventDetail,zxcaseJson);
            System.out.println(DateUtils.getTime()+"长期未执行高 end--------------------------");


        }
        System.out.println(DateUtils.getTime()+"超期分案 begin--------------------------");
        Strategy cqfnStrategy=stategys.get("cqfnMethod");
        cqfnMethod(cqfnStrategy, dubvioEvent,ajlbId,dubvioEventDetail);
        System.out.println(DateUtils.getTime()+"超期分案 end--------------------------");

        System.out.println(DateUtils.getTime()+"再审超期立案 begin--------------------------");
        if("再审".equals(dubvioEventDetail.getJbxxSpcx())){
            Strategy zscqlaStrategy=stategys.get("zscqlaMethod");
            zscqlaMethod(zscqlaStrategy,dubvioEvent,ajlbId,dubvioEventDetail);
        }
        System.out.println(DateUtils.getTime()+"再审超期立案 end--------------------------");

        System.out.println(" 一审超期立案 begin--------------------------");
        if("一审".equals(dubvioEventDetail.getJbxxSpcx())&&"公诉".equals(dubvioEventDetail.getJbxxSsxz())){
            Strategy zscqlaStrategy=stategys.get("yscqlaMethod");
            yscqlaMethod(zscqlaStrategy,dubvioEvent,ajlbId,dubvioEventDetail.getSadjSdszsj(),dubvioEventDetail);
        }
        System.out.println(" 一审超期立案 end--------------------------");


        System.out.println("二审超期立案 begin--------------------------");
        if("二审".equals(dubvioEventDetail.getJbxxSpcx())){
            Strategy erscqlaStrategy=stategys.get("escqMethod");
            escqMethod(erscqlaStrategy,dubvioEvent,ajlbId,dubvioEventDetail);
        }
        System.out.println("二审超期立案 end--------------------------");


        System.out.println("超期移送 begin--------------------------");
        Strategy cqysStrategy=stategys.get("cqysMethod");
        cqysMethod(cqysStrategy,dubvioEvent,ajlbId,dubvioEventDetail);

        System.out.println("超期移送 end--------------------------");
        System.out.println("---------------------结束策略筛选------------------------------");

//--------------------------开始超审限   策略------------------------------------------
        System.out.println("超审限（低） begin--------------------------");
        Strategy csxDStrategy=stategys.get("csxD");
        csxMethod(csxDStrategy,dubvioEvent,ajlbId,dubvioEventDetail);
        System.out.println("超审限（低） end--------------------------");
        System.out.println("---------------------结束策略筛选------------------------------");

        System.out.println("超审限（中） begin--------------------------");
        Strategy csxZStrategy=stategys.get("csxZ");
        csxMethod(csxZStrategy,dubvioEvent,ajlbId,dubvioEventDetail);

        System.out.println("超审限（中） end--------------------------");
        System.out.println("---------------------结束策略筛选------------------------------");
        System.out.println("超审限（高） begin--------------------------");
        Strategy csxGStrategy=stategys.get("csxG");
        csxMethod(csxGStrategy,dubvioEvent,ajlbId,dubvioEventDetail);

        System.out.println("超审限（高） end--------------------------");
        System.out.println("---------------------结束策略筛选------------------------------");
        System.out.println("超审限（严重） begin--------------------------");
        Strategy csxYStrategy=stategys.get("csxY");
        csxMethod(csxYStrategy,dubvioEvent,ajlbId,dubvioEventDetail);
        System.out.println("超审限（严重） end--------------------------");
        System.out.println("---------------------结束策略筛选------------------------------");
    }

    /**
     * 执行 长期未执行 （6个月）
     * @param cqwzxDZX
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     * @param zxcaseJson
     */
    private void cqwzxDZXMethod(Strategy cqwzxDZX, DubvioEvent dubvioEvent,
                                String ajlbId, DubvioEventDetail dubvioEventDetail,
                                JSONObject zxcaseJson) {
        JSONObject saxxJson = zxcaseJson.getJSONArray("saxx").getJSONObject(0);
        int param = Integer.valueOf(cqwzxDZX.getStrategyParam());
        String jafs=saxxJson.getString("结案方式");
        String jarq=saxxJson.getString("结案日期");
        String zxqxQsrq=saxxJson.getString("执行期限起始日期");
        if (" ".equals(jafs)&&" ".equals(jarq)){
            int betweenday=Months.monthsBetween(new DateTime(DateUtils.stringToDate(zxqxQsrq)),
                    new DateTime(DateUtils.stringToDate(DateUtils.getTime())))
                    .getMonths();
            if (betweenday>param&&betweenday<=12){
                saveMethod(cqwzxDZX,dubvioEvent,dubvioEventDetail);
            }
        }
    }

    /**
     *
     * 执行案件 长期未执行高 （18个月）
     * @param cqwzxGZX
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     * @param zxcaseJson
     */
    private void cqwzxGZXMethod(Strategy cqwzxGZX, DubvioEvent dubvioEvent,
                                String ajlbId, DubvioEventDetail dubvioEventDetail,
                                JSONObject zxcaseJson) {
        JSONObject saxxJson = zxcaseJson.getJSONArray("saxx").getJSONObject(0);
        int param = Integer.valueOf(cqwzxGZX.getStrategyParam());
        String jafs=saxxJson.getString("结案方式");
        String jarq=saxxJson.getString("结案日期");
        String zxqxQsrq=saxxJson.getString("执行期限起始日期");
        if (" ".equals(jafs)&&" ".equals(jarq)){
            int betweenday=Months.monthsBetween(new DateTime(DateUtils.stringToDate(zxqxQsrq)),
                    new DateTime(DateUtils.stringToDate(DateUtils.getTime())))
                    .getMonths();
            if (betweenday>param){
                saveMethod(cqwzxGZX,dubvioEvent,dubvioEventDetail);
            }
        }
    }
    /**
     *
     * 执行案件 长期未执行 （12个月）
     * @param cqwzxZZx
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     * @param zxcaseJson
     */
    private void cqwzxZZXMethod(Strategy cqwzxZZx, DubvioEvent dubvioEvent,
                                String ajlbId, DubvioEventDetail dubvioEventDetail,
                                JSONObject zxcaseJson) {
        JSONObject saxxJson = zxcaseJson.getJSONArray("saxx").getJSONObject(0);
        int param = Integer.valueOf(cqwzxZZx.getStrategyParam());

        String jafs=saxxJson.getString("结案方式");
        String jarq=saxxJson.getString("结案日期");
        String zxqxQsrq=saxxJson.getString("执行期限起始日期");
        if ("".equals(jafs)&&"".equals(jarq)){
            int betweenday=Months.monthsBetween(new DateTime(DateUtils.stringToDate(zxqxQsrq)),
                        new DateTime(DateUtils.stringToDate(DateUtils.getTime())))
                    .getMonths();
            if (betweenday>param&&betweenday<=18){
                saveMethod(cqwzxZZx,dubvioEvent,dubvioEventDetail);
            }
        }



    }


    /**
     *
     * 执行超审限(严重) 案件
     * @param zxcsxYZX
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     */
    private void zxcsxYZXMethod(Strategy zxcsxYZX, DubvioEvent dubvioEvent,
                               String ajlbId, DubvioEventDetail dubvioEventDetail,
                               JSONObject zxcaseJson) {
        JSONObject saxxJson = zxcaseJson.getJSONArray("saxx").getJSONObject(0);
        int zxcqDays=0;
        String zxcqts=saxxJson.getString("执行超期天数");
        if (!" ".equals(zxcqts)){
            zxcqDays=Integer.valueOf(zxcqts);
        }
        //获取策略案件类别
        String strategyAjlb=zxcsxYZX.getStrategyAjlb();
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            int param=Integer.valueOf(zxcsxYZX.getStrategyParam());
            if (zxcqDays>param){
                saveMethod(zxcsxYZX,dubvioEvent,dubvioEventDetail);
            }
        }
    }


    /**
     *
     * 执行超审限 案件
     * @param zxcsxZx
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     */
    private void zxcsxZxMethod(Strategy zxcsxZx, DubvioEvent dubvioEvent,
                               String ajlbId, DubvioEventDetail dubvioEventDetail,
                               JSONObject zxcaseJson) {
        JSONObject saxxJson = zxcaseJson.getJSONArray("saxx").getJSONObject(0);
        int zxcqDays=0;
        String zxcqts=saxxJson.getString("执行超期天数");
        if (!" ".equals(zxcqts)){
            zxcqDays=Integer.valueOf(zxcqts);
        }
        //获取策略案件类别
        String strategyAjlb=zxcsxZx.getStrategyAjlb();
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            int param=Integer.valueOf(zxcsxZx.getStrategyParam());
            if (0<zxcqDays&&zxcqDays<param){
                saveMethod(zxcsxZx,dubvioEvent,dubvioEventDetail);
            }
        }
    }

    /**
     *
     * 超审限 策略
     * @param strategy
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     */
    private void csxMethod(Strategy strategy, DubvioEvent dubvioEvent,
                           String ajlbId, DubvioEventDetail dubvioEventDetail)
    {
        System.out.println("--------------超审限筛查   begin--------------");
        String[] strategyParam=strategy.getStrategyParam().split(",");
        int begin=0;
        int end=0;
        if (strategyParam.length>1){
            begin=Integer.valueOf(strategyParam[0]);
            end=Integer.valueOf(strategyParam[1]);
        }else {
            begin=Integer.valueOf(strategyParam[0]);
        }
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");
        //判断该案件是否适用此策略
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            //获取该案件的 超审限天数
            int csxDays=0;
            String csxData=dubvioEventDetail.getSxxxCsxts();
            if (csxData!=null&&!" ".equals(csxData)){
                csxDays=Integer.valueOf(csxData);
            }
            if(end!=0&&begin!=0){
                if (begin<csxDays&&csxDays<end){
                    saveMethod(strategy, dubvioEvent, dubvioEventDetail);
                }
            }
            if(end==0&&begin!=0){
                if (begin<csxDays){
                    saveMethod(strategy, dubvioEvent, dubvioEventDetail);
                }
            }
        }
        System.out.println("--------------筛查超期移送   end--------------");

    }



    /**
     * 超期移送
     * @param
     * @param dubvioEvent
     * @param ajlbId
     * @param dubvioEventDetail
     */
    private void cqysMethod(Strategy strategy, DubvioEvent dubvioEvent, String ajlbId, DubvioEventDetail dubvioEventDetail) {
        System.out.println("--------------筛查超期移送   begin--------------");
        int param=Integer.valueOf(strategy.getStrategyParam());
        int days=-1;
        //策略适用案件
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");
        //判断该案件是否适用此策略
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            //判断该案件的审查意见是否为立案
            String scyj=dubvioEventDetail.getLascLascyj();
            if ("立案".equals(scyj)){
                //获取立案日期
                String larq = dubvioEventDetail.getLaspLarq();
                //获取分案确认日期
                String faqrrq=dubvioEventDetail.getFaFaqrrq();

                if (larq==null){
                    larq=DateUtils.getTime();
                }
                if (faqrrq==null){
                    faqrrq=DateUtils.getTime();
                }
                days=Days.daysBetween(new DateTime(DateUtils.stringToDate(larq)),new DateTime(DateUtils.stringToDate(faqrrq))).getDays();
                if (days>param){
                    saveMethod(strategy, dubvioEvent, dubvioEventDetail);
                }
            }
        }
        System.out.println("--------------筛查超期移送   end--------------");

    }

    private void saveMethod(Strategy strategy, DubvioEvent dubvioEvent, DubvioEventDetail dubvioEventDetail) {
        dubvioEvent.setDubvioStrategyId(String.valueOf(strategy.getStrategyId()));
        dubvioEvent.setDubvioStrategyName(strategy.getStrategyName());
        DubvioEvent dubparam=new DubvioEvent();
        dubparam.setJbxxAjbh(dubvioEvent.getJbxxAjbh());
        dubparam.setDubvioStrategyId(String.valueOf(strategy.getStrategyId()));
        //判断可以案件列表中是否存在
        List<DubvioEvent> dub = dubvioEventService.selectList(dubparam);
        if (dub.size()==0) {
            dubvioEvent.setDubvioId(StringUtils.getUUID());
            DubvioEventDetail dubdetail = dubvioEventDetailService.selectDubvioEventDetailById(dubvioEvent.getJbxxAjbh());
            if (dubdetail==null){
                dubvioEventDetailService.insertDubvioEventDetail(dubvioEventDetail);
            }
            dubvioEventService.insertDubvioEvent(dubvioEvent);
        }else{
            DubvioEvent event = dub.get(0);
            DubvioEventDetail dubdetail = dubvioEventDetailService.selectDubvioEventDetailById(event.getJbxxAjbh());
            if (dubdetail!=null){
                dubvioEventDetailService.updateDubvioEventDetail(dubvioEventDetail);
            }
            dubvioEvent.setDubvioId(event.getDubvioId());
            dubvioEventService.updateDubvioEvent(dubvioEvent);
        }
    }


    /**
     * 筛查超期分案
     * @param strategy
     * @param dubvioEvent
     * @return
     */
    private void cqfnMethod(Strategy strategy, DubvioEvent dubvioEvent,String ajlbId,DubvioEventDetail dubvioEventDetail) {
        System.out.println("--------------筛查超期分案   begin--------------");
        int param=Integer.valueOf(strategy.getStrategyParam());
        //策略适用案件
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");
        //判断该案件是否适用此策略
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            String Larq=dubvioEventDetail.getLaspLarq();
            String farq=dubvioEventDetail.getFaFarq();
            if (Larq==null){
                Larq=DateUtils.getTime();
            }
            if (farq==null){
                farq=DateUtils.getTime();
            }
            int days = Days.daysBetween(new DateTime(DateUtils.stringToDate(Larq)),new DateTime(DateUtils.stringToDate(farq))).getDays();
            if (days>param){
                saveMethod(strategy, dubvioEvent, dubvioEventDetail);
            }
        }
        System.out.println("--------------筛查超期分案   end--------------");
    }

    /**
     * 筛查 再审超期立案
     * @param strategy
     * @param dubvioEvent
     * @return
     */
    private void zscqlaMethod(Strategy strategy, DubvioEvent dubvioEvent,String ajlbId,DubvioEventDetail dubvioEventDetail) {
        int param=Integer.valueOf(strategy.getStrategyParam());
        //策略适用案件
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");
        //，收案日期-----立案日期>7
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            int days = Days.daysBetween(new DateTime(DateUtils.stringToDate(dubvioEvent.getJbxxSarq())),new DateTime(DateUtils.stringToDate(dubvioEvent.getJbxxLarq()))).getDays();
            if (days>param){
                saveMethod(strategy, dubvioEvent, dubvioEventDetail);
            }
        }
    }


    /**
     * 筛查 一审超期立案
     * @param strategy
     * @param dubvioEvent
     * @return
     */
    private void yscqlaMethod(Strategy strategy, DubvioEvent dubvioEvent,String ajlbId,String sdszrq,DubvioEventDetail dubvioEventDetail) {
        int param=Integer.valueOf(strategy.getStrategyParam());
        //策略适用案件
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");
        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            int days = Days.daysBetween(new DateTime(DateUtils.stringToDate(sdszrq)),new DateTime(DateUtils.stringToDate(dubvioEvent.getJbxxLarq()))).getDays();
            if (days>param){
                saveMethod(strategy, dubvioEvent, dubvioEventDetail);
            }
        }
    }



    /**
     * 筛查 二审超期立案
     * @param strategy
     * @param dubvioEvent
     * @return
     */
    private void escqMethod(Strategy strategy, DubvioEvent dubvioEvent,String ajlbId,DubvioEventDetail dubvioEventDetail) {
        int param=Integer.valueOf(strategy.getStrategyParam());
        //策略适用案件
        String[]strategyAjlb=strategy.getStrategyAjlb().split(",");

        if (Arrays.asList(strategyAjlb).contains(ajlbId)){
            //收案日期-立案日期>=2
            int days = Days.daysBetween(new DateTime(DateUtils.stringToDate(dubvioEvent.getJbxxSarq())),new DateTime(DateUtils.stringToDate(dubvioEvent.getJbxxLarq()))).getDays();
            if (days>param){
                saveMethod(strategy, dubvioEvent, dubvioEventDetail);
            }
        }
    }


    /**
     *
     * 获取可以违纪案件列表内容
     * @param
     * @return
     */

    private static DubvioEvent  getDubvioEvent(JSONObject ajlbJson,JSONArray ajxxArra){
        System.out.println("-----------------开始获取案件列表------------------------");
        //审判程序（一审,二审，再审）
        String spcx="";
        //诉讼性质
        String ssxz="";
        //收案日期
        String sarq="";
        //收到诉状日期
        String sdszrq="";
        //立案日期
        String larq="";
        //分案日期
        String farq="";
        //开庭日期
        String ktrq="";
        //案件编号
        String anbh=ajlbJson.getString("zhcxlist_StringAJBH");
        //原审案号
        String ah=ajlbJson.getString("zhcxlist_StringCAH");
        //承办人
        String cbr=ajlbJson.getString("zhcxlist_OrganNCBR").replace("\\n","");
        //承办人ID
        String cbrId=ajlbJson.getString("zhcxlist_OrganNCBRID").replace("\\n","");
        //被告人
        String bgr=ajlbJson.getString("zhcxlist_StringCDSR").replace("\\n","");
        //承办厅
        String cbt=ajlbJson.getString("zhcxlist_OrganCBSPT").replace("\\n","");
        //承办厅
        String cbtId=ajlbJson.getString("zhcxlist_OrganCBSPTID").replace("\\n","");
        //案件类别
        String ajlb=ajlbJson.getString("zhcxlist_CodeNAjlb").replace("\\n","");
        //案件类别Id
        String ajlbId=ajlbJson.getString("zhcxlist_CodeNAjlbId").replace("\\n","");

        //法院id
        String jbfyId=ajlbJson.getString("zhcxlist_Organ6aaceId").replace("\\n","");

        //立案日期
        larq=ajlbJson.getString("zhcxlist_DateDLARQ").replace("\\n","");

        if (ajxxArra!=null){
            //获取立案日期 和 分案日期
            List<Map<String,Object>> mapListJson = (List)ajxxArra;
            for (Map<String,Object> ajmap:mapListJson) {
                String caption=String.valueOf(ajmap.get("caption")).replace("\"","");
                if("立案审批".equals(caption)){
                    System.out.println("-----------立案审批------------");
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "立案日期".equals(name)){
                            //获取立案日期
                            larq=String.valueOf(laMap.get("value"));
                        }
                        if ( "分案日期".equals(name)){
                            //获取分案日期
                            farq=String.valueOf(laMap.get("value"));
                        }
                    }
                }
                if("分案".equals(caption)){
                    System.out.println("-----------分案------------");
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "分案日期".equals(name)){
                            //获取分案日期
                            if("".equals(farq)){
                                farq=String.valueOf(laMap.get("value"));
                            }
                        }
                        if ( "分案确认日期".equals(name)){
                            //分案确认日期
                            if("".equals(farq)||farq==null){
                                farq=String.valueOf(laMap.get("value"));
                            }
                        }
                    }
                }

                if("收案登记".equals(caption)){
                    System.out.println("-----------收案登记------------");
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "收案日期".equals(name)){
                            //获取立案日期
                            sarq=String.valueOf(laMap.get("value"));
                        }
                    }
                }

                if((ajlb+"开庭").equals(caption)){
                    System.out.println("-----------开庭信息------------");
                    JSONObject jsonObject = (JSONObject) ajmap.get("strJson");
                    List<Map<String,Object>> lasplist= (List)jsonObject.getJSONArray("\""+caption+"\"");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "开始时间".equals(name)){
                            //获取开庭日期
                            ktrq=String.valueOf(laMap.get("value"));
                        }
                    }
                }else{
                    ktrq="";
                }
            }
        }

        //结案日期
        String jarq=ajlbJson.getString("zhcxlist_DateDJARQ").replace("\"","");
        //归档日期
        String gdrq=ajlbJson.getString("zhcxlist_DateGDRQ").replace("\"","");

        //创建可疑违纪事件（列表）实体
        DubvioEvent dubvioEvent=new DubvioEvent(
                anbh,ah,cbrId,cbr
                ,bgr,cbtId,cbt,ajlb,sarq,larq,farq,
                ktrq,jarq,gdrq,"1",
                DateUtils.getNowDate(),"2");
        dubvioEvent.setJbfyId(jbfyId);

        return dubvioEvent;
    }


    /***
     *  获取案件详细内荣
     *
     *
     *
     */
    private static DubvioEventDetail  getDubvioEventDetai( JSONObject ajlbJson,JSONArray ajxxArra){

        DubvioEventDetail deDetail=new DubvioEventDetail();
        //案件编号
        String ajbh=ajlbJson.getString("zhcxlist_StringAJBH");
        deDetail.setJbxxAjbh(ajbh);

        //获取案件详细
        if (ajxxArra!=null){
            List<Map<String,Object>> mapListJson = (List)ajxxArra;
            for (Map<String,Object> ajmap:mapListJson) {
                String caption=String.valueOf(ajmap.get("caption")).replace("\"","");
                if("基本信息".equals(caption)){
                    System.out.println("-----------基本信息------------");
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "当事人".equals(name)){
                            String dsr=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxDsr(dsr);
                        }
                        if ( "新案件类别".equals(name)){
                            String xajlb=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxXajlb(xajlb);
                        }
                        if ( "案件类别".equals(name)){
                            String ajlb=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxAjlb(ajlb);
                        }
                        if ( "经办法院".equals(name)){
                            String jbfy=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxJbfy(jbfy);
                        }
                        if ( "经办法院".equals(name)){
                            String jbfy=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxJbfy(jbfy);
                        }
                        if ( "审判程序".equals(name)){
                            String spcx=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSpcx(spcx);
                        }
                        if ( "适用程序".equals(name)){
                            String sycx=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSycx(sycx);
                        }
                        if ( "收案意见".equals(name)){
                            String sayj=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSayj(sayj);
                        }
                        if ( "案号".equals(name)){
                            String ah=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxAh(ah);
                        }

                        if ( "诉讼性质".equals(name)){
                            String ssxz=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSsxz(ssxz);
                        }
                        if ( "一审诉讼性质".equals(name)){
                            String ysssxz=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxYsssxz(ysssxz);
                        }
                        if ( "最后修改日期".equals(name)){
                            String zhxgsj=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxZhxgsj(zhxgsj);
                        }
                        if ( "案件名称".equals(name)){
                            String ajmc=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxAjmc(ajmc);
                        }
                        if ( "案件进展阶段".equals(name)){
                            String ajjzjd=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxAjjzjd(ajjzjd);
                        }
                        if ( "是否有电子卷宗".equals(name)){
                            String sfydzjz=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSfydzjz(sfydzjz);
                        }
                        if ( "是否职务犯罪".equals(name)){
                            String sfzwfz=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSfzwfz(sfzwfz);
                        }
                        if ( "审判流程公开".equals(name)){
                            String splcgk=String.valueOf(laMap.get("value"));
                            deDetail.setJbxxSplcgk(splcgk);
                        }

                    }
                }
                if("收案登记".equals(caption)){
                    System.out.println("-----------收案登记------------");
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "收案登记人".equals(name)){
                            String sadjr=String.valueOf(laMap.get("value"));
                            deDetail.setSadjSadjr(sadjr);
                        }
                        if ( "收案日期".equals(name)){
                            String  sadjSasj=String.valueOf(laMap.get("value"));
                            deDetail.setSadjSasj(sadjSasj);
                        }
                        if ( "敏感案件".equals(name)){
                            String  sadjMgaj=String.valueOf(laMap.get("value"));
                            deDetail.setSadjMgaj(sadjMgaj);
                        }
                        if ( "地域涉及".equals(name)){
                            String  sadjDysj=String.valueOf(laMap.get("value"));
                            deDetail.setSadjDysj(sadjDysj);
                        }
                        if ( "收到诉状日期".equals(name)){
                            String  sdszsj=String.valueOf(laMap.get("value"));
                            deDetail.setSadjSdszsj(sdszsj);
                        }
                        if ( "立案案由".equals(name)){
                            String  laay=String.valueOf(laMap.get("value"));
                            deDetail.setSadjLaay(laay);
                        }
                        if ( "管辖依据".equals(name)){
                            String  gxyj=String.valueOf(laMap.get("value"));
                            deDetail.setSadjGxyj(gxyj);
                        }
                        if ( "立案部门".equals(name)){
                            String  labm=String.valueOf(laMap.get("value"));
                            deDetail.setSadjLabm(labm);
                        }
                        if ( "收案庭室".equals(name)){
                            String  sats=String.valueOf(laMap.get("value"));
                            deDetail.setSadjSats(sats);
                        }
                        if ( "立案审查人".equals(name)){
                            String  sats=String.valueOf(laMap.get("value"));
                            deDetail.setSadjSats(sats);
                        }
                    }
                }
                if("立案审查".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "立案审查人".equals(name)){
                            String lascr=String.valueOf(laMap.get("value"));
                            deDetail.setLascLascr(lascr);
                        }
                        if ( "立案审查日期".equals(name)){
                            String lascrq=String.valueOf(laMap.get("value"));
                            deDetail.setLascLascrq(lascrq);
                        }
                        if ( "立案审查意见".equals(name)){
                            String lascyj=String.valueOf(laMap.get("value"));
                            deDetail.setLascLascyj(lascyj);
                        }
                    }
                }
                if("立案审批".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "立案审批人".equals(name)){
                            String laspr=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLaspr(laspr);
                        }
                        if ( "立案审批日期".equals(name)){
                            String lasprq=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLasprq(lasprq);
                        }
                        if ( "立案审批意见".equals(name)){
                            String laspyj=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLaspyj(laspyj);
                        }
                        if ( "立案日期".equals(name)){
                            String larq=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLarq(larq);
                        }
                        if ( "立案庭室".equals(name)){
                            String lats=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLats(lats);
                        }
                        if ( "立案人".equals(name)){
                            String lar=String.valueOf(laMap.get("value"));
                            deDetail.setLaspLar(lar);
                        }
                        if ( "分案日期".equals(name)){
                            String farq=String.valueOf(laMap.get("value"));
                            deDetail.setFaFarq(farq);
                        }
                    }
                }
                if("分案".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "承办审判庭".equals(name)){
                            String cbspt=String.valueOf(laMap.get("value"));
                            deDetail.setFaCbspt(cbspt);
                        }
                        if ( "分案日期".equals(name)){
                            String farq=String.valueOf(laMap.get("value"));
                            deDetail.setFaFarq(farq);
                        }
                        if ( "承办人".equals(name)){
                            String cbr=String.valueOf(laMap.get("value"));
                            deDetail.setFaCbr(cbr);
                        }
                        if ( "分案确认人".equals(name)){
                            String faqrr=String.valueOf(laMap.get("value"));
                            deDetail.setFaFaqrr(faqrr);
                        }
                        if ( "分案确认日期".equals(name)){
                            String faqrrq=String.valueOf(laMap.get("value"));
                            deDetail.setFaFaqrrq(faqrrq);
                        }
                        if ( "移交状态".equals(name)){
                            String yjzt=String.valueOf(laMap.get("value"));
                            deDetail.setFaYjzt(yjzt);
                        }
                    }
                }
                if("办理".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "排期状态".equals(name)){
                            String pqzt=String.valueOf(laMap.get("value"));
                            deDetail.setBlPqzt(pqzt);
                        }
                        if ( "被告人同意认罪程序".equals(name)){
                            String bgrtyrzcx=String.valueOf(laMap.get("value"));
                            deDetail.setBlBgrtyrzcx(bgrtyrzcx);
                        }
                        if ( "是否庭前证据交换".equals(name)){
                            String sftqzjjh=String.valueOf(laMap.get("value"));
                            deDetail.setBlSftqzjjh(sftqzjjh);
                        }
                        if ( "是否法院收集调取证据".equals(name)){
                            String sffysjdqzj=String.valueOf(laMap.get("value"));
                            deDetail.setBlSffysjdqzj(sffysjdqzj);
                        }
                        if ( "公开审理".equals(name)){
                            String gksl=String.valueOf(laMap.get("value"));
                            deDetail.setBlGksl(gksl);
                        }
                        if ( "开庭审理".equals(name)){
                            String kysl=String.valueOf(laMap.get("value"));
                            deDetail.setBlKysl(kysl);
                        }
                        if ( "诉讼代理人数（辩护人数）".equals(name)){
                            String ssdlrs=String.valueOf(laMap.get("value"));
                            deDetail.setBlSsdlrs(ssdlrs);
                        }
                        if ( "律师人数".equals(name)){
                            String lsrs=String.valueOf(laMap.get("value"));
                            deDetail.setBlLsrs(lsrs);
                        }
                        if ( "法律援助律师人数".equals(name)){
                            String flyzlsrs=String.valueOf(laMap.get("value"));
                            deDetail.setBlFlyzlsrs(flyzlsrs);
                        }
                        if ( "重新排期".equals(name)){
                            String cxpq=String.valueOf(laMap.get("value"));
                            deDetail.setBlCxpq(cxpq);
                        }
                        if ( "是否独任审判".equals(name)){
                            String sfdrsp=String.valueOf(laMap.get("value"));
                            deDetail.setBlSfdrsp(sfdrsp);
                        }
                        if ( "是否有人民陪审员".equals(name)){
                            String sfyrmpsy=String.valueOf(laMap.get("value"));
                            deDetail.setBlSfyrmpsy(sfyrmpsy);
                        }
                    }
                }
                if("附带民事诉讼".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "是否提起附带民事诉讼".equals(name)){
                            String sftqfdmsss=String.valueOf(laMap.get("value"));
                            deDetail.setFdmsssSftqfdmsss(sftqfdmsss);
                        }
                    }
                }
                if("结案".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "移送状态".equals(name)){
                            String yszt=String.valueOf(laMap.get("value"));
                            deDetail.setJaYszt(yszt);
                        }
                        if ( "是否上诉".equals(name)){
                            String sfss=String.valueOf(laMap.get("value"));
                            deDetail.setJaSfss(sfss);
                        }
                        if ( "有无结案文书".equals(name)){
                            String ywjaws=String.valueOf(laMap.get("value"));
                            deDetail.setJaYwjaws(ywjaws);
                        }

                    }
                }
                if("审限信息".equals(caption)){
                    List<Map<String,Object>> lasplist=(List)ajmap.get("strJson");
                    for (Map<String,Object> laMap:lasplist ) {
                        String name=String.valueOf(laMap.get("name")).replace("\"","");
                        if ( "审限总天数".equals(name)){
                            String sxzts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxSxzts(sxzts);
                        }
                        if ( "延长天数".equals(name)){
                            String ycts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxYcts(ycts);
                        }
                        if ( "扣除天数".equals(name)){
                            String kcts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxKcts(kcts);
                        }
                        if ( "审限起始日期".equals(name)){
                            String sxqsrq=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxSxqsrq(sxqsrq);
                        }
                        if ( "审限届满日期".equals(name)){
                            String sxjmrq=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxSxjmrq(sxjmrq);
                        }
                        if ( "法定审限天数".equals(name)){
                            String fdsxts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxFdsxts(fdsxts);
                        }
                        if ( "超审限天数".equals(name)){
                            String csxts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxCsxts(csxts);
                        }
                        if ( "超审限".equals(name)){
                            String csx=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxCsx(csx);
                        }
                        if ( "审理天数".equals(name)){
                            String slts=String.valueOf(laMap.get("value"));
                            deDetail.setSxxxSlts(slts);
                        }


                    }
                }
            }
        }

        String csx=ajlbJson.getString("zhcxlist_NumericCSXTS");
        deDetail.setSxxxCsxts(csx);


        if (deDetail.getJbxxAh()==null){
            deDetail.setJbxxAh("无");
        }
        return deDetail;

    }

}
