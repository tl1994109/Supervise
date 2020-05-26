package com.datcent.project.module.journal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.journal.mapper.JournalMapper;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.service.IJournalService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 任务日志 服务层实现
 * 
 * @author datcent
 * @date 2019-01-22
 */
@Service
public class JournalServiceImpl implements IJournalService 
{
	@Autowired
	private JournalMapper journalMapper;

	/**
     * 查询任务日志信息
     * 
     * @param journalId 任务日志ID
     * @return 任务日志信息
     */
    @Override
	public Journal selectJournalById(String journalId)
	{
	    return journalMapper.selectJournalById(journalId);
	}
	
	/**
     * 查询任务日志列表
     * 
     * @param journal 任务日志信息
     * @return 任务日志集合
     */
	@Override
	public List<Journal> selectJournalList(Journal journal)
	{
	    return journalMapper.selectJournalList(journal);
	}
	
    /**
     * 新增任务日志
     * 
     * @param journal 任务日志信息
     * @return 结果
     */
	@Override
	public int insertJournal(Journal journal)
	{
	    return journalMapper.insertJournal(journal);
	}
	
	/**
     * 修改任务日志
     * 
     * @param journal 任务日志信息
     * @return 结果
     */
	@Override
	public int updateJournal(Journal journal)
	{
	    return journalMapper.updateJournal(journal);
	}

	/**
     * 删除任务日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteJournalByIds(String ids)
	{
		return journalMapper.deleteJournalByIds(Convert.toStrArray(ids));
	}

	/* 批量插入 */
	public int bathInsertJournal(List<Journal> journalList) {
		return journalMapper.bathInsertJournal(journalList);
	}

	@Override
	public List<Journal> selectGroupByCreateDate(String id) {
		return journalMapper.selectGroupByCreateDate(id);
	}
}
