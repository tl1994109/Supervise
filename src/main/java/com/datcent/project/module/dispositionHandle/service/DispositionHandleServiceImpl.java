package com.datcent.project.module.dispositionHandle.service;

import com.datcent.common.constant.UserConstants;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.datascope.DataScopeUtils;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.mapper.DispositionHandleMapper;
import com.datcent.project.system.role.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 线索处置记录过程 服务实现
 * 
 * @author datcent
 */
@Service
public class DispositionHandleServiceImpl implements IDispositionHandleService
{
    @Autowired
    private DispositionHandleMapper deptMapper;

    /**
     * 查询线索处置记录过程数据
     * 
     * @return 部门信息集合
     */
    @Override
    public List<DispositionHandle> selectDeptList(DispositionHandle dept)
    {
        dept.getParams().put("dataScope", DataScopeUtils.dataScopeFilter("d"));
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询线索处置记录过程树
     * 
     * @return 所有线索处置记录过程信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<DispositionHandle> deptList = selectDeptList(new DispositionHandle());
        trees = getTrees(deptList, false, null);
        return trees;
    }



    /**
     * 对象转线索处置记录过程树
     *
     * @param menuList 线索处置记录过程列表
     * @param isCheck 是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<DispositionHandle> deptList, boolean isCheck, List<String> roleDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (DispositionHandle dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Map<String, Object> deptMap = new HashMap<String, Object>();
                deptMap.put("id", dept.getDeptId());
                deptMap.put("pId", dept.getParentId());
                deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                if (isCheck)
                {
                    deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                else
                {
                    deptMap.put("checked", false);
                }
                trees.add(deptMap);
            }
        }
        return trees;
    }

    /**
     * 查询线索处置记录过程数
     * 
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(String parentId)
    {
        DispositionHandle dept = new DispositionHandle();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询线索处置记录过程是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(String deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDispositionHandle(DispositionHandle dept)
    {
        DispositionHandle info = deptMapper.selectDeptById(dept.getParentId());
        if(info==null){
            dept.setAncestors(dept.getParentId());
        }else{
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        }
        return deptMapper.insertDispositionHandle(dept);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDispositionHandle(DispositionHandle dept)
    {
//        DispositionHandle info = deptMapper.selectDeptById(dept.getParentId());
//        String ancestors = info.getAncestors() + "," + dept.getParentId();
//        dept.setUpdateBy(ShiroUtils.getLoginName());
//        dept.setAncestors(ancestors);
//        updateDeptChildren(dept.getDeptId(), ancestors);
        return deptMapper.updateDispositionHandle(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(String deptId, String ancestors)
    {
        DispositionHandle dept = new DispositionHandle();
        dept.setParentId(deptId);
        List<DispositionHandle> childrens = deptMapper.selectDeptList(dept);
        for (DispositionHandle children : childrens)
        {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0)
        {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public DispositionHandle selectDeptById(String deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(DispositionHandle dept)
    {
        String deptId = StringUtils.isNull(dept.getDeptId()) ? "-1" : dept.getDeptId();
        DispositionHandle info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().equals(deptId))
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }
}
