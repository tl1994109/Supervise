package com.datcent.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datcent.project.system.user.service.IUserService;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.system.user.domain.User;

/**
 * web层通用数据处理
 * 
 * @author datcent
 */
public class BaseController
{

    @Autowired
    private IUserService userService;
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
      //      PageHelper.startPage(pageNum, pageSize);
        }
    }

    public List<Object> getlocalCourtPerson() {
        long deptId = ShiroUtils.getUser().getDeptId();
        String deptIds = deptId + "";
        if (StringUtils.isNotEmpty(deptIds)) {
            //根据部门id  去查询所属法院id
            String parentId = userService.queryParentIdByDeptId(deptIds, "0");
            List<Object> allUserList = new ArrayList<>();
//            List<Object> secondAllUserList = new ArrayList<>();

                //查询所属法院下的所有部门id
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                //将集合 转成 String 分割成的字符串
                String str = String.join(",", deptIdList);
                //将字符串转成 String  数组
                String[] str1 = str.split(",");
                // String  数组转成 long  数组
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    //
                    List<Map> userList = userService.selectUserByDeptId(str3,"0");
                    if (!StringUtils.isNull(userList)) {
                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }
                return allUserList;


//            else {
//                List<String> idList = new ArrayList();
//                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
//                for (int i = 0; i < deptIdList.size(); i++) {
//                    String fyId = deptIdList.get(i);
//                    if (fyId.length() > 4) {
//                        idList.add(fyId);
//                    }
//                    if (fyId.length() == 4) ;
//                    List<String> deptIdLists = userService.queryDeptIdByParentId(fyId, "0");
//                    for (int j = 0; j < deptIdLists.size(); j++) {
//                        String secondId = deptIdLists.get(j);
//                        idList.add(secondId);
//                    }
//                }
//                String str = String.join(",", idList);
//                String[] str1 = str.split(",");
//                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
//                for (int i = 0; i < strArrNum.length; i++) {
//                    long str3 = Long.valueOf(str1[i]);
//                    List<Map> userList = userService.selectUserByDeptId(str3,"0");
//                    if (!StringUtils.isNull(userList)) {
//
//                        for (int j = 0; j < userList.size(); j++) {
//                            Map map = userList.get(j);
//                            secondAllUserList.add(map);
//                        }
//                    }
//                }
//                return allUserList;
//            }
        }
        return null;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message)
    {
        return AjaxResult.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    public User getUser()
    {
        return ShiroUtils.getUser();
    }

    public void setUser(User user)
    {
        ShiroUtils.setUser(user);
    }

    public Long getUserId()
    {
        return getUser().getUserId();
    }

    public String getLoginName()
    {
        return getUser().getLoginName();
    }
}
