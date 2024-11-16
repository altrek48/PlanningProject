package dev.PlanningProject.controllers;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/base/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupEntity createGroup(@RequestBody GroupEntity group) {
        return groupService.createGroup(group);
    }


    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteGroup(@PathVariable("id") Long id) {
        return groupService.deleteGroupById(id);
    }

}
