package com.datcent.project.module.subgroup.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.mapper.TableColumnsMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.subgroup.mapper.SubgroupMapper;
import com.datcent.project.module.subgroup.domain.Subgroup;
import com.datcent.project.module.subgroup.service.ISubgroupService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组件管理 服务层实现
 *
 * @author datcent
 * @date 2018-12-06
 */
@Service
public class SubgroupServiceImpl implements ISubgroupService {
    @Autowired
    private SubgroupMapper subgroupMapper;

    @Autowired
    private TableColumnsMapper tableColumnsMapper;

    /**
     * 查询组件管理信息
     *
     * @param subgroupId 组件管理ID
     * @return 组件管理信息
     */
    @Override
    public Subgroup selectSubgroupById(String subgroupId) {
        return subgroupMapper.selectSubgroupById(subgroupId);
    }

    /**
     * 查询组件管理列表
     *
     * @param subgroup 组件管理信息
     * @return 组件管理集合
     */
    @Override
    public List<Subgroup> selectSubgroupList(Subgroup subgroup) {
        return subgroupMapper.selectSubgroupList(subgroup);
    }

    /**
     * 新增组件管理
     *
     * @param subgroup 组件管理信息
     * @return 结果
     */
    @Override
    public int insertSubgroup(Subgroup subgroup) {
        return subgroupMapper.insertSubgroup(subgroup);
    }

    /**
     * 修改组件管理
     *
     * @param subgroup 组件管理信息
     * @return 结果
     */
    @Override
    public int updateSubgroup(Subgroup subgroup) {
        return subgroupMapper.updateSubgroup(subgroup);
    }

    /**
     * 删除组件管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSubgroupByIds(String ids) {
        Subgroup subgroup = subgroupMapper.selectSubgroupById(ids);
        TableColumns tableColumns = new TableColumns();
        tableColumns.setTableName(subgroup.getTableName());
        tableColumns.setColumnsType(subgroup.getSubgroupStyle());
        List<TableColumns> tableColumnsList = tableColumnsMapper.selectTableColumnsList(tableColumns);
        if(tableColumnsList.size()>0){
            String[] table_ids = new String[tableColumnsList.size()];
            for(int i=0;i<tableColumnsList.size();i++){
                table_ids[i] = tableColumnsList.get(i).getColumnId();
            }
            tableColumnsMapper.deleteTableColumnsByIds(table_ids);
        }
        return subgroupMapper.deleteSubgroupByIds(Convert.toStrArray(ids));
    }

    /**
     * @Author: 张梦圆
     * @Email: zhangmengyuan@datcent.com
     * @CreateDate: 2018/12/7 14:02
     * @Copyright: © 2018 德讯科技 版权所有
     * @param: Subgroup
     * @return: List<Subgroup>
     * @exception:
     * @Description: TODO
     **/
    public List<Subgroup> selectSubgroup_information(Subgroup subgroup) {
        return subgroupMapper.selectSubgroup_information(subgroup);
    }

    /**
     * @Author: 张梦圆
     * @Email: zhangmengyuan@datcent.com
     * @CreateDate: 2018/12/10 16:28
     * @Copyright: © 2018 德讯科技 版权所有
     * @param: jsonStr
     * @return: int
     * @exception: 添加tablecolumn和tableColumn
     * @Description: TODO
     **/
    @Transactional
    public int addTableColumnAndSubgroup(String head_json, String contaniner_json) {
        JSONObject jsonObject = JSONObject.fromObject(head_json);
        if (jsonObject.has("eventJson")) {
            jsonObject.put("eventJson", (jsonObject.getString("eventJson")).getBytes());
        }
        if (jsonObject.has("iniconditJson")) {
            jsonObject.put("iniconditJson", (jsonObject.getString("iniconditJson")).getBytes());
        }
        //添加时查询是否存在模型相同，类型相同的组件。
        Subgroup sub = (Subgroup) JSONObject.toBean(jsonObject, Subgroup.class);
        sub.setSubgroupId(StringUtils.getUUID());
        sub.setCreateTime(new Date());
        Subgroup selectCondition = new Subgroup();
        selectCondition.setSubgroupStyle(sub.getSubgroupStyle());
        selectCondition.setTableName(sub.getTableName());
        List<Subgroup> oldSub = subgroupMapper.selectSubgroupList(selectCondition);
        //清楚已存在的组件
        if (oldSub.size() > 0) {
            String[] ids = new String[oldSub.size()];
            for (int i = 0; i < oldSub.size(); i++) {
                ids[i] = oldSub.get(i).getSubgroupId();
            }
            subgroupMapper.deleteSubgroupByIds(ids);
        }
        TableColumns tableC = new TableColumns();
        tableC.setTableName(sub.getTableName());
        tableC.setColumnsType(sub.getSubgroupStyle());
        List<TableColumns> tableColumnsList = tableColumnsMapper.selectTableColumnsList(tableC);
        if (tableColumnsList.size() > 0) {
            String[] ids = new String[tableColumnsList.size()];
            for (int i = 0; i < tableColumnsList.size(); i++) {
                ids[i] = tableColumnsList.get(i).getColumnId();
            }
            tableColumnsMapper.deleteTableColumnsByIds(ids);
        }
        JSONObject table_json = JSONObject.fromObject(contaniner_json);
        List<TableColumns> list = JSON.parseArray(table_json.getString("tableColumn"), TableColumns.class);
        for (TableColumns t : list) {
            t.setColumnId(StringUtils.getUUID());
            t.setColumnsType(jsonObject.getString("subgroupStyle"));
            t.setIsKey(jsonObject.getString("isKey"));
            t.setIsSortable(jsonObject.getString("isSortable"));
            t.setVisibleCols(jsonObject.getString("visibleCols"));
            t.setTableName(jsonObject.getString("tableName"));
        }
        AjaxResult ajax = new AjaxResult();
        int rows = subgroupMapper.insertSubgroup(sub);
        rows = tableColumnsMapper.insertByBatch(list);
        return rows;
    }

    /**
     * @Author: 张梦圆
     * @Email: zhangemngyuan@datcent.com
     * @CreateDate: 2018/12/10 16:27
     * @Copyright: © 2018 德讯科技 版权所有
     * @param: jsonStr
     * @return: int
     * @exception: 修改subgroup和tableCoulmn
     * @Description: TODO
     **/
    @Transactional
    public int editTableColumnAndSubgroup(String jsonStr, String jsonTable) {
        JSONObject jsonobject = JSONObject.fromObject(jsonStr);
        if (jsonobject.has("eventJson")) {
            jsonobject.put("eventJson", (jsonobject.getString("eventJson")).getBytes());
        }
        if (jsonobject.has("iniconditJson")) {
            jsonobject.put("iniconditJson", (jsonobject.getString("iniconditJson")).getBytes());
        }
        Subgroup sub = (Subgroup) JSONObject.toBean(jsonobject, Subgroup.class);
        List<TableColumns> tablecolumsList = JSON.parseArray(jsonTable, TableColumns.class);
        int rows = subgroupMapper.updateSubgroup(sub);
        rows = tableColumnsMapper.updateByBatch(tablecolumsList);
        return rows;
    }
}
