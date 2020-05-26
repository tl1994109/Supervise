package com.datcent.project.module.massesReport.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.massesReport.mapper.MassesReportMapper;
import com.datcent.project.module.massesReport.domain.MassesReport;
import com.datcent.project.module.massesReport.service.IMassesReportService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 群众举报情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Service
public class MassesReportServiceImpl implements IMassesReportService 
{
	@Autowired
	private MassesReportMapper massesReportMapper;

	/**
     * 查询群众举报情况信息
     * 
     * @param xfjbqkId 群众举报情况ID
     * @return 群众举报情况信息
     */
    @Override
	public MassesReport selectMassesReportById(String xfjbqkId)
	{
	    return massesReportMapper.selectMassesReportById(xfjbqkId);
	}
	
	/**
     * 查询群众举报情况列表
     * 
     * @param massesReport 群众举报情况信息
     * @return 群众举报情况集合
     */
	@Override
	public List<MassesReport> selectMassesReportList(MassesReport massesReport)
	{
	    return massesReportMapper.selectMassesReportList(massesReport);
	}
	
    /**
     * 新增群众举报情况
     * 
     * @param massesReport 群众举报情况信息
     * @return 结果
     */
	@Override
	public int insertMassesReport(MassesReport massesReport)
	{
			return massesReportMapper.insertMassesReport(massesReport);
	}
	
	/**
     * 修改群众举报情况
     * 
     * @param massesReport 群众举报情况信息
     * @return 结果
     */
	@Override
	public int updateMassesReport(MassesReport massesReport)
	{
	    return massesReportMapper.updateMassesReport(massesReport);
	}

	/**
     * 删除群众举报情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMassesReportByIds(String ids)
	{
		return massesReportMapper.deleteMassesReportByIds(Convert.toStrArray(ids));
	}
	
}
