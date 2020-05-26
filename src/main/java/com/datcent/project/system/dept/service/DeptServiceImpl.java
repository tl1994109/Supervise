package com.datcent.project.system.dept.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datcent.common.constant.UserConstants;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.datascope.DataScopeUtils;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.mapper.DeptMapper;
import com.datcent.project.system.role.domain.Role;

/**
 * 部门管理 服务实现
 * 
 * @author datcent
 */
@Service
public class DeptServiceImpl implements IDeptService
{
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IUserService userService;


    /**
     * 查询部门管理数据
     * 
     * @return 部门信息集合
     */
    @Override
    public List<Dept> selectDeptList(Dept dept)
    {
        dept.getParams().put("dataScope", DataScopeUtils.dataScopeFilter("d"));
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r:roleList) {
            if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
                isok = true;
                break;
            }
        }
        Dept dpt = selectDeptById(Long.valueOf(selectDeptIdByUserId()));
        if("0".equals(dpt.getRank())){
            isok= true;
        }
        if(isok==false){
            dept.setDeptId(Long.valueOf(selectDeptIdByUserId()));
            dept.setParentId(Long.valueOf(selectParentDeptIdByUserId()));
        }
        return deptMapper.selectDeptList(dept);
    }

    public List<Dept> selectDeptLists(Dept dept){
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     * 
     * @return 所有部门信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Dept> deptList = selectDeptList(new Dept());
        trees = getTrees(deptList, false, null);
        return trees;
    }



    /**
     * 查询法院管理树
     *
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectFyTree()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        Dept dept=new Dept();
        dept.setDeptType("1");
        List<Dept> deptList = selectDeptList(dept);
        trees = getTrees(deptList, false, null);
        return trees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Map<String, Object>> roleDeptTreeData(Role role)
    {
        Long roleId = role.getRoleId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            trees = getTrees(deptList, true, roleDeptList);
        }
        else
        {
            trees = getTrees(deptList, false, null);
        }
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param  部门列表
     * @param isCheck 是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Dept> deptList, boolean isCheck, List<String> roleDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Dept dept : deptList)
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
     * 查询部门人数
     * 
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId)
    {
        Dept dept = new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
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
    public int deleteDeptById(Long deptId)
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
    public int insertDept(Dept dept)
    {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        dept.setCreateBy(ShiroUtils.getLoginName());
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
//        try {
//            File file = new File("c:/a.txt");
//            if(file.isFile() && file.exists()) {
//                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
//                BufferedReader br = new BufferedReader(isr);
//                String lineTxt = null;
//                String str = "";
//                while ((lineTxt = br.readLine()) != null) {
//                    str +=lineTxt;
//                }
//                br.close();
//                JSONArray jsonArray = JSONArray.parseArray(str);
//                for (int i=0;i<jsonArray.size();i++){
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    String cid= jsonObject.getString("cid");
//                    String type = jsonObject.getString("type");
//                    if("corp".equals(type)){
//                        type="1";
//                    }else if("dept".equals(type)){
//                        type="2";
//                    }else if("user".equals(type)){
//                        type="3";
//                    }
//                    Dept dept1 = new Dept();
//                    dept1.setDeptName(jsonObject.getString("text"));
//                    dept1.setDeptType(type);
//                    dept1.setStatus("0");
//                    dept1.setDelFlag("0");
//                    dept1.setAncestors(info.getAncestors() + "," + dept.getParentId());
//                    dept1.setDeptId(Long.valueOf(cid));
//                    dept1.setOrderNum((i+1)+"");
//                    dept1.setCreateBy(ShiroUtils.getUserId().toString());
//                    dept1.setParentId(dept.getParentId());
//                    deptMapper.insertDept(dept1);
//                    JSONArray jsonArray2 = jsonObject.getJSONArray("secondList");
//                    for(int j = 0;j<jsonArray2.size();j++){
//                        JSONObject jsonObject2 = (JSONObject) jsonArray2.get(j);
//                        String cid2= jsonObject2.getString("cid");
//                        String type2 = jsonObject2.getString("type");
//                        if("corp".equals(type2)){
//                            type2="1";
//                        }else if("dept".equals(type2)){
//                            type2="2";
//                        }else if("user".equals(type2)){
//                            type2="3";
//                        }
//                        Dept dept2 = new Dept();
//                        dept2.setDeptName(jsonObject2.getString("text"));
//                        dept2.setDeptType(type2);
//                        dept2.setStatus("0");
//                        dept2.setDelFlag("0");
//                        dept2.setAncestors(dept1.getAncestors()+","+dept1.getDeptId());
//                        dept2.setDeptId(Long.valueOf(cid2));
//                        dept2.setOrderNum((j+1)+"");
//                        dept2.setCreateBy(ShiroUtils.getUserId().toString());
//                        dept2.setParentId(dept1.getDeptId());
//                        deptMapper.insertDept(dept2);
//                    }
//                }
//            } else {
//                System.out.println("文件不存在!");
//            }
//        } catch (Exception e) {
//            System.out.println("文件读取错误!");
//        }
        return deptMapper.insertDept(dept);
//        return 1;
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(Dept dept)
    {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        String ancestors = info.getAncestors() + "," + dept.getParentId();
        dept.setUpdateBy(ShiroUtils.getLoginName());
        dept.setAncestors(ancestors);
        updateDeptChildren(dept.getDeptId(), ancestors);
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId, String ancestors)
    {
        List<Dept> childrens = deptMapper.selectDeptListByParentId(deptId.toString());
        for (Dept children : childrens)
        {
            children.setAncestors(ancestors + "," + deptId);
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
    public Dept selectDeptById(Long deptId)
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
    public String checkDeptNameUnique(Dept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 根据登陆用户查询部门
     * @return
     */
    public String selectDeptIdByUserId() {
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        return person.getDeptId();
    }

    /**
     * 根据登陆用户查询部门
     * @return
     */
    public String selectParentDeptIdByUserId() {
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        Dept det = deptMapper.selectDeptById(Long.valueOf(person.getDeptId()));
        return det.getParentId().toString();
    }

    @Override
    public List<Dept> selectDeptListByParentId(String parentId) {
        return deptMapper.selectDeptListByParentId(parentId);
    }
}
