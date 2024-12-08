package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto createGroup(@RequestBody GroupDto group) {
        return groupService.createGroup(group);
    }


    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteGroup(@PathVariable("id") Long id) {
        return groupService.deleteGroupById(id);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups() {
        return groupService.getAllGroups();
    }

}
