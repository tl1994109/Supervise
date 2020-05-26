package com.datcent.project.system.dept.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.court.domain.Court;
import com.datcent.project.module.court.service.ICourtService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.role.domain.Role;

/**
 * 部门信息
 * 
 * @author datcent
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController
{
    private String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;
    
    @Autowired
    private ICourtService iCourtService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @GetMapping("/list")
    @ResponseBody
    public List<Dept> list(Dept dept)
    {
        List<Dept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        Dept  dept = deptService.selectDeptById(parentId);
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Dept dept)
    {
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        Dept dept = deptService.selectDeptById(deptId);
        Dept p_dept = deptService.selectDeptById(dept.getParentId());
        mmap.put("dept", dept);
        mmap.put("pdept",p_dept.getRank());
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Dept dept)
    {
        List<Dept> deptList = deptService.selectDeptListByParentId(dept.getDeptId().toString());
        Boolean isok = true;
        for (Dept d:deptList) {
            if(Long.valueOf(d.getRank())<Long.valueOf(dept.getRank())){
                isok = false;
                break;
            }
        }
        if(isok == true){
            return toAjax(deptService.updateDept(dept));
        }else {
            return error("子集存在市级/级别的部门，无法更改部门级别");
        }
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return error(1, "存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return error(1, "部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }
    
    /**
     * 同步法院管理信息
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:syn")
    @PostMapping("/syn")
    @ResponseBody
    public AjaxResult syn()
    {
    	Dept dept=null;
    	List<Court> courtList = iCourtService.selectCourtList(null);
    	dept=new Dept();
    	dept.setParentId(100L);
    	List<Dept> deptList = deptService.selectDeptList(dept);
    	try {
    		for (int i=0; i<courtList.size();i++) {
				for (Dept dept2 : deptList) {
					if(dept2.getDeptName().equals(courtList.get(i).getCourtName())){
						courtList.remove(i);
					}
				}
			}
    		for (int i=0; i<courtList.size();i++) {
	    		dept=new Dept();
				dept.setParentId(100L);
				dept.setCreateTime(new Date());
				dept.setAncestors("0,100");
				dept.setDeptName(courtList.get(i).getCourtName());
				dept.setOrderNum("4");
				dept.setLeader(courtList.get(i).getCourtPersonName());
				dept.setStatus("0");
				dept.setDelFlag("0");
				dept.setCreateBy(ShiroUtils.getLoginName());
				deptService.insertDept(dept);
    		}
    		if(courtList.size()==0){
    			return error(1, "没有更新信息！");
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return error(1, "系统异常，请联系管理员！");
		}
        return toAjax(1);
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Dept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        if(deptId ==0){
            mmap.put("dept",new Dept());
        }else{
            mmap.put("dept", deptService.selectDeptById(deptId));
        }
        return prefix + "/tree";
    }


    /**
     * 选择法院树
     */
    @GetMapping("/selectFyTree/{deptId}")
    public String selectFyTree(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/fyTree";
    }


    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData()
    {
        List<Map<String, Object>> tree = deptService.selectDeptTree();
        return tree;
    }

    /**
     * 加载法院列表树
     */
    @GetMapping("/fyTreeData")
    @ResponseBody
    public List<Map<String, Object>> fyTreeData()
    {
        List<Map<String, Object>> tree = deptService.selectFyTree();
        return tree;
    }


    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Map<String, Object>> deptTreeData(Role role)
    {
        List<Map<String, Object>> tree = deptService.roleDeptTreeData(role);
        return tree;
    }




    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/selectDeptListByParentId")
    @ResponseBody
    public List<Dept> deptTreeData( String parentId)
    {
        List<Dept> deptList = deptService.selectDeptListByParentId(parentId);
        return deptList;
    }

    @PostMapping("/selectDeptInfoBydeptId")
    @ResponseBody
    public Dept selectDeptInfoBydeptId(String deptId){
        return deptService.selectDeptById(Long.valueOf(deptId));
    }
}
