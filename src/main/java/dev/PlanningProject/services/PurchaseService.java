package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.mappers.ListPurchaseMapper;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final ListPurchaseMapper listPurchaseMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public PurchaseDto createPurchase(PurchaseDto purchase, Long groupId, String username) {
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId);
        newPurchase.setAmount(tuneProducts(newPurchase));
        newPurchase.setDate(LocalDateTime.now());
        newPurchase.setUserPayer(userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found")));
        PurchaseEntity savedPurchase = purchaseRepository.save(newPurchase);
        return purchaseMapper.toPurchaseDto(savedPurchase);
    }

    //todo доделать
    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long groupId, Long taskId, String username) {
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId);
        //todo
        newPurchase.setAmount(tuneProducts(newPurchase));
        newPurchase.setDate(LocalDateTime.now());
        newPurchase.setUserPayer(userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found")));
        this.connectProducts(newPurchase, taskId);
        PurchaseEntity savedPurchase = purchaseRepository.save(newPurchase);
        return purchaseMapper.toPurchaseDto(savedPurchase);
    }

    //Привязка продукта к покупке и получение суммы всех продуктов
    public BigDecimal tuneProducts(PurchaseEntity purchase) {
        List<ProductEntity> products = purchase.getProducts();
        BigDecimal amount = BigDecimal.valueOf(0);
        for(ProductEntity product : products) {
            if(product.getPrice() != null && product.getName() != null) {
                product.setPurchase(purchase);
                amount = amount.add(product.getPrice());
            }
            else throw new IllegalArgumentException("product price and name cannot be null");
        }
        return amount;
    }

    //todo Set or Map
    public void connectProducts(PurchaseEntity purchase, Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task с переданным значение не найден"));
        for(ProductEntity product: purchase.getProducts()) {
            for(ProductInPlaneEntity productInPlane: task.getProducts()) {
                if(Objects.equals(product.getName(), productInPlane.getName())) {
                    product.setProductInPlane(productInPlane);
                    productInPlane.setLinkedProduct(product);
                    productInPlane.setCompleteness(true);
                    productInPlane.setPrice(product.getPrice());
                    if(purchase.getLinkedTask() == null) {
                        purchase.setLinkedTask(task);
                        task.getLinkedPurchases().add(purchase);
                    }
                }
            }
        }
    }

    public List<PurchaseShortDto> getAllPurchases(Long groupId) {
        List<PurchaseEntity> purchases = purchaseRepository.findAllByGroupId(groupId);
        return listPurchaseMapper.toListPurchaseShortDto(purchases);
    }

    public PurchaseDto getPurchase(Long purchaseId) {
        PurchaseEntity purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("purchase с id %d не найден", purchaseId)));
        return purchaseMapper.toPurchaseDto(purchase);
    }

}
