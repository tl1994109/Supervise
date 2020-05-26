package com.datcent.project.pulling;
import com.alibaba.fastjson.JSON;
import com.datcent.common.utils.http.HttpUtils;
import com.datcent.framework.shiro.web.session.OnlineWebSessionManager;
import com.datcent.project.module.lists.domain.Lists;
import com.datcent.project.module.lists.service.IListsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author datcent
 */
@Controller
@RequestMapping("/pullingDate")
public class PullingDateController {

    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    @Autowired
    private IListsService iListsService;

    /**
     * 列表数据获取
     */
    @RequestMapping("/searchall")
    @GetMapping()
    @Transactional(rollbackFor = {})
    public void searchall(){
        String sr ="";
        if(!sr.equals("")){
            JSONObject json = JSONObject.fromObject(sr);
            JSONArray nodes=json.getJSONArray("list");
            JSONObject countObject = nodes.getJSONObject(0);
            String count=countObject.get("totalCount").toString();
            System.out.println(count);
            nodes.remove(0);
            List<Lists> columns = JSON.parseArray(nodes.toString(),Lists.class);
            for (Lists item:columns) {
                iListsService.insertLists(item);
            }
            log.info("更新成功！！！");
        }else{
            log.info("连接失败！！！");
        }
    }


    @RequestMapping("/getDetail")
    @GetMapping()
    @Transactional(rollbackFor = {})
    public void getDetail(){
        String url = "http://143.64.7.133/zxQuery/getDetail";
        String param = "session=123&ahdm=220320181008000224";
        String sr = HttpUtils.sendGet(url,param);
        if(!sr.equals("")){
            JSONObject json = JSONObject.fromObject(sr);
            JSONArray nodes=json.getJSONArray("list");

            log.info("更新成功！！！");
        }else{
            log.info("连接失败！！！");
        }

    }


}
