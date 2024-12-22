package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.services.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    //Добавления покупки вне плана
    @PostMapping(value = "create/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    PurchaseDto createPurchase(@Valid @RequestBody PurchaseDto purchase, @PathVariable("groupId") Long groupId) {
        return purchaseService.createPurchase(purchase, groupId);
    }

    //Добавление покупки через план
    @PostMapping(value = "create/{groupId}/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    PurchaseDto createPurchaseByPlan(@Valid @RequestBody PurchaseDto purchase, @PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId) {
        return purchaseService.createPurchaseInTask(purchase, groupId, taskId);
    }

    @GetMapping(value = "getAll/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PurchaseShortDto> getAllPurchases(@PathVariable("groupId") Long groupId) {
        return purchaseService.getAllPurchases(groupId);
    }

}
