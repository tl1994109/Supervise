package com.datcent.project.module.journal.service;

import com.datcent.project.module.journal.domain.Journal;
import java.util.List;

/**
 * 任务日志 服务层
 * 
 * @author datcent
 * @date 2019-01-22
 */
public interface IJournalService 
{
	/**
     * 查询任务日志信息
     * 
     * @param journalId 任务日志ID
     * @return 任务日志信息
     */
	public Journal selectJournalById(String journalId);
	
	/**
     * 查询任务日志列表
     * 
     * @param journal 任务日志信息
     * @return 任务日志集合
     */
	public List<Journal> selectJournalList(Journal journal);
	
	/**
     * 新增任务日志
     * 
     * @param journal 任务日志信息
     * @return 结果
     */
	public int insertJournal(Journal journal);
	
	/**
     * 修改任务日志
     * 
     * @param journal 任务日志信息
     * @return 结果
     */
	public int updateJournal(Journal journal);
		
	/**
     * 删除任务日志信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteJournalByIds(String ids);

	/* 批量插入 */
	public int bathInsertJournal(List<Journal> journalList);

	/* 根据ID查询 创建时间分组 */
	public List<Journal> selectGroupByCreateDate(String id);
}
