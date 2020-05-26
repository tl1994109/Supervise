package com.datcent.project.module.dispositionAttachment.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datcent.common.utils.CustomFileUtil;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.file.FileUtils;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.config.Global;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工作记录附件 信息操作处理
 *
 * @author datcent
 * @date 2019-01-25
 */
@Controller
@RequestMapping("/module/dispositionAttachment")
public class DispositionAttachmentController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(DispositionAttachmentController.class);

    private String prefix = "module/dispositionAttachment";


    @Value("${datcent.profile}")
    private String profile;

    @Autowired
    private IDispositionAttachmentService dispositionAttachmentService;

    @RequiresPermissions("module:dispositionAttachment:view")
    @GetMapping()
    public String dispositionAttachment(DispositionAttachment dispositionAttachment, ModelMap mmap) {
        mmap.put("clueId", dispositionAttachment.getClueId());
        return "module/problemdisposal/dispositionAttachment";
    }

    /**
     * 查询有附件URL的列表
     */
    @RequiresPermissions("module:dispositionAttachment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DispositionAttachment dispositionAttachment) {
        startPage();
        List<DispositionAttachment> list = dispositionAttachmentService.selectFile(dispositionAttachment);
        return getDataTable(list);
    }

    @GetMapping("/uploadFileView")
    public String uploadFileView(DispositionAttachment dispositionAttachment, ModelMap mmap) {
        mmap.put("dispositionAttachment", dispositionAttachment);
        return "module/problemdisposal/uploadFileView";
    }

    /**
     * 处置过程文档下载
     */
    @Log(title = "处置过程文档下载", businessType = BusinessType.DOWNLOAD)
    @GetMapping("/downloadAll")
    public void remove(String ids, HttpServletResponse response, HttpServletRequest request) throws Exception{
        String[] personIds = ids.split(",");
        List<File> files = new ArrayList<File>();
        if (personIds.length > 0) {
            for (String item : personIds) {
                DispositionAttachment dispositionAttachment=dispositionAttachmentService.selectDispositionAttachmentById(item);
                files.add(new File(profile+dispositionAttachment.getAttachmentUrl()));
            }
        }
        CustomFileUtil.downLoadZipFiles("问题处置附件.zip", files, request, response);
    }

    /**
     * 附件上传
     */
    @Log(title = "处置附件上传", businessType = BusinessType.INSERT)
    @PostMapping("/uploadFile")
    @ResponseBody
    @Transactional
    public AjaxResult updateAvatar(DispositionAttachment dispositionAttachment, MultipartFile[] file) {
        try {
            if (file.length > 0) {
                for (MultipartFile f : file) {
                    String fileName = f.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    String uploadUrl = FileUploadUtils.upload(profile + "clueFile/", f, prefix);
                    dispositionAttachment.setAttachmentUrl("clueFile/" + uploadUrl);
                    dispositionAttachment.setAttachmentName(fileName);
                    dispositionAttachment.setAttachmentId(StringUtils.getUUID());
                    dispositionAttachment.setReportBy(ShiroUtils.getUser().getUserName());
                    dispositionAttachment.setReportTime(new Date());
                    dispositionAttachmentService.insertDispositionAttachment(dispositionAttachment);
                }
                return success();
            } else {
                return error();
            }

        } catch (Exception e) {
            log.error("附件上传失败！", e);
            return error(e.getMessage());
        }
    }

    @Log(title = "处置过程文档下载", businessType = BusinessType.DOWNLOAD)
    @GetMapping("/downLoad")
    public void download(String path, String oldName, HttpServletResponse response, HttpServletRequest request) {
        try
        {
            String realFileName = new File(oldName).getName();
            String filePath = profile + path;
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + HttpUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

//
//    @Log(title = "处置过程文档下载", businessType = BusinessType.DOWNLOAD)
//    @GetMapping("/downLoad")
//    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
//    {
//        try
//        {
//            if (!FileUtils.isValidFilename(fileName))
//            {
//                throw new Exception(StringUtils.format(" 文件名称({})非法，不允许下载。 ", fileName));
//            }
//            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
//            String filePath = Global.getDownloadPath() + fileName;
//
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/form-data");
//            response.setHeader("Content-Disposition",
//                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
//            FileUtils.writeBytes(filePath, response.getOutputStream());
//            if (delete)
//            {
//                FileUtils.deleteFile(filePath);
//            }
//        }
//        catch (Exception e)
//        {
//            log.error("下载文件失败", e);
//        }
//    }
//
//    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
//    {
//        final String agent = request.getHeader("USER-AGENT");
//        String filename = fileName;
//        if (agent.contains("MSIE"))
//        {
//            // IE浏览器
//            filename = URLEncoder.encode(filename, "utf-8");
//            filename = filename.replace("+", " ");
//        }
//        else if (agent.contains("Firefox"))
//        {
//            // 火狐浏览器
//            filename = new String(fileName.getBytes(), "ISO8859-1");
//        }
//        else if (agent.contains("Chrome"))
//        {
//            // google浏览器
//            filename = URLEncoder.encode(filename, "utf-8");
//        }
//        else
//        {
//            // 其它浏览器
//            filename = URLEncoder.encode(filename, "utf-8");
//        }
//        return filename;
//
//    }

    /**
     * 新增工作记录附件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存工作记录附件
     */
    @RequiresPermissions("module:dispositionAttachment:add")
    @Log(title = "工作记录附件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DispositionAttachment dispositionAttachment) {
        return toAjax(dispositionAttachmentService.insertDispositionAttachment(dispositionAttachment));
    }


    /**
     * 修改保存工作记录附件
     */
    @RequiresPermissions("module:dispositionAttachment:edit")
    @Log(title = "工作记录附件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DispositionAttachment dispositionAttachment) {
        return toAjax(dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment));
    }


    /**
     * 修改问题处置单
     */
    @GetMapping("/select/{attachmentId}")
    @ResponseBody
    public DispositionAttachment select(@PathVariable("attachmentId") String attachmentId, ModelMap mmap) {
        DispositionAttachment dispositionAttachment = dispositionAttachmentService.selectDispositionAttachmentById(attachmentId);
        return dispositionAttachment;
    }

}
