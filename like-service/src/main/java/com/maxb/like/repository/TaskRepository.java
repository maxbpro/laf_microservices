package com.maxb.like.repository;

import com.maxb.like.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom {

    Task findByUserIdAndItemIdAndPageId(String userId, String itemId, String pageId);

    List<Task> findAllByUserIdAndStatus(String userId, Task.TaskStatus taskStatus);

}
