package com.datcent.common.utils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/2/18 14:29
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public class DocConvertPdf {
    /**
     * 转换文件成pdf
     *
     * @param fromFileInputStream:
     * @throws IOException
     */
    public static Map<String,Object> file2pdf(File file, String oldName,String toFilePath, String type) throws Exception{
        String timesuffix = System.currentTimeMillis()+"_"+oldName.substring(0,oldName.lastIndexOf("."));
       // String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            //docFileName = timesuffix.concat(".doc");
            htmFileName = timesuffix.concat(".pdf");
        }else if("docx".equals(type)){
           // docFileName = timesuffix.concat(".docx");
            htmFileName = timesuffix.concat(".pdf");
        }else if("xls".equals(type)){
          //  docFileName = timesuffix.concat(".xls");
            htmFileName = timesuffix.concat(".pdf");
        }else if("xlsx".equals(type)){
           // docFileName = timesuffix.concat(".xlsx");
            htmFileName = timesuffix.concat(".pdf");
        }else if("ppt".equals(type)){
            //docFileName = timesuffix.concat(".ppt");
            htmFileName = timesuffix.concat(".pdf");
        }else if("pptx".equals(type)){
           // docFileName = timesuffix.concat(".pptx");
            htmFileName = timesuffix.concat(".pdf");
        }else if("txt".equals(type)){
           // docFileName = timesuffix.concat(".txt");
            htmFileName = timesuffix.concat(".pdf");
        }else{
            return null;
        }
        File htmlOutputFile = new File(toFilePath  + htmFileName);
       // File docInputFile = new File(toFilePath + docFileName);
        if (htmlOutputFile.exists()){
            htmlOutputFile.delete();
        }
        htmlOutputFile.createNewFile();
       // docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
//        int bytesRead = 0;
//        byte[] buffer = new byte[1024];
//        OutputStream os = new FileOutputStream(docInputFile);
//        while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
//            os.write(buffer, 0, bytesRead);
//        }
//        os.close();
//        fromFileInputStream.close();

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
        connection.connect();
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(file, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pdfurl",htmFileName);
       // map.put("docurl",docFileName);
        return map;
    }
    /**
     * 文件转换成Html
     * @param fromFileInputStream
     * @param toFilePath
     * @param type
     * @return
     * @throws Exception
     */
    public static String file2Html (InputStream fromFileInputStream, String toFilePath,String type) throws Exception{
        String timesuffix = StringUtils.getUUID();
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = timesuffix.concat(".doc");
            htmFileName = timesuffix.concat(".html");
        }else if("docx".equals(type)){
            docFileName = timesuffix.concat(".docx");
            htmFileName = timesuffix.concat(".html");
        }else if("xls".equals(type)){
            docFileName = timesuffix.concat(".xls");
            htmFileName = timesuffix.concat(".html");
        }else if("xlsx".equals(type)){
            docFileName = timesuffix.concat(".xlsx");
            htmFileName = timesuffix.concat(".html");
        }else if("ppt".equals(type)){
            docFileName = timesuffix.concat(".ppt");
            htmFileName = timesuffix.concat(".html");
        }else if("pptx".equals(type)){
            docFileName = timesuffix.concat(".pptx");
            htmFileName = timesuffix.concat(".html");
        }else if("txt".equals(type)){
            docFileName = timesuffix.concat(".txt");
            htmFileName = timesuffix.concat(".html");
        }else if("pdf".equals(type)){
            docFileName = timesuffix.concat(".pdf");
            htmFileName = timesuffix.concat(".html");
        }else{
            return null;
        }
        File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
        File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
        if (htmlOutputFile.exists()){
            htmlOutputFile.delete();
        }
        htmlOutputFile.createNewFile();
        docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        int bytesRead = 0;
        byte[] buffer = new byte[1024 * 8];
        OutputStream os = new FileOutputStream(docInputFile);
        while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        fromFileInputStream.close();
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
        connection.connect();
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        //docInputFile.delete();
        return htmFileName;
    }
}
