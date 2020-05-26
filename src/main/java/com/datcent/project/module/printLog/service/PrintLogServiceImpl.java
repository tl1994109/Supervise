package com.datcent.project.module.printLog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.printLog.mapper.PrintLogMapper;
import com.datcent.project.module.printLog.domain.PrintLog;
import com.datcent.project.module.printLog.service.IPrintLogService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 单打印日志 服务层实现
 * 
 * @author datcent
 * @date 2019-02-15
 */
@Service
public class PrintLogServiceImpl implements IPrintLogService 
{
	@Autowired
	private PrintLogMapper printLogMapper;

	/**
     * 查询单打印日志信息
     * 
     * @param printLogId 单打印日志ID
     * @return 单打印日志信息
     */
    @Override
	public PrintLog selectPrintLogById(Integer printLogId)
	{
	    return printLogMapper.selectPrintLogById(printLogId);
	}
	
	/**
     * 查询单打印日志列表
     * 
     * @param printLog 单打印日志信息
     * @return 单打印日志集合
     */
	@Override
	public List<PrintLog> selectPrintLogList(PrintLog printLog)
	{
	    return printLogMapper.selectPrintLogList(printLog);
	}
	
    /**
     * 新增单打印日志
     * 
     * @param printLog 单打印日志信息
     * @return 结果
     */
	@Override
	public int insertPrintLog(PrintLog printLog)
	{
	    return printLogMapper.insertPrintLog(printLog);
	}
	
	/**
     * 修改单打印日志
     * 
     * @param printLog 单打印日志信息
     * @return 结果
     */
	@Override
	public int updatePrintLog(PrintLog printLog)
	{
	    return printLogMapper.updatePrintLog(printLog);
	}

	/**
     * 删除单打印日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePrintLogByIds(String ids)
	{
		return printLogMapper.deletePrintLogByIds(Convert.toStrArray(ids));
	}
	
}
