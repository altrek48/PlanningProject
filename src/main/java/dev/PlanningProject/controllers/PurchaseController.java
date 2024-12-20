package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.services.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/base/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    //Добавления покупки вне плана
    @PostMapping(value = "create/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseDto createPurchase(@Valid @RequestBody PurchaseDto purchase, @PathVariable("groupId") Long groupId) {
        return purchaseService.createPurchase(purchase, groupId);
    }

    //Добавление покупки через план
    @PostMapping(value = "create/{group_id}/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseDto createPurchaseByPlan(@Valid @RequestBody PurchaseDto purchase, @PathVariable("group_id") Long group_id, @PathVariable("task_id") Long task_id) {
        return purchaseService.createPurchaseInTask(purchase, group_id, task_id);
    }

}
