package com.datcent.project.module.template.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.DocConvertPdf;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.accessory.domain.Accessory;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import com.datcent.project.module.knowledgeClick.service.IKnowledgeClickService;
import com.datcent.project.module.noticeClick.domain.NoticeClick;
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
import com.datcent.project.module.template.domain.Template;
import com.datcent.project.module.template.service.ITemplateService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板管理 信息操作处理
 * 
 * @author datcent
 * @date 2019-02-15
 */
@Controller
@RequestMapping("/module/template")
public class TemplateController extends BaseController
{
    private String prefix = "module/template";
	
	@Autowired
	private ITemplateService templateService;

	@Autowired
	private IKnowledgeClickService knowledgeClickService;

	@Value("${datcent.profile}")
	private String profile;

	@RequiresPermissions("module:template:view")
	@GetMapping()
	public String template(ModelMap map,Template template)
	{
		template.setParentId("0");
		List<Template> templateList = templateService.selectTemplateList(template);
		if(templateList.size()==0){
			map.put("pid","");
		}else{
			map.put("pid",templateList.get(0).getTemplateId());
		}
		return prefix + "/template";
	}
	
	/**
	 * 查询模板管理列表
	 */
	@RequiresPermissions("module:template:list")
	@GetMapping("/list")
	@ResponseBody
	public List<Template> list(Template template)
	{
        List<Template> list = templateService.selectTemplateList(template);
		return list;
	}
	
	/**
	 * 新增模板管理
	 */
	@GetMapping("/add/{id}")
	public String add(@PathVariable("id") String id, ModelMap mmap)
	{
		Template template = templateService.selectTemplateById(id);
		Template selTemp = new Template();
		selTemp.setParentId("0");
		List<Template> templateList = templateService.selectTemplateList(selTemp);
		mmap.put("temp",template);
		mmap.put("grandparent",templateList.size()>0?templateList.get(0).getTemplateId():"");
		if(template == null || template.getTemplateId().equals(templateList.get(0).getTemplateId())){
			return prefix + "/add_one";
		}else{
			return prefix + "/add";
		}
	}
	
	/**
	 * 新增保存模板管理
	 */
	@RequiresPermissions("module:template:add")
	@Log(title = "模板管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Template template,MultipartFile file) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		int i = templateService.insertTemplate(template,file,profile);
		if(i>0){
			Template temp = new Template();
			temp.setLevel("0");
			List<Template> templates = templateService.selectTemplateList(temp);
			ajaxResult.put("pId",templates.size()<=0?"":templates.get(0).getTemplateId());
			ajaxResult.put("msg", "操作成功");
			ajaxResult.put("code", 0);
		}else{
			ajaxResult.put("pId","");
			ajaxResult.put("msg", "操作失败");
			ajaxResult.put("code", 1);
		}
		return ajaxResult;
	}

	/**
	 * 修改模板管理
	 */
	@GetMapping("/edit/{templateId}")
	public String edit(@PathVariable("templateId") String templateId, ModelMap mmap)
	{
		Template template = templateService.selectTemplateById(templateId);
		Template parentTemplate = templateService.selectTemplateById(template.getParentId());
		template.setParentName(parentTemplate==null?"":parentTemplate.getTemplateName());
        Template selTemp = new Template();
        selTemp.setParentId("0");
        List<Template> templateList = templateService.selectTemplateList(selTemp);
        mmap.put("grandparent",templateList.size()>0?templateList.get(0).getTemplateId():"");
		mmap.put("temp", template);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存模板管理
	 */
	@RequiresPermissions("module:template:edit")
	@Log(title = "模板管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Template template,MultipartFile file) throws Exception {
		Template temp = templateService.selectTemplateById(template.getTemplateId());
		int i = templateService.updateTemplate(template,file,profile);
		if(i>0 && file!=null && file.isEmpty()){
			File re_pdffile = new File(profile+temp.getTemplateUrl());
			File re_docfile = new File(profile+temp.getTemplateDocUrl());
			if(re_docfile.exists() && re_docfile.isFile()){
				re_docfile.delete();
			}
			if(re_pdffile.exists() && re_pdffile.isFile()){
				re_pdffile.delete();
			}

		}
		return toAjax(i);
	}
	
	/**
	 * 删除模板管理
	 */
	@RequiresPermissions("module:template:remove")
	@Log(title = "模板管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		Template template = new Template();
		template.setParentId(ids);
		List<Template> templateList = templateService.selectTemplateList(template);
		if(templateList.size()>0){
			return error();
		}else{
			AjaxResult ajaxResult = new AjaxResult();
			int i = templateService.deleteTemplateByIds(ids);
			if(i>0){
				Template temp = new Template();
				temp.setLevel("0");
				List<Template> templates = templateService.selectTemplateList(temp);
				ajaxResult.put("pId",templates.size()<=0?"":templates.get(0).getTemplateId());
				ajaxResult.put("msg", "操作成功");
				ajaxResult.put("code", 0);
			}else{
				ajaxResult.put("pId","");
				ajaxResult.put("msg", "操作失败");
				ajaxResult.put("code", 1);
			}
			return ajaxResult;
		}
	}

	/**
	 * 选择部门树
	 */
	@GetMapping("/selectTempTree/{tempId}")
	public String selectDeptTree(@PathVariable("tempId") String templateId, ModelMap mmap)
	{
		Template template = templateService.selectTemplateById(templateId);
		mmap.put("temp", templateService.selectTemplateById(templateId));
		return prefix + "/tree";
	}

	/**
	 * 加载部门列表树
	 */
	@GetMapping("/treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData()
	{
		List<Map<String, Object>> tree = templateService.selectTempTree();
		return tree;
	}

	@PostMapping("selectTemplateByTempId")
	@ResponseBody
	public Template selectTemplateByTempId(String tempId){
		return templateService.selectTemplateById(tempId);
	}

	@GetMapping("/showview/{templateId}")
	@ResponseBody
	@RequiresPermissions("module:template:preview")
	public void showview(@PathVariable("templateId") String templateId, HttpServletRequest request, HttpServletResponse response){
		Template template = templateService.selectTemplateById(templateId);
		File file = new File(profile+template.getTemplateUrl());
		if (file.exists()){
			byte[] data = null;
			try {
				FileInputStream input = new FileInputStream(file);
				data = new byte[input.available()];
				input.read(data);
				response.getOutputStream().write(data);
				input.close();
			} catch (Exception e) {
				System.out.println("pdf文件处理异常：" + e);
			}

		}else{
			return;
		}
	}

	@PostMapping("/main")
	@RequiresPermissions("module:template:main")
	@ResponseBody
	public List<Template> main(){
		return templateService.selectSecondTree();
	}

	@GetMapping("/threeTree/{id}")
	public String threeTree(@PathVariable("id")String id,ModelMap map){
		map.put("tempId",id);
		return prefix+"/threeTree";
	}

	@PostMapping("/threeList")
	@ResponseBody
	public TableDataInfo threeList(String id)
	{
		startPage();
		Template template = new Template();
		template.setParentId(id);
		List<Template> list = templateService.selectTemplateList(template);
		return getDataTable(list);
	}
}
