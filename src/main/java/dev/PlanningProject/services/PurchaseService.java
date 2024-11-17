package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        productService.createProducts(newPurchase.getProducts(), newPurchase);

        return purchaseRepository.save(newPurchase);
    }

}
