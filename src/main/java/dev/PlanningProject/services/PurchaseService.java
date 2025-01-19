package dev.PlanningProject.services;

import dev.PlanningProject.dtos.ProductDto;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId, LocalDateTime.now(), userPayer, findAmount(purchase));
        tuneProducts(newPurchase);
        return purchaseMapper.toPurchaseDto(purchaseRepository.save(newPurchase));
    }

    public PurchaseDto createPurchaseInTask(PurchaseDto purchase, Long groupId, Long taskId, String username) {
        UserEntity userPayer = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        PurchaseEntity newPurchase = purchaseMapper.toPurchaseEntity(purchase, groupId,LocalDateTime.now(), userPayer, findAmount(purchase));
        tuneProducts(newPurchase);
        this.connectProducts(newPurchase, taskId);
        return purchaseMapper.toPurchaseDto(purchaseRepository.save(newPurchase));
    }

    //Привязка продуктов к покупке
    public void tuneProducts(PurchaseEntity purchase) {
        List<ProductEntity> products = purchase.getProducts();
        for(ProductEntity product : products) {
            if(product.getPrice() != null && product.getName() != null) {
                product.setPurchase(purchase);
            }
            else throw new IllegalArgumentException("product price and name cannot be null");
        }
    }

    public BigDecimal findAmount(PurchaseDto purchase) {
        List<ProductDto> products = purchase.getProducts();
        BigDecimal amount = BigDecimal.valueOf(0);
        for(ProductDto product : products) {
            if(product.getPrice() != null && product.getName() != null) {
                amount = amount.add(product.getPrice());
            }
            else throw new IllegalArgumentException("product price and name cannot be null");
        }
        return  amount;
    }

    @Transactional
    public void connectProducts(PurchaseEntity purchase, Long taskId) {
        TaskEntity task = taskRepository.findByIdWithProducts(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task с переданным значение не найден"));
        BigDecimal addedValue = BigDecimal.valueOf(0);
        Map<String, ProductInPlaneEntity> productsInPlaneMap = getProductInPlaneMap(task);
        for (ProductEntity product: purchase.getProducts()) {
            ProductInPlaneEntity productInPlane = productsInPlaneMap.get(product.getName());
            if (productInPlane != null && !productInPlane.getCompleteness()) {
                connectProductAndTuneProductInPlane(product, productInPlane);
                addedValue = addedValue.add(product.getPrice());
                connectPurchase(task, purchase);
            }
        }
        if(!addedValue.equals(BigDecimal.valueOf(0))) {
            taskService.updateTaskDetailsAfterAddPurchase(task, addedValue);
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

    public Long getPurchaseIdByProductId(Long productId) {
        return purchaseRepository.findPurchaseIdByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("purchaseId with productId: " + productId + " not found"));
    }

    public void connectProductAndTuneProductInPlane(ProductEntity product, ProductInPlaneEntity productInPlane) {
        product.setProductInPlane(productInPlane);
        productInPlane.setLinkedProduct(product);
        productInPlane.setCompleteness(true);
        productInPlane.setPrice(product.getPrice());
    }

    public Map<String, ProductInPlaneEntity> getProductInPlaneMap(TaskEntity task) {
        return task.getProducts()
                .stream()
                .collect(Collectors.toMap(ProductInPlaneEntity::getName, product -> product));
    }

    public void connectPurchase(TaskEntity task, PurchaseEntity purchase) {
        if (purchase.getLinkedTask() == null) {
            purchase.setLinkedTask(task);
        }
    }

}
