package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PurchaseMapper purchaseMapper;

    public PurchaseDto createPurchase(PurchaseDto purchase, Long group_id) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setGroup(group);
        purchaseRepository.save(newPurchase);
        productService.createProducts(purchase, newPurchase.getId());

        return purchaseMapper.toPurchaseDto(newPurchase);
    }

//    public PurchaseEntity createPurchaseInTask(PurchaseEntity newPurchase, Long group_id, Long task_id) {
//        GroupEntity group = groupRepository.getReferenceById(group_id);
//        newPurchase.setGroup(group);
//        purchaseRepository.save(newPurchase);
//        productService.createProducts(newPurchase.getProducts(), newPurchase);
//        return newPurchase;
//    }

    public Long deletePurchase(Long purchase_id) {
        productService.deleteAllProducts(purchase_id);
        purchaseRepository.deleteById(purchase_id);
        return purchase_id;
    }

    public void deleteAllPurchasesInGroup(GroupEntity group) {
        List<PurchaseEntity> purchases = group.getPurchases();
        for(PurchaseEntity purchase : purchases) {
            deletePurchase(purchase.getId());
        }
    }

}
