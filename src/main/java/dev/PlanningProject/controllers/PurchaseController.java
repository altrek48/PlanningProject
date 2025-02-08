package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.PurchaseService;
import dev.PlanningProject.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final GroupService groupService;
    private final TaskService taskService;

    //Добавления покупки вне плана
    @PostMapping(value = "create/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@groupService.isUserInGroup(authentication.name, #groupId)")
    PurchaseDto createPurchase(@Valid @RequestBody PurchaseDto purchase, @PathVariable("groupId") Long groupId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return purchaseService.createPurchase(purchase, groupId, username);
    }

    //Добавление покупки через план
    @PostMapping(value = "create/{groupId}/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@taskService.canUserAccessTask(authentication.name, #groupId, #taskId)")
    PurchaseDto createPurchaseByPlan(@Valid @RequestBody PurchaseDto purchase, @PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return purchaseService.createPurchaseInTask(purchase, groupId, taskId, username);
    }

    @GetMapping(value = "getAll/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@groupService.isUserInGroup(authentication.name, #groupId)")
    List<PurchaseShortDto> getAllPurchases(@PathVariable("groupId") Long groupId) {
        return purchaseService.getAllPurchases(groupId);
    }

    @GetMapping(value = "get/{groupId}/{purchaseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@purchaseService.canUserAccessPurchase(authentication.name, #groupId, #purchaseId)")
    PurchaseDto getPurchase(@PathVariable("groupId") Long groupId, @PathVariable("purchaseId") Long purchaseId) {
        return purchaseService.getPurchase(purchaseId);
    }

    @GetMapping(value = "getPurchaseId/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Long getPurchaseIdByProductId(@PathVariable("productId") Long productId) {
        return purchaseService.getPurchaseIdByProductId(productId);
    }

}
