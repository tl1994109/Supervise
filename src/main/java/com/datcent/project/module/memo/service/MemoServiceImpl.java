package com.datcent.project.module.memo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.journal.domain.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.memo.mapper.MemoMapper;
import com.datcent.project.module.memo.domain.Memo;
import com.datcent.project.module.memo.service.IMemoService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 备忘录 服务层实现
 * 
 * @author datcent
 * @date 2019-01-26
 */
@Service
public class MemoServiceImpl implements IMemoService 
{
	@Autowired
	private MemoMapper memoMapper;

	/**
     * 查询备忘录信息
     * 
     * @param memoId 备忘录ID
     * @return 备忘录信息
     */
    @Override
	public Memo selectMemoById(String memoId)
	{
	    return memoMapper.selectMemoById(memoId);
	}
	
	/**
     * 查询备忘录列表
     * 
     * @param memo 备忘录信息
     * @return 备忘录集合
     */
	@Override
	public List<Memo> selectMemoList(Memo memo)
	{
	    return memoMapper.selectMemoList(memo);
	}
	
    /**
     * 新增备忘录
     * 
     * @param memo 备忘录信息
     * @return 结果
     */
	@Override
	public int insertMemo(Memo memo)
	{
	    return memoMapper.insertMemo(memo);
	}
	
	/**
     * 修改备忘录
     * 
     * @param memo 备忘录信息
     * @return 结果
     */
	@Override
	public int updateMemo(Memo memo)
	{
	    return memoMapper.updateMemo(memo);
	}

	/**
     * 删除备忘录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMemoByIds(String ids)
	{
		return memoMapper.deleteMemoByIds(Convert.toStrArray(ids));
	}

	/**
	 *
	 * 批量添加
	 * @param memo
	 * @return
	 */
	@Override
	public int bathInsertMemo(String json_str) throws ParseException {
		List<Memo> memoList = JSON.parseArray(json_str, Memo.class);
		Iterator<Memo> it = memoList.iterator();
		int i =0;
		while (it.hasNext()) {
			Memo memo = it.next();
			if (StringUtils.isEmpty(memo.getMemoContent())) {
				it.remove();
			}
		}
		List<Memo> insert = new ArrayList<Memo>();
		for (Memo m:memoList) {
			if(m.getMemoId()== null||"".equals(m.getMemoId())){
				m.setMemoId(StringUtils.getUUID());
				m.setMemoCreateby(ShiroUtils.getUserId().toString());
				m.setMemoCreatedate(DateUtils.convertDate(new Date(),"yyyy-MM-dd"));
				insert.add(m);
			}else{
				i=memoMapper.updateMemo(m);
			}
		}
		if(insert.size()>0){
			 i=memoMapper.bathInsertMemo(insert);
		}
		return i;
	}
}
