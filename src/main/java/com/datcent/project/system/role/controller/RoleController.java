package com.datcent.project.system.role.controller;

import java.util.List;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.bean.BeanUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import com.github.pagehelper.PageHelper;
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

import com.datcent.common.utils.poi.ExcelUtil;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.service.IRoleService;

/**
 * 角色信息
 *
 * @author datcent
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController
{

    private String prefix = "system/role";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role()
    {
        return prefix + "/role";
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Role role)
    {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Role role)
    {
        List<Role> list = roleService.selectRoleList(role);
        ExcelUtil<Role> util = new ExcelUtil<Role>(Role.class);
        return util.exportExcel(list, "role");
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Role role)
    {
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/edit";
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Role role)
    {
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 新增数据权限
     */
    @GetMapping("/rule/{roleId}")
    public String rule(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/rule";
    }

    /**
     * 修改保存数据权限
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/rule")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult ruleSave(Role role)
    {
        return toAjax(roleService.updateRule(role));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(roleService.deleteRoleByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(Role role)
    {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(Role role)
    {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree()
    {
        return prefix + "/tree";
    }

    /**
     * 分配用户
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/authUser";
    }
    /**
     * 查询已分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(User user)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r:roleList) {
            if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
                isok = true;
                break;
            }
        }
        String parentId=deptService.selectParentDeptIdByUserId();
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if("0".equals(dpt.getRank())){
            isok= true;
        }
        if(isok==false){
            if(user.getDeptId() == null){
                user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
                user.setParentId(Long.valueOf(parentId));
            }else{
                Dept tree_dept = deptService.selectDeptById(user.getDeptId());
                if(!tree_dept.getParentId().toString().equals(parentId)){
                    user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
                    user.setParentId(Long.valueOf(parentId));
                }else{
                    user.setParentId(null);
                }
            }
        }else{
            //当前选中部门
            //Dept tree_dept = deptService.selectDeptById(user.getDeptId());
//            if(user.getDeptId() == null){
//                user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
//                user.setParentId(Long.valueOf(parentId));
//            }else if(tree_dept.getParentId().longValue() == Long.valueOf(parentId).longValue()){
//                user.setParentId(tree_dept.getDeptId());
//            }else if(tree_dept.getDeptId().longValue() == Long.valueOf(parentId).longValue()){
//                user.setParentId(Long.valueOf(parentId));
//            }else{
//                user.setParentId(null);
//            }
        }
        ShiroUtils.clearCachedPage(pageDomain);
        List<User> list = userService.selectUserListByRoleId(user);
        return getDataTable(list);
    }

    /**
     * 批量取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthUserAll(Long roleId, String userIds)
    {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public AjaxResult cancelAuthUser(UserRole userRole)
    {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 查询未分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(User user)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r:roleList) {
            if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
                isok = true;
                break;
            }
        }
        String parentId=deptService.selectParentDeptIdByUserId();
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if("0".equals(dpt.getRank())){
            isok= true;
        }
        if(isok==false){
            if(user.getDeptId() == null){
                user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
                user.setParentId(Long.valueOf(parentId));
            }else{
                Dept tree_dept = deptService.selectDeptById(user.getDeptId());
                if(!tree_dept.getParentId().toString().equals(parentId)){
                    user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
                    user.setParentId(Long.valueOf(parentId));
                }else{
                    user.setParentId(null);
                }
            }
        }else{
            //当前选中部门
            //Dept tree_dept = deptService.selectDeptById(user.getDeptId());
//            if(user.getDeptId() == null){
//                user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
//                user.setParentId(Long.valueOf(parentId));
//            }else if(tree_dept.getParentId().longValue() == Long.valueOf(parentId).longValue()){
//                user.setParentId(tree_dept.getDeptId());
//            }else if(tree_dept.getDeptId().longValue() == Long.valueOf(parentId).longValue()){
//                user.setParentId(Long.valueOf(parentId));
//            }else{
//                user.setParentId(null);
//            }
        }
        ShiroUtils.clearCachedPage(pageDomain);
        List<User> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 批量选择用户授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public AjaxResult selectAuthUserAll(Long roleId, String userIds)
    {
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }
}
