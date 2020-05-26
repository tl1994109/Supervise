package com.datcent.project.module.template.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DocConvertPdf;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.datcent.project.module.template.mapper.TemplateMapper;
import com.datcent.project.module.template.domain.Template;
import com.datcent.project.module.template.service.ITemplateService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模板管理 服务层实现
 *
 * @author datcent
 * @date 2019-02-15
 */
@Service
public class TemplateServiceImpl implements ITemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    @Value("${datcent.profile}")
    private String profile;

    /**
     * 查询模板管理信息
     *
     * @param templateId 模板管理ID
     * @return 模板管理信息
     */
    @Override
    public Template selectTemplateById(String templateId) {
        return templateMapper.selectTemplateById(templateId);
    }

    /**
     * 查询模板管理列表
     *
     * @param template 模板管理信息
     * @return 模板管理集合
     */
    @Override
    public List<Template> selectTemplateList(Template template) {
        return templateMapper.selectTemplateList(template);
    }

    /**
     * 新增模板管理
     *
     * @param template 模板管理信息
     * @return 结果
     */
    @Override
    public int insertTemplate(Template template, MultipartFile file, String profile) throws Exception {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            String uploadName = FileUploadUtils.upload(profile,file,prefix);
            template.setTemplateDocUrl(uploadName);
            template.setOldName(fileName);
            Map<String,Object> map = DocConvertPdf.
                    file2pdf(new File(profile+uploadName),fileName,profile,prefix.replace(".",""));
            template.setTemplateUrl(map.get("pdfurl").toString());
        }
        if (template.getParentId() == null || "".equals(template.getParentId())) {
            template.setParentId("0");
        }
        if(template.getLevel() == null || "".equals(template.getLevel())){
            template.setLevel("0");
        }else{
            int level = Integer.parseInt(template.getLevel())+1;
            template.setLevel(Integer.toString(level));
        }
        template.setTemplateId(StringUtils.getUUID());
        template.setCreateBy(ShiroUtils.getUserId().toString());
        template.setCreateTime(new Date());
        return templateMapper.insertTemplate(template);
    }

    /**
     * 修改模板管理
     *
     * @param template 模板管理信息
     * @return 结果
     */
    @Override
    public int updateTemplate(Template template,MultipartFile file,String profile) throws Exception {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            String uploadName = FileUploadUtils.upload(profile,file,prefix);
            template.setTemplateDocUrl(ActivitiDefinftion.VIRTUAL_PATH+uploadName);
            template.setOldName(fileName);
            Map<String,Object> map = DocConvertPdf.
                    file2pdf(new File(profile+uploadName),fileName,profile,prefix.replace(".",""));
            template.setTemplateUrl(map.get("pdfurl").toString());
        }
        if(template.getLevel() == null || "".equals(template.getLevel())){
            template.setLevel("0");
        }else{
            int level = Integer.parseInt(template.getLevel())+1;
            template.setLevel(Integer.toString(level));
        }
        return templateMapper.updateTemplate(template);
    }

    /**
     * 删除模板管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTemplateByIds(String ids) {
        Template template = templateMapper.selectTemplateById(ids);
        File file = new File(profile + template.getTemplateDocUrl());
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        file = new File(profile + template.getTemplateUrl());
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        return templateMapper.deleteTemplateByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<Map<String, Object>> selectTempTree() {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Template> templateList = selectTemplateList(new Template());
        trees = getTrees(templateList);
        return trees;
    }

    @Override
    public List<Map<String, Object>> getTrees(List<Template> templatesList) {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Template temp : templatesList) {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put("id", temp.getTemplateId());
            tempMap.put("pId", temp.getParentId());
            tempMap.put("name", temp.getTemplateName());
            tempMap.put("title",temp.getTemplateName());
            tempMap.put("level",temp.getLevel());
            trees.add(tempMap);
        }
        return trees;
    }

    /**
     * 查询所有二级
     *
     * @return
     */
    public List<Template> selectSecondTree() {
        return templateMapper.selectSecondTree();
    }
}
