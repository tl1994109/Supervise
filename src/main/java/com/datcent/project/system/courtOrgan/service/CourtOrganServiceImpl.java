package com.datcent.project.system.courtOrgan.service;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.constant.UserConstants;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.datascope.DataScopeUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.mapper.CourtOrganMapper;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * 部门管理 服务实现
 *
 * @author datcent
 */
@Service
public class CourtOrganServiceImpl implements ICourtOrganService {
    @Autowired
    private CourtOrganMapper courtOrganMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    /**
     * 查询部门管理数据
     *
     * @return 部门信息集合
     */
    @Override
    public List<CourtOrgan> selectDeptList(CourtOrgan dept) {
        dept.getParams().put("dataScope", DataScopeUtils.dataScopeFilter("d"));
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        //查询用户所在部门
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        //如果所在部门是市级   就可以查看所有
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            CourtOrgan courtOrgan = selectDeptByCid(dpt.getParentId().toString());

            if(!StringUtils.isNull(courtOrgan)){
                dept.setDeptId(courtOrgan.getDeptId());

            }

        }
        return courtOrganMapper.selectDeptList(dept);
    }


    /**
     * 查询部门管理数据(过滤南阳中级人民法院)
     *
     * @return 部门信息集合
     */
    @Override
    public List<CourtOrgan> selectNewDeptList(CourtOrgan dept) {
        dept.getParams().put("dataScope", DataScopeUtils.dataScopeFilter("d"));
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        //查询用户所在部门
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        //如果所在部门是市级   就可以查看所有
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            CourtOrgan courtOrgan = selectDeptByCid(dpt.getParentId().toString());
            if(courtOrgan!=null) {
                dept.setDeptId(courtOrgan.getDeptId());
                dept.setCreateBy("admin");
            }
        }
        return courtOrganMapper.selectDeptList(dept);
    }


    /**
     * 查询部门管理数据(过滤南阳中级人民法院)
     *
     * @return 部门信息集合
     */
    @Override
    public List<CourtOrgan> selectApprovedDeptList(CourtOrgan dept, PageDomain pageDomain) {
        dept.getParams().put("dataScope", DataScopeUtils.dataScopeFilter("d"));
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        //查询用户所在部门
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        //如果所在部门是市级   就可以查看所有
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            CourtOrgan courtOrgan = selectDeptByCid(dpt.getParentId().toString());
            dept.setDeptId(courtOrgan.getDeptId());
            dept.setCreateBy("admin");
        }

        ShiroUtils.clearCachedPage(pageDomain);



        return courtOrganMapper.selectDeptListOrder(dept);
    }



    /**
     * 查询部门管理树
     *
     * @return 所有部门信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree(CourtOrgan courtOrgan) {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<CourtOrgan> deptList = selectDeptList(courtOrgan);
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
    public List<Map<String, Object>> roleDeptTreeData(Role role) {
        Long roleId = role.getRoleId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<CourtOrgan> deptList = selectDeptList(new CourtOrgan());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = courtOrganMapper.selectRoleDeptTree(roleId);
            trees = getTrees(deptList, true, roleDeptList);
        } else {
            trees = getTrees(deptList, false, null);
        }
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param menuList     部门列表
     * @param isCheck      是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<CourtOrgan> deptList, boolean isCheck, List<String> roleDeptList) {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (CourtOrgan dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Map<String, Object> deptMap = new HashMap<String, Object>();
                deptMap.put("id", dept.getDeptId());
                deptMap.put("pId", dept.getParentId());
                deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                deptMap.put("idcard", dept.getIdcard());
                if (isCheck) {
                    deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                } else {
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
    public int selectDeptCount(Long parentId) {
        CourtOrgan dept = new CourtOrgan();
        dept.setParentId(parentId);
        return courtOrganMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = courtOrganMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return courtOrganMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(CourtOrgan dept) {
        CourtOrgan info = courtOrganMapper.selectDeptById(dept.getParentId());
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
//                    CourtOrgan courtOrgan = new CourtOrgan();
//                    courtOrgan.setDeptName(jsonObject.getString("text"));
//                    courtOrgan.setDeptType(type);
//                    courtOrgan.setStatus("0");
//                    courtOrgan.setDelFlag("0");
//                    courtOrgan.setAncestors(info.getAncestors() + "," + dept.getParentId());
//                    courtOrgan.setCid(cid);
//                    courtOrgan.setOrderNum((i+1)+"");
//                    courtOrgan.setCreateBy(ShiroUtils.getUserId().toString());
//                    courtOrgan.setParentId(dept.getParentId());
//                    courtOrganMapper.insertDept(courtOrgan);
//                    CourtOrgan c = selectCourtOrganByCid(courtOrgan);
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
//                        CourtOrgan courtOrgan2 = new CourtOrgan();
//                        courtOrgan2.setDeptName(jsonObject2.getString("text"));
//                        courtOrgan2.setDeptType(type2);
//                        courtOrgan2.setStatus("0");
//                        courtOrgan2.setDelFlag("0");
//                        courtOrgan2.setAncestors(c.getAncestors() + "," +c.getDeptId());
//                        courtOrgan2.setCid(cid2);
//                        courtOrgan2.setOrderNum((j+1)+"");
//                        courtOrgan2.setCreateBy(ShiroUtils.getUserId().toString());
//                        courtOrgan2.setParentId(c.getDeptId());
//                        courtOrganMapper.insertDept(courtOrgan2);
//                        CourtOrgan c2 = selectCourtOrganByCid(courtOrgan2);
//                        JSONArray jsonArray3 = jsonObject2.getJSONArray("thirdList");
//                        for(int k=0;k<jsonArray3.size();k++){
//                            JSONObject jsonObject3 = (JSONObject) jsonArray3.get(k);
//                            CourtOrgan courtOrgan3 = new CourtOrgan();
//                            courtOrgan3.setDeptName(jsonObject3.getString("text"));
//                            String type3 = jsonObject3.getString("type");
//                            if("corp".equals(type3)){
//                                type3="1";
//                            }else if("dept".equals(type3)){
//                                type3="2";
//                            }else if("user".equals(type3)){
//                                type3="3";
//                            }
//                            courtOrgan3.setDeptType(type3);
//                            courtOrgan3.setStatus("0");
//                            courtOrgan3.setDelFlag("0");
//                            courtOrgan3.setAncestors(c2.getAncestors() + "," +c2.getDeptId());
//                            courtOrgan3.setCid(jsonObject3.getString("cid"));
//                            courtOrgan3.setOrderNum((k+1)+"");
//                            courtOrgan3.setCreateBy(ShiroUtils.getUserId().toString());
//                            courtOrgan3.setParentId(c2.getDeptId());
//                            courtOrganMapper.insertDept(courtOrgan3);
//                        }
//                    }
//                }
//            } else {
//                System.out.println("文件不存在!");
//            }
//        } catch (Exception e) {
//            System.out.println("文件读取错误!");
//        }
        return courtOrganMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(CourtOrgan dept) {
        CourtOrgan info = courtOrganMapper.selectDeptById(dept.getParentId());
        String ancestors = info.getAncestors() + "," + dept.getParentId();
        dept.setUpdateBy(ShiroUtils.getLoginName());
        dept.setAncestors(ancestors);
        updateDeptChildren(dept.getDeptId(), ancestors);
        return courtOrganMapper.updateDept(dept);
    }


    public int updateSaveDept(CourtOrgan dept) {
//        CourtOrgan info = courtOrganMapper.selectDeptById(dept.getParentId());
//        String ancestors = info.getAncestors() + "," + dept.getParentId();
//        dept.setUpdateBy(ShiroUtils.getLoginName());
//        dept.setAncestors(ancestors);
//        updateDeptChildren(dept.getDeptId(), ancestors);
        return courtOrganMapper.updateDept(dept);
    }
    /**
     * 修改子元素关系
     *
     * @param deptId    部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId, String ancestors) {
        CourtOrgan dept = new CourtOrgan();
        dept.setParentId(deptId);
        List<CourtOrgan> childrens = courtOrganMapper.selectDeptList(dept);
        for (CourtOrgan children : childrens) {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0) {
            courtOrganMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public CourtOrgan selectDeptById(Long deptId) {
        return courtOrganMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(CourtOrgan dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        CourtOrgan info = courtOrganMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 根据cid查询组织
     *
     * @param dept
     * @return
     */
    public CourtOrgan selectCourtOrganByCid(CourtOrgan dept) {
        return courtOrganMapper.selectCourtOrganByCid(dept);
    }

    /**
     * 根据数据CID 查询 单位
     *
     * @param cid
     * @return
     */
    @Override
    public String queryDeptNameByCid(String cid, String deptType) {

        return courtOrganMapper.queryDeptNameByCid(cid, deptType);
    }

    @Override
    public CourtOrgan selectDeptByCid(String cid) {
        return courtOrganMapper.selectDeptByCid(cid);
    }


    /**
     * 根据主键id查找组织信息
     *
     * @param deptId
     * @return
     */
    @Override
    public CourtOrgan selectDeptByDeptId(Long deptId) {
        return courtOrganMapper.selectDeptByDeptId(deptId);
    }

    @Override
    public List<CourtOrgan> selectCourtOrganByParentId(String cid) {
        return courtOrganMapper.selectCourtOrganByParentId(cid);
    }

    @Override
    public List<CourtOrgan> selectAllFy() {
        return courtOrganMapper.selectAllFy();
    }

    public List<String> queryCidByParentId(String id) {

        return courtOrganMapper.queryCidByParentId(id);
    }


    public List<CourtOrgan> selectAllListByCid(CourtOrgan dept) {

        return courtOrganMapper.selectAllListByCid(dept);

    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateLeaderStatus(CourtOrgan dept) {
        dept.setUpdateBy(ShiroUtils.getLoginName());
        dept.setUpdateTime(new Date());
        return courtOrganMapper.updateDept(dept);
    }

    @Override
    public CourtOrgan selectLeaderByCardNo(String cardNo) {
        return courtOrganMapper.selectLeaderByCardNo(cardNo);
    }

    /**
     * 查询部门所有领导
     *
     * @return 结果
     */
    @Override
    public List<CourtOrgan> selectAllLeader() {
        return courtOrganMapper.selectAllLeader();
    }

    @Override
    public List<CourtOrgan> selectCourtOrganAllByParnetId(String parentId) {

        return courtOrganMapper.selectCourtOrganAllByParnetId(parentId);
    }

    public List<Map> selectSprList(String deptName,  String idcard){

        return courtOrganMapper.selectSprList(deptName,idcard);
    }
}
