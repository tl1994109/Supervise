package com.datcent.project.module.fytj.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.velocity.runtime.directive.MacroParseException;
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
import com.datcent.project.module.fytj.domain.Fytj;
import com.datcent.project.module.fytj.service.IFytjService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 法院统计 信息操作处理
 *
 * @author datcent
 * @date 2019-03-22
 */
@Controller
@RequestMapping("/module/fytj")
public class FytjController extends BaseController {
    private String prefix = "module/fytj";

    @Autowired
    private IFytjService fytjService;

    @RequiresPermissions("module:fytj:view")
    @GetMapping()
    public String fytj() {
        return prefix + "/fytj";
    }

    /**
     * 查询法院统计列表
     */
    @RequiresPermissions("module:fytj:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Fytj fytj) {
        startPage();
        List<Fytj> list = fytjService.selectFytjList(fytj);
        return getDataTable(list);
    }

    /**
     * 新增法院统计
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存法院统计
     */
    @RequiresPermissions("module:fytj:add")
    @Log(title = "法院统计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Fytj fytj) {
        return toAjax(fytjService.insertFytj(fytj));
    }

    /**
     * 修改法院统计
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Fytj fytj = fytjService.selectFytjById(id);
        mmap.put("fytj", fytj);
        return prefix + "/edit";
    }

    /**
     * 修改保存法院统计
     */
    @RequiresPermissions("module:fytj:edit")
    @Log(title = "法院统计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Fytj fytj) {
        return toAjax(fytjService.updateFytj(fytj));
    }

    /**
     * 删除法院统计
     */
    @RequiresPermissions("module:fytj:remove")
    @Log(title = "法院统计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fytjService.deleteFytjByIds(ids));
    }


    /**
     * 统计各个法院当月案件旧收 新存 已结 未结 数量
     *
     * @return
     */
    @GetMapping("/queryAjCountByfyId")
    @ResponseBody
    public List queryAjCountByfyId(String date) {

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
        List<Map> fyList = fytjService.queryAllFyAndId(date);
        List<String> jcList = new ArrayList<>();
        List<String> xsList = new ArrayList<>();
        List<String> wjList = new ArrayList<>();
        List<String> yjList = new ArrayList<>();
        Map<String, Object> jcMap = new HashMap<>();
        Map<String, Object> xsMap = new HashMap<>();
        Map<String, Object> wjMap = new HashMap<>();
        Map<String, Object> yjMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        for (Map map : fyList) {
            String fyId = map.get("jbfy_id").toString();
            String name = map.get("jbfy_name").toString();
            nameList.add(name);
            String jc = fytjService.queryJcByFyAndMonth(fyId, date);
            if (StringUtils.isEmpty(jc)) {
                jc = "0";
            }
            String xs = fytjService.queryXsByFyAndMonth(fyId, date);
            if (StringUtils.isEmpty(xs)) {
                xs = "0";
            }
            String wj = fytjService.queryWjByFyAndMonth(fyId, date);
            if (StringUtils.isEmpty(wj)) {
                wj = "0";
            }
            String yj = fytjService.queryYjByFyAndMonth(fyId, date);
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
