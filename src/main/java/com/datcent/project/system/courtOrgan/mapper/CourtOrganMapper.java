package com.datcent.project.system.courtOrgan.mapper;

import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 法院组织 数据层
 * 
 * @author datcent
 */
public interface CourtOrganMapper
{
    /**
     * 查询部门人数
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(CourtOrgan dept);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<CourtOrgan> selectDeptList(CourtOrgan dept);

    /**
     * 查询部门管理数据(排序)
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<CourtOrgan> selectDeptListOrder(CourtOrgan dept);


    /**
     * 查询 所有审批人
     * @param dept
     * @return
     */
    public List<Map> selectSprList(@Param("deptName") String deptName, @Param("idcard") String idcard);

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(CourtOrgan dept);

    /**
     * 修改部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(CourtOrgan dept);

    /**
     * 修改子元素关系
     * 
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<CourtOrgan> depts);

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
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public CourtOrgan checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleDeptTree(Long roleId);

    /**
     * 根据cid查询组织
     * @param dept
     * @return
     */
    public CourtOrgan selectCourtOrganByCid(CourtOrgan dept);

    /**
     * 根据数据CID 查询 单位
     * @param cid
     * @return
     */
    public  String queryDeptNameByCid(@Param("cid") String cid,@Param("deptType") String deptType);

    /**
     * 根据cid查询组织结构
     * @param cid
     * @return
     */
    public CourtOrgan selectDeptByCid(String cid);

    /**
     * 根据cid查询所有子节点
     * @param cid
     * @return
     */
    public List<CourtOrgan> selectCourtOrganByParentId(String cid);


    /**
     * 根据主键id查找组织信息
     * @param deptId
     * @return
     */
    public CourtOrgan selectDeptByDeptId(Long deptId);

    /**
     * 根据主键id查找组织信息
     * @param
     * @return
     */
    public List<CourtOrgan> selectAllFy();


    public List<String>  queryCidByParentId(String id);

    public List<CourtOrgan> selectAllListByCid(CourtOrgan dept);

    public CourtOrgan selectLeaderByCardNo(String cardNo);

    public List<CourtOrgan> selectAllLeader();

    public List<CourtOrgan> selectCourtOrganAllByParnetId(String parentId);
}
