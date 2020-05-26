package com.datcent.project.system.user.controller;

import java.util.ArrayList;
import java.util.List;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.service.DictService;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.service.IRoleService;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.config.RuoYiConfig;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.project.system.menu.domain.Menu;
import com.datcent.project.system.menu.service.IMenuService;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.notice.service.INoticeService;
import com.datcent.project.system.user.domain.User;

/**
 * 首页 业务处理
 * 
 * @author datcent
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;
    
    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private DictService dict;

    @Autowired
    private IRoleService roleService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getUser();
        user.setSex(dict.getLabel("sys_user_sex", user.getSex()));
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        //个人信息
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(Long.parseLong(user.getPersonId())));
        return "index";
    }
    
    // 信息数量
    @PostMapping("/system/newsCount")
    @ResponseBody
    public TableDataInfo newsCount(ModelMap mmap)
    {
        List<ModelMap> returnMmap=new ArrayList<ModelMap>();
    	Notice notice=new Notice();
        notice.setCreateBy(ShiroUtils.getUserId().toString());
        //通知
        notice.setNoticeType("1");
        List<Notice> infoNotice = noticeService.selectNoReadNotice(notice);
        //公告
        notice.setNoticeType("2");
        List<Notice> noticelist = noticeService.selectNoReadNotice(notice);
    	mmap.put("noticeTzCount", infoNotice.size());
        mmap.put("noticeGgCount", noticelist.size());
        returnMmap.add(mmap);
        return getDataTable(returnMmap);
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        List<UserRole> roleList = userService.selectUserRoleByUserId(ShiroUtils.getUserId().toString());
        String isok = "true";
//        for (UserRole role:roleList) {
//            if(ActivitiDefinftion.ROLE_KEY.equals(role.getRoleKey())|| ActivitiDefinftion.ROLE_ADMIN_KEY.equals(role.getRoleKey())){
//                isok = "true";
//                break;
//            }
//        }
        mmap.put("roleKey",isok);
        mmap.put("version", ruoYiConfig.getVersion());
        mmap.put("notice", noticeService.selectNoticeMain(null));
        return "main";
    }

}
