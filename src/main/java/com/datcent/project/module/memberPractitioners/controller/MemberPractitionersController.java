package com.datcent.project.module.memberPractitioners.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
import com.datcent.project.module.member.domain.Member;
import com.datcent.project.module.member.service.IMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.memberPractitioners.domain.MemberPractitioners;
import com.datcent.project.module.memberPractitioners.service.IMemberPractitionersService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 配偶子女从业情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/memberPractitioners")
public class MemberPractitionersController extends BaseController
{
    private String prefix = "module/memberPractitioners";
	
	@Autowired
	private IMemberPractitionersService memberPractitionersService;



	@Autowired
	private IBasicinformationService basicinformationService;
	@Autowired
	private IMemberService memberService;
	
	@RequiresPermissions("module:memberPractitioners:view")
	@GetMapping()
	public String memberPractitioners()
	{
	    return prefix + "/memberPractitioners";
	}
	
	/**
	 * 查询配偶子女从业情况列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MemberPractitioners memberPractitioners)
	{
		startPage();
        List<MemberPractitioners> list = memberPractitionersService.selectMemberPractitionersList(memberPractitioners);
		return getDataTable(list);
	}
	
	/**
	 * 新增配偶子女从业情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存配偶子女从业情况
	 */
	@Log(title = "配偶子女从业情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MemberPractitioners memberPractitioners, String memberpra, String poznJtcy, String jbqkId, Basicinformation basic)
	{
		AjaxResult json=new AjaxResult();
		boolean flag1=false;
		boolean flag2=false;

		//家庭成员
		int count=0;
		List<Member> members = JSONObject.parseArray(poznJtcy, Member.class);
		for (Member member : members) {
			int row=memberService.updateMember(member);
			count+=row;
		}
		if (count>0){
			flag1=true;
		}

		basic.setIsEdit("9");
		basicinformationService.updateBasicinformation(basic);

		MemberPractitioners memberP = JSONObject.parseObject(memberpra, MemberPractitioners.class);


		if(StringUtils.isEmpty(memberP.getPozncyqkId())){
			memberP.setPozncyqkId(StringUtils.getUUID());
			memberPractitionersService.insertMemberPractitioners(memberP);
		}
		int row =memberPractitionersService.updateMemberPractitioners(memberP);
        count+=row;
		if (count>0){
			json.put("code",200);
			json.put("mgs","保存成功！");
		}else {
			json.put("code",500);
			json.put("mgs","保存失败！");
		}
		return json;
	}

	/**
	 * 修改配偶子女从业情况
	 */
	@GetMapping("/edit/{pozncyqkId}")
	public String edit(@PathVariable("pozncyqkId") String pozncyqkId, ModelMap mmap)
	{
		MemberPractitioners memberPractitioners = memberPractitionersService.selectMemberPractitionersById(pozncyqkId);
		mmap.put("memberPractitioners", memberPractitioners);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存配偶子女从业情况
	 */
	@Log(title = "配偶子女从业情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MemberPractitioners memberPractitioners)
	{		
		return toAjax(memberPractitionersService.updateMemberPractitioners(memberPractitioners));
	}
	
	/**
	 * 删除配偶子女从业情况
	 */
	@Log(title = "配偶子女从业情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(memberPractitionersService.deleteMemberPractitionersByIds(ids));
	}
	
}
