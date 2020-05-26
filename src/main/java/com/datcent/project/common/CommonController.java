package com.datcent.project.common;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.framework.config.Global;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datcent.common.utils.file.FileUtils;
import com.datcent.framework.config.RuoYiConfig;

/**
 * 通用请求处理
 *
 * @author datcent
 */
@Controller
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);


    @Autowired
    private IDispositionFormService dispositionFormService;


    @RequestMapping("userGuide/download")
    public void userGuideDown(String fileName,HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format(" 文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName ="监督管理平台（法院）v3.0操作手册.doc";
            String filePath = Global.getDownloadPath() + "userGuide/纪检监察机关智慧监督管理平台（法院）v3.0操作手册.doc";

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + HttpUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }


    @RequestMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format(" 文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + HttpUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }




    @RequestMapping("common/export")
    public void exportWord( HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
        DispositionForm fck = dispositionFormService.selectDispositionFormById(id);
        try {
            //word内容
            String content="<html><body>" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                    + fck.getFormTitle() + "</span></p>" + fck.getFormContent() + "</body></html>";
            byte b[] = content.getBytes("UTF-8");
            //这里是必须要设置编码的，不然导出中文就会乱码。
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            //将字节数组包装到流中

            /*
             * 关键地方
             * 生成word格式 */
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
            request.setCharacterEncoding("utf-8");
            //导出word格式
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(fck.getFormTitle() .getBytes("utf-8"),"utf-8") + ".docx");
            ServletOutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
            poifs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
