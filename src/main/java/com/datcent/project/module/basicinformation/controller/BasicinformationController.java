package com.datcent.project.module.basicinformation.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.annualIncome.domain.AnnualIncome;
import com.datcent.project.module.annualIncome.service.IAnnualIncomeService;
import com.datcent.project.module.existingProperty.domain.ExistingProperty;
import com.datcent.project.module.existingProperty.service.IExistingPropertyService;
import com.datcent.project.module.goingAbroad.domain.GoingAbroad;
import com.datcent.project.module.goingAbroad.service.IGoingAbroadService;
import com.datcent.project.module.handlingSituation.domain.HandlingSituation;
import com.datcent.project.module.handlingSituation.service.IHandlingSituationService;
import com.datcent.project.module.implementationSystem.domain.ImplementationSystem;
import com.datcent.project.module.implementationSystem.service.IImplementationSystemService;
import com.datcent.project.module.itemreport.domain.Itemreport;
import com.datcent.project.module.itemreport.service.IItemreportService;
import com.datcent.project.module.massesReport.domain.MassesReport;
import com.datcent.project.module.massesReport.service.IMassesReportService;
import com.datcent.project.module.member.domain.Member;
import com.datcent.project.module.member.service.IMemberService;
import com.datcent.project.module.memberPractitioners.domain.MemberPractitioners;
import com.datcent.project.module.memberPractitioners.service.IMemberPractitionersService;
import com.datcent.project.module.serving.domain.Serving;
import com.datcent.project.module.serving.service.IServingService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import javafx.scene.Node;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 基本情况登记 信息操作处理
 *
 * @author datcent
 * @date 2019-03-29
 */
@Controller
@RequestMapping("/module/basicinformation")
public class BasicinformationController extends BaseController {
    private String prefix = "module/basicinformation";

    @Autowired
    private IBasicinformationService basicinformationService;

    @Autowired
    private ICourtOrganService courtOrganService;


    @Autowired
    private IExistingPropertyService existingPropertyService;

    @Autowired
    private IGoingAbroadService goingAbroadService;

    @Autowired
    private IImplementationSystemService implementationSystemService;

    @Autowired
    private IHandlingSituationService handlingSituationService;

    @Autowired
    private IMassesReportService massesReportService;


    @Autowired
    private IMemberPractitionersService memberPractitionersService;

    @Autowired
    private IItemreportService itemreportService;


    @Autowired
    private IAnnualIncomeService annualIncomeService;

    @Autowired
    private IServingService servingService;

    @Autowired
    private IMemberService memberService;


    @RequiresPermissions("module:basicinformation:view")
    @GetMapping()
    public String basicinformation() {
        return prefix + "/basicinformation";
    }

    /**
     * 查询基本情况登记列表
     */
    //@RequiresPermissions("module:basicinformation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Basicinformation basicinformation) {
        startPage();

        PageDomain pageDomain = TableSupport.buildPageRequest();

