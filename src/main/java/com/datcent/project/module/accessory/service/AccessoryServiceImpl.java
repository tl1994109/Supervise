package com.datcent.project.module.accessory.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.mapper.JournalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.accessory.mapper.AccessoryMapper;
import com.datcent.project.module.accessory.domain.Accessory;
import com.datcent.project.module.accessory.service.IAccessoryService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 任务附件 服务层实现
 * 
 * @author datcent
 * @date 2019-01-22
 */
@Service
public class AccessoryServiceImpl implements IAccessoryService 
{
	@Autowired
	private AccessoryMapper accessoryMapper;

	@Autowired
	private JournalMapper journalMapper;

	/**
     * 查询任务附件信息
     * 
     * @param accessoryId 任务附件ID
     * @return 任务附件信息
     */
    @Override
	public Accessory selectAccessoryById(String accessoryId)
	{
	    return accessoryMapper.selectAccessoryById(accessoryId);
	}
	
	/**
     * 查询任务附件列表
     * 
     * @param accessory 任务附件信息
     * @return 任务附件集合
     */
	@Override
	public List<Accessory> selectAccessoryList(Accessory accessory)
	{
	    return accessoryMapper.selectAccessoryList(accessory);
	}
	
    /**
     * 新增任务附件
     * 
     * @param accessory 任务附件信息
     * @return 结果
     */
	@Override
	public int insertAccessory(Accessory accessory)
	{
	    return accessoryMapper.insertAccessory(accessory);
	}
	
	/**
     * 修改任务附件
     * 
     * @param accessory 任务附件信息
     * @return 结果
     */
	@Override
	public int updateAccessory(Accessory accessory)
	{
	    return accessoryMapper.updateAccessory(accessory);
	}

	/**
     * 删除任务附件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAccessoryByIds(String ids,String taskId) throws ParseException {
		Journal journal = new Journal();
		journal.setJournalTaskid(taskId);
		journal.setJournalCreateby(ShiroUtils.getUserId().toString());
		journal.setJournalCreatedate(DateUtils.convertDate(new Date(), "yyyy-MM-dd"));
		journal.setJournalId(StringUtils.getUUID());
		journal.setJournalContent("删除了附件");
		journalMapper.insertJournal(journal);
		return accessoryMapper.deleteAccessoryByIds(Convert.toStrArray(ids));
	}
	
}
