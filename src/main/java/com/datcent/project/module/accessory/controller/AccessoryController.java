package com.datcent.project.module.accessory.controller;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.appraise.domain.Appraise;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.service.IJournalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
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
import com.datcent.project.module.accessory.domain.Accessory;
import com.datcent.project.module.accessory.service.IAccessoryService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 任务附件 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-22
 */
@Controller
@RequestMapping("/module/accessory")
public class AccessoryController extends BaseController
{
    private String prefix = "module/accessory";
	
	@Autowired
	private IAccessoryService accessoryService;

	@Autowired
	private IJournalService journalService;

	@Value("${datcent.profile}")
	private String profile;
	
	@RequiresPermissions("module:accessory:view")
	@GetMapping()
	public String accessory()
	{
	    return prefix + "/accessory";
	}
	
	/**
	 * 查询任务附件列表
	 */
	@RequiresPermissions("module:accessory:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Accessory accessory)
	{
		startPage();
        List<Accessory> list = accessoryService.selectAccessoryList(accessory);
		return getDataTable(list);
	}
	
	/**
	 * 新增任务附件
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存任务附件
	 */
	@RequiresPermissions("module:accessory:add")
	@Log(title = "任务附件", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Accessory accessory)
	{		
		return toAjax(accessoryService.insertAccessory(accessory));
	}

	/**
	 * 修改任务附件
	 */
	@GetMapping("/edit/{accessoryId}")
	public String edit(@PathVariable("accessoryId") String accessoryId, ModelMap mmap)
	{
		Accessory accessory = accessoryService.selectAccessoryById(accessoryId);
		mmap.put("accessory", accessory);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存任务附件
	 */
	@RequiresPermissions("module:accessory:edit")
	@Log(title = "任务附件", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Accessory accessory)
	{		
		return toAjax(accessoryService.updateAccessory(accessory));
	}
	
	/**
	 * 删除任务附件
	 */
	//@RequiresPermissions("module:accessory:remove")
	@Log(title = "任务附件", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids,String taskId) throws ParseException {
		return toAjax(accessoryService.deleteAccessoryByIds(ids,taskId));
	}

	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult upload(MultipartFile file,String taskId,String isFlag) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException, ParseException {
		String fileName = file.getOriginalFilename();
		String prefix = fileName.substring(fileName.lastIndexOf("."));
		String uploadName = FileUploadUtils.upload(profile,file,prefix);
		if(StringUtils.isNotEmpty(uploadName)){
			AjaxResult ajaxResult= new AjaxResult();
			Accessory accessory = new Accessory();
			String uuid = StringUtils.getUUID();
			if(StringUtils.isNotEmpty(taskId)){
				accessory.setAccessoryTaskid(taskId);
				Journal journal = new Journal();
				journal.setJournalTaskid(taskId);
				journal.setJournalCreateby(ShiroUtils.getUserId().toString());
				journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
				journal.setJournalId(StringUtils.getUUID());
				journal.setJournalContent("上传了附件");
				journalService.insertJournal(journal);
			}
			if(StringUtils.isEmpty(isFlag)){
				accessory.setAccessoryType("0");
			}else{
				accessory.setAccessoryType("1");
			}
			accessoryService.insertAccessory(accessory);
			ajaxResult.put("code",0);
			ajaxResult.put("acId",uuid);
			return ajaxResult;
		}else{
			return error();
		}
	}

	@GetMapping("/downLoad")
	public HttpServletResponse download(String path,String oldName,HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			//String filename = file.getName();
			// 取得文件的后缀名。
			//String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(oldName.getBytes(),"iso-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
