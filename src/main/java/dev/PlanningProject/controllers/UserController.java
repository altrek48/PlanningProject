package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.UserProfile;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        userService.addUser(groupId, username);
    }

    @GetMapping(value = "getInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfile getUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserProfile(username);
    }

    @GetMapping(value = "getProfilesInGroup/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@groupService.isUserInGroup(authentication.name, #groupId)")
    List<UserProfile> getProfilesByGroupId(@PathVariable Long groupId) {
        return userService.getProfilesByGroupId(groupId);
    }

    @GetMapping(value = "getUsername", produces =  MediaType.TEXT_PLAIN_VALUE)
    String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PutMapping(value = "editEmail", produces =  MediaType.APPLICATION_JSON_VALUE)
    String editEmail(@RequestBody String newEmail) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.editEmail(newEmail, username);
    }

}
