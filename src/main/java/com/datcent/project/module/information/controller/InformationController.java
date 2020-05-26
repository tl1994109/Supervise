package com.datcent.project.module.information.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.DoubleUtils;
import com.datcent.common.utils.MapDataUtil;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.accessory.domain.Accessory;
import com.datcent.project.module.accessory.service.IAccessoryService;
import com.datcent.project.module.appraise.domain.Appraise;
import com.datcent.project.module.appraise.service.IAppraiseService;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.service.IJournalService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.dict.domain.DictData;
import com.datcent.project.system.dict.service.IDictDataService;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.service.IRoleService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import com.mchange.util.StringObjectMap;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.information.domain.Information;
import com.datcent.project.module.information.service.IInformationService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务 信息操作处理
 *
 * @author datcent
 * @date 2019-01-20
 */
@Controller
@RequestMapping("/module/information")
public class InformationController extends BaseController {
    private String prefix = "module/information";

    @Autowired
    private IInformationService informationService;

    @Autowired
    private IAccessoryService accessoryService;

    @Autowired
    private IAppraiseService appraiseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IJournalService journalService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IDictDataService dictDataService;

    @Autowired
    private ICourtOrganService courtOrganService;

    @Value("${datcent.profile}")
    private String profile;

    @RequiresPermissions("module:information:view")
    @GetMapping()
    public String information(String isTXStatus,ModelMap modelMap)
    {
        modelMap.put("isTXStatus",isTXStatus);
        return prefix + "/information";
    }

    /**
     * 查询任务列表
     */
    @RequiresPermissions("module:information:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Information information) throws ParseException {
        Long userid = getUser().getUserId();
        List<Role> roleList = roleService.selectUserRoleByUserId(userid);
        List<Information> list = new ArrayList<Information>();
        Boolean iscommon = false;
        for (Role role : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(role.getRoleKey())) {
                iscommon = true;
            }
        }
        if (iscommon == false) {
            information.setTaskProcessor(userid.toString());
            information.setTaskCreateby(userid.toString());
            information.setSubmitter(userid.toString());
        }

        startPage();
        list = informationService.selectInformationList(information);

        for (int i = 0; i < list.size(); i++) {

            String processor = list.get(i).getTaskProcessor();


        }

