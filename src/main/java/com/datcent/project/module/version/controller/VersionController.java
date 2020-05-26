package com.datcent.project.module.version.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.project.module.version.service.IVersionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import com.datcent.project.module.version.domain.Version;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目版本管理 信息操作处理
 * 
 * @author datcent
 * @date 2019-05-27
 */
@Controller
@RequestMapping("/module/version")
public class VersionController extends BaseController
{
    private String prefix = "module/version";


	@Value("${datcent.profile}")
	private String profile;

	@Autowired
	private IVersionService versionService;
	
	@RequiresPermissions("module:version:view")
	@GetMapping()
	public String version()
	{

	    return prefix + "/version";
	}
	
	/**
	 * 查询项目版本管理列表
	 */

	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Version version)
	{
		startPage();
        List<Version> list = versionService.selectVersionList(version);
		return getDataTable(list);
	}
	
	/**
	 * 新增项目版本管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存项目版本管理
	 */

	@Log(title = "项目版本管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Version version, String versioncreattime1, MultipartFile file)
	{
		Date versionCreat = DateUtils.stringToDate(versioncreattime1);
		version.setVersionCreattime(versionCreat);
		String filename="";
		if(!file.isEmpty()){
				try {
				int fileNamelength = file.getOriginalFilename().length();
				if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
				{
					return error("文件名过长！");
				}

					String time = DateUtils.dateTimeNow();
					filename = time+file.getOriginalFilename().replace("_", " ");
//				String extension =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//				filename= new Md5Hash(filename + System.nanoTime()).toHex().toString() + extension;
				File desc = new File(profile+"version/" + filename);
				if (!desc.getParentFile().exists())
				{
					desc.getParentFile().mkdirs();
				}
				if (!desc.exists()){
					desc.createNewFile();
				}
					file.transferTo(desc);
				} catch (IOException e) {
					return error("文件上传失败");
				}
			}
		version.setVersionPath(profile+"version/" + filename);
		version.setVersionUploadtime(DateUtils.getNowDate());
		int i = versionService.insertVersion(version);
		if (i>0){
			return success();
		}else {
			return error("保存失败！");
		}
	}

	/**
	 * 修改项目版本管理
	 */
	@GetMapping("/edit/{versionId}")
	public String edit(@PathVariable("versionId") Integer versionId, ModelMap mmap)
	{
		Version version = versionService.selectVersionById(versionId);
		mmap.put("version", version);
		File file =new File( version.getVersionPath().trim());
		if(file.exists()){
			String fileName = file.getName();
			mmap.put("fileName", fileName);
		}
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存项目版本管理
	 */

	@Log(title = "项目版本管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Version version)
	{		
		return toAjax(versionService.updateVersion(version));
	}
	
	/**
	 * 删除项目版本管理
	 */

	@Log(title = "项目版本管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(versionService.deleteVersionByIds(ids));
	}
	
}
