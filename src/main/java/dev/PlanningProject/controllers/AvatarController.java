package dev.PlanningProject.controllers;

import dev.PlanningProject.services.AvatarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/base/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return avatarService.uploadFile(file, username);
    }

    @GetMapping("/get/{filename}")
    public String getAvatarUrl(@PathVariable String filename) {
        return avatarService.getFileUrl(filename);
    }

}
