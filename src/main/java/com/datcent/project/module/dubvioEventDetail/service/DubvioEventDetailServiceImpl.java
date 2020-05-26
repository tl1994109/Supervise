package com.datcent.project.module.dubvioEventDetail.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.dubvioEventDetail.mapper.DubvioEventDetailMapper;
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 案件详细 服务层实现
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Service
public class DubvioEventDetailServiceImpl implements IDubvioEventDetailService 
{
	@Autowired
	private DubvioEventDetailMapper dubvioEventDetailMapper;

	/**
     * 查询案件详细信息
     * 
     * @param jbxxAjbh 案件详细ID
     * @return 案件详细信息
     */
    @Override
	public DubvioEventDetail selectDubvioEventDetailById(String jbxxAjbh)
	{
	    return dubvioEventDetailMapper.selectDubvioEventDetailById(jbxxAjbh);
	}
	
	/**
     * 查询案件详细列表
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 案件详细集合
     */
	@Override
	public List<DubvioEventDetail> selectDubvioEventDetailList(DubvioEventDetail dubvioEventDetail)
	{
	    return dubvioEventDetailMapper.selectDubvioEventDetailList(dubvioEventDetail);
	}
	
    /**
     * 新增案件详细
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 结果
     */
	@Override
	public int insertDubvioEventDetail(DubvioEventDetail dubvioEventDetail)
	{
	    return dubvioEventDetailMapper.insertDubvioEventDetail(dubvioEventDetail);
	}
	
	/**
     * 修改案件详细
     * 
     * @param dubvioEventDetail 案件详细信息
     * @return 结果
     */
	@Override
	public int updateDubvioEventDetail(DubvioEventDetail dubvioEventDetail)
	{
	    return dubvioEventDetailMapper.updateDubvioEventDetail(dubvioEventDetail);
	}

	/**
     * 删除案件详细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDubvioEventDetailByIds(String ids)
	{
		return dubvioEventDetailMapper.deleteDubvioEventDetailByIds(Convert.toStrArray(ids));
	}



}
