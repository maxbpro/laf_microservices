package com.maxb.like.repository;

import com.maxb.like.domain.Task;

import java.util.List;

public interface TaskRepositoryCustom {
    List<Task> findAllRunningTasksExceptUser(String userId, String bdateString,
                                             int sex, int vkStatus, int countryId, int cityId);
}
