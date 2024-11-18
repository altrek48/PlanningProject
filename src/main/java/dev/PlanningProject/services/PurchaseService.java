package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
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

    public PurchaseEntity createPurchase(PurchaseEntity newPurchase, Long group_id) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
        newPurchase.setGroup(group);
        purchaseRepository.save(newPurchase);
        productService.createProducts(newPurchase.getProducts(), newPurchase);

        return newPurchase;
    }

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
