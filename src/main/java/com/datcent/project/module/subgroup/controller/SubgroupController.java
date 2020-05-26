package com.datcent.project.module.subgroup.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.subgroupEvent.domain.SubgroupEvent;
import com.datcent.project.module.subgroupInicondit.domain.SubgroupInicondit;
import com.datcent.project.system.dict.domain.DictType;
import com.datcent.project.system.dict.service.IDictTypeService;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.system.dict.domain.DictType;
import com.datcent.project.system.dict.service.IDictTypeService;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.domain.TableInfo;
import com.datcent.project.tool.gen.mapper.GenMapper;
import com.datcent.project.tool.gen.service.IGenService;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.service.ITableColumnsService;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.subgroup.domain.Subgroup;
import com.datcent.project.module.subgroup.service.ISubgroupService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

import static com.alibaba.fastjson.JSON.*;

/**
 * 组件管理 信息操作处理
 *
 * @author datcent
 * @date 2018-11-29
 */
@Controller
@RequestMapping("/module/subgroup")
public class SubgroupController extends BaseController {
    private String prefix = "module/subgroup";

    @Autowired
    private ISubgroupService subgroupService;

    @Autowired
    private IGenService genService;

    @Autowired
    private IDictTypeService dictTypeService;


    @Autowired
    private ITableColumnsService tableColumnsService;


    @Autowired
    private GenMapper genMapper;

    @RequiresPermissions("module:subgroup:view")
    @GetMapping()
    public String subgroup() {
        return prefix + "/subgroup";
    }


    /**
     * 字段属性
     */
    @RequiresPermissions("module:subgroup:queryCondition")
    @PostMapping("/queryCondition")
    @ResponseBody
    public TableDataInfo queryCondition(String tableName, ModelMap mmap, TableInfo tableInfo, ModelMap modelMap) {
        List<TableInfo> tableInfoList = genService.selectTableList(tableInfo);

        modelMap.put("tableInfoList", tableInfoList);
        List<ColumnInfo> tableColumns = genMapper.selectTableColumnsByName(tableName);
        mmap.put("tableColumns", tableColumns);
        List<DictType> dictList = dictTypeService.selectDictTypeAll();
        mmap.put("dictList", dictList);

        List<Object> list = new ArrayList<>();
        list.add(dictList);
        list.add(tableColumns);
        // TableDataInfo tableDataInfo= getDataTable(dictList);

        return getDataTable(list);
        //return prefix + "/add";
    }

    /**
     * 查询组件管理列表
     */
    @RequiresPermissions("module:subgroup:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Subgroup subgroup) {
        startPage();
        List<Subgroup> list = subgroupService.selectSubgroupList(subgroup);
        return getDataTable(list);
    }

    /**
     * 新增组件管理
     */
    @GetMapping("/add")
    public String add(TableInfo tableInfo, ModelMap modelMap) {
        List<TableInfo> tableInfoList = genService.selectTableList(tableInfo);
        modelMap.put("tableInfoList", tableInfoList);
        return prefix + "/add";
    }

    /**
     * 新增保存组件管理
     */
    @RequiresPermissions("module:subgroup:add")
    @Log(title = "组件管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Subgroup subgroup) {
        Subgroup incoditionSubgroup = new Subgroup();
        incoditionSubgroup.setTableName(subgroup.getTableName());
        incoditionSubgroup.setSubgroupStyle(subgroup.getSubgroupStyle());
        List<Subgroup> subgroupList = subgroupService.selectSubgroupList(incoditionSubgroup);
        if (subgroupList.size() > 0) {
            Subgroup selectSubgroup = subgroupList.get(0);
            if (selectSubgroup != null) {
                subgroupService.deleteSubgroupByIds(selectSubgroup.getSubgroupId());
            }
        }
        subgroup.setSubgroupId(StringUtils.getUUID());
        subgroup.setCreateTime(new Date());
        return toAjax(subgroupService.insertSubgroup(subgroup));
    }

