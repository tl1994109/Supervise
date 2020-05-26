package com.datcent.project.module.dubvioEvent.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datcent.common.exception.BusinessException;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import com.datcent.project.module.datashareFollow.service.IDatashareFollowService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.service.IUserService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.dubvioEvent.mapper.DubvioEventMapper;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 可疑违纪事件 服务层实现
 *
 * @author datcent
 * @date 2019-01-12
 */
@Service
public class DubvioEventServiceImpl implements IDubvioEventService {

    private static final Logger log = LoggerFactory.getLogger(DubvioEventServiceImpl.class);
    @Autowired
    private DubvioEventMapper dubvioEventMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IUserService userService;


    @Autowired
    private IDatashareFollowService iDatashareFollowService;

    /**
     * 查询可疑违纪事件信息
     *
     * @param dubvioId 可疑违纪事件ID
     * @return 可疑违纪事件信息
     */
    @Override
    public DubvioEvent selectDubvioEventById(String dubvioId) {
        return dubvioEventMapper.selectDubvioEventById(dubvioId);
    }

    /**
     * 查询可疑违纪事件列表
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 可疑违纪事件集合
     */
    @Override
    public List<DubvioEvent> selectDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain) {

        // 用户的部门id
        String deptId = ShiroUtils.getUser().getDeptId().toString();
        //定义一个法院id集合
        List<String> jbfyIdList = new ArrayList<>();

       String orderByColumn=pageDomain.getOrderByColumn();

       if (StringUtils.isNotEmpty(orderByColumn)){
           pageDomain.setOrderByColumn("");
           pageDomain.setIsAsc("asc");
       }



        if (StringUtils.isNotEmpty(deptId)) {

            //查出部门的所属法院id
            Dept dept = deptService.selectDeptById(Long.parseLong(deptId));
            DatashareFollow datashareFollow = new DatashareFollow();
            datashareFollow.setDatashareCourtid(dept.getParentId() + "");
            datashareFollow.setStatus("0");
            //根据用户所属法院id 所有共享的法院id
            List<DatashareFollow> DatashareList = iDatashareFollowService.selectDatashareFollowList(datashareFollow);
            for (DatashareFollow follow : DatashareList) {
                jbfyIdList.add(follow.getSharecourtId());
            }
        }
        ShiroUtils.clearCachedPage(pageDomain);ShiroUtils.clearCachedPage(pageDomain);
        dubvioEvent.setJbfyIdList(jbfyIdList);

        return dubvioEventMapper.selectNewDubvioEventList(dubvioEvent);

//		List<UserRole> roleList = userService.selectUserRoleByUserId(ShiroUtils.getUserId().toString());
//		Boolean isok = false;
//		for (UserRole r:roleList) {
//			if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
//				isok = true;
//				break;
//			}
//		}
//		Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
//		if("0".equals(dpt.getRank())){
//			isok= true;
//		}
//		if(isok ==false){
//			dubvioEvent.setJbfyId(dpt.getParentId().toString());
//		}
//		if(pageDomain!=null){
//			Integer pageNum = pageDomain.getPageNum();
//			Integer pageSize = pageDomain.getPageSize();
//			if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
//			{
//				String orderBy = pageDomain.getOrderBy();
//				PageHelper.startPage(pageNum, pageSize, orderBy);
//			}
//		}

    }



    public  List<DubvioEvent> selectPassDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain){
        // 用户的部门id
        String deptId = ShiroUtils.getUser().getDeptId().toString();
        //定义一个法院id集合
        List<String> jbfyIdList = new ArrayList<>();

        if (StringUtils.isNotEmpty(deptId)) {
            //查出部门的所属法院id
            Dept dept = deptService.selectDeptById(Long.parseLong(deptId));
            DatashareFollow datashareFollow = new DatashareFollow();
            datashareFollow.setDatashareCourtid(dept.getParentId() + "");
            datashareFollow.setStatus("0");
            //根据用户所属法院id 所有共享的法院id
            List<DatashareFollow> DatashareList = iDatashareFollowService.selectDatashareFollowList(datashareFollow);

            for (DatashareFollow follow : DatashareList) {
                jbfyIdList.add(follow.getSharecourtId());
            }
        }
        ShiroUtils.clearCachedPage(pageDomain);
        dubvioEvent.setJbfyIdList(jbfyIdList);

        return dubvioEventMapper.selectPassDubvioEventList(dubvioEvent);

    }

    /**
     * 查询可疑违纪事件列表
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 可疑违纪事件集合
     */
    public List<DubvioEvent> selectSecondDubvioEventList(DubvioEvent dubvioEvent, PageDomain pageDomain) {
        // 用户的部门id
        String deptId = ShiroUtils.getUser().getDeptId().toString();
        //定义一个法院id集合
        List<String> jbfyIdList = new ArrayList<>();

        if (StringUtils.isNotEmpty(deptId)) {
            //查出部门的所属法院id
            Dept dept = deptService.selectDeptById(Long.parseLong(deptId));
            DatashareFollow datashareFollow = new DatashareFollow();
            datashareFollow.setDatashareCourtid(dept.getParentId() + "");
            datashareFollow.setStatus("0");
            //根据用户所属法院id 所有共享的法院id
            List<DatashareFollow> DatashareList = iDatashareFollowService.selectDatashareFollowList(datashareFollow);

            for (DatashareFollow follow : DatashareList) {
                jbfyIdList.add(follow.getSharecourtId());
            }
        }
        dubvioEvent.setJbfyIdList(jbfyIdList);

        if (pageDomain != null) {
            Integer pageNum = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
                String orderBy = pageDomain.getOrderBy();
                PageHelper.startPage(pageNum, pageSize, orderBy);
            }
        }
        return dubvioEventMapper.selectSecondDubvioEventList(dubvioEvent);
    }

    /**
     * 新增可疑违纪事件
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 结果
     */
    @Override
    public int insertDubvioEvent(DubvioEvent dubvioEvent) {
        return dubvioEventMapper.insertDubvioEvent(dubvioEvent);
    }

    /**
     * 修改可疑违纪事件
     *
     * @param dubvioEvent 可疑违纪事件信息
     * @return 结果
     */
    @Override
    public int updateDubvioEvent(DubvioEvent dubvioEvent) {
        return dubvioEventMapper.updateDubvioEvent(dubvioEvent);
    }

    /**
     * 删除可疑违纪事件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDubvioEventByIds(String ids) {
        return dubvioEventMapper.deleteDubvioEventByIds(Convert.toStrArray(ids));
    }


    /**
     * 根据名字查询人员相关的案号和案件编号
     *
     * @param name
     * @return
     */
    public List<Map> queyAhAndAjbhByAjbh(String name) {
        return dubvioEventMapper.queyAhAndAjbhByAjbh(name);
    }

    /**
     * 查出所有的案号
     *
     * @return
     */
    public List<String> queryAllAh() {
        return dubvioEventMapper.queryAllAh();
    }

    @Override
    public List<DubvioEvent> selectList(DubvioEvent dubvioEven) {
        return dubvioEventMapper.selectDubvioEventList(dubvioEven);
    }


    /**
     * 根据用户id查出 用户所在部门
     *
     * @param userId
     * @return
     */
    public String queryDeptNameByUserId(String userId) {

        return dubvioEventMapper.queryDeptNameByUserId(userId);
    }


    /**
     * 统计各案件类别各策略的数量
     * @return
     */
    public List<Map<String,Object>> queryEventAjlbCountByStrategyName(DubvioEvent dubvioEvent){
        return dubvioEventMapper.queryEventAjlbCountByStrategyName(dubvioEvent);
    }

    /**
     * 被反映人统计数据
     * @return
     */
    public List<Map<String,Object>> querySuspectsByManData(DubvioEvent dubvioEvent)
    {
        return dubvioEventMapper.querySuspectsByManData(dubvioEvent);
    }


    /**
     ////	 * 根据案件类别 和 策略id来查询线索
     ////	 * @param ajlb
     ////	 * @param id
     ////	 * @return
     */
    public List<Map> selectEventListByAjlbAndStrategyId(@Param("ajlb") String ajlb, @Param("id") String id){
        return dubvioEventMapper.selectEventListByAjlbAndStrategyId(ajlb,id);
    }

    /**
     * 根据 策略id来查询线索
     * @param id
     * @return
     */
    public List<Map> selectEventListByStrategyId(@Param("id") String id){
        return dubvioEventMapper.selectEventListByStrategyId(id);

    }

    /**
     * 根据反映人编号  统计反映人情况
     * @return
     */
    public List<Map<String,Object>> queryEventFyrInfoByFyy(@Param("deptId") String deptId ){
        return dubvioEventMapper.queryEventFyrInfoByFyy(deptId);

    }

    public String queryEventFycs(String id){
        return dubvioEventMapper.queryEventFycs(id);

    }

    /**
     * 导入用户数据
     *
     * @param dubvioEventList 信访数据列表
     * @return 结果
     */
    @Override
    public String importDubvioEvent(List<DubvioEvent> dubvioEventList)
    {
        if (StringUtils.isNull(dubvioEventList) || dubvioEventList.size() == 0)
        {
            throw new BusinessException("导入信访数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DubvioEvent dubvioEvent : dubvioEventList)
        {
            try
            {
                dubvioEvent.setCreateBy(ShiroUtils.getLoginName());
                dubvioEvent.setDubvioSource("2");
                Date time =new Date(dubvioEvent.getJbxxXffysj());
                dubvioEvent.setJbxxXffysj(sdf.format(time));
                dubvioEvent.setDubvioId(StringUtils.getUUID());
                dubvioEvent.setCreateTime(new Date());
                String deptId=personService.selectPersonById(ShiroUtils.getUser().getPersonId()).getDeptId();
                dubvioEvent.setJbfyId(deptService.selectDeptById(Long.valueOf(deptId)).getParentId().toString());
                dubvioEvent.setXfDeptId(deptId + "");
                this.insertDubvioEvent(dubvioEvent);
                successNum++;
                successMsg.append("<br/>" + successNum + "、信访序号: " + dubvioEvent.getJbxxXfxh()+ " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、信访序号: " + dubvioEvent.getJbxxXfxh()+ " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
