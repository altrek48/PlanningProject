package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.services.GroupService;
import jakarta.validation.Valid;
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
@RequestMapping("api/base/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto createGroup(@Valid @RequestBody GroupDto group) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.createGroup(group, username);
    }


    @DeleteMapping(value = "delete/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@groupService.isUserCreator(authentication.name, #groupId)")
    public Long deleteGroup(@PathVariable("groupId") Long groupId) {
        return groupService.deleteGroupById(groupId);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.getAllGroups(username);
    }

    @GetMapping(value = "isCreator/{groupId}")
    public boolean isGroupCreator(@PathVariable("groupId") Long groupId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.isUserCreator(username, groupId);
    }

}
