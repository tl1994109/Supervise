package com.datcent.project.module.annualIncome.service;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.annualIncome.mapper.AnnualIncomeMapper;
import com.datcent.project.module.annualIncome.domain.AnnualIncome;
import com.datcent.project.module.annualIncome.service.IAnnualIncomeService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 家庭全年收入情况 服务层实现
 * 
 * @author datcent
 * @date 2019-04-03
 */
@Service
public class AnnualIncomeServiceImpl implements IAnnualIncomeService 
{
	@Autowired
	private AnnualIncomeMapper annualIncomeMapper;

	/**
     * 查询家庭全年收入情况信息
     * 
     * @param id 家庭全年收入情况ID
     * @return 家庭全年收入情况信息
     */
    @Override
	public AnnualIncome selectAnnualIncomeById(String id)
	{
	    return annualIncomeMapper.selectAnnualIncomeById(id);
	}
	
	/**
     * 查询家庭全年收入情况列表
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 家庭全年收入情况集合
     */
	@Override
	public List<AnnualIncome> selectAnnualIncomeList(AnnualIncome annualIncome)
	{
	    return annualIncomeMapper.selectAnnualIncomeList(annualIncome);
	}
	
    /**
     * 新增家庭全年收入情况
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 结果
     */
	@Override
	public int insertAnnualIncome(AnnualIncome annualIncome)
	{
		AnnualIncome income = new AnnualIncome();
		income.setCid(annualIncome.getCid());
		List<AnnualIncome> annualIncomes = selectAnnualIncomeList(income);
		if(annualIncomes.size()==0){
			annualIncome.setId(StringUtils.getUUID());
			annualIncome.setCreateBy(ShiroUtils.getLoginName());
			annualIncome.setCreateTime(new Date());
			return annualIncomeMapper.insertAnnualIncome(annualIncome);
		}else{
			return 0;
		}

	}
	
	/**
     * 修改家庭全年收入情况
     * 
     * @param annualIncome 家庭全年收入情况信息
     * @return 结果
     */
	@Override
	public int updateAnnualIncome(AnnualIncome annualIncome)
	{
	    return annualIncomeMapper.updateAnnualIncome(annualIncome);
	}

	/**
     * 删除家庭全年收入情况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAnnualIncomeByIds(String ids)
	{
		return annualIncomeMapper.deleteAnnualIncomeByIds(Convert.toStrArray(ids));
	}
	
}
