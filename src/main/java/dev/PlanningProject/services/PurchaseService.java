package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.mappers.ListPurchaseMapper;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final ListPurchaseMapper listPurchaseMapper;
    private final UserRepository userRepository;


    public PurchaseDto createPurchase(PurchaseDto purchase, Long groupId, String username) {
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setGroupId(groupId);
        tuneProducts(newPurchase);
        newPurchase.setDate(LocalDateTime.now());
        newPurchase.setUserPayer(userRepository.getUserByUsername(username));
        PurchaseEntity savedPurchase = purchaseRepository.save(newPurchase);
        return purchaseMapper.toPurchaseDto(savedPurchase);
    }

    //todo доделать
    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long group_id, Long task_id) {
        return null;
    }


    public void tuneProducts(PurchaseEntity purchase) {
        List<ProductEntity> products = purchase.getProducts();
        for(ProductEntity product : products) {
            product.setPurchase(purchase);
        }
    }

    public List<PurchaseShortDto> getAllPurchases(Long groupId) {
        List<PurchaseEntity> purchases = purchaseRepository.findAllByGroupId(groupId);
        return listPurchaseMapper.toListPurchaseShortDto(purchases);
    }

    public PurchaseDto getPurchase(Long purchaseId) {
        PurchaseEntity purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("purchase с переданным id не найден"));
        return purchaseMapper.toPurchaseDto(purchase);
    }

}
