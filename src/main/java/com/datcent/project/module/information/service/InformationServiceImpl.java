package com.datcent.project.module.information.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.http.DatcentHttpUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.accessory.domain.Accessory;
import com.datcent.project.module.accessory.mapper.AccessoryMapper;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.mapper.JournalMapper;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.information.mapper.InformationMapper;
import com.datcent.project.module.information.domain.Information;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务 服务层实现
 *
 * @author datcent
 * @date 2019-01-20
 */
@Service
public class InformationServiceImpl implements IInformationService {
    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private AccessoryMapper accessoryMapper;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    /**
     * 查询任务信息
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    @Override

    public Information selectInformationById(String taskId) {
        return informationMapper.selectInformationById(taskId);
    }

    /**
     * 查询任务列表
     *
     * @param information 任务信息
     * @return 任务集合
     */
    @Override
    public List<Information> selectInformationList(Information information) {
        return informationMapper.selectInformationList(information);
    }

    /**
     * 新增任务
     *
     * @param information 任务信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertInformation(Information information, String acId, MultipartFile[] file, String profile) throws ParseException, IOException, FileNameLengthLimitExceededException, FileUploadBase.FileSizeLimitExceededException {
        String uuid = StringUtils.getUUID();
        Accessory accessory = new Accessory();
        if (file.length > 0) {
            for (MultipartFile f : file) {
                if (!f.isEmpty()) {
                    String fileName = f.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    String uploadName = FileUploadUtils.upload(profile, f, prefix);
                    accessory.setAccessoryId(StringUtils.getUUID());
                    accessory.setAccessoryOldname(fileName);
                    accessory.setAccessoryUrl(profile + uploadName);
                    accessory.setAccessoryTaskid(uuid);
                    accessoryMapper.insertAccessory(accessory);
                    Journal journal = new Journal();
                    journal.setJournalTaskid(uuid);
                    journal.setJournalCreateby(ShiroUtils.getUserId().toString());
                    journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
                    journal.setJournalId(StringUtils.getUUID());
                    journal.setJournalContent("上传了附件");
                    journalMapper.insertJournal(journal);
                }
            }
        }
        Journal journal = new Journal();
        journal.setJournalTaskid(uuid);
        journal.setJournalContent("创建任务");
        journal.setJournalCreateby(ShiroUtils.getUserId().toString());
        try {
            journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        journal.setJournalId(StringUtils.getUUID());
        journal.setJournalType("1");
        journalMapper.insertJournal(journal);
        information.setTaskId(uuid);
        information.setTaskCreateby(ShiroUtils.getUserId().toString());
        information.setTaskCreatedate(new Date());
        //指派人不为空，提交人为空
        if (StringUtils.isNotEmpty(information.getTaskProcessor()) && StringUtils.isEmpty(information.getSubmitter())) {
            //获取当前时间
            String nowdate = DateUtils.getDate();
            //计划结束时间
            String p_date = information.getTaskPlanEnddate();
            //计算相差时间
            int diff_pe = DateUtils.differentDays(p_date,nowdate);
            //当前时间大于计划结束时间，状态修改为超期未完成
            if(diff_pe>0){
                information.setTaskStatus("5");
            }else{
                String ps_date = information.getTaskPlanStartdate();
                int diff_date = DateUtils.differentDays(ps_date,nowdate);
                if(diff_date>=0){
                    information.setTaskStatus("1");
                    information.setTaskStartdate(nowdate);
                }else{
                    information.setTaskStatus("0");
                }
            }
        } else if (StringUtils.isNotEmpty(information.getSubmitter())
                && StringUtils.isEmpty(information.getTaskProcessor())) { //提交人不为空，指派人为空
            information.setTaskStatus("3"); //状态为待分配
        }else if(StringUtils.isEmpty(information.getSubmitter()) && StringUtils.isEmpty(information.getTaskProcessor())){
            information.setTaskStatus("6");
        } else {
            //获取当前时间
            String nowdate = DateUtils.getDate();
            //计划结束时间
            String p_date = information.getTaskPlanEnddate();
            //计算相差时间
            int diff_pe = DateUtils.differentDays(p_date,nowdate);
            //当前时间大于计划结束时间，状态修改为超期未完成
            if(diff_pe>0){
                information.setTaskStatus("5");
            }else{
                String ps_date = information.getTaskPlanStartdate();
                int diff_date = DateUtils.differentDays(ps_date,nowdate);
                if(diff_date>=0){
                    information.setTaskStatus("1");
                    information.setTaskStartdate(nowdate);
                }else{
                    information.setTaskStatus("0");
                }
            }
        }
        return informationMapper.insertInformation(information);
    }

    /**
     * 修改任务
     *
     * @param information 任务信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateInformation(Information information, MultipartFile[] taskFile, MultipartFile[] overFile, String profile) throws ParseException, FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
        Information primitive_information = informationMapper.selectInformationById(information.getTaskId());
        Journal journal = new Journal();
        journal.setJournalTaskid(information.getTaskId());
        journal.setJournalCreateby(ShiroUtils.getUserId().toString());
        journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
        if (!primitive_information.getTaskType().equals(information.getTaskType())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了任务类型");
            journalMapper.insertJournal(journal);
        }
        if (!primitive_information.getTaskName().equals(information.getTaskName())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了任务名称");
            journalMapper.insertJournal(journal);
        }
        if (!primitive_information.getTaskImportance().equals(information.getTaskImportance())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了任务重要度");
            journalMapper.insertJournal(journal);
        }

        if (StringUtils.isEmpty(primitive_information.getTaskContent())) {
            if (StringUtils.isNotEmpty(information.getTaskContent())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务描述");
                journalMapper.insertJournal(journal);
            }
        } else {
            if (!primitive_information.getTaskContent().equals(information.getTaskContent())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务描述");
                journalMapper.insertJournal(journal);
            }
        }

        if(StringUtils.isEmpty(primitive_information.getTaskProcessor())){
            if(StringUtils.isNotEmpty(information.getTaskProcessor())){
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了指派给");
                journalMapper.insertJournal(journal);
            }
        }else{
            if (!primitive_information.getTaskProcessor().equals(information.getTaskProcessor())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了指派给");
                journalMapper.insertJournal(journal);
            }
        }

        if(StringUtils.isEmpty(primitive_information.getSubmitter())){
            if(StringUtils.isNotEmpty(information.getSubmitter())){
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了提交给");
                journalMapper.insertJournal(journal);
            }
        }else{
            if(!primitive_information.getSubmitter().equals(information.getSubmitter())){
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了提交给");
                journalMapper.insertJournal(journal);
            }
        }

        if (!primitive_information.getTaskPlanStartdate().equals(information.getTaskPlanStartdate())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了日程规划开始时间");
            journalMapper.insertJournal(journal);
        }
        if (!primitive_information.getTaskPlanEnddate().equals(information.getTaskPlanEnddate())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了日程规划结束时间");
            journalMapper.insertJournal(journal);
        }
        if (!primitive_information.getTaskUrgency().equals(information.getTaskUrgency())) {
            journal.setJournalId(StringUtils.getUUID());
            journal.setJournalContent("修改了任务紧迫度");
            journalMapper.insertJournal(journal);
        }

        if (StringUtils.isEmpty(primitive_information.getTaskStartdate())) {
            if (StringUtils.isNotEmpty(information.getTaskStartdate())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务开始时间");
                journalMapper.insertJournal(journal);
//                if(StringUtils.isNotEmpty(information.getTaskProcessor()) ||
//                        (StringUtils.isNotEmpty(primitive_information.getTaskProcessor()) && StringUtils.isNotEmpty(information.getTaskProcessor()))){
//                    information.setTaskStatus("1");
//                }
            }
        } else {
            if (!primitive_information.getTaskStartdate().equals(information.getTaskStartdate())) {
//                if (StringUtils.isEmpty(information.getTaskStartdate())) {
//                    if(StringUtils.isNotEmpty(information.getTaskProcessor()) ||
//                            (StringUtils.isNotEmpty(primitive_information.getTaskProcessor()) && StringUtils.isNotEmpty(information.getTaskProcessor()))){
//                        information.setTaskStatus("0");
//                    }
//                }
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务开始时间");
                journalMapper.insertJournal(journal);
            }
        }

        if (StringUtils.isEmpty(primitive_information.getTaskEnddate())) {
            if (StringUtils.isNotEmpty(information.getTaskEnddate())) {
//                if(StringUtils.isNotEmpty(information.getTaskProcessor()) ||
//                        (StringUtils.isNotEmpty(primitive_information.getTaskProcessor()) && StringUtils.isNotEmpty(information.getTaskProcessor()))){
//                    information.setTaskStatus("2");
//                }
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务结束时间");
                journalMapper.insertJournal(journal);
                information.setTaskConsimmator(ShiroUtils.getUserId().toString());
            }
        } else {
            if (!primitive_information.getTaskEnddate().equals(information.getTaskEnddate())) {
//                if (StringUtils.isEmpty(information.getTaskEnddate()) && (StringUtils.isNotEmpty(primitive_information.getTaskStartdate()) || StringUtils.isNotEmpty(information.getTaskStartdate()))) {
//                    if(StringUtils.isNotEmpty(information.getTaskProcessor()) ||
//                            (StringUtils.isNotEmpty(primitive_information.getTaskProcessor()) && StringUtils.isNotEmpty(information.getTaskProcessor()))){
//                        information.setTaskStatus("1");
//                    }
//                }
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务结束时间");
                journalMapper.insertJournal(journal);
            }
        }

        if (StringUtils.isEmpty(primitive_information.getTaskEndContent())) {
            if (StringUtils.isNotEmpty(information.getTaskEndContent())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务总结");
                journalMapper.insertJournal(journal);
            }
        } else {
            if (!primitive_information.getTaskEndContent().equals(information.getTaskEndContent())) {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalContent("修改了任务总结");
                journalMapper.insertJournal(journal);
            }
        }
        if (taskFile != null && taskFile.length > 0) {
            Accessory accessory = new Accessory();
            for (MultipartFile f : taskFile) {
                if (!f.isEmpty()) {
                    String fileName = f.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    String uploadName = FileUploadUtils.upload(profile, f, prefix);
                    accessory.setAccessoryId(StringUtils.getUUID());
                    accessory.setAccessoryOldname(fileName);
                    accessory.setAccessoryUrl(profile + uploadName);
                    accessory.setAccessoryTaskid(information.getTaskId());
                    accessory.setAccessoryType("0");
                    accessoryMapper.insertAccessory(accessory);
                    journal.setJournalId(StringUtils.getUUID());
                    journal.setJournalContent("修改了任务附件");
                    journalMapper.insertJournal(journal);
                }
            }
        }
        if (overFile != null && overFile.length > 0) {
            Accessory accessory = new Accessory();
            for (MultipartFile f : overFile) {
                if (!f.isEmpty()) {
                    String fileName = f.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    String uploadName = FileUploadUtils.upload(profile, f, prefix);
                    accessory.setAccessoryId(StringUtils.getUUID());
                    accessory.setAccessoryOldname(fileName);
                    accessory.setAccessoryUrl(profile + uploadName);
                    accessory.setAccessoryTaskid(information.getTaskId());
                    accessory.setAccessoryType("1");
                    accessoryMapper.insertAccessory(accessory);
                    journal.setJournalId(StringUtils.getUUID());
                    journal.setJournalContent("修改了总结任务附件");
                    journalMapper.insertJournal(journal);
                }
            }
        }

        //提交给不为空，指派给为空请情况
        if(StringUtils.isEmpty(information.getTaskProcessor()) && StringUtils.isNotEmpty(information.getSubmitter())){
            information.setTaskStatus("3");
        }else if(StringUtils.isEmpty(information.getSubmitter()) && StringUtils.isEmpty(information.getTaskProcessor())){
            information.setTaskStatus("6");
        }else if(StringUtils.isNotEmpty(information.getTaskProcessor())){ //指派给不为空的情况，
            //短信通知
            /*DatcentHttpUtils.sendSMS("15952007827",primitive_information.getTaskContent());*/
            //实际开始时间和实际结束时间都为空情况
            if(StringUtils.isEmpty(information.getTaskStartdate()) && StringUtils.isEmpty(information.getTaskEnddate())){
                //获取当前时间
                String nowdate = DateUtils.getDate();
                //计划结束时间
                String p_date = information.getTaskPlanEnddate();
                //计算相差时间
                int diff_pe = DateUtils.differentDays(p_date,nowdate);
                //当前时间大于计划结束时间，状态修改为超期未完成
                if(diff_pe>0){
                    information.setTaskStatus("5");
                }else{
                    String ps_date = information.getTaskPlanStartdate();
                    int diff_date = DateUtils.differentDays(ps_date,nowdate);
                    if(diff_date>=0){
                        information.setTaskStatus("1");
                        information.setTaskStartdate(nowdate);
                    }else{
                        information.setTaskStatus("0");
                    }
                }
            }else if(StringUtils.isNotEmpty(information.getTaskStartdate()) && StringUtils.isEmpty(information.getTaskEnddate())){//实际开始时间不为空，实际结束时间为空的情况
                //获取当前时间
                String nowdate = DateUtils.getDate();
                //计划结束时间
                String p_date = information.getTaskPlanEnddate();
                //计算相差时间
                int diff_pe = DateUtils.differentDays(p_date,nowdate);
                //当前时间大于计划结束时间，状态修改为超期未完成
                if(diff_pe>0){
                    information.setTaskStatus("5");
                }else{
                    String ps_date = information.getTaskPlanStartdate();
                    int ps_now_diff = DateUtils.differentDays(nowdate,ps_date);
                    if(ps_now_diff>0){
                        information.setTaskStatus("0");
                    }else{
                        information.setTaskStatus("1");
                    }
                }
            }else if(StringUtils.isNotEmpty(information.getTaskStartdate()) && StringUtils.isNotEmpty(information.getTaskEnddate())){//实际开始时间，实际结束时间都不为空
                //实际结束时间
                String sj_date = information.getTaskEnddate();
                //计划结束时间
                String jh_date = information.getTaskPlanEnddate();
                //计算相差时间
                int diff_d = DateUtils.differentDays(jh_date,sj_date);
                if(diff_d>0){
                    information.setTaskStatus("4");
                }else{
                    information.setTaskStatus("2");
                }
            }
        }else if(StringUtils.isEmpty(information.getTaskProcessor()) && StringUtils.isEmpty(information.getSubmitter()) ){
            information.setTaskStatus("6");
        }

        information.setLastUpdateBy(ShiroUtils.getUserId().toString());
        information.setLastUpdateBy(ShiroUtils.getUserId().toString());
        information.setLastUpdateDate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
        return informationMapper.updateInformation(information);
    }

    /**
     * 删除任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationByIds(List<Information> informationList) {
        try {
            for (Information in : informationList) {
                Information upInfo = new Information();
                upInfo.setTaskId(in.getTaskId());
                upInfo.setIsDelete("1");
                informationMapper.updateInformation(upInfo);
            }
            return informationList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*批量添加日志*/
    public int addLisrLog(String json_str) {
        List<Journal> journalList = JSON.parseArray(json_str, Journal.class);
        Iterator<Journal> it = journalList.iterator();
        while (it.hasNext()) {
            Journal journal = it.next();
            if (StringUtils.isEmpty(journal.getJournalContent())) {
                it.remove();
            } else {
                journal.setJournalId(StringUtils.getUUID());
                journal.setJournalCreateby(ShiroUtils.getUserId().toString());
                journal.setJournalType("1");
            }
        }
        return journalMapper.bathInsertJournal(journalList);
    }

    /**
     * 统计任务总数
     *
     * @return
     */
    public int queryAllCount() {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Information information = new Information();
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            information.setParentDeptId(dpt.getParentId().toString());
        }
        return informationMapper.queryAllCount(information);
    }

    /**
     * 统计当月新增总数
     *
     * @return
     */
    public int queryAddCount() {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Information information = new Information();
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            information.setParentDeptId(dpt.getParentId().toString());
        }
        information.setTaskCreatedate(DateUtils.stringToDate(DateUtils.dateTimeNow("yyyy-MM-dd")));
        return informationMapper.queryAllCount(information);
    }

    /**
     * 根据任务状态统计任务数量
     *
     * @return
     */
    public int queryCountByStatus(String status) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Information information = new Information();
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            information.setParentDeptId(dpt.getParentId().toString());
        }
        information.setTaskStatus(status);
        return informationMapper.queryAllCount(information);
    }

    /**
     * 统计完成和进行中各自任务数量
     *
     * @return
     */
    public Map queryCountWcAndJxz() {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        String parentDetpId = null;
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            parentDetpId = dpt.getParentId().toString();
        }
        return informationMapper.queryCountWcAndJxz(parentDetpId);
    }


    /**
     * 统计半年各任务类别的数量
     *
     * @param time
     * @param type
     * @return
     */
    public int queryTaskCountByMonth(@Param("time") String time, @Param("type") String type) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        Information information = new Information();
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            information.setParentDeptId(dpt.getParentId().toString());
        }
        information.setTaskCreatedate(DateUtils.stringToDate(DateUtils.dateTimeNow("yyyy-MM-dd")));
        information.setTaskType(type);
        return informationMapper.queryTaskCountByMonth(information);
    }


    /**
     * 统计各月份  任务完成量和任务总量
     *
     * @param time
     * @param status
     * @return
     */
    public int queryByStatusAndMonth(String time, String status) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        String parentDetpId = null;
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            parentDetpId = dpt.getParentId().toString();
        }
        return informationMapper.queryByStatusAndMonth(time, Convert.toStrArray(status), parentDetpId);
    }


    /**
     * 统计优良中差 各个评级的任务数量
     *
     * @return
     */
    public int queryByMonthAndLevel(@Param("time") String time, @Param("level") String level) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        String parentDetpId = null;
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            parentDetpId = dpt.getParentId().toString();
        }
        return informationMapper.queryByMonthAndLevel(time, level, parentDetpId);
    }

    /**
     * 统计 各个任务类型的数量
     *
     * @return
     */
    public int queryCountByTaskType(String type) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        String parentDetpId = null;
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            parentDetpId = dpt.getParentId().toString();
        }
        return informationMapper.queryCountByTaskType(type, parentDetpId);
    }


    /**
     * 统计各个月份 重要度任务数量
     *
     * @return
     */
    public int queryTaskCountByMonthAndImportance(@Param("time") String time, @Param("importance") String importance) {
        String userid = ShiroUtils.getUserId().toString();
        List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
        String parentDetpId = null;
        Boolean isok = false;
        for (UserRole r : roleList) {
            if (ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())) {
                isok = true;
                break;
            }
        }
        Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
        if ("0".equals(dpt.getRank())) {
            isok = true;
        }
        if (isok == false) {
            parentDetpId = dpt.getParentId().toString();
        }
        return informationMapper.queryTaskCountByMonthAndImportance(time, importance, parentDetpId);
    }

    /**
     * 工作地图  获取各县任务数量
     *
     * @return
     */
    public List<Map<String, Object>> getListMap() {
        Dept dept = new Dept();
        dept.setDeptType("0");
        List<Dept> deptList = deptService.selectDeptList(dept);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Dept p : deptList) {
            Map<String, Object> map = new HashMap<String, Object>();
            Information information = new Information();
            information.setParentDeptId(p.getDeptId().toString());
            map.put("name", p.getDeptName());
            map.put("count", informationMapper.queryAllCount(information));
            list.add(map);
        }
        return list;
    }

    public List<Information> mainSelectList(String userid) {
        return informationMapper.mainSelectList(userid);
    }

    @Override
    public List<Information> mianselectListTaskStatus_Two(String userid) {
        return informationMapper.mianselectListTaskStatus_Two(userid);
    }

    @Override
    public List<Information> selectRemindList(Information information) {
        return informationMapper.selectRemindList(information);
    }

    @Override
    public int updateTask(Information information) {
        return informationMapper.updateTask(information);
    }
}


