package com.datcent.project.module.statistics.controller;

import com.datcent.common.utils.StringUtils;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.project.module.statistics.service.IStatisticsService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 统计汇总 信息操作处理
 *
 * @author zhoushiji
 * @date 2019-11-29
 */
@Controller
@RequestMapping("/module/statistics")
public class StatisticsController extends BaseController
{
    private String prefix = "module/statistics";

	@Autowired
	private IStatisticsService iStatisticsService;

	@Autowired
	private ICourtOrganService iCourtOrganService;

	@Autowired
	private IDubvioEventDetailService dubvioEventDetailService;

	/**
	 * 可疑事件中可疑点维度统计--页面跳转
	 * @return
	 */
	@RequiresPermissions("module:statistics:kysjBykyd")
	@GetMapping("/kysjBykyd")
	public String kysjBykyd(ModelMap mmap)
	{
		CourtOrgan courtOrgan = new CourtOrgan();
		courtOrgan.setDeptType("1");
		courtOrgan.setStatus("0");
		List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
		mmap.put("deptList", thirdList);
	    return prefix + "/kysjBykyd";
	}

	/**
	 * 可疑事件中可疑点维度统计--数据接口
	 */
	@PostMapping("/kysjBykydList")
	@ResponseBody
	public TableDataInfo kysjBykydList(DubvioEvent dubvioEvent)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<Map<String,Object>> list=iStatisticsService.getKysjBykyd(dubvioEvent,pageDomain);
		return getDataTable(list);
	}

	/**
	 * 根据不同的维度条件查询案件列表--页面跳转
	 * @return
	 */
	@GetMapping("/getDimListPage")
	public String getDimListPage(DubvioEvent dubvioEvent, ModelMap mmap){
		mmap.put("dubvioEvent",dubvioEvent);
		return  prefix + "/dimList";
	}

	/**
	 * 根据不同的维度条件查询案件列表--数据接口
	 * @return
	 */
	@PostMapping("/getDimList")
	@ResponseBody
	public TableDataInfo getDimList(DubvioEvent dubvioEvent){
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<DubvioEvent> list = iStatisticsService.getDimList(dubvioEvent, pageDomain);
		return getDataTable(list);
	}

	/**
	 * 可疑点被反映人统计--页面
	 * @return
	 */
	@RequiresPermissions("module:statistics:suspectsByMan")
	@GetMapping("/suspectsByMan")
	public String suspectsByMan(ModelMap mmap)
	{
		CourtOrgan courtOrgan = new CourtOrgan();
		courtOrgan.setDeptType("1");
		courtOrgan.setStatus("0");
		List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
		mmap.put("deptList", thirdList);
		return prefix + "/suspectsByMan";
	}

	/**
	 * 可疑点被反映人统计--数据接口
	 */
	@PostMapping("/suspectsByManData")
	@ResponseBody
	public TableDataInfo suspectsByManData(DubvioEvent dubvioEvent)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<Map<String,Object>> lists=iStatisticsService.getSuspectsByManData(dubvioEvent,pageDomain);
		return getDataTable(lists);
	}

	/**
	 * 承办庭可疑点统计页面
	 */
	@RequiresPermissions("module:statistics:suspectsCount")
	@GetMapping("/suspectsCount")
	public String suspectsCount() {
		return "module/dubvioEvent/suspectsCount";
	}

	/**
	 * 承办庭可疑点统计数据接口
	 */
	@PostMapping("/getSuspectsCountByTribunal")
	@ResponseBody
	public TableDataInfo getSuspectsCountByTribunal(DubvioEvent dubvioEvent)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<Map<String,Object>> lists=iStatisticsService.getSuspectsCountByTribunal(dubvioEvent, pageDomain);
		return getDataTable(lists);
	}

	/**
	 * 承办庭可疑点详情
	 * @author zhang
	 * @date 2019-12-17
	 */
	@GetMapping("/suspectsDetailByTribunal")
	public String suspectsDetailByCourt(String id, ModelMap mmap)
	{
		mmap.put("id", id);
		return prefix + "/suspectsDetailByTribunal";
	}

	/**
	 * 获取承办庭可疑点详情数据接口
	 * @author zhang
	 * @date 2019-12-18
	 */
	@PostMapping("/getSuspectsDetailByCourtId")
	@ResponseBody
	public TableDataInfo getSuspectsDetailByCourtId(String id)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<Map<String,Object>> suspectsDetailList = iStatisticsService.getSuspectsDetailByCourtId(pageDomain, id);
		return getDataTable(suspectsDetailList);
	}

	/**
	 * 查看案件详情
	 * @param caseId 案件号
	 * @author zhang
	 * @date 2019-12-18
	 */
	@GetMapping("/getCaseDetailByCaseId")
	public String getCaseDetailByCaseId(String caseId, ModelMap mmap)
	{
		DubvioEventDetail dubvioEventDetail = dubvioEventDetailService.selectDubvioEventDetailById(caseId);
		if (dubvioEventDetail == null) {
			dubvioEventDetail = new DubvioEventDetail();
		}
		mmap.put("dubvioEventDetail", dubvioEventDetail);
		return prefix + "/suspectsCaseDetail";
	}

	/**
	 * 月度可疑点统计页面
	 */
	@RequiresPermissions("module:statistics:suspectsCountByMonth")
	@GetMapping("/suspectsCountByMonth")
	public String suspectsCountByMonth() {
		return "module/statistics/suspectsCountByMonth";
	}

	/**
	 * 月度可疑点统计数据接口
	 */
	@PostMapping("/getSuspectsCountByMonth")
	@ResponseBody
	public TableDataInfo getSuspectsCountByMonth(String startDate, String endDate)
	{
		startPage();
		List<Map> lists=iStatisticsService.getSuspectsCountByMonth(startDate, endDate);
		return getDataTable(lists);
	}

	/**
	 * 月度可疑点详细
	 * @param suspectsTime 日期
	 * @param caseTypeName 案件类别
	 * @author zhang
	 * @date 2019-12-19
	 */
	@GetMapping("/suspectDetailByMonth")
	public String suspectDetailByMonth(String suspectsTime, String  caseTypeName, ModelMap mmap)
	{
		mmap.put("suspectsTime", suspectsTime);
		mmap.put("caseTypeName", caseTypeName);
		return prefix + "/suspectsDetailByMonth";
	}

	/**
	 * 月度可疑点详细数据接口
	 * @param suspectsTime 日期
	 * @param caseTypeName 案件类别
	 * @author zhang
	 * @date 2019-12-19
	 */
	@PostMapping("/getSuspectsDetailByMonth")
	@ResponseBody
	public TableDataInfo getSuspectsDetailByMonth(String suspectsTime, String caseTypeName)
	{
		String caseType = getCaseTypeByCaseNum(caseTypeName);
		List<Map<String,Object>> suspectsDetailList = null;
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if(StringUtils.isNotEmpty(suspectsTime)){
			suspectsDetailList = iStatisticsService.getSuspectsDetailByMonth(pageDomain, suspectsTime,caseType);
		}
		return getDataTable(suspectsDetailList);

	}

	/**
	 * 院可疑点统计
	 * @author zhang
	 * @date 2019-12-19
	 */
    @RequiresPermissions("module:statistics:suspectsCountByCourt")
	@GetMapping("/suspectsCountByCourt")
    public String suspectsCountByCourt(ModelMap mmap)
    {
		CourtOrgan courtOrgan = new CourtOrgan();
		courtOrgan.setDeptType("1");
		courtOrgan.setStatus("0");
		List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
		mmap.put("deptList", thirdList);
        return "module/statistics/suspectsCountByCourt";
    }

	/**
	 * 院可疑点统计数据接口
	 * @param dubvioEvent 可疑事件信息
	 * @author zhang
	 * @date 2019-12-20
	 */
	@PostMapping("/getSuspectsCountByCourt")
	@ResponseBody
	public TableDataInfo getSuspectsCountByCourt(DubvioEvent dubvioEvent)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		List<Map<String,Object>> suspectsCountByCourtList=iStatisticsService.getSuspectsCountByCourt(dubvioEvent, pageDomain);
		return getDataTable(suspectsCountByCourtList);
	}

	/**
	 * 根据法院ID和案件类别，获取院可疑点统计详细
	 * @param id 法院ID
	 * @param name 案件类别名称
	 * @return 可疑点详细列表
	 * @author zhang
	 * @date 2019-12-20
	 */
	@PostMapping("/suspectDetailByCourtId")
	@ResponseBody
	public TableDataInfo suspectDetailByCourtId(String id, String name)
	{
		String caseType = getCaseTypeByCaseNum(name);
		List<Map<String,Object>> suspectsDetailList = null;
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if(StringUtils.isNotEmpty(id)){
			suspectsDetailList = iStatisticsService.getSuspectsDetailByCourtIdAndName(pageDomain, id, caseType);
		}
		return getDataTable(suspectsDetailList);
	}

	/**
	 * 根据法院id 查看承办庭可疑点统计页面
	 * @param id 法院ID
	 * @author zhang
	 * @date 2019-12-30
	 */
	@GetMapping("/tribunalSuspectCountByCourtId")
	public String tribunalSuspectCountByCourtId (String id, ModelMap mmap)
	{
		mmap.put("id", id);
		return prefix + "/tribunalSuspectsCount";
	}

	/**
	 * 院-承办庭可疑点统计数据接口
	 * @param id 法院ID
	 * @author zhang
	 * @date 2019-12-30
	 */
	@PostMapping("/getTribunalSuspectCountByCourtId")
	@ResponseBody
	public TableDataInfo getTribunalSuspectCountByCourtId(String id)
	{
		List<Map<String,Object>> getTribunalSuspectsCountList = null;
		PageDomain pageDomain = TableSupport.buildPageRequest();
		System.out.println(id);
		if(StringUtils.isNotEmpty(id)){
			getTribunalSuspectsCountList = iStatisticsService.getTribunalSuspectsCountByCourtId(pageDomain, id);
		}
		return getDataTable(getTribunalSuspectsCountList);
	}

	/**
	 * 可疑点详细页面
	 * @param id 法院ID
	 * @param name 案件类别名称
	 * @author zhang
	 * @date 2019-12-19
	 */
	@GetMapping("/suspectDetailByCourt")
	public String suspectDetailByCourt(String id, String  name, ModelMap mmap)
	{
		mmap.put("id", id);
		mmap.put("name", name);
		return prefix + "/suspectsDetailByCourt";
	}

	/**
	 * 根据案件类别编号获取案件类别名称
	 * @param caseNum 案件编号（web端js中定义）
	 * @return caseType 案件类别
	 * @author zhang
	 * @date 2019-12-20
	 */
	public String getCaseTypeByCaseNum(String caseNum)
	{
		String caseType = "";
		switch (caseNum){
			case "1":
				caseType = "民事";
				break;
			case "2" :
				caseType = "刑事";
				break;
			case "3":
				caseType = "行政";
				break;
			case "4" :
				caseType = "执行";
				break;
			default :
				caseType = "";
				break;
		}
		return caseType;
	}
}
