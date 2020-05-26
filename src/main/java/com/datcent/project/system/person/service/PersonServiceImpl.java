package com.datcent.project.system.person.service;

import java.util.ArrayList;
import java.util.List;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.BusinessException;
import com.datcent.common.utils.StringUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.system.config.service.IConfigService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.role.domain.Role;
import com.datcent.project.system.role.service.IRoleService;
import com.datcent.project.system.user.domain.UserRole;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datcent.project.system.person.mapper.PersonMapper;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserPost;
import com.datcent.project.system.user.mapper.UserPostMapper;
import com.datcent.project.system.user.service.IUserService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.datascope.DataScopeUtils;

/**
 *
 * @描述: 人员  服务层实现
 * @创建人: zhou_shiji
 * @创建时间: 2018年10月19日下午3:16:42
 */
@Service
public class PersonServiceImpl implements IPersonService
{
	private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
	@Autowired
	private PersonMapper personMapper;

	@Autowired
    private UserPostMapper userPostMapper;

	@Autowired
	private IDeptService deptService;

	@Autowired
    private IUserService userService;

	@Autowired
	private IConfigService configService;

	@Autowired
	private IRoleService roleService;
	/**
     * 查询人员 信息
     *
     * @param personId 人员 ID
     * @return 人员 信息
     */
    @Override
	public Person selectPersonById(String personId)
	{
	    return personMapper.selectPersonById(personId);
	}

	/**
     * 查询人员 列表  根据部门做权限判断
     *
     * @param person 人员 信息
     * @return 人员 集合
     */
	@Override
	public List<Person> selectPersonList(Person person, PageDomain pageDomain)
	{
		List<UserRole> roleList = userService.selectUserRoleByUserId(ShiroUtils.getUserId().toString());
		Boolean isok = false;
		for (UserRole r:roleList) {
			if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
				isok = true;
				break;
			}
		}
		Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
		if("0".equals(dpt.getRank())){
			isok= true;
		}
		if(isok ==false){
			person.setDeptId(deptService.selectDeptIdByUserId());
		}
		ShiroUtils.clearCachedPage(pageDomain);
	    return personMapper.selectPersonList(person);
	}

	/**
     * 查询人员Id Name 列表  添加权限
     * @return 人员 集合
     */
	@Override
	public List<Person> selectPersonAll()
	{
		String userid = ShiroUtils.getUserId().toString();
		List<UserRole> roleList = userService.selectUserRoleByUserId(userid);
		Boolean isok = false;
		for (UserRole r:roleList) {
			if(ActivitiDefinftion.ROLE_ADMIN_KEY.equals(r.getRoleKey())){
				isok = true;
				break;
			}
		}
		Dept dpt = deptService.selectDeptById(Long.valueOf(deptService.selectDeptIdByUserId()));
		if("0".equals(dpt.getRank())){
			isok= true;
		}
		Person person = new Person();
		if(isok ==false){
			String deptId = deptService.selectDeptIdByUserId();
			person.setDeptId(deptId);
			return personMapper.selectPersonAll(person);
		}else{
			return personMapper.selectPersonAll(person);
		}
	}

    /**
     * 新增人员
     *
     * @param person 人员 信息
     * @return 结果
     */
	@Override
	public int insertPerson(Person person)
	{
		person.setCreateBy(ShiroUtils.getLoginName());
		int row=personMapper.insertPerson(person);
		insertPersonPost(person);
	    return row;
	}

	/**
     * 新增人员岗位信息
     *
     * @param Person 人员对象
     */
    public void insertPersonPost(Person person)
    {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        for (Long postId : person.getPostIds())
        {
            UserPost up = new UserPost();
            up.setUserId(Long.parseLong(person.getPersonId()));
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0)
        {
            userPostMapper.batchUserPost(list);
        }
    }

	/**
     * 修改人员
     *
     * @param person 人员 信息
     * @return 结果
     */
	@Override
	public int updatePerson(Person person)
	{
		person.setCreateBy(ShiroUtils.getLoginName());
		// 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(Long.parseLong(person.getPersonId()));
        // 新增用户与岗位管理
        insertPersonPost(person);
	    return personMapper.updatePerson(person);
	}

	/**
     * 逻辑删除人员 对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePersonByIds(String ids) throws Exception
	{
		String[] personIds=ids.split(",");
		if(personIds.length>0){
			//查询与用户是否存在关联 如有则不能删除 无反之
			for (String item : personIds) {
				User user = userService.selectUserByPersonId(item);
				if(user==null){
					personMapper.logicallyDelPersonById(item);
				}else{
					throw new Exception("该人员信息与系统用户绑定 不允许删除！");
				}
			}
			return 1;
		}else{
			return 0;
		}

	}

	@Override
	public String importPerson(List<Person> personList, Boolean isUpdateSupport) {
		if (StringUtils.isNull(personList) || personList.size() == 0)
		{
			throw new BusinessException("导入用户数据不能为空！");
		}
		int successNum = 0;
		int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		String operName = ShiroUtils.getLoginName();
		for (Person person : personList)
		{
			try
			{
				// 验证是否存在这个用户
				Person per = personMapper.selectPersonById(person.getPersonId());
				if (StringUtils.isNull(per))
				{
					this.insertPerson(person);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + person.getPersonName() + " 导入成功");
				}
				else if (isUpdateSupport)
				{
					person.setUpdateBy(operName);
					this.updatePerson(person);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + person.getPersonName() + " 更新成功");
				}
				else
				{
					failureNum++;
					failureMsg.append("<br/>" + failureNum + "、账号 " + person.getPersonName() + " 已存在");
				}
			}
			catch (Exception e)
			{
				failureNum++;
				String msg = "<br/>" + failureNum + "、账号 " + person.getPersonName() + " 导入失败：";
				failureMsg.append(msg + e.getMessage());
				log.error(msg, e);
			}
		}
		if (failureNum > 0)
		{
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new BusinessException(failureMsg.toString());
		}
		else
		{
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}
}