        return getDataTable(list);
    }
    /**
     * 查询任务列表
     */
    @RequiresPermissions("module:information:list")
    @PostMapping("/isTXStatusList")
    @ResponseBody
    public TableDataInfo isTXStatusList(Information information){
        Long userId = ShiroUtils.getUserId();
        information.setTaskProcessor(userId.toString());
        startPage();
        List<Information> informationList  = informationService.selectRemindList(information);
        return getDataTable(informationList);
    }
    /**
     * 新增任务
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) throws Exception {
        List<Object> objects = getlocalCourtPerson();
        modelMap.put("userList", objects);
        List<Object> objectList = new ArrayList<Object>();
        for (Object obj : objects) {
            Map mapList = (Map) obj;
            String userId = mapList.get("user_id").toString();
            List<UserRole> roleList = userService.selectUserRoleByUserId(userId);
            for (UserRole r : roleList) {
                if (ActivitiDefinftion.ROLE_TASKASSIGNMENT.equals(r.getRoleKey())) {
                    objectList.add(obj);
                    break;
                }
            }
        }
        modelMap.put("userRoleList", objectList);
        return prefix + "/add";
    }

    /**
     * 新增保存任务
     */
    @RequiresPermissions("module:information:add")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Information information, String acId, MultipartFile[] file) throws ParseException, IOException, FileNameLengthLimitExceededException, FileUploadBase.FileSizeLimitExceededException {
        return toAjax(informationService.insertInformation(information, acId, file, profile));
    }

    /**
     * 修改任务
     */
    @GetMapping("/edit/{taskId}")
    public String edit(@PathVariable("taskId") String taskId, ModelMap mmap) {
        Information information = informationService.selectInformationById(taskId);
        String[] cid_arr = information.getTaskProcessor().split(",");
        long deptId = ShiroUtils.getUser().getDeptId();
        String deptIds = deptId + "";
        List<Map> allUserList = new ArrayList<>();

        if (StringUtils.isNotEmpty(deptIds)) {
            String parentId = userService.queryParentIdByDeptId(deptIds, "0");
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                String str = String.join(",", deptIdList);
                String[] str1 = str.split(",");
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    List<Map> userList = userService.selectUserByDeptId(str3, "0");
                    if (!StringUtils.isNull(userList)) {
                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }

        }
        for (String arr : cid_arr) {
            for (Map m : allUserList) {
                if (arr.equals(m.get("user_id").toString())) {
                    m.put("del_flag", 11);
                }
            }
        }
        Accessory accessory = new Accessory();
        accessory.setAccessoryTaskid(taskId);
        accessory.setAccessoryType("0");
        List<Accessory> accessoryList = accessoryService.selectAccessoryList(accessory);
        accessory.setAccessoryType("1");
        List<Accessory> endTaskAccessories = accessoryService.selectAccessoryList(accessory);
        List<Map> objectList = new ArrayList<Map>();
        for (Map m : allUserList) {
            String userId = m.get("user_id").toString();
            List<UserRole> roleList = userService.selectUserRoleByUserId(userId);
            for (UserRole r : roleList) {
                if (ActivitiDefinftion.ROLE_TASKASSIGNMENT.equals(r.getRoleKey())) {
                    m.put("select_flag", "false");
                    objectList.add(m);
                    break;
                }
            }
        }

        String submitter = information.getSubmitter();
        if (StringUtils.isNotEmpty(submitter)) {
            String[] submitter_arr = submitter.split(",");
            for (Map mp : objectList) {
                for (String str : submitter_arr) {
                    if (str.equals(mp.get("user_id").toString())) {
                        mp.put("select_flag", "true");
                        break;
                    }
                }
            }
        }

        mmap.put("userRoleList", objectList);
        mmap.put("accessoryList", accessoryList);
        mmap.put("endTaskAccessories", endTaskAccessories);
        mmap.put("information", information);
        mmap.put("userList", allUserList);
        mmap.put("size", accessoryList.size());
        mmap.put("endSize", endTaskAccessories.size());
        return prefix + "/edit";
    }

    /**
     * 修改保存任务
     */
    @RequiresPermissions("module:information:edit")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Information information, MultipartFile[] taskFile, MultipartFile[] overFile) throws Exception {
        if(information.getSubmitter() == null){
            information.setSubmitter("");
        }
        if(information.getTaskProcessor() == null){
            information.setTaskProcessor("");
        }
        return toAjax(informationService.updateInformation(information, taskFile, overFile, profile));
    }

    /**
     * 删除任务
     */
    @RequiresPermissions("module:information:remove")
    @Log(title = "任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        String[] str_array = Convert.toStrArray(ids);
        List<Information> informationList = new ArrayList<Information>();
        for (String str : str_array) {
            informationList.add(informationService.selectInformationById(str));
        }
        return toAjax(informationService.deleteInformationByIds(informationList));
    }

    //    @RequiresPermissions("module:information:detail")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
        Information information = informationService.selectInformationById(id);
        User user = userService.selectUserById(Long.parseLong(information.getTaskCreateby()));
        //List<User> userList = userService.selectUserByDepId(user.getDeptId());
        String[] cid_arr = information.getTaskProcessor().split(",");
        long deptId = ShiroUtils.getUser().getDeptId();
        String deptIds = deptId + "";
        List<Map> allUserList = new ArrayList<>();

        if (StringUtils.isNotEmpty(deptIds)) {
            String parentId = userService.queryParentIdByDeptId(deptIds, "0");
            if (parentId.length() == 4) {
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                String str = String.join(",", deptIdList);
                String[] str1 = str.split(",");
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    List<Map> userList = userService.selectUserByDeptId(str3, "0");
                    if (!StringUtils.isNull(userList)) {
                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }

            } else {
                List<String> idList = new ArrayList();
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                for (int i = 0; i < deptIdList.size(); i++) {
                    String fyId = deptIdList.get(i);
                    if (fyId.length() > 4) {
                        idList.add(fyId);
                    }
                    if (fyId.length() == 4) ;
                    List<String> deptIdLists = userService.queryDeptIdByParentId(fyId, "0");
                    for (int j = 0; j < deptIdLists.size(); j++) {
                        String secondId = deptIdLists.get(j);
                        idList.add(secondId);
                    }
                }
                String str = String.join(",", idList);
                String[] str1 = str.split(",");
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    List<Map> userList = userService.selectUserByDeptId(str3, "0");
                    if (!StringUtils.isNull(userList)) {

                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }

            }
        }
        for (String arr : cid_arr) {
            for (Map m : allUserList) {
                if (arr.equals(m.get("user_id").toString())) {

                    m.put("del_flag", 11);

                }
            }
        }
        modelMap.put("information", information);
        Accessory accessory = new Accessory();
        accessory.setAccessoryTaskid(id);
        accessory.setAccessoryType("0");
        List<Accessory> accessoryList = accessoryService.selectAccessoryList(accessory);
        accessory.setAccessoryType("1");
        List<Accessory> accessoryList1 = accessoryService.selectAccessoryList(accessory);
        Appraise appraise = appraiseService.selectAppraiseByTaskId(id);
        modelMap.put("accessoryList", accessoryList);
        modelMap.put("accessoryList1", accessoryList1);
        modelMap.put("appraise", appraise);
        modelMap.put("userList", allUserList);
        modelMap.put("size", accessoryList.size());
        modelMap.put("size1", accessoryList1.size());
        return prefix + "/detail";
    }

    @RequiresPermissions("module:information:appraise")
    @GetMapping("/appraise")
    public String appraise(String taskId, ModelMap mmap) {
        Information information = informationService.selectInformationById(taskId);
        String[] cid_arr = information.getTaskProcessor().split(",");
        long deptId = ShiroUtils.getUser().getDeptId();
        String deptIds = deptId + "";
        List<Map> allUserList = new ArrayList<>();
        if (StringUtils.isNotEmpty(deptIds)) {
            String parentId = userService.queryParentIdByDeptId(deptIds, "0");
            if (parentId.length() == 4) {
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                String str = String.join(",", deptIdList);
                String[] str1 = str.split(",");
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    List<Map> userList = userService.selectUserByDeptId(str3, "0");
                    if (!StringUtils.isNull(userList)) {
                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }

            } else {
                List<String> idList = new ArrayList();
                List<String> deptIdList = userService.queryDeptIdByParentId(parentId, "0");
                for (int i = 0; i < deptIdList.size(); i++) {
                    String fyId = deptIdList.get(i);
                    if (fyId.length() > 4) {
                        idList.add(fyId);
                    }
                    if (fyId.length() == 4) ;
                    List<String> deptIdLists = userService.queryDeptIdByParentId(fyId, "0");
                    for (int j = 0; j < deptIdLists.size(); j++) {
                        String secondId = deptIdLists.get(j);
                        idList.add(secondId);
                    }
                }
                String str = String.join(",", idList);
                String[] str1 = str.split(",");
                long[] strArrNum = (long[]) ConvertUtils.convert(str1, long.class);
                for (int i = 0; i < strArrNum.length; i++) {
                    long str3 = Long.valueOf(str1[i]);
                    List<Map> userList = userService.selectUserByDeptId(str3, "0");
                    if (!StringUtils.isNull(userList)) {

                        for (int j = 0; j < userList.size(); j++) {
                            Map map = userList.get(j);
                            allUserList.add(map);
                        }
                    }
                }

            }
        }
        for (String arr : cid_arr) {
            for (Map m : allUserList) {
                if (arr.equals(m.get("user_id").toString())) {
                    m.put("del_flag", 11);
                }
            }
        }

        List<Map> objectList = new ArrayList<Map>();
        for (Map m : allUserList) {
            String userId = m.get("user_id").toString();
            List<UserRole> roleList = userService.selectUserRoleByUserId(userId);
            for (UserRole r : roleList) {
                if (ActivitiDefinftion.ROLE_TASKASSIGNMENT.equals(r.getRoleKey())) {
                    m.put("select_flag", "false");
                    objectList.add(m);
                    break;
                }
            }
        }

        String submitter = information.getSubmitter();
        if (StringUtils.isNotEmpty(submitter)) {
            String[] submitter_arr = submitter.split(",");
            for (Map mp : objectList) {
                for (String str : submitter_arr) {
                    if (str.equals(mp.get("user_id").toString())) {
                        mp.put("select_flag", "true");
                        break;
                    }
                }
            }
        }

        mmap.put("userRoleList", objectList);
        Accessory accessory = new Accessory();
        accessory.setAccessoryTaskid(taskId);
        accessory.setAccessoryType("0");
        List<Accessory> accessoryList = accessoryService.selectAccessoryList(accessory);
        accessory.setAccessoryType("1");
        List<Accessory> endTaskAccessories = accessoryService.selectAccessoryList(accessory);

        mmap.put("accessoryList", accessoryList);
        mmap.put("endTaskAccessories", endTaskAccessories);
        mmap.put("information", information);
        mmap.put("userList", allUserList);
        mmap.put("size", accessoryList.size());
        mmap.put("endsize", endTaskAccessories.size());
        return prefix + "/appraise";
    }

    @PostMapping("/main")
    @ResponseBody
    public TableDataInfo main(Information information) {
        List<Information> list = new ArrayList<Information>();
        startPage();
        if ("0".equals(information.getTaskStatus())) {
            list = informationService.mainSelectList(ShiroUtils.getUserId().toString());
        } else {
            list = informationService.mianselectListTaskStatus_Two(ShiroUtils.getUserId().toString());
        }
        return getDataTable(list);
    }

    /**
     * 根据任务状态统计任务数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryEveCount")
    @GetMapping("/queryEveCount")
    @ResponseBody
    public Map queryEveCount() {
        Map<String, Object> map = new HashMap<>();
        //根据当前部门查询任务总数    中院查看所有
        int allCount = informationService.queryAllCount();

        int addCount = informationService.queryAddCount();

        int jxzCount = informationService.queryCountByStatus("1");

        int finishCount = informationService.queryCountByStatus("2");

        map.put("allCount", allCount);
        map.put("addCount", addCount);
        map.put("jxzCount", jxzCount);
        map.put("finishCount", finishCount);
        return map;
    }

    /**
     * 统计完成和进行中各自任务数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryCountWcAndJxz")
    @GetMapping("/queryCountWcAndJxz")
    @ResponseBody
    public Map queryCountWcAndJxz() {
        Map<String, Object> map = informationService.queryCountWcAndJxz();
        return map;
    }


    /**
     * 根据任务类型统计当前时间前半年各个月份的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryTaskCountByMonth")
    @GetMapping("/queryTaskCountByMonth")
    @ResponseBody
    public List<Object> queryTaskCountByMonth() {
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<DictData> dictDataList = dictDataService.selectDictDataByType("task_type");
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();
        allList.add(dateList);
        for (DictData data : dictDataList) {
            Map<String, Object> stringObjectMap = new HashMap<String, Object>();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < dateList.size(); i++) {
                String time = dateList.get(i).toString();
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("count", informationService.queryTaskCountByMonth(time, data.getDictValue()));
                mapList.add(objectMap);
            }
            stringObjectMap.put("name", data.getDictLabel());
            stringObjectMap.put("value", mapList);
            allList.add(stringObjectMap);
        }
        return allList;
    }


    /**
     * 根据案件类别统计当前时间前半年各个月份的线索数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryByStatusAndMonth")
    @GetMapping("/queryByStatusAndMonth")
    @ResponseBody
    public List<Object> queryByStatusAndMonth() {
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();

        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {
            String time = dateList.get(i).toString();
            //根据月份参数和任务状态统计任务数量（0 1 2 总量  2 完成  ）
            int totalCount = informationService.queryByStatusAndMonth(time, "0,1,2");
            int wcCount = informationService.queryByStatusAndMonth(time, "1");
            countList.add(totalCount);
            secondCountList.add(wcCount);
        }
        map.put("total", countList);
        map.put("wc", secondCountList);

        allList.add(map);
        allList.add(dateList);
        return allList;
    }


    /**
     * 统计优良中差 各个评价等级的任务数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryByMonthAndLevel")
    @GetMapping("/queryByMonthAndLevel")
    @ResponseBody
    public List<Object> queryByMonthAndLevel() {
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<DictData> dictDataList = dictDataService.selectDictDataByType("appraise_level");
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();
        allList.add(dateList);
        Map<String, Object> map = new HashMap<>();
        for (DictData data : dictDataList) {
            Map<String, Object> stringObjectMap = new HashMap<String, Object>();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < dateList.size(); i++) {
                String time = dateList.get(i).toString();
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("count", informationService.queryByMonthAndLevel(time, data.getDictValue()));
                mapList.add(objectMap);
            }
            stringObjectMap.put("name", data.getDictLabel());
            stringObjectMap.put("value", mapList);
            allList.add(stringObjectMap);
        }
        return allList;
    }

    /**
     * 统计 各个任务类型的数量
     *
     * @return
     */

    //@RequiresPermissions("module:information:queryCountByTaskType")
    @GetMapping("/queryCountByTaskType")
    @ResponseBody
    public List queryCountByTaskType() {
        List<DictData> dictDataList = dictDataService.selectDictDataByType("task_type");
        List<Object> list = new ArrayList<>();
        for (DictData data : dictDataList) {
            Map<String, Object> objectMap = new HashMap<String, Object>();
            objectMap.put("name", data.getDictLabel());
            objectMap.put("value", informationService.queryCountByTaskType(data.getDictValue()));
            list.add(objectMap);
        }
        return list;
    }


    /**
     * 根据任务重要度统计任务数量
     *
     * @return
     */
    //@RequiresPermissions("module:information:queryTaskCountByMonthAndImportance")
    @GetMapping("/queryTaskCountByMonthAndImportance")
    @ResponseBody
    public List<Object> queryTaskCountByMonthAndImportance() {
        //定义各个集合
        List<Object> allList = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        List<Integer> countList = new ArrayList<>();
        List<Integer> secondCountList = new ArrayList<>();
        //查询出当前时间的前半年的各个月份
        List<Object> dateList = DateUtils.getSixMonth();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {
            String time = dateList.get(i).toString();
            //根据月份参数和任务重要度统计任务数量（ 0.重要1.一般 ）
            int zyCount = informationService.queryTaskCountByMonthAndImportance(time, "0");
            int ybCount = informationService.queryTaskCountByMonthAndImportance(time, "1");
            countList.add(zyCount);
            secondCountList.add(ybCount);
        }
        map.put("zy", countList);
        map.put("yb", secondCountList);
        allList.add(map);
        allList.add(dateList);
        return allList;
    }

    @PostMapping("/map")
    @ResponseBody
    public List<Map<String, Object>> map() {
        return informationService.getListMap();
    }

    @PostMapping("/checkAppraise")
    @ResponseBody
    public AjaxResult checkAppraise(String taskId) {
        Information information = informationService.selectInformationById(taskId);
        String userid = ShiroUtils.getUserId().toString();
        if (StringUtils.isNotEmpty(information.getSubmitter())) {
            if (userid.equals(information.getSubmitter())) {
                return success();
            } else {
                return error("没有评价权限！");
            }
        } else {
            if (userid.equals(information.getTaskCreateby())) {
                return success();
            } else {
                return error("没有评价权限！");
            }
        }
    }

    @PostMapping("/checkEditTask")
    @ResponseBody
    public AjaxResult checkEditTask(String taskId) {
        Information information = informationService.selectInformationById(taskId);
        String userid = ShiroUtils.getUserId().toString();
        if (StringUtils.isEmpty(information.getSubmitter()) && StringUtils.isEmpty(information.getTaskProcessor())
                && StringUtils.isNotEmpty(information.getTaskCreateby())) {//创建人不为空  提交人指派人都为空
            if(userid.equals(information.getTaskCreateby())){
                return success();
            }else{
                return error("当前任务您无权修改！");
            }
        } else if (StringUtils.isNotEmpty(information.getTaskCreateby()) && StringUtils.isNotEmpty(information.getSubmitter())
                && StringUtils.isEmpty(information.getTaskProcessor())) {//创建人不为空  提交人不为空  指派人为空情况
            String[] submitter_arr = information.getSubmitter().split(",");
            if(Arrays.asList(submitter_arr).contains(userid)){
                return success();
            }else{
                return error("当前任务您无权修改！");
            }
        }else if(StringUtils.isNotEmpty(information.getTaskCreateby()) && StringUtils.isNotEmpty(information.getSubmitter())
                && StringUtils.isNotEmpty(information.getTaskProcessor())){//创建人不为空   提交人不为空  指派人不为空的情况
            String[] tp_arr = information.getTaskProcessor().split(",");
            if(Arrays.asList(tp_arr).contains(userid)){
                return success();
            }else{
                return error("当前任务您无权修改！");
            }
        }else{ //创建人不为空  提交人为空   指派人不为空
            String[] arr = information.getTaskProcessor().split(",");
            if(Arrays.asList(arr).contains(userid)){
                return success();
            }else{
                return error("当前任务您无权修改！");
            }
        }
    }
}

