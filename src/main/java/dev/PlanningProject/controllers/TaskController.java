package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/base/task")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //Создание плана
    @PostMapping(value = "create/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto createTask(@RequestBody TaskDto task, @PathVariable("id") Long groupId ) {
        log.info("Succesful");
        return taskService.createTask(task, groupId);
    }

    //Изменение плана
    @PutMapping(value = "change", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto changeTask(@RequestBody TaskDto task) {
        log.info("Zapros prinyat");
        return  taskService.changeTask(task);
    }

    //Удаление плана
    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long deleteTask(@PathVariable("id") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping(value = "get/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<TaskDto> getTasks(@PathVariable("groupId") Long groupId) {
        return taskService.getTasks(groupId);
    }

}
