package dev.PlanningProject.controllers;

import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/base/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    @PostMapping(value = "add/{groupId}", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("@groupService.isUserCreator(authentication.name, #groupId)")
    void addUserToGroup(@RequestBody String username, @PathVariable Long groupId) {
        userService.addUserToGroup(groupId, username);
    }


}
