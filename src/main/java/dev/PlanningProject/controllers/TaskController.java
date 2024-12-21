package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @PostMapping(value = "create/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto createTask(@Valid @RequestBody TaskDto task, @PathVariable("groupId") Long groupId ) {
        log.info("Succesful");
        return taskService.createTask(task, groupId);
    }

    //Изменение плана
    @PutMapping(value = "change", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto changeTask(@Valid @RequestBody TaskDto task) {
        log.info("Changing...");
        return  taskService.changeTask(task);
    }

    //Удаление плана
    @DeleteMapping(value = "delete/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long deleteTask(@PathVariable("taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping(value = "get/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<TaskShortDto> getTasks(@PathVariable("groupId") Long groupId) {
        return taskService.getTasks(groupId);
    }

    @GetMapping(value = "getOne/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return taskService.getTask(taskId);
    }

}
