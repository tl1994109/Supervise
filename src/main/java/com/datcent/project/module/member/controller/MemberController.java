package com.datcent.project.module.member.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.serving.domain.Serving;
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
import com.datcent.project.module.member.domain.Member;
import com.datcent.project.module.member.service.IMemberService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 家庭成员情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/member")
public class MemberController extends BaseController
{
    private String prefix = "module/member";
	
	@Autowired
	private IMemberService memberService;

	@GetMapping()
	public String member()
	{
	    return prefix + "/member";
	}
	
	/**
	 * 查询家庭成员情况列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Member member)
	{
		startPage();
        List<Member> list = memberService.selectMemberList(member);
		return getDataTable(list);
	}
	
	/**
	 * 新增家庭成员情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存家庭成员情况
	 */
	@Log(title = "家庭成员情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(String jbqkJtcy,String jbqkId)
	{


		AjaxResult json = new AjaxResult();

		Member memb=new Member();
		memb.setBasicinforId(jbqkId);
		List<Member> memberList = memberService.selectMemberList(memb);
		if (memberList.size()>0){
			json.put("code",500);
			json.put("msg","信息已录入,请勿重复提交！");
		}else{
			List<Member> members = JSONObject.parseArray(jbqkJtcy, Member.class);
			int count=0;
			for (Member member : members) {
				if (StringUtils.isNotEmpty(member.getName())
						||StringUtils.isNotEmpty(member.getRelationship())
						||StringUtils.isNotEmpty(member.getBirthday())
						||StringUtils.isNotEmpty(member.getPoliticalOutlook())
						||StringUtils.isNotEmpty(member.getUnitPost())){
					member.setId(StringUtils.getUUID());
					member.setBasicinforId(jbqkId);
					int row = memberService.insertMember(member);
					count+=row;
				}
			}
			if (count>0){
				json.put("code",200);
				json.put("msg","保存成功！");
			}
		}
		return json;
	}

	/**
	 * 修改家庭成员情况
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		Member member = memberService.selectMemberById(id);
		mmap.put("member", member);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存家庭成员情况
	 */
	@Log(title = "家庭成员情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(String poznJtcy,String jbqkId)
	{
		AjaxResult json = new AjaxResult();
		List<Member> members = JSONObject.parseArray(poznJtcy, Member.class);
		int count=0;
		for (Member member : members) {
			if (StringUtils.isNotEmpty(member.getId())){
				int rows=memberService.updateMember(member);
				count+=rows;
			}else {
				if (StringUtils.isNotEmpty(member.getName())
						||StringUtils.isNotEmpty(member.getRelationship())
						||StringUtils.isNotEmpty(member.getBirthday())
						||StringUtils.isNotEmpty(member.getPoliticalOutlook())
						||StringUtils.isNotEmpty(member.getUnitPost())){

					member.setId(StringUtils.getUUID());
					member.setBasicinforId(jbqkId);
					int rows=memberService.insertMember(member);
					count+=rows;
				}
			}
		}
		if (count>0){
			json.put("code",200);
			json.put("msg","sucess");
		}
		return json;
	}
	
	/**
	 * 删除家庭成员情况
	 */
	@Log(title = "家庭成员情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(memberService.deleteMemberByIds(ids));
	}

	/**
	 * 查询家庭成员情况列表
	 */
	@PostMapping("/select")
	@ResponseBody
	public List<Member> select(Member member)
	{
		List<Member> list=null;
		if(member.getBasicinforId()!=null){
			list = memberService.selectMemberList(member);
		}
		return list;
	}

	
}
