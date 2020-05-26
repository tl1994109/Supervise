package com.datcent.project.module.lists.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.lists.mapper.ListsMapper;
import com.datcent.project.module.lists.domain.Lists;
import com.datcent.project.module.lists.service.IListsService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 案件列 服务层实现
 * 
 * @author datcent
 * @date 2018-12-26
 */
@Service
public class ListsServiceImpl implements IListsService 
{
	@Autowired
	private ListsMapper listsMapper;

	/**
     * 查询案件列信息
     * 
     * @param zhcxlistStringajbh 案件列ID
     * @return 案件列信息
     */
    @Override
	public Lists selectListsById(String zhcxlistStringajbh)
	{
	    return listsMapper.selectListsById(zhcxlistStringajbh);
	}
	
	/**
     * 查询案件列列表
     * 
     * @param lists 案件列信息
     * @return 案件列集合
     */
	@Override
	public List<Lists> selectListsList(Lists lists)
	{
	    return listsMapper.selectListsList(lists);
	}
	
    /**
     * 新增案件列
     * 
     * @param lists 案件列信息
     * @return 结果
     */
	@Override
	public int insertLists(Lists lists)
	{
	    return listsMapper.insertLists(lists);
	}
	
	/**
     * 修改案件列
     * 
     * @param lists 案件列信息
     * @return 结果
     */
	@Override
	public int updateLists(Lists lists)
	{
	    return listsMapper.updateLists(lists);
	}

	/**
     * 删除案件列对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteListsByIds(String ids)
	{
		return listsMapper.deleteListsByIds(Convert.toStrArray(ids));
	}
	
}
