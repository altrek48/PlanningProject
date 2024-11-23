package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final GroupRepository groupRepository;

    private final PurchaseRepository purchaseRepository;

    private final ProductService productService;

    private final PurchaseMapper purchaseMapper;

    private final TaskRepository taskRepository;

    public PurchaseDto createPurchase(PurchaseDto purchase, Long group_id) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setGroup(group);
        purchaseRepository.save(newPurchase);
        productService.createProducts(purchase, newPurchase.getId());

        return purchaseMapper.toPurchaseDto(newPurchase);
    }

    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long group_id, Long task_id) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setGroup(group);
        purchaseRepository.save(newPurchase);
        productService.createProducts(purchase, newPurchase.getId());
        if(productService.checkCoincidences(newPurchase.getId(), task_id)) {
            TaskEntity task = taskRepository.getReferenceById(task_id);
            newPurchase.setTask(task);
            purchaseRepository.save(newPurchase);
        }

        return purchaseMapper.toPurchaseDtoWithPlane(newPurchase);
    }

    public void deletePurchase(Long purchase_id) {
        productService.deleteAllProducts(purchase_id);
        purchaseRepository.deleteById(purchase_id);
    }

    public void deleteAllPurchasesInGroup(GroupEntity group) {
        List<PurchaseEntity> purchases = group.getPurchases();
        for(PurchaseEntity purchase : purchases) {
            deletePurchase(purchase.getId());
        }
    }

}
