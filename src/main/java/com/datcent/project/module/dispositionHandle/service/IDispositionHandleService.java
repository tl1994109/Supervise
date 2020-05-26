package com.datcent.project.module.dispositionHandle.service;

import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.system.role.domain.Role;

import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 * 
 * @author datcent
 */
public interface IDispositionHandleService
{
    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<DispositionHandle> selectDeptList(DispositionHandle dispositionHandle);

    /**
     * 查询部门管理树
     * 
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectDeptTree();

    /**
     * 查询部门人数
     * 
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(String parentId);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(String deptId);

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDispositionHandle(DispositionHandle dispositionHandle);

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDispositionHandle(DispositionHandle dispositionHandle);

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    public DispositionHandle selectDeptById(String deptId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(DispositionHandle dispositionHandle);
}
