package com.datcent.project.system.courtOrgan.service;

import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.role.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 * 
 * @author datcent
 */
public interface ICourtOrganService
{
    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<CourtOrgan> selectDeptList(CourtOrgan dept);



    public List<CourtOrgan> selectNewDeptList(CourtOrgan dept);

    public List<CourtOrgan> selectApprovedDeptList(CourtOrgan dept, PageDomain pageDomain);

    /**
     * 查询部门管理树
     * 
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectDeptTree(CourtOrgan courtOrgan);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Map<String, Object>> roleDeptTreeData(Role role);

    /**
     * 查询部门人数
     * 
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(CourtOrgan dept);

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(CourtOrgan dept);


    public int updateSaveDept(CourtOrgan dept);

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    public CourtOrgan selectDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(CourtOrgan dept);


    public CourtOrgan selectCourtOrganByCid(CourtOrgan dept);


    /**
     * 根据数据CID 查询 单位
     * @param cid
     * @return
     */
    public  String queryDeptNameByCid(String cid,String deptType);

    /**
     * 根据cid查询组织结构
     * @param cid
     * @return
     */
    public CourtOrgan selectDeptByCid(String cid);


    /**
     * 根据主键id查找组织信息
     * @param deptId
     * @return
     */
    public CourtOrgan selectDeptByDeptId(Long deptId);



    public List<CourtOrgan> selectCourtOrganByParentId(String cid);

    /**
     * 查询所有法院
     * @return
     */
    public List<CourtOrgan> selectAllFy();


    public List<String>  queryCidByParentId(String id);

    public List<CourtOrgan> selectAllListByCid(CourtOrgan dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateLeaderStatus(CourtOrgan dept);

    public CourtOrgan selectLeaderByCardNo(String cardNo);

    /**
     * 查询部门所有领导人
     * @return
     */
    public List<CourtOrgan> selectAllLeader();

    public List<CourtOrgan> selectCourtOrganAllByParnetId(String parentId);


    public List<Map> selectSprList(String deptName, String idcard);
}
