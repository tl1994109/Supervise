package com.datcent.project.module.cbttj.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.zt.SystemLogTimerAnnotation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.cbttj.domain.Cbttj;
import com.datcent.project.module.cbttj.service.ICbttjService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 承办庭统计 信息操作处理
 *
 * @author datcent
 * @date 2019-03-25
 */
@Controller
@RequestMapping("/module/cbttj")
public class CbttjController extends BaseController {
    private String prefix = "module/cbttj";

    @Autowired
    private ICbttjService cbttjService;

    @Autowired
    private ICourtOrganService courtOrganService;

    @RequiresPermissions("module:cbttj:view")
    @GetMapping()
    public String cbttj() {
        return prefix + "/cbttj";
    }

    /**
     * 查询承办庭统计列表
     */
    @SystemLogTimerAnnotation("查询承办庭统计列表")
    @RequiresPermissions("module:cbttj:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Cbttj cbttj) {
        startPage();
        List<Cbttj> list = cbttjService.selectCbttjList(cbttj);
        return getDataTable(list);
    }

    /**
     * 新增承办庭统计
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存承办庭统计
     */
    @RequiresPermissions("module:cbttj:add")
    @Log(title = "承办庭统计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Cbttj cbttj) {
        return toAjax(cbttjService.insertCbttj(cbttj));
    }

    /**
     * 修改承办庭统计
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Cbttj cbttj = cbttjService.selectCbttjById(id);
        mmap.put("cbttj", cbttj);
        return prefix + "/edit";
    }

    /**
     * 修改保存承办庭统计
     */
    @RequiresPermissions("module:cbttj:edit")
    @Log(title = "承办庭统计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Cbttj cbttj) {
        return toAjax(cbttjService.updateCbttj(cbttj));
    }

    /**
     * 删除承办庭统计
     */
    @RequiresPermissions("module:cbttj:remove")
    @Log(title = "承办庭统计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cbttjService.deleteCbttjByIds(ids));
    }


    /**
     * 统计各个厅当月案件旧收 新存 已结 未结 数量
     *
     * @return
     */
    @GetMapping("/queryAjCountByCbt")
    @ResponseBody
    public Map<String, Object> queryAjCountByCbt(String time, String fyId) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = formatter.parse(time);
            formatter = new SimpleDateFormat("yyyy-MM");
            time = formatter.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        List<CourtOrgan> cbtList = courtOrganService.selectCourtOrganByParentId(fyId);
        List<String> xArex = new ArrayList<String>();
        List<Object> yArex = new ArrayList<Object>();
        List<String> jcList = new ArrayList<String>();
        List<String> xsList = new ArrayList<String>();
        List<String> wjList = new ArrayList<String>();
        List<String> yjList = new ArrayList<String>();
        Map<String, Object> jcMap = new HashMap<>();
        Map<String, Object> xsMap = new HashMap<>();
        Map<String, Object> wjMap = new HashMap<>();
        Map<String, Object> yjMap = new HashMap<>();

        for (CourtOrgan organ : cbtList) {
            String cbtId = organ.getCid();
            String cbtName = organ.getDeptName();
            xArex.add(cbtName);
            Cbttj cbttj = null;
            List<Cbttj> cbttjList = cbttjService.queryByCbtAndMonth(time, cbtId, fyId);
            for (Cbttj cbtj : cbttjList) {
                cbttj = cbtj;
            }
            if (cbttj != null) {

                jcList.add(cbttj.getJc());
                xsList.add(cbttj.getXs());
                wjList.add(cbttj.getWj());
                yjList.add(cbttj.getYj());
            }
        }
        jcMap.put("name", "旧存");
        jcMap.put("type", "bar");
        jcMap.put("data", jcList);
        yArex.add(jcMap);

        xsMap.put("name", "新收");
        xsMap.put("type", "bar");
        xsMap.put("data", xsList);
        yArex.add(xsMap);

        wjMap.put("name", "未结");
        wjMap.put("type", "bar");
        wjMap.put("data", wjList);
        yArex.add(wjMap);

        yjMap.put("name", "已结");
        yjMap.put("type", "bar");
        yjMap.put("data", yjList);
        yArex.add(yjMap);

        resMap.put("xdata", xArex);
        resMap.put("ydata", yArex);
        return resMap;
    }


    @GetMapping("/queryAjByCbtId")
    @ResponseBody
    public List queryAjByCbtId(String fyId,String date) {

        List<String> jcList = new ArrayList<>();
        List<String> xsList = new ArrayList<>();
        List<String> wjList = new ArrayList<>();
        List<String> yjList = new ArrayList<>();
        List<Object> allList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = sdf.format(m);

        if (StringUtils.isEmpty(date)){
            date=mon;
        }
        List<Map> cbtList = cbttjService.queryCbtIdByFyId(fyId,date);

        Map<String, Object> jcMap = new HashMap<>();
        Map<String, Object> xsMap = new HashMap<>();
        Map<String, Object> wjMap = new HashMap<>();
        Map<String, Object> yjMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        for (Map map : cbtList) {
            String cbtId = map.get("cbt_id").toString();
            String cbtName = map.get("cbt_name").toString();
            nameList.add(cbtName);

            String jc = cbttjService.queryJcByFyAndMonth(cbtId, date);
            if (StringUtils.isEmpty(jc)) {
                jc = "0";
            }
            String xs = cbttjService.queryXsByFyAndMonth(cbtId, date);
            if (StringUtils.isEmpty(xs)) {
                xs = "0";
            }
            String wj = cbttjService.queryWjByFyAndMonth(cbtId, date);
            if (StringUtils.isEmpty(wj)) {
                wj = "0";
            }
            String yj = cbttjService.queryYjByFyAndMonth(cbtId, date);
            if (StringUtils.isEmpty(yj)) {
                yj = "0";
            }
            jcList.add(jc);
            xsList.add(xs);
            wjList.add(wj);
            yjList.add(yj);
        }
        jcMap.put("data", jcList);
        jcMap.put("name", "旧存");
        xsMap.put("data", xsList);
        xsMap.put("name", "新收");
        wjMap.put("data", wjList);
        wjMap.put("name", "未结");
        yjMap.put("data", yjList);
        yjMap.put("name", "已结");
        mapList.add(jcMap);
        mapList.add(xsMap);
        mapList.add(wjMap);
        mapList.add(yjMap);
        allList.add(nameList);
        allList.add(mapList);
        return allList;

    }


}