        if (StringUtils.isNotEmpty(basicinformation.getDeptId())) {
            long id = Long.parseLong(basicinformation.getDeptId());
            CourtOrgan court = courtOrganService.selectDeptByDeptId(id);

            if (StringUtils.isNotEmpty(court.getCid())) {
                if (court.getCid().length() == 4) {

                    List<String> list = courtOrganService.queryCidByParentId(basicinformation.getDeptId());

                    String str = String.join(",", list);
                    basicinformation.setJbqkBmd(str);
                } else {
                    basicinformation.setJbqkBmd(court.getCid());
                }
            }

        }
        List<Basicinformation> list = basicinformationService.selectBasicinformationLists(basicinformation, pageDomain);
        return getDataTable(list);
    }

    /**
     * 新增基本情况登记
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存基本情况登记
     */
    @Log(title = "基本情况登记", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Basicinformation basicinformation) {
        AjaxResult json = new AjaxResult();
        int rows = -1;
        if (StringUtils.isEmpty(basicinformation.getJbqkId())) {
            if (StringUtils.isEmpty(basicinformation.getJbqkCid())) {
                basicinformation.setJbqkCid(StringUtils.getUUID());
            }
            basicinformation.setJbqkId(StringUtils.getUUID());
            basicinformation.setCreateTime(DateUtils.getNowDate());
            basicinformation.setIsEdit("9");
            json.put("jbqkCid", basicinformation.getJbqkId());
            rows = basicinformationService.insertBasicinformation(basicinformation);
        } else {
            basicinformation.setIsEdit("9");
            basicinformation.setCreateTime(DateUtils.getNowDate());
            rows = basicinformationService.updateBasicinformation(basicinformation);
        }
        if (rows > 0) {
            json.put("code", 200);
            json.put("msg", "保存成功！");
            basicinformation = basicinformationService.selectBasicinformationById(basicinformation.getJbqkId());
            json.put("jbqkZw", basicinformation.getJbqkZw());
            json.put("jbqkXm", basicinformation.getJbqkXm());
            json.put("jbqkBm", basicinformation.getJbqkBm());
            json.put("jbqkDw", basicinformation.getJbqkDw());
            json.put("jbqk", basicinformation.getJbqkBm());
            if (StringUtils.isNotEmpty(basicinformation.getJbqkCid())) {
                json.put("jbqkCid", basicinformation.getJbqkCid());
            } else {
                json.put("jbqkCid", basicinformation.getJbqkId());
            }
            json.put("jbqkId", basicinformation.getJbqkId());
        } else {
            json.put("code", 500);
            json.put("msg", "保存失败！");
        }
        return json;
    }

    /**
     * 修改基本情况登记
     */
    @GetMapping("/edit/{jbqkId}")
    public String edit(@PathVariable("jbqkId") String jbqkId, ModelMap mmap) {
        Basicinformation basicinformation = basicinformationService.selectBasicinformationById(jbqkId);
        mmap.put("basicinformation", basicinformation);
        return prefix + "/edit";
    }


    /**
     * 修改保存基本情况登记
     */
    @Log(title = "基本情况登记", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(String basicdata, String jbqkZyrz, String jbqkJtcy, String jbqkId, String jbqkCid) {
        AjaxResult json = new AjaxResult();
        List<Member> members = JSONObject.parseArray(jbqkJtcy, Member.class);
        int count = 0;
        for (Member member : members) {
            if (StringUtils.isEmpty(member.getId())){
                if (StringUtils.isEmpty(member.getName())
                        &&StringUtils.isEmpty(member.getBirthday())
                        &&StringUtils.isEmpty(member.getRelationship())
                        &&StringUtils.isEmpty(member.getUnitPost())
                        &&StringUtils.isEmpty(member.getUnitProperty())){
                    continue;
                }else{
                    member.setId(StringUtils.getUUID());
                    member.setBasicinforId(jbqkId);
                    int row=memberService.insertMember(member);
                    count+=row;
                }
            }else{
                member.setBasicinforId(jbqkId);
                int row= memberService.updateMember(member);
                count+=row;
            }
        }
        List<Serving> servings = JSONObject.parseArray(jbqkZyrz, Serving.class);
        for (Serving serving : servings) {
            if (StringUtils.isEmpty(serving.getId())){
                if (StringUtils.isEmpty(serving.getStartTime())
                        &&StringUtils.isEmpty(serving.getEndTime())
                        &&StringUtils.isEmpty(serving.getAssumeOffice())
                        &&StringUtils.isEmpty(serving.getToUnit())){
                    continue;
                }else{
                    serving.setId(StringUtils.getUUID());
                    serving.setBasicinforId(jbqkId);
                    int row=servingService.insertServing(serving);
                    count+=row;
                }
            }else{
                serving.setBasicinforId(jbqkId);
                int row=servingService.updateServing(serving);
                count+=row;
            }
        }
        Basicinformation basicinformation = JSONObject.parseObject(basicdata, Basicinformation.class);
        basicinformation.setJbqkCid(jbqkCid);
        basicinformation.setJbqkId(jbqkId);
        basicinformation.setIsEdit("9");
        if (StringUtils.isNotEmpty(basicinformation.getJbqkId())) {
            int rows = basicinformationService.updateBasicinformation(basicinformation);
            count+=rows;
        }
        if (count>0) {
            json.put("code", 200);
            json.put("msg", "保存成功！");
        } else {
            json.put("code", 500);
            json.put("msg", "保存失败!");
        }

        return json;

    }

    /**
     * 删除基本情况登记
     */
    @Log(title = "基本情况登记", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(basicinformationService.deleteBasicinformationByIds(ids));
    }


    /**
     * 查询基本信息
     *
     * @param basic
     * @return
     */
    @PostMapping("/select")
    @ResponseBody
    public Basicinformation select(Basicinformation basic) {
        Basicinformation basicinformation = basicinformationService.selectBasicinformationById(basic.getJbqkId());
        if (StringUtils.isNotNull(basicinformation)) {
            return basicinformation;
        } else {
            return null;
        }
    }


    /**
     * 查看廉政档案信息
     */
    @GetMapping("/detail/{jbqkCid}")
    public String detail(@PathVariable("jbqkCid") String jbqkCid, ModelMap mmap) {
        getdetail(jbqkCid, mmap);
        return "module/incorrupt/cincorrupt";
    }



    /**
     * 查看廉政档案信息
     */
    @GetMapping("/cdetail/{jbqkCid}")
    public String cdetail(@PathVariable("jbqkCid") String jbqkCid, ModelMap mmap) {
        getdetail(jbqkCid, mmap);
        return "module/incorrupt/dincorrupt";
    }

    /***
     *
     * 获取廉政档案信息私有方法
     * @param jbqkCid
     * @param mmap
     */
    private void getdetail(String jbqkCid, ModelMap mmap) {
        Basicinformation basic = new Basicinformation();
        basic.setJbqkCid(jbqkCid);
        List<Basicinformation> list = basicinformationService.selectBasicinformationList(basic);
        String jbqkId = "";
        if (list.size() > 0) {
            basic = list.get(0);
            mmap.put("basic", basic);
        } else {
            mmap.put("basic", basic);
        }
        jbqkId = basic.getJbqkId();
        //基本信息


        //主要任职情况
        Serving server = new Serving();
        server.setBasicinforId(basic.getJbqkId());
        List<Serving> serverList = servingService.selectServingList(server);
        mmap.put("serverList", serverList);

        //家庭成员信息
        Member member = new Member();
        member.setBasicinforId(basic.getJbqkId());
        List<Member> memberList = memberService.selectMemberList(member);
        mmap.put("memberList", memberList);

        //工作人员个人有关事项
        Itemreport itemreport = new Itemreport();
        itemreport.setSxbgCid(jbqkCid);
        List<Itemreport> itemreportList = itemreportService.selectItemreportList(itemreport);
        if (itemreportList.size() > 0) {
            itemreport = itemreportList.get(0);
        }
        jbqkId = basic.getJbqkId();
        for (Itemreport itemreport1 : itemreportList) {
            itemreport = itemreport1;
        }
        mmap.put("itemreport", itemreport);

        //个人经商办企业情况
        MemberPractitioners memberP = new MemberPractitioners();
        memberP.setCid(jbqkCid);
        List<MemberPractitioners> memberPlist = memberPractitionersService.selectMemberPractitionersList(memberP);
        for (MemberPractitioners memberPractitioners : memberPlist) {
            memberP = memberPractitioners;
        }
        mmap.put("memberP", memberP);

        //家庭财产情况
        ExistingProperty existingProperty = new ExistingProperty();
        existingProperty.setCid(jbqkCid);
        List<ExistingProperty> existingPlist = existingPropertyService.selectExistingPropertyList(existingProperty);
        mmap.put("existingPlist", existingPlist);

        //家庭全年收入
        AnnualIncome annualIncome = new AnnualIncome();
        annualIncome.setCid(jbqkCid);
        List<AnnualIncome> annualIncomeList = annualIncomeService.selectAnnualIncomeList(annualIncome);
        for (AnnualIncome annualIncome1 : annualIncomeList) {
            annualIncome = annualIncome1;
        }
        mmap.put("annualIncome", annualIncome);

        //工作人员出国
        GoingAbroad goingAbroad = new GoingAbroad();
        goingAbroad.setCid(jbqkCid);
        List<GoingAbroad> goingAbroadList = goingAbroadService.selectGoingAbroadList(goingAbroad);
        for (GoingAbroad abroad : goingAbroadList) {
            goingAbroad = abroad;
        }
        mmap.put("goingAbroad", goingAbroad);

        //落实规章制度情况
        ImplementationSystem implementation = new ImplementationSystem();
        implementation.setCid(jbqkCid);
        List<ImplementationSystem> implementationList = implementationSystemService.selectImplementationSystemList(implementation);
        for (ImplementationSystem implementationSystem : implementationList) {
            implementation = implementationSystem;
        }
        mmap.put("implementation", implementation);

        //党政纪处理
        HandlingSituation handling = new HandlingSituation();
        handling.setCid(jbqkCid);
        List<HandlingSituation> handlingList = handlingSituationService.selectHandlingSituationList(handling);
        if (handlingList.size() > 0) {
            mmap.put("handling", handlingList.get(0));
        } else {
            mmap.put("handling", handling);
        }

        //群众举报情况
        MassesReport massesReport = new MassesReport();
        massesReport.setCid(jbqkCid);
        List<MassesReport> massesReportList = massesReportService.selectMassesReportList(massesReport);
        if (massesReportList.size() > 0) {
            mmap.put("massesReport", massesReportList.get(0));
        } else {
            mmap.put("massesReport", massesReport);
        }
    }


    /**
     * 查看廉政档案状态
     */
    @Log(title = "查看廉政档案状态", businessType = BusinessType.INSERT)
    @PostMapping("/check")
    @ResponseBody
    public AjaxResult check(Basicinformation basicinformation) {
        AjaxResult json = new AjaxResult();
        List<Basicinformation> basics = basicinformationService.selectBasicinformationList(basicinformation);

        if (basics.size() > 0) {
            json.put("code", 200);
            for (Basicinformation basic : basics) {
                basicinformation = basic;
            }
            String isEdit = basicinformation.getIsEdit();
            json.put("jbqkCid", basicinformation.getJbqkCid());
            if ("0".equals(isEdit)) {
                json.put("msg", "审核通过！");
                json.put("isEdit", isEdit);
            } else if ("1".equals(isEdit)) {
                json.put("msg", "审核未通过！");
                json.put("isEdit", isEdit);
            } else if ("9".equals(isEdit)) {
                json.put("msg", "等待审核中...");
                json.put("isEdit", isEdit);
            } else {
                json.put("code", 500);
                json.put("isEdit", isEdit);
            }
        } else {
            json.put("code", 201);
            json.put("msg", "未找到该人员廉政信息，请核对填写信息，或完成廉政档案录入！");
        }
        return json;
    }

    /**
     * 修改基本情况登记
     */
    @GetMapping("/editincorrupt/{jbqkCid}")
    public String editincorrupt(@PathVariable("jbqkCid") String jbqkCid, ModelMap mmap) {

        Basicinformation basic = new Basicinformation();
        basic.setJbqkCid(jbqkCid);
        List<Basicinformation> list = basicinformationService.selectBasicinformationList(basic);
        String jbqkId = "";
        if (list.size() > 0) {
            basic = list.get(0);
        }
        basic.setIsEdit("9");
        basicinformationService.updateBasicinformation(basic);
        getdetail(jbqkCid, mmap);

        return "module/incorrupt/editincorrupt";
    }

    @PostMapping("/reg")
    @ResponseBody
    public AjaxResult reg(Basicinformation basicinformation) {
        Integer i = basicinformationService.selectBasicinformationByCardNo(basicinformation.getJbqkSfzh());
        if (i > 0) {
            return error("该用户已注册！");
        } else {
            if (StringUtils.isEmpty(basicinformation.getJbqkCid())) {
                basicinformation.setJbqkCid(StringUtils.getUUID());
            }
            //初始值设置为9
            basicinformation.setIsEdit("9");
            basicinformation.setJbqkId(StringUtils.getUUID());

            //工作履历
            Serving servings = new Serving();
            for (int j = 0; j < 8; j++) {
                servings.setId(StringUtils.getUUID());
                servings.setBasicinforId(basicinformation.getJbqkId());
                servingService.insertServing(servings);
            }

            //家庭成员
            Member members = new Member();
            for (int j = 0; j < 6; j++) {
                members.setId(StringUtils.getUUID());
                members.setBasicinforId(basicinformation.getJbqkId());
                memberService.insertMember(members);
            }

            //工作人员个人有关事项报告表
            Itemreport itemreport = new Itemreport();
            itemreport.setSxbgId(StringUtils.getUUID());
            itemreport.setSxbgCid(basicinformation.getJbqkCid());
            itemreportService.insertItemreport(itemreport);

            //工作人员配偶、子女及其配偶从业情况登记表
            MemberPractitioners memberPractitioners = new MemberPractitioners();
            memberPractitioners.setPozncyqkId(StringUtils.getUUID());
            memberPractitioners.setCid(basicinformation.getJbqkCid());
            memberPractitionersService.insertMemberPractitioners(memberPractitioners);

            //现有财产状况
            ExistingProperty existingPropertys = new ExistingProperty();
            for (int j = 0; j < 5; j++) {
                existingPropertys.setJtccqkId(StringUtils.getUUID());
                existingPropertys.setCid(basicinformation.getJbqkCid());
                existingPropertyService.insertExistingProperty(existingPropertys);
            }

            //家庭全年收入情况表
            AnnualIncome annualIncome = new AnnualIncome();
            annualIncome.setId(StringUtils.getUUID());
            annualIncome.setCid(basicinformation.getJbqkCid());
            annualIncomeService.insertAnnualIncome(annualIncome);

            //出国情况登记表
            GoingAbroad goingAbroad = new GoingAbroad();
            goingAbroad.setCid(basicinformation.getJbqkCid());
            goingAbroad.setCgqkdjId(StringUtils.getUUID());
            goingAbroadService.insertGoingAbroad(goingAbroad);

            //落实规章制度情况
            ImplementationSystem implementationSystem = new ImplementationSystem();
            implementationSystem.setCid(basicinformation.getJbqkCid());
            implementationSystem.setLsgzzdqkId(StringUtils.getUUID());
            implementationSystemService.insertImplementationSystem(implementationSystem);

            //党政纪处理及“四拒”情况
            HandlingSituation handlingSituation = new HandlingSituation();
            handlingSituation.setCid(basicinformation.getJbqkCid());
            handlingSituation.setCljsjqkId(StringUtils.getUUID());
            handlingSituationService.insertHandlingSituation(handlingSituation);

            //群众举报情况（由监察处填写）
            MassesReport massesReport = new MassesReport();
            massesReport.setCid(basicinformation.getJbqkCid());
            massesReport.setXfjbqkId(StringUtils.getUUID());
            massesReportService.insertMassesReport(massesReport);
            return toAjax(basicinformationService.insertBasicinformation(basicinformation));
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(Basicinformation basicinformation) {
        Basicinformation basicinformation1 = new Basicinformation();
        basicinformation1.setJbqkSfzh(basicinformation.getJbqkSfzh());
        List<Basicinformation> basicinformationList = basicinformationService.selectBasicinformationList(basicinformation1);
        if (basicinformationList.size() == 0) {
            return error("身份证号码不存在！");
        } else {
            Basicinformation b = basicinformationList.get(0);
            if (!b.getPassWord().equals(basicinformation.getPassWord())) {
                return error("口令不正确！");
            } else {
                AjaxResult ajaxResult = new AjaxResult();
                ajaxResult.put("code", 0);
                ajaxResult.put("msg", "操作成功！");
                ajaxResult.put("sfz", b.getJbqkSfzh());
                ajaxResult.put("id", b.getJbqkCid());
                return ajaxResult;
            }
        }
    }

    @GetMapping("/loginView")
    public String loginView(String sfz, String id, ModelMap modelMap) {
        modelMap.put("sfz", sfz);
        modelMap.put("id", id);
        return "module/incorrupt/mylist";
    }

    @PostMapping("/mylist")
    @ResponseBody
    public TableDataInfo mylist(Basicinformation basicinformation) {
        CourtOrgan courtOrgan = courtOrganService.selectLeaderByCardNo(basicinformation.getJbqkSfzh());
        List<Basicinformation> basicinformationList = new ArrayList<Basicinformation>();
        startPage();
        if (courtOrgan != null && "1".equals(courtOrgan.getLeaderStatus())) {
           basicinformationList = basicinformationService.selectBasicinformationByApproveAndCardNo(basicinformation);
        } else {
            basicinformationList = basicinformationService.selectBasicinformationList(basicinformation);
        }

        return getDataTable(basicinformationList);
    }

    @PostMapping("/password_edit")
    @ResponseBody
    public AjaxResult passWordEdit(Basicinformation basicinformation, String password) {
        Basicinformation find = new Basicinformation();
        find.setJbqkSfzh(basicinformation.getJbqkSfzh());
        List<Basicinformation> bi = basicinformationService.selectBasicinformationList(find);
        if (bi.size() > 0) {
            if (!bi.get(0).getPassWord().equals(password)) {
                return error("旧口令有误！");
            } else {
                basicinformation.setJbqkId(bi.get(0).getJbqkId());
                return toAjax(basicinformationService.updateBasicinformation(basicinformation));
            }
        } else {
            return error("身份证号码不存在！");
        }
    }

    @PostMapping("/approve")
    @ResponseBody
    public AjaxResult approve(Basicinformation basicinformation) {
        Basicinformation basicinformation1 = new Basicinformation();
        basicinformation1.setJbqkSfzh(basicinformation.getJbqkSfzh());
        Basicinformation basicinformation2 = basicinformationService.selectBasicinformationList(basicinformation1).get(0);
        basicinformation2.setIsEdit(basicinformation.getIsEdit());
        AjaxResult ajaxResult = toAjax(basicinformationService.updateBasicinformation(basicinformation2));
        return ajaxResult;
    }
}
