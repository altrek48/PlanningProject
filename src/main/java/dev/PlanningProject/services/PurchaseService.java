package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.*;
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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final ListPurchaseMapper listPurchaseMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final GroupService groupService;
    private final TaskService taskService;


    public PurchaseDto createPurchase(PurchaseDto purchase, Long groupId, String username) {
        UserEntity userPayer = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId, LocalDateTime.now(), userPayer);
        newPurchase.setAmount(tuneProducts(newPurchase));
        return purchaseMapper.toPurchaseDto(purchaseRepository.save(newPurchase));
    }

    //todo доделать
    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long groupId, Long taskId, String username) {
        UserEntity userPayer = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId,LocalDateTime.now(), userPayer);
        //todo
        newPurchase.setAmount(tuneProducts(newPurchase));
        this.connectProducts(newPurchase, taskId);
        return purchaseMapper.toPurchaseDto(purchaseRepository.save(newPurchase));
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
        BigDecimal addedValue = BigDecimal.valueOf(0);
        Map<String, ProductInPlaneEntity> productsInPlaneMap = task.getProducts()
                .stream()
                .collect(Collectors.toMap(ProductInPlaneEntity::getName, product -> product));
        for (ProductEntity product: purchase.getProducts()) {
            ProductInPlaneEntity productInPlane = productsInPlaneMap.get(product.getName());
            if (productInPlane != null) {
                product.setProductInPlane(productInPlane);
                productInPlane.setLinkedProduct(product);
                productInPlane.setCompleteness(true);
                productInPlane.setPrice(product.getPrice());
                addedValue = addedValue.add(product.getPrice());
                if (purchase.getLinkedTask() == null) {
                    purchase.setLinkedTask(task);
                    task.getLinkedPurchases().add(purchase);
                }
            }
        }
        if(!addedValue.equals(BigDecimal.valueOf(0))) {
            taskService.updateTaskDetails(task, addedValue);
        }
    }

    public List<PurchaseShortDto> getAllPurchases(Long groupId) {
        return listPurchaseMapper.toListPurchaseShortDto(purchaseRepository.findAllByGroupId(groupId));
    }

    public PurchaseDto getPurchase(Long purchaseId) {
        return purchaseMapper.toPurchaseDto(purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("purchase с id %d не найден", purchaseId))));
    }

    public Boolean canUserAccessPurchase(String username, Long groupId, Long purchaseId) {
        if(purchaseRepository.isPurchaseInGroup(groupId, purchaseId)) {
            return groupService.isUserInGroup(username, groupId);
        }
        else return false;
    }

}
