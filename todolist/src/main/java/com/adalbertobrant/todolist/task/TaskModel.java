package com.adalbertobrant.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskModel {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
