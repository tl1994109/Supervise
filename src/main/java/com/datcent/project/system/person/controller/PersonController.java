package com.datcent.project.system.person.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.poi.ExcelUtil;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.post.domain.Post;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.service.IRoleService;
import com.datcent.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.post.service.IPostService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @描述: 人员  信息操作处理
 * @创建人: zhou_shiji
 * @创建时间: 2018年10月19日下午3:15:43
 */
@Controller
@RequestMapping("/system/person")
public class PersonController extends BaseController
{
    private String prefix = "system/person";
    
    @Autowired
    private IPostService postService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private IPersonService personService;
	
	@RequiresPermissions("system:person:view")
	@GetMapping()
	public String person( ModelMap mmap)
	{
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
	    return prefix + "/person";
	}
	
	/**
	 * 查询人员 列表
	 */
	@RequiresPermissions("system:person:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Person person)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Person> list = personService.selectPersonList(person,pageDomain);
		for (Person per : list) {
			List<Post> posts = postService.selectPostsByPersonId(Long.parseLong(per.getPersonId()));
			per.setPostList(posts);
			String deptId = per.getDeptId();
			if (StringUtils.isNotEmpty(deptId)){
				Dept dept = deptService.selectDeptById(Long.valueOf(deptId));
				Dept parent = deptService.selectDeptById(dept.getParentId());
				if (StringUtils.isNull(parent)){
					per.setParentDeptName("暂无单位");
					per.setDeptName(dept.getDeptName());
				}else{
					per.setParentDeptName(parent.getDeptName());
					per.setDeptName(dept.getDeptName());
				}
			}else {
				per.setParentDeptName("暂无单位");
				per.setDeptName("暂无部门");
			}
		}
		return getDataTable(list);
	}
	
	/**
	 * 新增人员 
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		mmap.put("posts", postService.selectPostAll());
		Person person = new Person();
		person.setDeptId("123");

		Person person1 = new Person("456");

	    return prefix + "/add";
	}
	
	/**
	 * 新增保存人员 
	 */
	@RequiresPermissions("system:person:add")
	@Log(title = "人员 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Person person)
	{
		return toAjax(personService.insertPerson(person));
	}

	/**
	 * 修改人员 
	 */
	@GetMapping("/edit/{personId}")
	public String edit(@PathVariable("personId") String personId, ModelMap mmap)
	{
		Person person = personService.selectPersonById(personId);
		mmap.put("person", person);
		mmap.put("posts", postService.selectPostsByPersonId(Long.parseLong(personId)));
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存人员 
	 */
	@RequiresPermissions("system:person:edit")
	@Log(title = "人员 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	@Transactional()
	public AjaxResult  editSave(Person person)
	{		
		return toAjax(personService.updatePerson(person));
	}
	
	/**
	 * 删除人员 
	 */
	@RequiresPermissions("system:person:remove")
	@Log(title = "人员", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		try
        {
            return toAjax(personService.deletePersonByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
	}



	@Log(title = "人员管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:person:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(Person person)
	{
		List<Person> personList = personService.selectPersonList(person,new PageDomain());
		ExcelUtil<Person> util = new ExcelUtil<Person>(Person.class);
		return util.exportExcel(personList, "人员信息");
	}



	@Log(title = "人员管理", businessType = BusinessType.IMPORT)
	@RequiresPermissions("system:person:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
	{
		ExcelUtil<Person> util = new ExcelUtil<Person>(Person.class);
		List<Person> personList = util.importExcel(file.getInputStream());
		String message = personService.importPerson(personList, updateSupport);
		return AjaxResult.success(message);
	}

	@RequiresPermissions("system:person:view")
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate()
	{
		ExcelUtil<Person> util = new ExcelUtil<Person>(Person.class);
		return util.importTemplateExcel("用户数据");
	}
	
}