    /**
     * 新增保存组件管理
     */
    @RequiresPermissions("module:subgroup:add")
    @Log(title = "组件管理", businessType = BusinessType.INSERT)
    @PostMapping("/addTableColumn")
    @ResponseBody
    public AjaxResult addTableColumn(String head_json,String contaniner_json) {
        return toAjax(subgroupService.addTableColumnAndSubgroup(head_json,contaniner_json));
    }

    /**
     * 修改组件管理
     */
    @GetMapping("/edit/{subgroupId}")
    public String edit(@PathVariable("subgroupId") String subgroupId, ModelMap mmap, TableInfo tableInfo) {
        Subgroup subgroup = subgroupService.selectSubgroupById(subgroupId);
        List<ColumnInfo> columnInfoList = genMapper.selectTableColumnsByName(subgroup.getTableName());
        TableColumns tableColumns = new TableColumns();
        tableColumns.setTableName(subgroup.getTableName());
        tableColumns.setColumnsType(subgroup.getSubgroupStyle());
        List<TableColumns> tableColumnsList = tableColumnsService.selectTableColumnsList(tableColumns);
        List<TableInfo> tableInfoList = genService.selectTableList(tableInfo);
        if(subgroup.getEventJson()!=null){
            List<SubgroupEvent> subgroupEventList = JSON.parseArray(new String(subgroup.getEventJson()),SubgroupEvent.class);
            for (SubgroupEvent se: subgroupEventList) {
                se.setJsonStr(JSON.toJSONString(se));
            }
            mmap.put("subgroupEventList",subgroupEventList);
        }
        if(subgroup.getIniconditJson()!=null){
            List<SubgroupInicondit> subgroupIniconditList = JSON.parseArray(new String(subgroup.getIniconditJson()),SubgroupInicondit.class);
            for (SubgroupInicondit si: subgroupIniconditList) {
                si.setJsonStr(JSON.toJSONString(si));
            }
            mmap.put("subgroupIniconditList",subgroupIniconditList);
        }
        List<DictType> dictList = dictTypeService.selectDictTypeAll();
        String subgroupStule = subgroup.getSubgroupStyle();
        mmap.put("subgroupStyle",subgroupStule);
        mmap.put("dictList", dictList);
        mmap.put("tableColumnsList",tableColumnsList);
        mmap.put("subgroup", subgroup);
        mmap.put("tableInfoList", tableInfoList);
        mmap.put("columnInfoList",columnInfoList);
        return prefix + "/edit";
    }

    /**
     * 修改保存组件管理
     */
    @RequiresPermissions("module:subgroup:edit")
    @Log(title = "组件管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(String json_str,String json_table) {
        int i = subgroupService.editTableColumnAndSubgroup(json_str,json_table);
        return toAjax(i);
    }

    /**
     * 删除组件管理
     */
    @RequiresPermissions("module:subgroup:remove")
    @Log(title = "组件管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {

        return toAjax(subgroupService.deleteSubgroupByIds(ids));
    }

    /*
     *根据表名查询所有列和字典表，以及值
     *
     * */
    @PostMapping("/selectTableColumns")
    @ResponseBody
    public Map<String, Object> selectTableColumns(TableColumns tableColumns) {
        List<ColumnInfo> columnInfoList = genMapper.selectTableColumnsByName(tableColumns.getTableName());
        List<DictType> dictList = dictTypeService.selectDictTypeAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("columnInfoList", columnInfoList);
        map.put("dictList", dictList);
        return map;
    }

    @PostMapping("/selectEvetJsonAndInciditonJson")
    @ResponseBody
    public Map<String, Object> selectEvetJsonAndInciditonJson(Subgroup subgroup) {
        Subgroup selectSubgroup = subgroupService.selectSubgroupList(subgroup).get(0);
        List<SubgroupEvent> subgroupEventList = JSON.parseArray(selectSubgroup.getEventJson().toString(), SubgroupEvent.class);
        List<SubgroupInicondit> subgroupIniconditList = JSON.parseArray(selectSubgroup.getIniconditJson().toString(), SubgroupInicondit.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subgroupEventList", subgroupEventList);
        map.put("subgroupIniconditList", subgroupIniconditList);
        return map;
    }


}
