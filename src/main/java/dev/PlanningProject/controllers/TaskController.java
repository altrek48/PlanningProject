package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/base/task")
@Slf4j
public class TaskController {

    @Autowired
    TaskService taskService;

    //Создание плана
    @PostMapping(value = "create/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto createTask(@RequestBody TaskDto task, @PathVariable("id") Long group_id ) {
        log.info("Succesful");
        return taskService.createTask(task, group_id);
    }

    //Изменение плана
    @PutMapping(value = "change", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto changeTask(@RequestBody TaskDto task) {
        log.info("Zapros prinyat");
        return  taskService.changeTask(task);
    }

    //Удаление плана
    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long deleteTask(@PathVariable("id") Long task_id) {
        return taskService.deleteTask(task_id);
    }




}
