package com.datcent.project.tool.gen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.datcent.common.constant.CommonMap;
import com.datcent.common.constant.Constants;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.framework.config.GenConfig;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.domain.TableInfo;
import com.datcent.project.tool.tableColumns.domain.TableColumns;

/**
 * 代码生成器 工具类
 *
 * @author datcent
 */
public class GenUtils
{
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java/com/datcent/project";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mybatis";

    /** html空间路径 */
    private static final String TEMPLATES_PATH = "main/resources/templates";

    /**
     * 设置列信息
     */
    public static List<TableColumns> transColums(List<TableColumns> columns)
    {
        // 列信息
        List<TableColumns> columsList = new ArrayList<>();
        for (TableColumns column : columns)
        {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = CommonMap.javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        //定义一个初始化数据
       List<Map<String,Object>> initList = new ArrayList<Map<String,Object>>();


        String packageName = GenConfig.getPackageName();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", GenUtils.getModuleName(packageName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName + "." + table.getClassname());
        velocityContext.put("author", GenConfig.getAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("queryColmun",3);
        //初始化数据
        velocityContext.put("initList",initList);
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/vm/java/domain.java.vm");
        templates.add("templates/vm/java/Mapper.java.vm");
        templates.add("templates/vm/java/Service.java.vm");
        templates.add("templates/vm/java/ServiceImpl.java.vm");
        templates.add("templates/vm/java/Controller.java.vm");
        templates.add("templates/vm/xml/Mapper.xml.vm");
        templates.add("templates/vm/html/list.html.vm");
        templates.add("templates/vm/html/add.html.vm");
        templates.add("templates/vm/html/edit.html.vm");
        templates.add("templates/vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName)
    {
        if (Constants.AUTO_REOMVE_PRE.equals(GenConfig.getAutoRemovePre()))
        {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotEmpty(GenConfig.getTablePrefix()))
        {
            tableName = tableName.replace(GenConfig.getTablePrefix(), "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName)
    {
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH + "/" + moduleName + "/";
        String mybatisPath = MYBATIS_PATH + "/" + moduleName + "/" + className;
        String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + classname;

        if (StringUtils.isNotEmpty(classname))
        {
            javaPath += classname.replace(".", "/") + "/";
        }

        if (template.contains("domain.java.vm"))
        {
            return javaPath + "domain" + "/" + className + ".java";
        }

        if (template.contains("Mapper.java.vm"))
        {
            return javaPath + "mapper" + "/" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm"))
        {
            return javaPath + "service" + "/" + "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm"))
        {
            return javaPath + "service" + "/" + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm"))
        {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm"))
        {
            return mybatisPath + "Mapper.xml";
        }

        if (template.contains("list.html.vm"))
        {
            return htmlPath + "/" + classname + ".html";
        }
        if (template.contains("add.html.vm"))
        {
            return htmlPath + "/" + "add.html";
        }
        if (template.contains("edit.html.vm"))
        {
            return htmlPath + "/" + "edit.html";
        }
        if (template.contains("sql.vm"))
        {
            return classname + "Menu.sql";
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public static String replaceKeyword(String keyword)
    {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    public static void main(String[] args)
    {
        System.out.println(StringUtils.convertToCamelCase("user_name"));
        System.out.println(replaceKeyword("岗位信息表"));
        System.out.println(getModuleName("com.datcent.project.system"));
    }
}
