package com.datcent.project.system.adress.controller;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.system.adress.domain.Adress;
import com.datcent.project.system.adress.service.IAdressService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通讯录 信息操作处理
 * 
 * @author datcent
 * @date 2019-02-15
 */
@Controller
@RequestMapping("/system/adress")
public class AdressController extends BaseController
{
    private String prefix = "system/adress";
	
	@Autowired
	private IAdressService adressService;

	@Autowired
	private IClueService clueService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private ICourtOrganService iCourtOrganService;
	
	@RequiresPermissions("system:adress:view")
	@GetMapping()
	public String adress(ModelMap mmap)
	{
		List<Map> secondList = clueService.queryAllCbt();
		mmap.put("cbtList", secondList);
	    return prefix + "/adress";
	}


	@GetMapping("/personAdress")
	public String personAdress(ModelMap mmap)
	{
		List<Map> secondList = clueService.queryAllCbt();
		mmap.put("cbtList", secondList);
		CourtOrgan courtOrgan=new CourtOrgan();
		courtOrgan.setDeptType("1");
		courtOrgan.setStatus("0");
		List<CourtOrgan> thirdList = iCourtOrganService.selectDeptList(courtOrgan);
		mmap.put("deptList", thirdList);
		return prefix + "/personAdress";
	}

	/**
	 * 查询通讯录列表
	 */
	@RequiresPermissions("system:adress:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Adress adress)
	{
		startPage();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String loginName=ShiroUtils.getLoginName();

		adress.setCreateBy(loginName);

		if (StringUtils.isNotEmpty(adress.getParams())) {
			if (StringUtils.isNotEmpty(adress.getParams().get("createTime").toString())) {
				adress.setCreateTimes(adress.getParams().get("createTime").toString());
			}
		}
		adress.setDeleteFlag("0");
        List<Adress> list = adressService.selectAdressList(adress);

		return getDataTable(list);
	}
	
	/**
	 * 新增通讯录
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		List<Map> secondList = clueService.queryAllCbt();
		CourtOrgan courtOrgan=new CourtOrgan();
		courtOrgan.setDeptType("1");
		courtOrgan.setStatus("0");
		List<CourtOrgan> thirdList = iCourtOrganService.selectDeptList(courtOrgan);
		mmap.put("deptList", thirdList);
		mmap.put("cbtList", secondList);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存通讯录
	 */

	@Log(title = "通讯录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave (Adress adress)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String jbfyName = clueService.queryDeptNameByCid(adress.getJbfyId());
		adress.setDept(jbfyName);

		Date date=new Date();

         String loginName= ShiroUtils.getLoginName();
         adress.setCreateBy(loginName);

		 adress.setCreateTimes(sdf.format(date));


		return toAjax(adressService.insertAdress(adress));
	}

	/**
	 * 修改通讯录
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Adress adress = adressService.selectAdressById(id);
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
		mmap.put("adress", adress);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存通讯录
	 */

	@Log(title = "通讯录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Adress adress)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date=new Date();
		String jbfyName = clueService.queryDeptNameByCid(adress.getJbfyId());
		adress.setDept(jbfyName);
		adress.setUpdateTimes(sdf.format(date));
		return toAjax(adressService.updateAdress(adress));
	}
	
	/**
	 * 删除通讯录
	 */

	@Log(title = "通讯录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(adressService.deleteAdressByIds(ids));
	}



    /**
     * 查询通讯录列表
     */
    @RequiresPermissions("system:adress:allList")
    @PostMapping("/allList")
    @ResponseBody
    public TableDataInfo allList(Adress adress)
    {
        startPage();

        List<Map> list = adressService.selectAllAdressList(adress);
        return getDataTable(list);
    }


	/**
	 * 查询通讯录列表
	 */

	@PostMapping("/allSecondList")
	@ResponseBody
	public TableDataInfo allSecondList(Adress adress)
	{
		startPage();
		List<Map> list = adressService.selectAllAdressList(adress);
		if (list.size()>=5){
			List secondList = list.subList(0, 5);
			return getDataTable(secondList);
		}else{
			return getDataTable(list);
		}
	}



	@RequestMapping("/releaseAdress")
	@ResponseBody
	public AjaxResult releaseAdress(String id){
		try {

			if (StringUtils.isNotEmpty(id)) {
				int ids = Integer.parseInt(id);
				Adress adress = adressService.selectAdressById(ids);
				adress.setStatus("1");
				adressService.updateAdress(adress);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return error();
		}
		return success();
	}
}
