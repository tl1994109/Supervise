package com.datcent.project.module.itemreport.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.itemreport.mapper.ItemreportMapper;
import com.datcent.project.module.itemreport.domain.Itemreport;
import com.datcent.project.module.itemreport.service.IItemreportService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 有关事件报告 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class ItemreportServiceImpl implements IItemreportService 
{
	@Autowired
	private ItemreportMapper itemreportMapper;

	/**
     * 查询有关事件报告信息
     * 
     * @param sxbgId 有关事件报告ID
     * @return 有关事件报告信息
     */
    @Override
	public Itemreport selectItemreportById(String sxbgId)
	{
	    return itemreportMapper.selectItemreportById(sxbgId);
	}
	
	/**
     * 查询有关事件报告列表
     * 
     * @param itemreport 有关事件报告信息
     * @return 有关事件报告集合
     */
	@Override
	public List<Itemreport> selectItemreportList(Itemreport itemreport)
	{
	    return itemreportMapper.selectItemreportList(itemreport);
	}
	
    /**
     * 新增有关事件报告
     * 
     * @param itemreport 有关事件报告信息
     * @return 结果
     */
	@Override
	public int insertItemreport(Itemreport itemreport)
	{
	    return itemreportMapper.insertItemreport(itemreport);
	}
	
	/**
     * 修改有关事件报告
     * 
     * @param itemreport 有关事件报告信息
     * @return 结果
     */
	@Override
	public int updateItemreport(Itemreport itemreport)
	{
	    return itemreportMapper.updateItemreport(itemreport);
	}

	/**
     * 删除有关事件报告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteItemreportByIds(String ids)
	{
		return itemreportMapper.deleteItemreportByIds(Convert.toStrArray(ids));
	}
	
}
