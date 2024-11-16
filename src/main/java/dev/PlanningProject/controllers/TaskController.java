package dev.PlanningProject.controllers;

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

    @PostMapping(value = "create/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskEntity createTask(@RequestBody TaskEntity task, @PathVariable("id") Long group_id ) {
        log.info("Succesful");
        return taskService.createTask(task, group_id);
    }

    @PutMapping(value = "change", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskEntity changeTask(@RequestBody TaskEntity task) {
        log.info("Zapros prinyat");
        return  taskService.changeTask(task);
    }




}
