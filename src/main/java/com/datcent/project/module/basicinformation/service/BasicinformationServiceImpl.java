package com.datcent.project.module.basicinformation.service;

import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.basicinformation.mapper.BasicinformationMapper;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 基本情况登记 服务层实现
 *
 * @author datcent
 * @date 2019-03-29
 */
@Service
public class BasicinformationServiceImpl implements IBasicinformationService
{
	@Autowired
	private BasicinformationMapper basicinformationMapper;

	/**
     * 查询基本情况登记信息
     *
     * @param jbqkId 基本情况登记ID
     * @return 基本情况登记信息
     */
    @Override
	public Basicinformation selectBasicinformationById(String jbqkId)
	{
	    return basicinformationMapper.selectBasicinformationById(jbqkId);
	}

	/**
     * 查询基本情况登记列表
     *
     * @param basicinformation 基本情况登记信息
     * @return 基本情况登记集合
     */
	@Override
	public List<Basicinformation> selectBasicinformationList(Basicinformation basicinformation)
	{



	    return basicinformationMapper.selectBasicinformationList(basicinformation);
	}


	/**
	 * 查询基本情况登记列表
	 *
	 * @param basicinformation 基本情况登记信息
	 * @return 基本情况登记集合
	 */
	@Override
	public List<Basicinformation> selectBasicinformationLists(Basicinformation basicinformation, PageDomain pageDomain)
	{

		ShiroUtils.clearCachedPage(pageDomain);

		return basicinformationMapper.selectBasicinformationList(basicinformation);
	}

    /**
     * 新增基本情况登记
     *
     * @param basicinformation 基本情况登记信息
     * @return 结果
     */
	@Override
	public int insertBasicinformation(Basicinformation basicinformation)
	{
	    return basicinformationMapper.insertBasicinformation(basicinformation);
	}

	/**
     * 修改基本情况登记
     *
     * @param basicinformation 基本情况登记信息
     * @return 结果
     */
	@Override
	public int updateBasicinformation(Basicinformation basicinformation)
	{
	    return basicinformationMapper.updateBasicinformation(basicinformation);
	}

	/**
     * 删除基本情况登记对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBasicinformationByIds(String ids)
	{
		return basicinformationMapper.deleteBasicinformationByIds(Convert.toStrArray(ids));
	}

	@Override
	public Integer selectBasicinformationByCardNo(String cardNo) {
		return basicinformationMapper.selectBasicinformationByCardNo(cardNo);
	}

	@Override
	public List<Basicinformation> selectBasicinformationByApproveAndCardNo(Basicinformation basicinformation) {
		return basicinformationMapper.selectBasicinformationByApproveAndCardNo(basicinformation);
	}
}
