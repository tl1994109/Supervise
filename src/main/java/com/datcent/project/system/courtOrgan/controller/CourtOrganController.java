package com.datcent.project.system.courtOrgan.controller;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.court.domain.Court;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.role.domain.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 部门信息
 *
 * @author datcent
 */
@Controller
@RequestMapping("/system/courtOrgan")
public class CourtOrganController extends BaseController {
    private String prefix = "system/courtOrgan";

    @Autowired
    private ICourtOrganService courtOrganService;

    @Autowired
    private IDubvioEventService dubvioEventService;

    @RequiresPermissions("system:courtOrgan:view")
    @GetMapping()
    public String dept() {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:courtOrgan:list")
    @GetMapping("/list")
    @ResponseBody
    public List<CourtOrgan> list(CourtOrgan dept) {
        List<CourtOrgan> deptList = courtOrganService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", courtOrganService.selectDeptById(parentId));
        return prefix + "/add";
    }


    /**
     * 新增审批人
     */
    @GetMapping("/approveAdd")
    public String approveAdd( ModelMap mmap) {
        mmap.put("dept", courtOrganService.selectDeptById(100L));
        return prefix + "/approveAdd";
    }




    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:courtOrgan:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourtOrgan dept) {
        return toAjax(courtOrganService.insertDept(dept));
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:courtOrgan:approveAdd")
    @PostMapping("/updateSave")
    @ResponseBody
    public AjaxResult updateSave(CourtOrgan dept) {
        dept.setDeptId(dept.getParentId());
        dept.setParentId(0l);
        dept.setLeaderStatus("1");
        return toAjax(courtOrganService.updateSaveDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        CourtOrgan courtOrgan = courtOrganService.selectDeptById(deptId);
        mmap.put("dept", courtOrgan);
        mmap.put("pdept",courtOrganService.selectDeptById(courtOrgan.getParentId()));
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:courtOrgan:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourtOrgan dept) {
        List<CourtOrgan> courtOrgans = courtOrganService.selectCourtOrganAllByParnetId(dept.getDeptId().toString());
        Boolean isok = true;
        for (CourtOrgan c:courtOrgans) {
            if(Long.valueOf(c.getDeptType())<Long.valueOf(dept.getDeptType())){
                isok = false;
                break;
            }
        }
        if(isok == true){
            return toAjax(courtOrganService.updateDept(dept));
        }else{
            return error("子级存在大于当前组织类型的部门，无法更改组织类型");
        }

    }


    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:courtOrgan:edits")
    @PostMapping("/edits")
    @ResponseBody
    public AjaxResult editSaves(CourtOrgan dept) {
        List<CourtOrgan> courtOrgans = courtOrganService.selectCourtOrganAllByParnetId(dept.getDeptId().toString());
        Boolean isok = true;
        for (CourtOrgan c:courtOrgans) {
            if(Long.valueOf(c.getDeptType())<Long.valueOf(dept.getDeptType())){
                isok = false;
                break;
            }
        }
        if(isok == true){
            return toAjax(courtOrganService.updateDept(dept));
        }else{
            return error("子级存在大于当前组织类型的部门，无法更改组织类型");
        }

    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:courtOrgan:remove")
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId) {
        if (courtOrganService.selectDeptCount(deptId) > 0) {
            return error(1, "存在下级部门,不允许删除");
        }
        if (courtOrganService.checkDeptExistUser(deptId)) {
            return error(1, "部门存在用户,不允许删除");
        }
        return toAjax(courtOrganService.deleteDeptById(deptId));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(CourtOrgan dept) {
        return courtOrganService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", courtOrganService.selectDeptById(deptId));
        return prefix + "/tree";
    }


    /**
     * 选择审批部门树
     */
    @GetMapping("/selectSpDeptTree/{deptId}")
    public String selectSpDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", courtOrganService.selectDeptById(deptId));
        return prefix + "/sptree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData(CourtOrgan courtOrgan) {
        List<Map<String, Object>> tree = courtOrganService.selectDeptTree(courtOrgan);
        return tree;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Map<String, Object>> deptTreeData(Role role) {
        List<Map<String, Object>> tree = courtOrganService.roleDeptTreeData(role);
        return tree;
    }

    /**
     * 同步法院管理信息
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:syn")
    @PostMapping("/syn")
    @ResponseBody
    public AjaxResult syn() {
        CourtOrgan dept = new CourtOrgan();
        List<Court> courtList = null;// iCourtService.selectCourtList(null);
        dept.setParentId(100L);
        List<CourtOrgan> deptList = null;// deptService.selectDeptList(dept);
        try {
            for (int i = 0; i < courtList.size(); i++) {
                for (CourtOrgan dept2 : deptList) {
                    if (dept2.getDeptName().equals(courtList.get(i).getCourtName())) {
                        courtList.remove(i);
                    }
                }
            }
            for (int i = 0; i < courtList.size(); i++) {
                dept = new CourtOrgan();
                dept.setParentId(100L);
                dept.setCreateTime(new Date());
                dept.setAncestors("0,100");
                dept.setDeptName(courtList.get(i).getCourtName());
                dept.setOrderNum("4");
                dept.setLeader(courtList.get(i).getCourtPersonName());
                dept.setStatus("0");
                dept.setDelFlag("0");
                dept.setCreateBy(ShiroUtils.getLoginName());
                courtOrganService.insertDept(dept);
            }
            if (courtList.size() == 0) {
                return error(1, "没有更新信息！");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return error(1, "系统异常，请联系管理员！");
        }
        return toAjax(1);
    }


    @GetMapping("/queyAhAndAjbhByAjbh")
    @ResponseBody
    public List<Map> queyAhAndAjbhByAjbh(String name) {
        List<Map> list = null;
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> maps = new HashMap<>();
        map.put("name", "案号");
        map.put("checked", false);
        map.put("pId", 0);
        map.put("id", 100);
        map.put("titie", "案号");

        if (StringUtils.isNotEmpty(name)) {
            list = dubvioEventService.queyAhAndAjbhByAjbh(name);
            if (!StringUtils.isNull(list)) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> sMap = list.get(i);
                    sMap.put("checked", false);
                    sMap.put("pId", 100);
                    sMap.put("titie", sMap.get("jbxx_ah"));
                    sMap.put("id", sMap.get("jbxx_ajbh"));
                    sMap.put("name", sMap.get("jbxx_ah"));
                    sMap.remove("jbxx_ajbh");
                    sMap.remove("jbxx_ah");
                }
            }
        }
        list.add(map);
        return list;
    }



    /**
     * 根据DeptId 查找组织信息
     */
    @GetMapping("/selectDeptById")
    @ResponseBody
    public Map<String,Object> selectDeptById(Long  deptId) {
        Map<String,Object> mmp=new HashMap<>();
        CourtOrgan court = courtOrganService.selectDeptByDeptId(deptId);
        CourtOrgan parentCourt = courtOrganService.selectDeptByDeptId(court.getParentId());
        CourtOrgan grandCourt = courtOrganService.selectDeptByDeptId(parentCourt.getParentId());
        mmp.put("court",court);
        mmp.put("parentCourt",parentCourt);
        mmp.put("grandCourt",grandCourt);
        return mmp;
    }


    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:courtOrgan:editLeaderStatuss")
    @PostMapping("/editLeaderStatuss")
    @ResponseBody
    public AjaxResult editLeaderStatuss(CourtOrgan dept) {
        AjaxResult json = new AjaxResult();
        CourtOrgan courtOrgan = courtOrganService.selectDeptById(dept.getDeptId());
        if (StringUtils.isEmpty(courtOrgan.getIdcard())){
            json.put("code",500);
            json.put("msg","请完整该领导身份证信息！");
        }else{
            int i = courtOrganService.updateSaveDept(dept);
            if (i>0){
                json = toAjax(i);
            }else{
                json=AjaxResult.error();
            }
        }
        return json;
    }


    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:courtOrgan:removes")
    @PostMapping("/editLeaderStatusss")
    @ResponseBody
    public AjaxResult editLeaderStatusss(CourtOrgan dept) {
        AjaxResult json = new AjaxResult();
        CourtOrgan courtOrgan = courtOrganService.selectDeptById(dept.getDeptId());
        if (StringUtils.isEmpty(courtOrgan.getIdcard())){
            json.put("code",500);
            json.put("msg","请完整该领导身份证信息！");
        }else{
            int i = courtOrganService.updateSaveDept(dept);
            if (i>0){
                json = toAjax(i);
            }else{
                json=AjaxResult.error();
            }
        }
        return json;
    }

    @PostMapping("/selectCourtOrganTypeByDeptId")
    @ResponseBody
    public CourtOrgan selectCourtOrganTypeByDeptId(Long parentId) {
        return courtOrganService.selectDeptById(parentId);
    }


    /**
     * 审批人列表
     * @param mmap
     * @return
     */
    @GetMapping("/approvedBy")
    public String approvedBy(ModelMap mmap) {

        return prefix + "/approvedBy";
    }


    @RequiresPermissions("system:courtOrgan:approvedByList")
    @PostMapping("/approvedByList")
    @ResponseBody
    public TableDataInfo approvedByList(CourtOrgan dept) {
        startPage();

        List<Map> deptList = courtOrganService.selectSprList(dept.getDeptName(),dept.getIdcard());

        return getDataTable(deptList);

    }

    /**
     * 修改
     */

    @GetMapping("/approveEdit")
    public String approveEdit( String  deptId, ModelMap mmap) {
        long id = Long.parseLong(deptId);
        CourtOrgan courtOrgan = courtOrganService.selectDeptById(id);
        mmap.put("dept", courtOrgan);

        return prefix + "/approveEdit";
    }
}
