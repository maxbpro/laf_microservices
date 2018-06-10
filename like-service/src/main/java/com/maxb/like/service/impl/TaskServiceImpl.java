package com.maxb.like.service.impl;

import com.maxb.like.domain.Task;
import com.maxb.like.events.models.BalanceChangeModel;
import com.maxb.like.events.source.SourceBeanImpl;
import com.maxb.like.repository.TaskRepository;
import com.maxb.like.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    SourceBeanImpl sourceBean;

    @Override
    public void incrementTaskCount(String taskId, String userActionId) {

        Task task = taskRepository.findOne(taskId);

        //TODO stop if balance is 0 and send a notification

        if(task != null){
            task.setCurrentCount(task.getCurrentCount() + 1);
            taskRepository.save(task);
        }

        //1. send notification about increase balance
        //TODO think about increase value. Should it be 100% or 50%?
        log.debug("send INCREASE event for" + userActionId);
        //sourceBean.publishBalanceChange(BalanceChangeModel.Action.INCREASE, userActionId, task.getActionPrice() / 2);
        sourceBean.publishBalanceChange(BalanceChangeModel.Action.INCREASE, userActionId, 1400);


        //2. send notification about decrease balance
        log.debug("send DECREASE event for" + task.getUserId());
        sourceBean.publishBalanceChange(BalanceChangeModel.Action.DECREASE, task.getUserId(), 500);
        //sourceBean.publishBalanceChange(BalanceChangeModel.Action.DECREASE, task.getUserId(), task.getActionPrice());

    }

    @Override
    public void addNewTask(Task task) {
        Task oldTask = taskRepository.findByUserIdAndItemIdAndPageId(task.getUserId(), task.getItemId(), task.getPageId());
        if(oldTask == null)
           taskRepository.save(task);
    }

    @Override
    public void updateTask(Task task) {
        Task oldTask = taskRepository.findByUserIdAndItemIdAndPageId(task.getUserId(), task.getItemId(), task.getPageId());
        if(oldTask != null)
            taskRepository.save(task);
    }

    @Override
    public void deleteTask(String taskId) {
        Task oldTask = taskRepository.findOne(taskId);
        if(oldTask != null)
            taskRepository.delete(oldTask);
    }

    @Override
    public List<Task> getTasks(String userId, Task.TaskStatus taskStatus) {
        return taskRepository.findAllByUserIdAndStatus(userId, taskStatus);
    }


    @Override
    public List<Task> getEarnTasks(String userId, String bdateString,
                                   int sex, int vkStatus, int countryId, int cityId) {
        return taskRepository.findAllRunningTasksExceptUser(userId, bdateString, sex, vkStatus, countryId, cityId);
    }

    @Override
    public void clearAll() {
        taskRepository.deleteAll();
    }
}
