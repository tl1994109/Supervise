package com.datcent.project.module.appraise.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.mapper.JournalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.appraise.mapper.AppraiseMapper;
import com.datcent.project.module.appraise.domain.Appraise;
import com.datcent.project.module.appraise.service.IAppraiseService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 任务评价 服务层实现
 * 
 * @author datcent
 * @date 2019-01-24
 */
@Service
public class AppraiseServiceImpl implements IAppraiseService 
{
	@Autowired
	private AppraiseMapper appraiseMapper;

	@Autowired
	private JournalMapper journalMapper;

	/**
     * 查询任务评价信息
     * 
     * @param appraiseId 任务评价ID
     * @return 任务评价信息
     */
    @Override
	public Appraise selectAppraiseById(String appraiseId)
	{
	    return appraiseMapper.selectAppraiseById(appraiseId);
	}
	
	/**
     * 查询任务评价列表
     * 
     * @param appraise 任务评价信息
     * @return 任务评价集合
     */
	@Override
	public List<Appraise> selectAppraiseList(Appraise appraise)
	{
	    return appraiseMapper.selectAppraiseList(appraise);
	}
	
    /**
     * 新增任务评价
     * 
     * @param appraise 任务评价信息
     * @return 结果
     */
	@Override
	public int insertAppraise(Appraise appraise)
	{
		appraise.setAppraiseId(StringUtils.getUUID());
		appraise.setAppraiseCreateby(ShiroUtils.getUserId().toString());
		appraise.setAppraiseCreatedate(new Date());
		Journal journal = new Journal();
		journal.setJournalTaskid(appraise.getAppraiseTaskid());
		journal.setJournalCreateby(ShiroUtils.getUserId().toString());
		try {
			journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		journal.setJournalId(StringUtils.getUUID());
		journal.setJournalContent("评价了任务");
		journalMapper.insertJournal(journal);
		return appraiseMapper.insertAppraise(appraise);
	}
	
	/**
     * 修改任务评价
     * 
     * @param appraise 任务评价信息
     * @return 结果
     */
	@Override
	public int updateAppraise(Appraise appraise)
	{
	    return appraiseMapper.updateAppraise(appraise);
	}

	/**
     * 删除任务评价对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAppraiseByIds(String ids)
	{
		return appraiseMapper.deleteAppraiseByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据任务编号查询
	 * @param taskId
	 * @return
	 */
	@Override
	public Appraise selectAppraiseByTaskId(String taskId) {
		return appraiseMapper.selectAppraiseByTaskId(taskId);
	}
}
