package dev.PlanningProject.controllers;

import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    //Добавления покупки вне плана
    @PostMapping(value = "create/{group_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseEntity createPurchase(@RequestBody PurchaseEntity purchase, @PathVariable("group_id") Long group_id) {
        return purchaseService.createPurchase(purchase, group_id);
    }

    //Добавление покупки через план
    @PostMapping(value = "create/{group_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseEntity createPurchase(@RequestBody PurchaseEntity purchase, @PathVariable("group_id") Long group_id, @RequestBody TaskEntity task) {
        return purchaseService.createPurchase(purchase, group_id);
    }

}