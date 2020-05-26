package com.datcent.project.module.problemDisposal;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.DocConvertPdf;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.clue.controller.ClueController;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.service.IDispositionHandleService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.ruTask.service.IRuTaskService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题处置管理 信息操作处理
 *
 * @author zhoushiji
 * @date 2019-1-15
 */
@Controller
@RequestMapping("/module/problemdisposal")
public class ProblemDisposalController extends BaseController {
    private String prefix = "module/problemdisposal";

    @Autowired
    private IDispositionFormService dispositionFormService;

    @Autowired
    private IRuTaskService ruTaskService;

    @Autowired
    private IClueService clueService;

    @Autowired
    private IDispositionAttachmentService dispositionAttachmentService;

    @Autowired
    private IDispositionHandleService dispositionHandleService;

    @Autowired
    private IUserService userService;


    @Autowired
    private IDubvioEventService dubvioEventService;

    @Value("${datcent.profile}")
    private String profile;


//    @PostMapping("/upload")
//    @ResponseBody
//    public AjaxResult upload(MultipartFile file){
//        String fileName = file.getOriginalFilename();
//        String prefix = fileName.substring(fileName.lastIndexOf("."));
//        try {
//            Map<String,Object> map = DocConvertPdf.
//                    file2pdf(file.getInputStream(),fileName,profile,prefix.replace(".",""));
//            AjaxResult ajaxResult= new AjaxResult();
//            ajaxResult.put("code",0);
//            ajaxResult.put("pdfurl",map.get("pdfurl"));
//            ajaxResult.put("docurl",map.get("docurl"));
//            return ajaxResult;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return error();
//        }
//    }



    /**
     * 线索处置过程
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:proHandle")
    @GetMapping("/proHandle/{clueId}/{count}")
    public String proHandle(@PathVariable("clueId") String clueId, ModelMap mmap,@PathVariable("count") Integer count) {
        Clue clue = clueService.selectClueById(clueId);
        DispositionAttachment dispositionAttachment=new DispositionAttachment();
        dispositionAttachment.setClueId(clueId);
        List<DispositionAttachment> dispositionAttachmentList=dispositionAttachmentService.selectFile(dispositionAttachment);
        if(dispositionAttachmentList.size()>0){
            mmap.put("fileSize",dispositionAttachmentList.size());
        }else{
            mmap.put("fileSize","0");
        }
        String czjg = clue.getClueCzjg();
        mmap.put("clueId",clueId);
        mmap.put("czjg",czjg);
        String proUrl="";
        mmap.put("clue",clue);
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(clue.getDubvioId());
        mmap.put("dubvioEvent",dubvioEvent);
        DispositionHandle handle=new DispositionHandle();
        handle.setClueId(clue.getClueId());
        //handle.setNodeType(czjg);
        handle.setStatus("1");
        mmap.put("count",count);
        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
        mmap.put("allCount",parentHandle.getCount());
        handle.setCount(count);
        List<DispositionHandle> handleList=dispositionHandleService.selectDeptList(handle);
        mmap.put("form1",false);
        mmap.put("form2",false);
        mmap.put("form3",false);
        for (DispositionHandle item:handleList) {
            if(item.getNodeFlag().equals("7") || item.getNodeFlag().equals("15")
                    || item.getNodeFlag().equals("23")|| item.getNodeFlag().equals("36")){
                mmap.put("form1",true);
            }
            if(item.getNodeFlag().equals("9") || item.getNodeFlag().equals("17")
                    || item.getNodeFlag().equals("25")|| item.getNodeFlag().equals("42")){
                mmap.put("form2",true);
            }
            if(item.getNodeFlag().equals("11") || item.getNodeFlag().equals("19")
                    || item.getNodeFlag().equals("27")|| item.getNodeFlag().equals("40")){
                mmap.put("form3",true);
            }
            czjg=item.getNodeType();
        }
        if("3".equals(czjg)){
            proUrl=prefix + "/proHandle";
            handle.setNodeFlag("7");
        }else if ("4".equals(czjg)){
            proUrl=prefix + "/thHandle";
        }else if ("5".equals(czjg)){
            proUrl=prefix + "/hxHandle";
        }else if ("10".equals(czjg)){
            proUrl=prefix + "/laHandle";
        }
        return proUrl;
    }

    @RequiresPermissions("module:problemDisposal:view")
    @GetMapping()
    public String problemDisposal() {
        return prefix + "/problemDisposal";
    }

    /**
     * 函询函询页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:thhxview")
    @GetMapping("/thhxproblemDisposal")
    public String thhxproblemDisposal() {
        return prefix + "/thhxproblemDisposal";
    }

    /**
     * 初步核实页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:cbhsview")
    @GetMapping("/cbhsproblemDisposal")
    public String cbhsproblemDisposal() {
        return prefix + "/cbhsproblemDisposal";
    }

    /**
     * 立案调查页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:ladcview")
    @GetMapping("/ladcproblemDisposal")
    public String ladcproblemDisposal() {
        return prefix + "/ladcproblemDisposal";
    }

    /**
     * 予以了结页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:yyljview")
    @GetMapping("/yyljproblemDisposal")
    public String yyljproblemDisposal() {
        return prefix + "/yyljproblemDisposal";
    }

    /**
     * 暂存待查页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:zcdcview")
    @GetMapping("/zcdcproblemDisposal")
    public String zcdcproblemDisposal(String isCQ,ModelMap modelMap) {
        modelMap.put("isCQ",isCQ);
        return prefix + "/zcdcproblemDisposal";
    }



    /**
     * 函询函询页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:yjsgproblemView")
    @GetMapping("/yjsgproblemDisposal")
    public String yjsgproblemDisposal() {
        return prefix + "/yjsgproblemDisposal";
    }


    /**
     * 其他处置页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:qtview")
    @GetMapping("/qtproblemDisposal")
    public String qtproblemDisposal() {
        return prefix + "/qtproblemDisposal";
    }



    /**
     * 初步核实页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:yjsgbview")
    @GetMapping("/yjsgbproblemDisposal")
    public String yjsgbproblemDisposal() {
        return prefix + "/yjsgproblemDisposal";
    }

    /**
     * 案件审理页面 跳转
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @RequiresPermissions("module:problemDisposal:ajslview")
    @GetMapping("/ajslproblemDisposal")
    public String ajslproblemDisposal() {
        return prefix + "/ajslproblemDisposal";
    }

    /**
     * 线索移交审批 信息操作处理
     *
     * @author zhoushiji
     * @date 2019-1-17
     */
    @RequiresPermissions("module:problemdisposal:approve")
    @GetMapping("/approve")
    public String approve() {
        return prefix + "/approve";
    }

