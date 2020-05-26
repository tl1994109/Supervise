package com.datcent.project.module.rytj.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.common.utils.StringUtils;
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
import com.datcent.project.module.rytj.domain.Rytj;
import com.datcent.project.module.rytj.service.IRytjService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 人员统计 信息操作处理
 *
 * @author datcent
 * @date 2019-03-27
 */
@Controller
@RequestMapping("/module/rytj")
public class RytjController extends BaseController {
    private String prefix = "module/rytj";

    @Autowired
    private IRytjService rytjService;

    @RequiresPermissions("module:rytj:view")
    @GetMapping()
    public String rytj() {
        return prefix + "/rytj";
    }

    /**
     * 查询人员统计列表
     */
    @RequiresPermissions("module:rytj:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Rytj rytj) {
        startPage();
        List<Rytj> list = rytjService.selectRytjList(rytj);
        return getDataTable(list);
    }

    /**
     * 新增人员统计
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存人员统计
     */
    @RequiresPermissions("module:rytj:add")
    @Log(title = "人员统计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Rytj rytj) {
        return toAjax(rytjService.insertRytj(rytj));
    }

    /**
     * 修改人员统计
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Rytj rytj = rytjService.selectRytjById(id);
        mmap.put("rytj", rytj);
        return prefix + "/edit";
    }

    /**
     * 修改保存人员统计
     */
    @RequiresPermissions("module:rytj:edit")
    @Log(title = "人员统计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Rytj rytj) {
        return toAjax(rytjService.updateRytj(rytj));
    }

    /**
     * 删除人员统计
     */
    @RequiresPermissions("module:rytj:remove")
    @Log(title = "人员统计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(rytjService.deleteRytjByIds(ids));
    }


    @GetMapping("/queryAjByRyId")
    @ResponseBody
    public List queryAjByRyId(String fyId, String cbtId, String date) {


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

        if (StringUtils.isEmpty(date)) {
            date = mon;
        }

        Rytj rytj = new Rytj();
        rytj.setJbfyId(fyId);
        rytj.setCbtId(cbtId);
        rytj.setCountTime(date);


        List<Rytj> ryList = rytjService.selectRytjList(rytj);

        Map<String, Object> jcMap = new HashMap<>();
        Map<String, Object> xsMap = new HashMap<>();
        Map<String, Object> wjMap = new HashMap<>();
        Map<String, Object> yjMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        for (Rytj rytjs : ryList) {


            String ryId = rytjs.getRyId();
            String ryName = rytjs.getRyName();
            nameList.add(ryName);

            String jc = rytjService.queryJcByFyAndMonth(fyId, cbtId, ryId, date);
            if (StringUtils.isEmpty(jc)) {
                jc = "0";
            }
            String xs = rytjService.queryXsByFyAndMonth(fyId, cbtId, ryId, date);
            if (StringUtils.isEmpty(xs)) {
                xs = "0";
            }
            String wj = rytjService.queryWjByFyAndMonth(fyId, cbtId, ryId, date);
            if (StringUtils.isEmpty(wj)) {
                wj = "0";
            }
            String yj = rytjService.queryYjByFyAndMonth(fyId, cbtId, ryId, date);
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
