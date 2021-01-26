package com.tools.module.workflow.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.common.util.ShiroUtils;
import com.tools.module.sys.entity.SysUser;
import com.tools.module.sys.service.SysUserService;
import com.tools.module.workflow.entity.BpmEntity;
import com.tools.module.workflow.entity.BpmLeave;
import com.tools.module.workflow.entity.TaskLog;
import com.tools.module.workflow.repository.BpmLeaveRepository;
import com.tools.module.workflow.service.BpmLeaveService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 小柒2012
 * @Date 2022/9/9
 */
@Service
public class BpmLeaveServiceImpl implements BpmLeaveService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityservice;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private BpmLeaveRepository bpmLeaveRepository;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(BpmLeave leave) {
        SysUser user = ShiroUtils.getUserEntity();
        leave.setGmtCreate(DateUtils.getTimestamp());
        leave.setUserCreate(user.getUserId());
        leave.setNickname(user.getNickname());
        bpmLeaveRepository.save(leave);
        String businessKey = leave.getId().toString();
        identityservice.setAuthenticatedUserId(leave.getUserCreate().toString());
        ProcessInstance process = runtimeService
                .startProcessInstanceByKey("leave", businessKey);
        leave.setProcessInstanceId(process.getId());
        Task task = taskService.createTaskQuery().processInstanceId(process.getId()).active().singleResult();
        bpmLeaveRepository.saveAndFlush(leave);
        /**
         * 生产要设置成动态获取
         */
        String role = "HR";
        taskService.addCandidateGroup(task.getId(), role);
        /**
         * 保存日志
         */
        TaskLog log = new TaskLog();
        log.setUserCreate(user.getUserId());
        log.setNickname(user.getNickname());
        log.setComments("");
        log.setGmtCreate(DateUtils.getTimestamp());
        log.setStartTime(log.getGmtCreate());
        log.setTaskName("发起");
        log.setTaskId("");
        log.setProcessInstanceId(process.getId());
        dynamicQuery.save(log);
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result audit(BpmEntity entity) {
        SysUser user = ShiroUtils.getUserEntity();
        String taskId = entity.getTaskId();
        String approve = entity.getApprove();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        /**
         * 认领完成任务
         */
        Map<String,Object> variables=new HashMap<>(6);
        variables.put("approve", approve);
        taskService.claim(taskId, ShiroUtils.getUserId().toString());
        taskService.complete(taskId, variables);
        TaskLog log = new TaskLog();
        log.setUserCreate(user.getUserId());
        log.setNickname(user.getNickname());
        log.setComments(entity.getComment());
        log.setGmtCreate(DateUtils.getTimestamp());
        log.setStartTime(log.getGmtCreate());
        log.setTaskName(task.getName());
        log.setTaskId(taskId);
        log.setProcessInstanceId(task.getProcessInstanceId());
        dynamicQuery.save(log);
        return Result.ok();
    }

    @Override
    public Result get(Long id) {
        BpmLeave leave = bpmLeaveRepository.getOne(id);
        return Result.ok(leave);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result myList(BpmLeave leave) {
        Long userId = ShiroUtils.getUserId();
        PageBean<BpmLeave> data = new PageBean<>();
        long count = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("leave").startedBy(userId.toString())
                .count();
        if (count > 0) {
            Integer pageSize = leave.getPageSize();
            Integer firstResult = (leave.getPageNo()) * pageSize;
            List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey("leave").startedBy(userId.toString())
                    .listPage(firstResult, pageSize);
            List<BpmLeave> listLeave = new ArrayList<>();
            list.forEach(instance->{
                String nativeSql = "SELECT * FROM bpm_leave WHERE process_instance_id=?";
                BpmLeave entity = dynamicQuery.nativeQuerySingleResult(BpmLeave.class,nativeSql,new Object[]{instance.getId()});
                entity.setStatus(instance.getEndTime()==null?"进行中":"已完成");
                listLeave.add(entity);
            });
            data = new PageBean(listLeave, count);
        }
        return Result.ok(data);
    }

    @Override
    public Result toDoList(BpmLeave leave) {
        Long userId = ShiroUtils.getUserId();
        PageBean<BpmLeave> data = new PageBean<>();
        long count = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userId.toString()).count();
        if(count>0){
            Integer pageSize = leave.getPageSize();
            Integer firstResult = (leave.getPageNo()) * pageSize;
            List<Task> tasks = taskService.createTaskQuery()
                    .taskCandidateOrAssigned(userId.toString())
                    .listPage(firstResult,pageSize);
            List<BpmLeave> listLeave = new ArrayList<>();
            tasks.forEach(task->{
                String processInstanceId = task.getProcessInstanceId();
                String nativeSql = "SELECT * FROM bpm_leave WHERE process_instance_id=?";
                BpmLeave entity = dynamicQuery.nativeQuerySingleResult(BpmLeave.class,nativeSql,new Object[]{processInstanceId});
                entity.setTaskId(task.getId());
                listLeave.add(entity);
            });
            data = new PageBean(listLeave, count);
        }
        return Result.ok(data);
    }

    @Override
    public Result doneList(BpmLeave leave) {
        Long userId = ShiroUtils.getUserId();
        PageBean<BpmLeave> data = new PageBean<>();
        long count = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId.toString())
                .finished()
                .count();
        if(count>0){
            Integer pageSize = leave.getPageSize();
            Integer firstResult = (leave.getPageNo()) * pageSize;
            List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(userId.toString())
                    .finished()
                    .listPage(firstResult,pageSize);
            List<BpmLeave> listLeave = new ArrayList<>();
            tasks.forEach(task->{
                String processInstanceId = task.getProcessInstanceId();
                String nativeSql = "SELECT * FROM bpm_leave WHERE process_instance_id=?";
                BpmLeave entity = dynamicQuery.nativeQuerySingleResult(BpmLeave.class,nativeSql,new Object[]{processInstanceId});
                entity.setTaskId(task.getId());
                listLeave.add(entity);
            });
            data = new PageBean(listLeave, count);
        }
        return Result.ok(data);
    }
}
