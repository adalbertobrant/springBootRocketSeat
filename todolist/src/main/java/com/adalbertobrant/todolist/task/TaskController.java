package com.adalbertobrant.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        var initialTask = taskModel.getStartAt();
        var endTask = taskModel.getEndAt();

        if (currentDate.isAfter(initialTask) || currentDate.isAfter(endTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início ou término " +
                    "devem ser maior que data atual");
        }
        if (initialTask.isAfter(endTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }
        var task = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
