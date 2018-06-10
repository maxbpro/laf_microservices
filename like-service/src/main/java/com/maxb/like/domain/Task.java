package com.maxb.like.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "tasks")
public class Task {

    public enum TaskStatus {
        PENDING,
        RUNNING,
        PAUSED
    }


    @Id
    private String id;

    private String userId;
    private String pageId;
    private String itemId;

    private int currentCount = 0;
    private int maxCount = 0;
    private int priority = 0;

    private int sex = 0;
    private int vkStatus = 0;
    private int ageFrom = 0;
    private int ageTo = 0;

    private int countryId = 0;
    private int cityId = 0;
    private String countryTitle = null;
    private String cityTitle = null;

    private TaskStatus status = TaskStatus.RUNNING;

    private String img = null;
    private String title = null;
    private String desc = null;

    private List<String> actionUserIds = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime created = LocalDateTime.now();
}