    /**
     * 审批分类页面跳转
     *
     * @author zhoushiji
     * @date 2019-1-22
     */
    @GetMapping("/fpClassify")
    public String fpClassify() {
        return prefix + "/fpClassify";
    }



    private void saveDispositionHandle(String DeptName, String NodeFlag, String way, String Result, String clueId, String content,
                                           String clueClassify, Clue clue, String taskId, String status, String createBy, String createTime) {
        DispositionHandle dispositionHandle = new DispositionHandle();
        if (dispositionHandleService.selectDeptById(clueId) == null) {
            dispositionHandle.setParentId("0");
            dispositionHandle.setDeptId(clue.getClueId());
            dispositionHandle.setDeptName(clue.getJbxxAh());
            dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
            /*dispositionHandle.setCreateTime("");*/
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setNodeId(taskId);
            dispositionHandle.setCount(1);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.insertDispositionHandle(dispositionHandle);
        }

        //判断节点是否在数据中存在
        dispositionHandle.setClueId(clueId);
        dispositionHandle.setNodeFlag(NodeFlag);
        List<DispositionHandle> dispositionHandles = dispositionHandleService.selectDeptList(dispositionHandle);
        if (dispositionHandles.size() > 0) {
            dispositionHandle = new DispositionHandle();
            dispositionHandle.setParentId(clue.getClueId());
            dispositionHandle.setSuggestion(content);
            dispositionHandle.setDeptName(DeptName);
            dispositionHandle.setNodeType(clueClassify);
            dispositionHandle.setNodeFlag(NodeFlag);
            dispositionHandle.setNodeId(NodeFlag);
            dispositionHandle.setDeptId(StringUtils.getUUID());
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setCreateBy(createBy);
            dispositionHandle.setWay(way);
            dispositionHandle.setCount(1);
            dispositionHandle.setResult(Result);
            dispositionHandle.setStatus(status);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.updateDispositionHandle(dispositionHandle);
        }else {
            dispositionHandle = new DispositionHandle();
            dispositionHandle.setParentId(clue.getClueId());
            dispositionHandle.setSuggestion(content);
            dispositionHandle.setDeptName(DeptName);
            dispositionHandle.setNodeType(clueClassify);
            dispositionHandle.setNodeFlag(NodeFlag);
            dispositionHandle.setNodeId(NodeFlag);
            dispositionHandle.setDeptId(StringUtils.getUUID());
            dispositionHandle.setClueId(clue.getClueId());
            dispositionHandle.setCreateBy(createBy);
            dispositionHandle.setWay(way);
            dispositionHandle.setCount(1);
            dispositionHandle.setResult(Result);
            dispositionHandle.setStatus(status);
            dispositionHandle.setCreateTimes(createTime);
            dispositionHandleService.insertDispositionHandle(dispositionHandle);
        }
    }

