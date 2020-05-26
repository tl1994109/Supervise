package com.datcent.project.system.user.mapper;

import com.datcent.project.system.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 用户表 数据层
 * @创建人: zhou_shiji
 * @创建时间: 2018年11月1日上午10:18:18
 */
public interface UserMapper
{

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user);

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
     * @param personId 手机号码
     * @return 用户对象Id
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
     */
    public int deleteUserByIds(Long[] ids);
    
    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据PersonID
     * @return 结果
     */
    public int deleteUserByPersonIds(Long[] ids);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public User checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public User checkEmailUnique(String email);

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


    public String queryParentIdByDeptId(@Param("deptId") String deptId,@Param("delFlag") String delFlag);


    public List<String> queryDeptIdByParentId(@Param("parentId") String parentId,@Param("delFlag") String delFlag);



    public List<Map> selectUserByDeptId(@Param("deptId") long deptId,@Param("delFlag") String delFlag);

    /**
     * 根据roleId查询用户
     * @param roleId
     * @return
     */
    public List<User> selectUserListByRoleId(User user);

}
