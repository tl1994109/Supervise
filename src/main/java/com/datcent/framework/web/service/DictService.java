package com.datcent.framework.web.service;

import java.util.ArrayList;
import java.util.List;

import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.system.dict.domain.DictData;
import com.datcent.project.system.dict.service.IDictDataService;

/**
 * RuoYi首创 html调用 thymeleaf 实现字典读取
 * 
 * @author datcent
 */
@Service("dict")
public class DictService
{
    @Autowired
    private IDictDataService dictDataService;

    @Autowired
    private ICourtOrganService courtOrganService;

    /**
     * 根据字典类型查询字典数据信息
     * 
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<DictData> getType(String dictType)
    {
        List<DictData> dictDataList = dictDataService.selectDictDataByType(dictType);
        for (DictData dic : dictDataList) {
            if(dic.getDictValue().contains("&lt;")){
                dic.setDictValue(dic.getDictValue().replace("&lt;","<"));
                dic.setDictLabel(dic.getDictLabel().replace("&lt;","<"));
            }else if(dic.getDictValue().contains("&gt;")){
                dic.setDictValue(dic.getDictValue().replace("&gt;",">"));
                dic.setDictLabel(dic.getDictLabel().replace("&gt;",">"));
            }
        }
        return dictDataList;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }

    /**
     *
     *查询所有法院
     * @return
     */
    public List<CourtOrgan> getFy()
    {
        List<CourtOrgan> dictDataList = courtOrganService.selectAllFy();
        return dictDataList;
    }

    /**
     *
     * 廉政档案查询所有审批权限的人
     * @return
     */
    public List<CourtOrgan> getLeader()
    {
        List<CourtOrgan> dictDataList = courtOrganService.selectAllLeader();
        return dictDataList;
    }

}
