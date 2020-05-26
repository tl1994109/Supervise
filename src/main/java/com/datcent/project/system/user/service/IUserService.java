package com.datcent.project.system.user.service;

import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 用户 业务层
 * @创建人: zhou_shiji
 * @创建时间: 2018年11月1日上午10:17:23
 */
public interface IUserService
{

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user, PageDomain pageDomain);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    public User selectUserByPhoneNumber(String phoneNumber);
    
    /**
     * 通过personId查询用户
     * 
     * @param personId 
     * @return 用户对象UserId
     */
    public User selectUserByPersonId(String personid);

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    public User selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByIds(String ids) throws Exception;
    
    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据PersonID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByPersonIds(String ids) throws Exception;

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 修改用户详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(User user);

    /**
     * 修改用户密码信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(User user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(User user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(User user);

    /**
     * 根据用户ID查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(Long userId);

    /**
     * 根据用户ID查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserPostGroup(Long userId);

    /**
     * @return: List<User></>
     * @exception:
     * @Description: TODO 查询部门下所有用户
     **/
    public List<User> selectUserByDepId(Long depId);

    /**
     * 用户id   查询权限
     * @param userId
     * @return
     */
    public List<UserRole> selectUserRoleByUserId(String userId);

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importUser(List<User> userList, Boolean isUpdateSupport);

    /**
     * 根据用户当前部门 查询院下所有审批人员
     * @param deptId
     * @return
     */
    public List<User> selectUserListByDeptId(String deptId);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUnallocatedList(User user);

    /**
     * 根据部门id查出所属法院id
     * @param deptId
     * @return
     */
    public String queryFyidByDeptId(String deptId);

    /**
     * 根据法院id 统计该法院下所有工作人员
     * @param cid
     * @return
     */
    public List<Map> queryAllUserByFyid(String cid);

    public List<User> selectByUserNotDept(User user);



    public String queryParentIdByDeptId(@Param("deptId") String deptId, @Param("delFlag") String delFlag);


    public List<String> queryDeptIdByParentId(@Param("parentId") String parentId,@Param("delFlag") String delFlag);



    public List<Map> selectUserByDeptId(long deptId,@Param("delFlag") String delFlag);

    /**
     * 根据roleId查询用户
     * @param roleId
     * @return
     */
    public List<User> selectUserListByRoleId(User user);

}
