package com.datcent.project.module.dispositionHandle.controller;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.service.IDispositionHandleService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 线索处置记录过程信息
 * 
 * @author datcent
 */
@Controller
@RequestMapping("/module/dispositionHandle")
public class DispositionHandleController extends BaseController
{
    private String prefix = "module/dispositionHandle";

    @Autowired
    private IClueService clueService;

    @Autowired
    private IDispositionHandleService dispositionHandleService;

    @Autowired
    private IDispositionAttachmentService dispositionAttachmentService;

    @Autowired
    private IDubvioEventService dubvioEventService;

    /*@RequiresPermissions("module:dispositionHandle:view")*/
    @GetMapping()
    public String dept(String clueId,ModelMap mmap)
    {
        mmap.put("clueId",clueId);
        Clue clue = clueService.selectClueById(clueId);
        DispositionAttachment dispositionAttachment=new DispositionAttachment();
        dispositionAttachment.setClueId(clueId);
        List<DispositionAttachment> dispositionAttachmentList=dispositionAttachmentService.selectFile(dispositionAttachment);
        if(dispositionAttachmentList.size()>0){
            mmap.put("fileSize",dispositionAttachmentList.size());
        }else{
            mmap.put("fileSize","0");
        }
        //根据 可疑违纪时间id 查找可疑违纪事件表中 信访录入信息  busi_dubvio_event
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(clue.getDubvioId());
        mmap.put("czjg",clue.getClueCzjg());
        mmap.put("dubvioEvent",dubvioEvent);
        mmap.put("clue",clue);
        return prefix + "/dept";


    }

    /*@RequiresPermissions("module:dispositionHandle:list")*/
    @GetMapping("/list")
    @ResponseBody
    public List<DispositionHandle> list(DispositionHandle dept)
    {
        List<DispositionHandle> deptList = dispositionHandleService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增线索处置记录过程
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") String parentId, ModelMap mmap)
    {
        mmap.put("dept", dispositionHandleService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存线索处置记录过程
     */
    @Log(title = "线索处置记录过程", businessType = BusinessType.INSERT)
    /*@RequiresPermissions("module:dispositionHandle:add")*/
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DispositionHandle dept)
    {
        return toAjax(dispositionHandleService.insertDispositionHandle(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") String deptId, ModelMap mmap)
    {
        mmap.put("dept", dispositionHandleService.selectDeptById(deptId));
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "线索处置记录过程", businessType = BusinessType.UPDATE)
    /*@RequiresPermissions("module:dispositionHandle:edit")*/
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DispositionHandle dept)
    {
        return toAjax(dispositionHandleService.updateDispositionHandle(dept));
    }

    /**
     * 删除
     */
    @Log(title = "线索处置记录过程", businessType = BusinessType.DELETE)
    /*@RequiresPermissions("module:dispositionHandle:remove")*/
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") String deptId)
    {
        if (dispositionHandleService.selectDeptCount(deptId) > 0)
        {
            return error(1, "存在下级线索处置记录过程,不允许删除");
        }
        if (dispositionHandleService.checkDeptExistUser(deptId))
        {
            return error(1, "部门存在用户,不允许删除");
        }
        return toAjax(dispositionHandleService.deleteDeptById(deptId));
    }
    /**
     * 校验线索处置记录过程名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(DispositionHandle dept)
    {
        return dispositionHandleService.checkDeptNameUnique(dept);
    }

    /**
     * 选择线索处置记录过程树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") String deptId, ModelMap mmap)
    {
        mmap.put("dept", dispositionHandleService.selectDeptById(deptId));
        return prefix + "/tree";
    }

    /**
     * 加载线索处置记录过程列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData()
    {
        List<Map<String, Object>> tree = dispositionHandleService.selectDeptTree();
        return tree;
    }
}
