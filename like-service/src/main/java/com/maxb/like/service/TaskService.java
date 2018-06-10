package com.maxb.like.service;

import com.maxb.like.domain.Task;

import java.util.List;

public interface TaskService {

    void incrementTaskCount(String taskId, String userActionId);

    void addNewTask(Task task);

    void updateTask(Task task);

    void deleteTask(String taskId);

    List<Task> getTasks(String userId, Task.TaskStatus taskStatus);

    List<Task> getEarnTasks(String userId, String bdateString,
                            int sex, int vkStatus, int countryId, int cityId);

    void clearAll();
}
