package com.datcent.project.module.memo.service;

import com.datcent.project.module.memo.domain.Memo;

import java.text.ParseException;
import java.util.List;

/**
 * 备忘录 服务层
 * 
 * @author datcent
 * @date 2019-01-26
 */
public interface IMemoService 
{
	/**
     * 查询备忘录信息
     * 
     * @param memoId 备忘录ID
     * @return 备忘录信息
     */
	public Memo selectMemoById(String memoId);
	
	/**
     * 查询备忘录列表
     * 
     * @param memo 备忘录信息
     * @return 备忘录集合
     */
	public List<Memo> selectMemoList(Memo memo);
	
	/**
     * 新增备忘录
     * 
     * @param memo 备忘录信息
     * @return 结果
     */
	public int insertMemo(Memo memo);
	
	/**
     * 修改备忘录
     * 
     * @param memo 备忘录信息
     * @return 结果
     */
	public int updateMemo(Memo memo);
		
	/**
     * 删除备忘录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMemoByIds(String ids);

	/*
	 * 批量添加
	 *
	 * */
	public int bathInsertMemo(String json_str) throws ParseException;
	
}
