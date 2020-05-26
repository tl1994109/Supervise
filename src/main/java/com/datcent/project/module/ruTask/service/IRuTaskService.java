package com.datcent.project.module.ruTask.service;

import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.ruTask.domain.RuTask;
import org.activiti.engine.task.Task;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

/**
 * 任务列 服务层
 *
 * @author datcent
 * @date 2019-01-12
 */
public interface IRuTaskService
{
	/**
     * 查询任务列信息
     *
     * @param id 任务列ID
     * @return 任务列信息
     */
	public RuTask selectRuTaskById(String id);

	/**
     * 查询任务列列表
     *
     * @param ruTask 任务列信息
     * @return 任务列集合
     */
	public List<RuTask> selectRuTaskList(RuTask ruTask);

	/**
     * 新增任务列
     *
     * @param ruTask 任务列信息
     * @return 结果
     */
	public int insertRuTask(RuTask ruTask);

	/**
     * 修改任务列
     *
     * @param ruTask 任务列信息
     * @return 结果
     */
	public int updateRuTask(RuTask ruTask);

	/**
     * 删除任务列信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRuTaskByIds(String ids);


	/**
	 * 提交任务   通用方法
	 *
	 * @param  isok 判断同意 不同意,  taskId 当前流程节点,  opinion 批注,  pid,  approver 审批人
	 * @return 结果
	 */
	public Map<String,Object> compleTask(String isok,String content,String proposerName, String taskId, String opinion,String pid,String approver,Map<String,Object> mmap);



	/**
	 * 任务审核   通用方法
	 *
	 * @param isok  是否同意
	 * @param opinion 审核批注
	 * @param clueClassify 类型
	 * @param task 任务流程
	 * @param clue 线索
	 * @param s1 成功值
	 * @param s2 失败值
	 */
	public void checkProblem(int count,String isok, String opinion, String clueClassify, Clue clue, String nodeFlag,String way,String s1, String s2);

	/**
	 *
	 *
	 * @param process
	 * @param clue
	 * @param approver
	 * @param content
	 * @return
	 */
	public AjaxResult submitForm(int count,String resultType,String process, Clue clue, String approver, String content,String isEdit,
								 DispositionAttachment dispositionAttachment,String type,
								 String clueClassify,String handleUrl,String formType,String nodeFlag,String templateUrl);

	/**
	 *
	 * 页面跳转时判断表单是否填写
	 * @param clueId
	 * @param returnUrl
	 * @param modelMap
	 * @return
	 */
	public String returnUrl(String nodeFlag,String clueId,String returnUrl, ModelMap modelMap);

	public void dispositionSubmit(int count,String nodeFlag,String clueId, String isEdit, DispositionAttachment dispositionAttachment,String templateUrl);
}
