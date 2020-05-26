package com.datcent.project.module.printLog.mapper;

import com.datcent.project.module.printLog.domain.PrintLog;
import java.util.List;	

/**
 * 单打印日志 数据层
 * 
 * @author datcent
 * @date 2019-02-15
 */
public interface PrintLogMapper 
{
	/**
     * 查询单打印日志信息
     * 
     * @param printLogId 单打印日志ID
     * @return 单打印日志信息
     */
	public PrintLog selectPrintLogById(Integer printLogId);
	
	/**
     * 查询单打印日志列表
     * 
     * @param printLog 单打印日志信息
     * @return 单打印日志集合
     */
	public List<PrintLog> selectPrintLogList(PrintLog printLog);
	
	/**
     * 新增单打印日志
     * 
     * @param printLog 单打印日志信息
     * @return 结果
     */
	public int insertPrintLog(PrintLog printLog);
	
	/**
     * 修改单打印日志
     * 
     * @param printLog 单打印日志信息
     * @return 结果
     */
	public int updatePrintLog(PrintLog printLog);
	
	/**
     * 删除单打印日志
     * 
     * @param printLogId 单打印日志ID
     * @return 结果
     */
	public int deletePrintLogById(Integer printLogId);
	
	/**
     * 批量删除单打印日志
     * 
     * @param printLogIds 需要删除的数据ID
     * @return 结果
     */
	public int deletePrintLogByIds(String[] printLogIds);
	
}