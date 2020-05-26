package com.datcent.project.module.memo.controller;

import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
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
import com.datcent.project.module.memo.domain.Memo;
import com.datcent.project.module.memo.service.IMemoService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 备忘录 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-26
 */
@Controller
@RequestMapping("/module/memo")
public class MemoController extends BaseController
{
    private String prefix = "module/memo";
	
	@Autowired
	private IMemoService memoService;
	
//	@RequiresPermissions("module:memo:view")
	@GetMapping()
	public String memo()
	{
	    return prefix + "/memo";
	}
	
	/**
	 * 查询备忘录列表
	 */
//	@RequiresPermissions("module:memo:list")
	@PostMapping("/list")
	@ResponseBody
	public List<Memo> list(Memo memo)
	{
		memo.setMemoCreateby(ShiroUtils.getUserId().toString());
		return memoService.selectMemoList(memo);
	}
	
	/**
	 * 新增备忘录
	 */
	@GetMapping("/add")
	public String add(String date,ModelMap modelMap)
	{
		modelMap.put("date",date);
		Memo memo = new Memo();
		memo.setMemoDate(date);
		memo.setMemoCreateby(ShiroUtils.getUserId().toString());
		List<Memo> memoList = memoService.selectMemoList(memo);
		String json = JSONObject.toJSONString(memoList);
		modelMap.put("list",json);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存备忘录
	 */
//	@RequiresPermissions("module:memo:add")
	@Log(title = "备忘录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(String json_str) throws ParseException {
		return toAjax(memoService.bathInsertMemo(json_str));
	}

	/**
	 * 修改备忘录
	 */
	@GetMapping("/edit/{memoId}")
	public String edit(@PathVariable("memoId") String memoId, ModelMap mmap)
	{
		Memo memo = memoService.selectMemoById(memoId);
		mmap.put("memo", memo);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存备忘录
	 */
//	@RequiresPermissions("module:memo:edit")
	@Log(title = "备忘录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Memo memo)
	{		
		return toAjax(memoService.updateMemo(memo));
	}
	
	/**
	 * 删除备忘录
	 */
//	@RequiresPermissions("module:memo:remove")
	@Log(title = "备忘录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(memoService.deleteMemoByIds(ids));
	}
	
}
