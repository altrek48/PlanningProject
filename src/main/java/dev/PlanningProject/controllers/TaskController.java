package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/base/task")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final GroupService groupService;

    //Создание плана
    @PostMapping(value = "create/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@groupService.isUserInGroup(authentication.name, #groupId)")
    TaskDto createTask(@Valid @RequestBody TaskDto task, @PathVariable("groupId") Long groupId ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.createTask(task, groupId, username);
    }

    //Изменение плана
    @PutMapping(value = "change/{groupId}/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@taskService.canUserAccessTask(authentication.name, #groupId, #taskId)")
    TaskDto changeTask(@Valid @RequestBody TaskDto task, @PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId) {
        return  taskService.changeTask(task);
    }

    //Удаление плана
    @DeleteMapping(value = "delete/{groupId}/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@taskService.canUserDeleteTask(authentication.name, #groupId, #taskId)")
    Long deleteTask(@PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping(value = "get/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@groupService.isUserInGroup(authentication.name, #groupId)")
    List<TaskShortDto> getTasks(@PathVariable("groupId") Long groupId) {
        return taskService.getTasks(groupId);
    }

    @GetMapping(value = "getOne/{groupId}/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@taskService.canUserAccessTask(authentication.name, #groupId, #taskId)")
    TaskDto getTask(@PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId) {
        return taskService.getTask(taskId);
    }

}
