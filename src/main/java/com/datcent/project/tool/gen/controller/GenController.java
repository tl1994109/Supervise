package com.datcent.project.tool.gen.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.ZipEntry;

import javax.servlet.http.HttpServletResponse;

import com.datcent.common.constant.Constants;
import com.datcent.common.exception.base.BaseException;
import com.datcent.framework.config.GenConfig;
import com.datcent.project.tool.gen.util.VelocityInitializer;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.domain.TableInfo;
import com.datcent.project.tool.gen.mapper.GenMapper;
import com.datcent.project.tool.gen.service.IGenService;
import com.datcent.project.tool.gen.util.GenUtils;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.service.ITableColumnsService;

/**
 * 代码生成 操作处理
 * 
 * @author datcent
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    private String prefix = "tool/gen";
    
    @Autowired
    private GenMapper genMapper;
    
    @Autowired
	private ITableColumnsService tableColumnsService;

    @Autowired
    private IGenService genService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen()
    {
        return prefix + "/gen";
    }
    
    /**
     * 预览新增页面跳转
     */
    @GetMapping("/add/{tableName}")
    public String previewAdd(@PathVariable("tableName") String tableName, ModelMap mmap)
    {
    	String templateName="templates/vm/preview/add.vm";
        mmap.put("columns", preview_Vm(templateName,tableName));
        return "vm/preview" + "/add";
    }

    /**
     * 预览首页页面跳转
     */
    @GetMapping("/previewList/{tableName}")
    public String previewList(@PathVariable("tableName") String tableName, ModelMap mmap)
    {
        String templateName="templates/vm/preview/list.vm";
        mmap.put("columns", preview_Vm(templateName,tableName));
        return "vm/preview" + "/list";
    }

    public String preview_Vm(String templateNae,String tableName){
        TableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        TableColumns tableColumns=new TableColumns();
        tableColumns.setTableName(tableName);
        List<TableColumns> columns = tableColumnsService.selectTableColumnsList(tableColumns);
        //没有则新增
        if(columns.size()==0){
            List<ColumnInfo> tableColumn = genMapper.selectTableColumnsByName(tableName);
            columns=tableColumnsService.selectTableColumnsAndSyn(tableName, tableColumn);
        }
        String className = GenUtils.tableToJava(table.getTableName());
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));
        // 列信息
        table.setColumns(GenUtils.transColums(columns));
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());

        VelocityInitializer.initVelocity();
        //java对象数据传递到模板文件vm
        VelocityContext context = GenUtils.getVelocityContext(table);
        StringWriter sw = new StringWriter();
        // 渲染模板
        Template tpl = Velocity.getTemplate(templateNae, Constants.UTF8);
        tpl.merge(context, sw);
        return sw.toString();
    }

    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableInfo tableInfo)
    {
        startPage();
        List<TableInfo> list = genService.selectTableList(tableInfo);
        return getDataTable(list);
    }

    /**
     * 生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genService.generatorCode(tableName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"datcent.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"datcent.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
