package com.datcent.project.module.information.service;

import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.project.module.information.domain.Information;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 任务 服务层
 * 
 * @author datcent
 * @date 2019-01-20
 */
public interface IInformationService 
{
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
	public int insertInformation(Information information, String acId, MultipartFile[] file, String profile) throws ParseException, IOException, FileNameLengthLimitExceededException, FileUploadBase.FileSizeLimitExceededException;
	
	/**
     * 修改任务
     * 
     * @param information 任务信息
     * @return 结果
     */
	public int updateInformation(Information information,MultipartFile[] taskFile,MultipartFile[] overFile,String profile) throws Exception;
		
	/**
     * 删除任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteInformationByIds(List<Information> informationList);

	/*添加日志信息*/
	public int addLisrLog(String json_str);


	/**
	 * 统计任务总数
	 * @return
	 */
	public int  queryAllCount();

	/**
	 * 统计当月新增总数
	 * @return
	 */
	public int  queryAddCount();

	/**
	 * 根据任务状态统计任务数量
	 * @return
	 */
	public int  queryCountByStatus(String status);

	/**
	 * 统计完成和进行中各自任务数量
	 * @return
	 */
	public Map queryCountWcAndJxz();

	/**
	 * 统计半年各任务类别的数量
	 * @param time
	 * @param type
	 * @return
	 */
	public int queryTaskCountByMonth(@Param("time") String time, @Param("type") String type);

	/**
	 * 统计各月份  任务完成量和任务总量
	 * @param time
	 * @param status
	 * @return
	 */
	public int queryByStatusAndMonth(String time, String status);


	/**
	 * 统计优良中差 各个评级的任务数量
	 * @return
	 */
	public int queryByMonthAndLevel(@Param("time") String time, @Param("level") String  level);

	/**
	 * 统计 各个任务类型的数量
	 * @return
	 */
	public int  queryCountByTaskType(String type);


	/**
	 *统计各个月份 重要度任务数量
	 * @return
	 */
	public int queryTaskCountByMonthAndImportance(@Param("time") String time,@Param("importance") String importance);

	/**
	 * 工作地图  获取各县任务数量
	 * @return
	 */
	public List<Map<String,Object>> getListMap();

	/**
	 * 首页获取数据
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
