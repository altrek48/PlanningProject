package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;


    public PurchaseDto createPurchase(PurchaseDto purchase, Long groupId) {
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setGroupId(groupId);
        tuneProducts(newPurchase);
        PurchaseEntity savedPurchase = purchaseRepository.save(newPurchase);
        return purchaseMapper.toPurchaseDto(savedPurchase);
    }

    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long group_id, Long task_id) {
        return null;
    }


    public void tuneProducts(PurchaseEntity purchase) {
        List<ProductEntity> products = purchase.getProducts();
        for(ProductEntity product : products) {
            product.setPurchase(purchase);
        }
    }

}
