package com.tools.module.workflow.web;

import com.tools.common.model.Result;
import com.tools.module.workflow.entity.BpmEntity;
import com.tools.module.workflow.entity.BpmLeave;
import com.tools.module.workflow.service.BpmLeaveService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请假流程
 * @author 小柒2012
 */
@Api(tags = "请假管理")
@RestController
@RequestMapping("workflow/leave")
public class LeaveController {

    @Autowired
    private BpmLeaveService bpmLeaveService;

    /**
     * 发起
     */
    @PostMapping("/save")
    public Result save(@RequestBody BpmLeave leave) {
        return bpmLeaveService.save(leave);
    }

    /**
     * 休假申请列表
     */
    @PostMapping("/myList")
    public Result myList(BpmLeave leave) {
        return bpmLeaveService.myList(leave);
    }


    /**
     * 休假待办任务列表
     */
    @PostMapping("/toDoList")
    public Result toDoList(BpmLeave leave) {
        return bpmLeaveService.toDoList(leave);
    }

    /**
     * 休假已办任务列表
     */
    @PostMapping("/doneList")
    public Result doneList(BpmLeave leave) {
        return bpmLeaveService.doneList(leave);
    }

    /**
     * 审核
     */
    @PostMapping("/audit")
    public Result audit(@RequestBody BpmEntity entity) {
        return bpmLeaveService.audit(entity);
    }

    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id) {
        return bpmLeaveService.get(id);
    }

}