    /**
     * 问题线索分类审核
     *
     * @author zhoushiji
     * @date 2019-1-23
     */
    @PostMapping("/classilyCompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult compleTask(String isok, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String talkType, String clueClassify,String clueCbrName) {
        try {
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("talkType", talkType);
            proposerName = approverName;
            Clue clue = clueService.selectClueById(clueId);
            if (isok.equals("true")){
                // 初步核实
                mmap.put("formType", clueClassify);
                if("3".equals(clueClassify)){
                    mmap.put("handleUrl", "module/initcheck/cbhscpform");
                }
                //谈话
                else if("4".equals(clueClassify)){
                    mmap.put("handleUrl", prefix+"/thcpform");
                }
                //函询
                else if("5".equals(clueClassify)){
                   mmap.put("handleUrl", prefix+"/hxcpform");
                }
            } else {
                clue.setProcessStatus("2");
                mmap.put("pid", pid);
                mmap.put("taskId", taskId);
                mmap.put("handleUrl", "module/clue/release");
            }

//            if (talkType.equals("1")) {
//                clueClassify = "4";
//            } else if (talkType.equals("2")) {
//                clueClassify = "5";
//            }
//            if (clueClassify.equals("3")) {
//                if (isok.equals("true")) {
//                    mmap.put("formType", clueClassify);
//                    mmap.put("handleUrl", "module/initcheck/cbhscpform");
//                } else {
//                    mmap.put("handleUrl", "module/clue/release");
//                }
//            } else if (clueClassify.equals("4") || clueClassify.equals("5")) {
//                if (isok.equals("true")) {
//                    if (talkType.equals("1")) {
//                        mmap.put("handleUrl", prefix+"/thcpform");
//                    } else if (talkType.equals("2")) {
//                        mmap.put("handleUrl", prefix+"/hxcpform");
//                    }
//                } else {
//                    mmap.put("handleUrl", "module/clue/release");
//                    mmap.put("isok", "false");
//                    Map<String,Object> paramsMap=new HashMap<String,Object>();
//                    paramsMap.put("pid", pid);
//                    paramsMap.put("taskId", taskId);
//                    mmap.put("paramsMap",paramsMap);
//                }
//            }

            if (clueClassify.equals("3")) {
                nodeFlag = "6";
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "5", "提交", "提出-初步核实", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "6", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("创建初步核实呈批", "7", "提交", "初步核实呈批已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("初步核实呈批审核", "8", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("工作方案", "9", "提交", "工作方案已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("工作方案审核", "10", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("提交情况报告和处置意见", "11", "提交", "情况报告和处置意见已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("情况报告和处置意见审核", "12", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
//                saveDispositionHandle("最后处置结果", "0", "最终结果", "", clueId, "", clueClassify, clue, task, "0","");
                    clue.setClueCzjd("5");
                    clue.setClueRemarks("提出-初步核实");
                }
                ruTaskService.checkProblem(1,isok, opinion, clueClassify,  clue, nodeFlag,"审核", "同意-初步核实", "不同意-初步核实");
            } else if (clueClassify.equals("4")) {
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "13", "提交", "提出-谈话", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "14", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("创建谈话呈批", "15", "提交", "谈话呈批已提交-等待审核", clueId, "", clueClassify, clue, taskId ,"0", "", null);
                    saveDispositionHandle("谈话呈批审核", "16", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("谈话笔录", "17", "提交", "谈话笔录已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("谈话笔录审核", "18", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("提交情况报告和处置意见", "19", "提交", "谈话情况报告已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("情况报告和处置意见审核", "20", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
//                saveDispositionHandle("最后处置结果", "0", "最终结果", "", clueId, "", clueClassify, clue, task, "0","");
                    clue.setClueCzjd("13");
                    clue.setClueRemarks("提出-谈话");
                }
                nodeFlag = "14";
                ruTaskService.checkProblem(1,isok, opinion, clueClassify,  clue, nodeFlag,"审核", "同意-谈话", "不同意-谈话");
            } else if (clueClassify.equals("5")) {
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "21", "提交", "提出-函询", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "22", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("创建函询呈批", "23", "提交", "函询呈批已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("函询呈批审核", "24", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("函询笔录", "25", "提交", "函询笔录已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("函询笔录审核", "26", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("提交情况报告和处置意见", "27", "提交", "函询情况报告已提交-等待审核", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    saveDispositionHandle("情况报告和处置意见审核", "28", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
//                saveDispositionHandle("最后处置结果", "0", "最终结果", "", clueId, "", clueClassify, clue, task, "0","");
                    clue.setClueCzjd("21");
                    clue.setClueRemarks("提出-函询");
                }

                nodeFlag = "22";
                ruTaskService.checkProblem(1,isok, opinion, clueClassify,  clue, nodeFlag,"审核", "同意-函询", "不同意-函询");
            }else if(clueClassify.equals("1")){
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "1", "提交", "提出-暂存待查", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "2", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    /*saveDispositionHandle("最后处置结果", "0", "最终结果", "", clueId, "", clueClassify, clue, task, "0","");*/
                    clue.setClueCzjd("1");
                    clue.setClueRemarks("提出-暂存待查");
                }
                ruTaskService.checkProblem(1,isok, opinion, clueClassify,  clue, "34","", "同意-暂存待查", "不同意-暂存待查");
                if(isok.equals("true")){
                    clue.setProcessStatus("0");
                }
                clueService.updateClue(clue);
            }else if(clueClassify.equals("2")){
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "3", "提交", "提出-予以了结", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "4", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    /*saveDispositionHandle("最后处置结果", "0", "最终结果", "", clueId, "", clueClassify, clue, task, "0","");*/
                    clue.setClueCzjd("3");
                    clue.setClueRemarks("提出-予以了结");
                }
                ruTaskService.checkProblem(1,isok, opinion, clueClassify, clue, "29","", "同意-予以了结", "不同意-予以了结");
                if(isok.equals("true")){
                    clue.setProcessStatus("0");
                }
                clueService.updateClue(clue);
            }else if(clueClassify.equals("11")){
                if ("true".equals(isok)){
                    clue.setClueCzjg(clueClassify);
                    clue.setProcessStatus("1");
                    clue.setUpdateTime(DateUtils.getNowDate());
                    clue.setClueCbrName(clueCbrName);
                    saveDispositionHandle("线索处置分类", "38", "提交", "提出-移交审管办", clueId, content, clueClassify, clue, taskId, "1", ShiroUtils.getUser().getUserName(), DateUtils.getDate());
                    saveDispositionHandle("处置审批确认", "39", "审核", "", clueId, "", clueClassify, clue, taskId, "0", "", null);
                    clue.setClueCzjd("38");
                    clue.setClueRemarks("提出-移交审管办");
                }
                ruTaskService.checkProblem(1,isok, opinion, clueClassify,  clue, "39","", "同意-移交审管办", "不同意-移交审管办");
                if(isok.equals("true")){
                    clue.setProcessStatus("0");
                }
                clueService.updateClue(clue);
            }
            clueService.updateClue(clue);
            ruTaskService.compleTask(isok, content, proposerName, taskId, opinion, pid, approver, mmap);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }


    /**
     * 线索处置跳转 谈话笔录
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/uploadFile/{clueId}")
    public String uploadFile(@PathVariable("clueId") String clueId, ModelMap modelMap){

        String returnUrl=prefix + "/uploadfile";

        return returnUrl;
    }

    /**
     * 线索处置跳转 谈话呈批
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/thcpform")
    public String thcpform(String clueId,String count, ModelMap modelMap){
        String returnUrl=prefix + "/thcpform";
        String nodeFlag="15";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;
    }
    /**
     * 线索处置跳转 谈话笔录
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/thblform")
    public String thblform(String clueId,String count, ModelMap modelMap){
        String returnUrl=prefix + "/thblform";
        String nodeFlag="17";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;
    }
    /**
     * 线索处置跳转 谈话情况报告
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/thqkbgform")
    public String thqkbgform(String clueId,String count, ModelMap modelMap){
        String returnUrl=prefix + "/thqkbgform";
        String nodeFlag="19";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;
    }
    /**
     * 谈话 呈批表 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/thcpcompleTask")
    @ResponseBody
    public AjaxResult thcpcompleTask(int count,String content, String proposerName, String attachmentName, String formContent,
                                     String isok, String taskId, String opinion, String pid, String approver,
                                     String clueId, String clueClassify, String nodeFlag, String formType,
                                     String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
        Clue clue = clueService.selectClueById(clueId);
        String process="thcpProcess";
        String type="谈话呈批";
        String handleUrl=prefix+"/spthcpform";
        if ("true".equals(isEdit)){
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("formType", formType);
            mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
            mmap.put("formContent", dispositionAttachment.getFormContent());
            if (StringUtils.isNotEmpty(clue.getJbxxAh())){
                mmap.put("title",clue.getJbxxAh());
            }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
                mmap.put("title",dispositionAttachment.getAttachmentName());
            }
            mmap.put("type",type);
            mmap.put("clueId",clue.getClueId());
            mmap.put("dispositionAttachment",dispositionAttachment);
            mmap.put("content",content);
            mmap.put("clueClassify",clueClassify);
            mmap.put("handleUrl", handleUrl);
            nodeFlag="27";
            ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
            //表单修改
            ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
            return success("保存成功");
        }else {
            nodeFlag="15";
            AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
                    type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
            return ajaxResult;
        }

    }

    /**
     * 谈话呈批表审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/spthcpCompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult spthcpCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {
            String resultType="";
            String truehandleUrl=prefix+"/thblform";
            String falsehandleUrl=prefix+"/xgthcpform";
            String trueRemarks="同意-谈话呈批表";
            String falseRemarks="不同意-谈话呈批表";
            String paramNodeFlag="16";
            Map<String, Object> paramsMap=new HashMap<String, Object>();
            sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
            return success();
        } catch (Exception e) {
            return error();
        }
    }

    /**
     *
     * 谈话 修改的呈批表 提交
     * @param content
     * @param approver
     * @param clueId
     * @param isEdit
     * @param dispositionAttachment
     * @return
     */
    @PostMapping("/xgthcpcompleTask")
    @ResponseBody
    public AjaxResult xgthcpcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
        System.out.println("---------------------------上传路径："+templateUrl);
        Clue clue = clueService.selectClueById(clueId);
        String process="thcpProcess";
        String clueClassify="4";
        String type="谈话呈批";
        String handleUrl=prefix+"/spthcpform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        String nodeFlag="15";
        ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }

    /**
     * 谈话 笔录 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/thblcompleTask")
    @ResponseBody
    public AjaxResult thblcompleTask(int count,String content, String proposerName, String attachmentName, String formContent, String isok,
                                     String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
                                     String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
        Clue clue = clueService.selectClueById(clueId);
        String process="thblProcess";
        String type="谈话笔录";
        String handleUrl=prefix+"/spthblform";
        if ("true".equals(isEdit)){
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("formType", formType);
            mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
            mmap.put("formContent", dispositionAttachment.getFormContent());
            if (StringUtils.isNotEmpty(clue.getJbxxAh())){
                mmap.put("title",clue.getJbxxAh());
            }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
                mmap.put("title",dispositionAttachment.getAttachmentName());
            }
            mmap.put("type",type);
            mmap.put("clueId",clue.getClueId());
            mmap.put("dispositionAttachment",dispositionAttachment);
            mmap.put("content",content);
            mmap.put("clueClassify",clueClassify);
            mmap.put("handleUrl", handleUrl);
            nodeFlag="27";
            ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
            //表单修改
            ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
            return success("保存成功");
        }else {
            nodeFlag="17";
            AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
                    type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
            return ajaxResult;
        }
    }
    /**
     * 谈话笔录审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/spthblCompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult spthblCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {
            String resultType="";
            String truehandleUrl=prefix+"/thqkbgform";
            String falsehandleUrl=prefix+"/xgthblform";
            String trueRemarks="同意-谈话笔录";
            String falseRemarks="不同意-谈话笔录";
            String paramNodeFlag="18";
            Map<String, Object> paramsMap=new HashMap<String, Object>();
            sameAduditing( count,isok,resultType,formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
            return success();
        } catch (Exception e) {
            return error();
        }
    }

    /**
     *
     * 谈话 修改的记录 提交
     * @param content
     * @param approver
     * @param clueId
     * @param isEdit
     * @param dispositionAttachment
     * @return
     */
    @PostMapping("/xgthblcompleTask")
    @ResponseBody
    public AjaxResult xgthblcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
        System.out.println("---------------------------上传路径："+templateUrl);
        Clue clue = clueService.selectClueById(clueId);
        String process="thblProcess";
        String clueClassify="4";
        String type="谈话笔录";
        String handleUrl=prefix+"/spthblform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        String nodeFlag="17";
        ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }

    /**
     * 谈话 情况报告 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/thqkbgcompleTask")
    @ResponseBody
    public AjaxResult thqkbgcompleTask(int count,String content,String resultType, String proposerName, String attachmentName,
                                       String formContent, String isok, String taskId, String opinion, String pid, String approver,
                                       String clueId, String clueClassify, String nodeFlag, String formType,String isEdit,
                                       DispositionAttachment dispositionAttachment,String templateUrl) {

        Clue clue = clueService.selectClueById(clueId);
        String process="thqkbgProcess";
        String type="谈话情况报告";
        String handleUrl=prefix+"/spthqkbgform";
        if ("true".equals(isEdit)){
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("formType", formType);
            mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
            mmap.put("formContent", dispositionAttachment.getFormContent());
            if (StringUtils.isNotEmpty(clue.getJbxxAh())){
                mmap.put("title",clue.getJbxxAh());
            }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
                mmap.put("title",dispositionAttachment.getAttachmentName());
            }
            mmap.put("type",type);
            mmap.put("clueId",clue.getClueId());
            mmap.put("dispositionAttachment",dispositionAttachment);
            mmap.put("content",content);
            mmap.put("resultType",resultType);
            mmap.put("clueClassify",clueClassify);
            mmap.put("handleUrl", handleUrl);
            nodeFlag="27";
            ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
            //表单修改
            ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
            return success("保存成功");
        }else {
            nodeFlag="19";
            AjaxResult ajaxResult = ruTaskService.submitForm(count,resultType,process,clue,approver,content,isEdit,dispositionAttachment,
                    type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
            return ajaxResult;
        }
    }
    /**
     * 谈话 情况报告审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/spthqkbgCompleTask")
    @ResponseBody
    public AjaxResult spthqkbgCompleTask(int  count,String isok,String resultType,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {
            if ("true".equals(isok)) {
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xgthqkbgform";
                String trueRemarks="同意-谈话情况报告";
                String falseRemarks="不同意-谈话情况报告";
                String paramNodeFlag="20";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                paramsMap.put("resultType",resultType);
                paramsMap.put("count",count);
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                DispositionHandle dispositionHandle = new DispositionHandle();
                dispositionHandle.setParentId(clueId);
                dispositionHandle.setSuggestion(opinion);
                dispositionHandle.setDeptName("最后处置结果");
                dispositionHandle.setNodeId(taskId);
                dispositionHandle.setDeptId(StringUtils.getUUID());
                dispositionHandle.setNodeType(resultType);
                DispositionHandle findway=new DispositionHandle();
                findway.setClueId(clueId);
                findway.setWay("归类");
                if(dispositionHandleService.selectDeptList(findway).size()>0){
                    DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                    dispositionHandle.setCount(parentHandle.getCount());
                }else{
                    dispositionHandle.setCount(1);
                }
                dispositionHandle.setWay("归类");
                dispositionHandle.setClueId(clueId);
                dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
                dispositionHandle.setCreateTimes(DateUtils.getDate());
                if(!resultType.equals("6") && !resultType.equals("7")){
                    Clue clue = clueService.selectClueById(clueId);
                    if(resultType.equals("1")){
                        dispositionHandle.setSuggestion("了结澄清");
                        clue.setClueCzjd("29");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                    }else if(resultType.equals("2")){
                        dispositionHandle.setSuggestion("移送有关单位处理");
                        clue.setClueCzjd("30");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                    }else if(resultType.equals("3")){
                        dispositionHandle.setSuggestion("需要初步核实");
                        dispositionHandle.setNodeFlag("1");
                        clue.setClueCzjg("3");
                        clue.setClueCzjd("6");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "5", "提交", "提出-初步核实", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "6", "审核", "处置审批确认", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建初步核实呈批", "7", "提交", "初步核实呈批已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("初步核实呈批审核", "8", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("工作方案", "9", "提交", "工作方案已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("工作方案审核", "10", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("提交情况报告和处置意见", "11", "提交", "情况报告和处置意见已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("情况报告和处置意见审核", "12", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                    }else if(resultType.equals("4")){
                        dispositionHandle.setSuggestion("重新谈话");
                        clue.setClueCzjg("4");
                        clue.setClueCzjd("14");
                        dispositionHandle.setNodeFlag("1");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "13", "提交", "提出-谈话", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "14", "审核", "同意-谈话", clueId, "", resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建谈话呈批", "15", "提交", "谈话呈批已提交-等待审核", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("谈话呈批审核", "16", "审核", "同意-谈话呈批表", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("谈话笔录", "17", "提交", "谈话笔录已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("谈话笔录审核", "18", "审核", "同意-谈话笔录", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("提交情况报告和处置意见", "19", "提交", "谈话情况报告已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("情况报告和处置意见审核", "20", "审核", "同意-谈话情况报告", clueId, "", resultType, clue,  "0","",null);
                    }else if(resultType.equals("5")){
                        dispositionHandle.setSuggestion("重新函询");
                        clue.setClueCzjd("22");
                        clue.setClueCzjg("5");
                        dispositionHandle.setNodeFlag("1");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "21", "提交", "提出-函询", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "22", "审核", "同意-函询", clueId, "", resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建函询呈批", "23", "提交", "函询呈批已提交-等待审核", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("函询呈批审核", "24", "审核", "同意-函询呈批表", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("函询笔录", "25", "提交", "函询笔录已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("函询笔录审核", "26", "审核", "同意-函询笔录", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("提交情况报告和处置意见", "27", "提交", "函询情况报告已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("情况报告和处置意见审核", "28", "审核", "同意-函询情况报告", clueId, "", resultType, clue, "0","",null);
                    }
                    clueService.updateClue(clue);
                }
                return success();
            }else {
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xgthqkbgform";
                String trueRemarks="同意-谈话情况报告";
                String falseRemarks="不同意-谈话情况报告";
                String paramNodeFlag="20";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                return success();
            }
        } catch (Exception e) {
            return error();
        }
    }

    /**
     *
     * 谈话 修改的情况报告 提交
     * @param content
     * @param approver
     * @param clueId
     * @param isEdit
     * @param dispositionAttachment
     * @return
     */
    @PostMapping("/xgthqkbgcompleTask")
    @ResponseBody
    public AjaxResult xgthqkbgcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
        System.out.println("---------------------------上传路径："+templateUrl);
        Clue clue = clueService.selectClueById(clueId);
        String process="thqkbgProcess";
        String clueClassify="4";
        String type="谈话情况报告";
        String handleUrl=prefix+"/spthqkbgform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        String nodeFlag="19";
        ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }


    private void saveDispositionHandle(String DeptName, String NodeFlag, String way, String Result, String clueId, String content,
                                       String clueClassify, Clue clue,  String status,String createBy,String createTime) {
        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
        DispositionHandle dispositionHandle = new DispositionHandle();
        dispositionHandle.setParentId(clue.getClueId());
        dispositionHandle.setSuggestion(content);
        dispositionHandle.setDeptName(DeptName);
        dispositionHandle.setNodeType(clueClassify);
        dispositionHandle.setNodeFlag(NodeFlag);
        dispositionHandle.setNodeId(NodeFlag);
        dispositionHandle.setDeptId(StringUtils.getUUID());
        dispositionHandle.setClueId(clue.getClueId());
        dispositionHandle.setCreateBy(createBy);
        dispositionHandle.setWay(way);
        dispositionHandle.setCount(parentHandle.getCount());
        dispositionHandle.setResult(Result);
        dispositionHandle.setStatus(status);
        dispositionHandle.setCreateTimes(createTime);
        dispositionHandleService.insertDispositionHandle(dispositionHandle);
    }





    /**
     * 线索处置跳转 函询呈批
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/hxcpform")
    public String hxcpform(String clueId,String count, ModelMap modelMap){

        String returnUrl=prefix + "/hxcpform";
        String nodeFlag="23";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;

    }
    /**
     * 线索处置跳转 谈话笔录
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/hxblform")
    public String hxblform(String clueId,String count, ModelMap modelMap){
        String returnUrl=prefix + "/hxblform";
        String nodeFlag="25";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;
    }
    /**
     * 线索处置跳转 谈话情况报告
     *
     * @author wujing
     * @date 2019-1-17
     */
    @GetMapping("/hxqkbgform")
    public String hxqkbgform(String clueId,String count, ModelMap modelMap){

        String returnUrl=prefix + "/hxqkbgform";
        String nodeFlag="27";
        modelMap.put("count",count);
        returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
        return returnUrl;
    }



    /**
     * 函询 呈批表 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/hxcpcompleTask")
    @ResponseBody
    public AjaxResult hxcpcompleTask(int count,String content, String proposerName, String attachmentName, String formContent, String isok, String taskId,
                                     String opinion, String pid, String approver, String clueId, String clueClassify, String nodeFlag,
                                     String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {


        Clue clue = clueService.selectClueById(clueId);
        String process="hxcpProcess";
        String type="函询呈批";
        String handleUrl=prefix+"/sphxcpform";
        if ("true".equals(isEdit)){
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("formType", formType);
            mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
            mmap.put("formContent", dispositionAttachment.getFormContent());
            if (StringUtils.isNotEmpty(clue.getJbxxAh())){
                mmap.put("title",clue.getJbxxAh());
            }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
                mmap.put("title",dispositionAttachment.getAttachmentName());
            }
            mmap.put("type",type);
            mmap.put("clueId",clue.getClueId());
            mmap.put("dispositionAttachment",dispositionAttachment);
            mmap.put("content",content);
            mmap.put("clueClassify",clueClassify);
            mmap.put("handleUrl", handleUrl);
            nodeFlag="27";
            ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
            //表单修改
            ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
            return success("保存成功");
        }else {
            nodeFlag="23";
            AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
                    type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
            return ajaxResult;
        }
    }

    /**
     * 函询呈批表审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/sphxcpcompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult sphxcpcompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {
            String resultType="";
            String truehandleUrl=prefix+"/hxblform";
            String falsehandleUrl=prefix+"/xghxcpform";
            String trueRemarks="同意-函询呈批表";
            String falseRemarks="不同意-函询呈批表";
            String paramNodeFlag="24";
            Map<String, Object> paramsMap=new HashMap<String, Object>();
            sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }


    /**
     *
     * 函询 修改的呈批 提交
     * @param content
     * @param approver
     * @param clueId
     * @param isEdit
     * @param dispositionAttachment
     * @return
     */
    @PostMapping("/xghxcpcompleTask")
    @ResponseBody
    public AjaxResult xghxcpcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
        System.out.println("---------------------------上传路径："+templateUrl);
        Clue clue = clueService.selectClueById(clueId);
        String process="hxcpProcess";
        String clueClassify="5";
        String type="函询呈批";
        String handleUrl=prefix+"/sphxcpform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        String nodeFlag="23";
        ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }

    /**
     * 函询 笔录 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/hxblcompleTask")
    @ResponseBody
    public AjaxResult hxblcompleTask(int count,String content, String proposerName, String attachmentName, String formContent, String isok,
                                     String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
                                     String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {


        Clue clue = clueService.selectClueById(clueId);
        String process="hxblProcess";
        String type="函询笔录";
        String handleUrl=prefix+"/sphxblform";
        if ("true".equals(isEdit)){
            Map<String, Object> mmap = new HashMap<String, Object>();
            mmap.put("formType", formType);
            mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
            mmap.put("formContent", dispositionAttachment.getFormContent());
            if (StringUtils.isNotEmpty(clue.getJbxxAh())){
                mmap.put("title",clue.getJbxxAh());
            }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
                mmap.put("title",dispositionAttachment.getAttachmentName());
            }
            mmap.put("type",type);
            mmap.put("clueId",clue.getClueId());
            mmap.put("dispositionAttachment",dispositionAttachment);
            mmap.put("content",content);
            mmap.put("clueClassify",clueClassify);
            mmap.put("handleUrl", handleUrl);
            nodeFlag="27";
            ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
            //表单修改
            ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
            return success("保存成功");
        }else {
            nodeFlag="25";
            AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
                    type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
            return ajaxResult;
        }
    }
    /**
     * 函询笔录审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/sphxblCompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult sphxblCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {
            String resultType="";
            String truehandleUrl=prefix+"/hxqkbgform";
            String falsehandleUrl=prefix+"/xghxblform";
            String trueRemarks="同意-函询笔录";
            String falseRemarks="不同意-函询笔录";
            String paramNodeFlag="26";
            Map<String, Object> paramsMap=new HashMap<String, Object>();
            sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
            return success();
        } catch (Exception e) {
            return error();
        }
    }

    /**
     *
     * 函询 修改的笔录 提交
     * @param content
     * @param approver
     * @param clueId
     * @param isEdit
     * @param dispositionAttachment
     * @return
     */
    @PostMapping("/xghxblcompleTask")
    @ResponseBody
    public AjaxResult xghxblcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
        System.out.println("---------------------------上传路径："+templateUrl);
        Clue clue = clueService.selectClueById(clueId);
        String process="hxblProcess";
        String clueClassify="5";
        String type="函询笔录";
        String handleUrl=prefix+"/sphxblform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        String nodeFlag="25";
        ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }


    /**
     * 函询 情况报告 修改提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/xghxqkbgcompleTask")
    @ResponseBody
    public AjaxResult xghxqkbgcompleTask(int count,String content,String resultType, String proposerName, String attachmentName, String formContent,
                                       String isok, String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
                                       String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {

        Clue clue = clueService.selectClueById(clueId);
        String process="hxqkbgProcess";
        String type="函询情况报告及处置意见";
        String handleUrl=prefix+"/sphxqkbgform";
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("formType", formType);
        mmap.put("resultType", resultType);
        mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
        mmap.put("formContent", dispositionAttachment.getFormContent());
        if (StringUtils.isNotEmpty(clue.getJbxxAh())){
            mmap.put("title",clue.getJbxxAh());
        }else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
            mmap.put("title",dispositionAttachment.getAttachmentName());
        }
        mmap.put("type",type);
        mmap.put("clueId",clue.getClueId());
        mmap.put("dispositionAttachment",dispositionAttachment);
        mmap.put("content",content);
        mmap.put("clueClassify",clueClassify);
        mmap.put("handleUrl", handleUrl);
        nodeFlag="27";
        ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
        //表单修改
        ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
        return success("保存成功");
    }
    /**
     * 函询 情况报告 提交
     *
     * @param isok
     * @param taskId
     * @param opinion
     * @param pid
     * @param approver
     * @return
     */
    @PostMapping("/hxqkbgcompleTask")
    @ResponseBody
    public AjaxResult hxqkbgcompleTask(int count,String content,String resultType, String proposerName, String attachmentName, String formContent,
                                       String isok, String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
                                       String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {

        Clue clue = clueService.selectClueById(clueId);
        String process="hxqkbgProcess";
        String type="函询情况报告及处置意见";
        String handleUrl=prefix+"/sphxqkbgform";
        nodeFlag="27";
        AjaxResult ajaxResult = ruTaskService.submitForm(count,resultType,process,clue,approver,content,isEdit,
                dispositionAttachment,type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
        return ajaxResult;
    }
    /**
     * 函询 情况报告审核
     *
     * @author zhoushiji
     * @date 2019-1-26
     */
    @PostMapping("/sphxqkbgCompleTask")
    @ResponseBody
    @Transactional
    public AjaxResult sphxqkbgCompleTask(int  count,String isok,String resultType,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
        try {

            if ("true".equals(isok)){
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xghxqkbgform";
                String trueRemarks="同意-函询情况报告";
                String falseRemarks="不同意-函询情况报告";
                String paramNodeFlag="28";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                paramsMap.put("resultType",resultType);
                paramsMap.put("count",count);
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                DispositionHandle dispositionHandle = new DispositionHandle();
                dispositionHandle.setParentId(clueId);
                dispositionHandle.setSuggestion(opinion);
                dispositionHandle.setDeptName("最后处置结果");
                dispositionHandle.setNodeId(taskId);
                dispositionHandle.setDeptId(StringUtils.getUUID());
                dispositionHandle.setNodeType(resultType);
                DispositionHandle findway=new DispositionHandle();
                findway.setClueId(clueId);
                findway.setWay("归类");
                if(dispositionHandleService.selectDeptList(findway).size()>0){
                    DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                    dispositionHandle.setCount(parentHandle.getCount());
                }else{
                    dispositionHandle.setCount(1);
                }
                dispositionHandle.setWay("归类");
                dispositionHandle.setClueId(clueId);
                dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
                dispositionHandle.setCreateTimes(DateUtils.getDate());
                if(!resultType.equals("6") && !resultType.equals("7")){
                    Clue clue = clueService.selectClueById(clueId);
                    if(resultType.equals("1")){
                        dispositionHandle.setSuggestion("了结澄清");
                        clue.setClueCzjd("29");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                    }else if(resultType.equals("2")){
                        dispositionHandle.setSuggestion("移送有关单位处理");
                        clue.setClueCzjd("30");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                    }else if(resultType.equals("3")){
                        dispositionHandle.setSuggestion("需要初步核实");
                        dispositionHandle.setNodeFlag("1");
                        clue.setClueCzjg("3");
                        clue.setClueCzjd("6");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "5", "提交", "提出-初步核实", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "6", "审核", "处置审批确认", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建初步核实呈批", "7", "提交", "初步核实呈批已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("初步核实呈批审核", "8", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("工作方案", "9", "提交", "工作方案已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("工作方案审核", "10", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("提交情况报告和处置意见", "11", "提交", "情况报告和处置意见已提交-等待审核", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("情况报告和处置意见审核", "12", "审核", "", clueId, content, resultType, clue,  "0",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                    }else if(resultType.equals("4")){
                        dispositionHandle.setSuggestion("重新谈话");
                        clue.setClueCzjg("4");
                        clue.setClueCzjd("14");
                        dispositionHandle.setNodeFlag("1");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "13", "提交", "提出-谈话", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "14", "审核", "同意-谈话", clueId, "", resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建谈话呈批", "15", "提交", "谈话呈批已提交-等待审核", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("谈话呈批审核", "16", "审核", "同意-谈话呈批表", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("谈话笔录", "17", "提交", "谈话笔录已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("谈话笔录审核", "18", "审核", "同意-谈话笔录", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("提交情况报告和处置意见", "19", "提交", "谈话情况报告已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("情况报告和处置意见审核", "20", "审核", "同意-谈话情况报告", clueId, "", resultType, clue,  "0","",null);
                    }else if(resultType.equals("5")){
                        dispositionHandle.setSuggestion("重新函询");
                        clue.setClueCzjd("22");
                        clue.setClueCzjg("5");
                        dispositionHandle.setNodeFlag("1");
                        dispositionHandleService.insertDispositionHandle(dispositionHandle);
                        DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                        parentHandle.setCount(parentHandle.getCount()+1);
                        dispositionHandleService.updateDispositionHandle(parentHandle);
                        saveDispositionHandle("线索处置分类", "21", "提交", "提出-函询", clueId, content, resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("处置审批确认", "22", "审核", "同意-函询", clueId, "", resultType, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
                        saveDispositionHandle("创建函询呈批", "23", "提交", "函询呈批已提交-等待审核", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("函询呈批审核", "24", "审核", "同意-函询呈批表", clueId, "", resultType, clue, "0","",null);
                        saveDispositionHandle("函询笔录", "25", "提交", "函询笔录已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("函询笔录审核", "26", "审核", "同意-函询笔录", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("提交情况报告和处置意见", "27", "提交", "函询情况报告已提交-等待审核", clueId, "", resultType, clue,  "0","",null);
                        saveDispositionHandle("情况报告和处置意见审核", "28", "审核", "同意-函询情况报告", clueId, "", resultType, clue, "0","",null);
                    }

                    clueService.updateClue(clue);
                }
                return success();
            }else {
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xghxqkbgform";
                String trueRemarks="同意-函询情况报告";
                String falseRemarks="不同意-函询情况报告";
                String paramNodeFlag="28";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                paramsMap.put("resultType",resultType);
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                return success();
            }
        } catch (Exception e) {
            return error();
        }
    }


    private void sameAduditing(int count,String isok,String resultType, String formType, String content, String proposerName, String clueId, String taskId, String opinion, String pid, String approver, String clueClassify, DispositionAttachment dispositionAttachment, String truehandleUrl, String falsehandleUrl, String trueRemarks, String falseRemarks, String paramNodeFlag,Map<String, Object> paramsMap) {
        String nodeFlag;
        dispositionAttachment.setUpdateBy(ShiroUtils.getUser().getUserName());
        dispositionAttachment.setUpdateTime(new Date());
        dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment);
        Map<String, Object> mmap = new HashMap<String, Object>();
        if (isok.equals("true")) {
            mmap.put("handleUrl", truehandleUrl);
            mmap.put("resultType",resultType);
        } else {
            paramsMap.put("formType", formType);
            paramsMap.put("attchmentId", dispositionAttachment.getAttachmentId());
            paramsMap.put("formContent", dispositionAttachment.getFormContent());
            paramsMap.put("count", count);
            mmap.put("handleUrl", falsehandleUrl);
        }
        mmap.put("paramsMap", paramsMap);
        Map<String, Object> map = ruTaskService.compleTask(isok, content, proposerName, taskId, opinion, pid, approver, mmap);
        Task task = (Task) map.get("task");
        Clue clue = clueService.selectClueById(clueId);
        nodeFlag = paramNodeFlag;
        ruTaskService.checkProblem(count,isok, opinion, clueClassify, clue, nodeFlag, "审核", trueRemarks, falseRemarks);
    }
    private void sameSubmit(int count,String content, String attachmentName, String formContent, String taskId, String opinion, String pid, String approver, String clueId, String clueClassify, String formType, String isEdit, DispositionAttachment dispositionAttachment, String handleUrl, String remarks,String paramsNodeFlag,Map<String, Object> paramsMap) {
        String isok;
        String proposerName;
        String nodeFlag;//提交表单 默认同意
        isok = "true";
        proposerName = ShiroUtils.getUser().getUserName();
        String attchmentId = null;
        if (dispositionAttachment.getAttachmentId() == null) {
            attchmentId = StringUtils.getUUID();
        } else {
            attchmentId = dispositionAttachment.getAttachmentId();
        }
        Map<String, Object> mmap = new HashMap<String, Object>();
        mmap.put("handleUrl", handleUrl);
        paramsMap.put("thcontent", content);
        paramsMap.put("thproposerName", proposerName);
        paramsMap.put("attchmentId", attchmentId);
        paramsMap.put("formType", formType);
        paramsMap.put("nodeFlag", paramsNodeFlag);
        mmap.put("paramsMap", paramsMap);
        Map<String, Object> map = ruTaskService.compleTask(isok, content, proposerName, taskId, opinion, pid, approver, mmap);
        Task task = (Task) map.get("task");
        Clue clue = clueService.selectClueById(clueId);
        dispositionAttachment.setAttachmentId(attchmentId);
        dispositionAttachment.setClueId(clueId);
        dispositionAttachment.setHandleId(clueId);
        dispositionAttachment.setAttachmentName(attachmentName);
        dispositionAttachment.setFormContent(formContent);
        nodeFlag = paramsNodeFlag;
        ruTaskService.checkProblem(count,isok, content, clueClassify, clue, nodeFlag, "提交", remarks, "");
        dispositionAttachment.setNodeFlag(nodeFlag);
        if (isEdit.equals("true")) {
            dispositionAttachment.setUpdateBy(ShiroUtils.getUser().getUserName());
            dispositionAttachment.setUpdateTime(new Date());
            dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment);
        } else {
            dispositionAttachment.setReceiveBy(ShiroUtils.getUser().getUserName());
            dispositionAttachment.setReceiveTime(new Date());
            dispositionAttachmentService.insertDispositionAttachment(dispositionAttachment);
        }
    }
}
