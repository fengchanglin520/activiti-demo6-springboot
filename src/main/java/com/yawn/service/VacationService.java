package com.yawn.service;

import com.yawn.entity.VacTask;
import com.yawn.entity.Vacation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Created by yawn on 2018-01-08 13:44
 */
@Service
public class VacationService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;

    public Object startVac(String userName, Vacation vac) {

        identityService.setAuthenticatedUserId(userName);

        // 开始流程
        ProcessInstance vacationInstance = runtimeService.startProcessInstanceByKey("vacationProcess");

        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(vacationInstance.getId()).singleResult();

        // 申明任务
        taskService.claim(currentTask.getId(), userName);

        Map<String, Object> vars = new HashMap<>(4);
        vars.put("days", vac.getDays());
        vars.put("reason", vac.getReason());

        taskService.complete(currentTask.getId(), vars);

        return true;
    }

    public Object myVac(String userName) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().startedBy(userName).list();
        List<Vacation> vacList = new ArrayList<>();
        for (ProcessInstance instance : instanceList) {
            Vacation vac = getVac(instance);
            vacList.add(vac);
        }
        return vacList;
    }

    private Vacation getVac(ProcessInstance instance) {
        Integer days = runtimeService.getVariable(instance.getId(), "days", Integer.class);
        String reason = runtimeService.getVariable(instance.getId(), "reason", String.class);
        Vacation vac = new Vacation();
        vac.setApplyUser(instance.getStartUserId());
        vac.setDays(days);
        vac.setReason(reason);
        Date startTime = instance.getStartTime(); // activiti 6 才有
        vac.setApplyTime(startTime);
        vac.setApplyStatus(instance.isEnded() ? "申请结束" : "等待审批");
        return vac;
    }


    public Object myAudit(String userName) {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userName).list();
//        / 多此一举 taskList中包含了以下内容(用户的任务中包含了所在用户组的任务)
//        Group group = identityService.createGroupQuery().groupMember(userName).singleResult();
//        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(group.getId()).list();
//        taskList.addAll(list);
        List<VacTask> vacTaskList = new ArrayList<>();
        for (Task task : taskList) {
            VacTask vacTask = new VacTask();
            vacTask.setId(task.getId());
            vacTask.setName(task.getName());
            vacTask.setCreateTime(task.getCreateTime());
            String instanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            Vacation vac = getVac(instance);
            vacTask.setVac(vac);
            vacTaskList.add(vacTask);
        }
        return vacTaskList;
    }

    public Object passAudit(String userName, String taskId) {
        taskService.claim(taskId, userName);
        taskService.complete(taskId);
        return true;
    }
}
