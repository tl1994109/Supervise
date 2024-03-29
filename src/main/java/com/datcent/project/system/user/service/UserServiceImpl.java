package com.datcent.project.system.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.BusinessException;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.config.service.IConfigService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.mapper.PersonMapper;
import com.datcent.project.system.post.service.IPostService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.common.constant.UserConstants;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.datascope.DataScopeUtils;
import com.datcent.framework.shiro.service.PasswordService;
import com.datcent.project.system.post.domain.Post;
import com.datcent.project.system.post.mapper.PostMapper;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.mapper.RoleMapper;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserPost;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.mapper.UserMapper;
import com.datcent.project.system.user.mapper.UserPostMapper;
import com.datcent.project.system.user.mapper.UserRoleMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @描述: 用户 业务层处理
 * @创建人: zhou_shiji
 * @创建时间: 2018年11月1日上午10:17:45
 */
@Service
public class UserServiceImpl implements IUserService
{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserService userService;


    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     *
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user, PageDomain pageDomain)
    {
        // 生成数据权限过滤条件
        user.getParams().put("dataScope", DataScopeUtils.dataScopeFilter());
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
            Dept tree_dept = deptService.selectDeptById(user.getDeptId());
            if(user.getDeptId() == null){
                user.setDeptId(Long.valueOf(deptService.selectDeptIdByUserId()));
                user.setParentId(Long.valueOf(parentId));
            }else if(tree_dept.getParentId().longValue() == Long.valueOf(parentId).longValue()){
                user.setParentId(tree_dept.getDeptId());
            }else if(tree_dept.getDeptId().longValue() == Long.valueOf(parentId).longValue()){
                user.setParentId(Long.valueOf(parentId));
            }else{
                user.setParentId(null);
            }
        }
        ShiroUtils.clearCachedPage(pageDomain);
        List<User> userList =userMapper.selectUserList(user);
        return userList;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String ids) throws Exception
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            if (User.isAdmin(userId))
            {
                throw new Exception("不允许删除超级管理员用户");
            }
            List<UserRole> userRoles = userRoleMapper.selectUserRoleByUserId(userId.toString());
            if(userRoles.size()>0){
                Long[] userid_arr = new Long[userRoles.size()];
                for(int i =0;i<userRoles.size();i++){
                    userid_arr[i] = userRoles.get(i).getUserId();
                }
                userRoleMapper.deleteUserRole(userid_arr);
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据PersonID
     * @return 结果
     */
    @Override
    public User selectUserByPersonId(String personid)
    {
        return userMapper.selectUserByPersonId(personid);
    }


    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据PersonID
     * @return 结果
     */
    @Override
    public int deleteUserByPersonIds(String ids) throws Exception
    {
        Long[] personIds = Convert.toLongArray(ids);
        for (Long personId : personIds)
        {
            if (User.isAdmin(personId))
            {
                throw new Exception("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByPersonIds(personIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        Person person=personMapper.selectPersonById(user.getPersonId());
        user.setUserName(person.getPersonName());
        user.setEmail(person.getEmail());
        user.setPhonenumber(person.getMobilePhone());
        user.setSex(person.getSex());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        /*// 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);*/
        return userMapper.updateUser(user);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (User user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                User u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(password);
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds())
        {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0)
        {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        for (Long postId : user.getPostIds())
        {
            UserPost up = new UserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0)
        {
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName)
    {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0)
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        /*List<Post> list = postMapper.selectPostsByUserId(userId);*/
        List<Post> list=postService.selectPostsByPersonId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list)
        {
            if(post.isFlag()){
                idsStr.append(post.getPostName()).append(",");
            }
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * @Author: 张梦圆
     * @Email: 张梦圆@datcent.com
     * @CreateDate: 2019/1/10 11:34
     * @param: depid
     * @return: List<User></>
     * @exception:
     * @Description: TODO 查询部门下所有用户
     **/
    public List<User> selectUserByDepId(Long depId) {
        User user = new User();
        user.setDeptId(depId);
        return userMapper.selectUserList(user);
    }

    @Override
    public List<UserRole> selectUserRoleByUserId(String userId) {
        return userRoleMapper.selectUserRoleByUserId(userId);
    }

    /**
     * 根据用户当前部门 查询院下所有审批人员
     * @param deptId
     * @return
     */
    public List<User> selectUserListByDeptId(String deptId) {
        return userMapper.selectUserListByDeptId(deptId);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUnallocatedList(User user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 根据部门id查出所属法院id
     * @param deptId
     * @return
     */
    public String queryFyidByDeptId(String deptId){
        return userMapper.queryFyidByDeptId(deptId);
    }

    /**
     * 根据法院id 统计该法院下所有工作人员
     * @param cid
     * @return
     */
    public List<Map> queryAllUserByFyid(String cid){
        return userMapper.queryAllUserByFyid(cid);
    }

    @Override
    public List<User> selectByUserNotDept(User user) {
        return userMapper.selectUserList(user);
    }



    public String queryParentIdByDeptId(@Param("deptId") String deptId, @Param("delFlag") String delFlag){
        return userMapper.queryParentIdByDeptId(deptId,delFlag);
    }


    public List<String> queryDeptIdByParentId(@Param("parentId") String parentId,@Param("delFlag") String delFlag){
        return userMapper.queryDeptIdByParentId(parentId,delFlag);
    }



    public List<Map> selectUserByDeptId(long  deptId,String delFlag){
        return userMapper.selectUserByDeptId(deptId,delFlag);
    }


    @Override
    public List<User> selectUserListByRoleId(User user) {
        return userMapper.selectUserListByRoleId(user);
    }
}
