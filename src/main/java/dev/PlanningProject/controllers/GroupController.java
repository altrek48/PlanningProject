package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto createGroup(@Valid @RequestBody GroupDto group) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.createGroup(group, username);
    }


    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteGroup(@PathVariable("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //todo проверка является ли юзер создателем группы
        return groupService.deleteGroupById(id);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.getAllGroups(username);
    }

}
