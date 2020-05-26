package com.datcent.project.module.information.mapper;

import com.datcent.project.module.information.domain.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 任务 数据层
 *
 * @author datcent
 * @date 2019-01-20
 */
public interface InformationMapper {
    /**
     * 查询任务信息
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    public Information selectInformationById(String taskId);

    /**
     * 查询任务列表
     *
     * @param information 任务信息
     * @return 任务集合
     */
    public List<Information> selectInformationList(Information information);

    /**
     * 新增任务
     *
     * @param information 任务信息
     * @return 结果
     */
    public int insertInformation(Information information);

    /**
     * 修改任务
     *
     * @param information 任务信息
     * @return 结果
     */
    public int updateInformation(Information information);

    /**
     * 删除任务
     *
     * @param taskId 任务ID
     * @return 结果
     */
    public int deleteInformationById(String taskId);

    /**
     * 批量删除任务
     *
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationByIds(String[] taskIds);

    /**
     * 统计任务总数
     *
     * @return
     */
    public int queryAllCount(Information information);

    /**
     * 统计当月新增总数
     *
     * @return
     */
//    public int queryAddCount();

    /**
     * 根据任务状态统计任务数量
     *
     * @return
     */
  //  public int queryCountByStatus(String status);

    /**
     * 统计完成和进行中各自任务数量
     *
     * @return
     */
    public Map queryCountWcAndJxz(String parentDeptId);

    /**
     * 统计半年各任务类别的数量
     *
     * @param time
     * @param type
     * @return
     */
    public int queryTaskCountByMonth(Information information);

    /**
     * 统计各月份  任务完成量和任务总量
     *
     * @param time
     * @param status
     * @return
     */
    public int queryByStatusAndMonth(@Param("time") String time, @Param("status") String[] status,@Param("parentDeptId")String parentDetpId);

    /**
     * 统计优良中差 各个评级的任务数量
     * @return
     */
    public int queryByMonthAndLevel(@Param("time") String time, @Param("level") String  level,@Param("parentDeptId")String parentDeptId);

    /**
     * 统计 各个任务类型的数量
     * @return
     */
    public int queryCountByTaskType(@Param("type")String  type,@Param("parentDeptId")String parentDetpId);

    /**
     *统计各个月份 重要度任务数量
     * @return
     */
    public int queryTaskCountByMonthAndImportance(@Param("time") String time,@Param("importance") String importance,@Param("parentDeptId")String  parentDeptId);

    /**
     * 首页获取数据   未完成
     * @param userid
     * @return
     */
    public List<Information> mainSelectList(String userid);

    /**
     * 首页获取数据   已完成
     * @param userid
     * @return
     */
    public List<Information> mianselectListTaskStatus_Two(String userid);

    /**
     * 首页提醒查询
     * @param information
     * @return
     */
    public List<Information> selectRemindList(Information information);

    public int updateTask(Information information);
}