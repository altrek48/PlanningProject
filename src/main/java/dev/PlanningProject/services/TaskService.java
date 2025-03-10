package dev.PlanningProject.services;

import dev.PlanningProject.annotations.SaveLog;
import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.*;
import dev.PlanningProject.mappers.ListTaskMapper;
import dev.PlanningProject.mappers.TaskMapper;
import dev.PlanningProject.repositories.ProductRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.repositories.CredentialsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ListTaskMapper listTaskMapper;
    private final CredentialsRepository credentialsRepository;
    private final GroupService groupService;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    @SaveLog(action = "PERSIST")
    public TaskDto createTask(TaskDto task,Long groupId, String username) {
        UserEntity userCreator = credentialsRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        TaskEntity newTask = taskMapper.toTaskEntity(task, groupId, userCreator);
        if(task.getProducts() != null) {
            connectProducts(newTask);
        }
        return taskMapper.toTaskDto(taskRepository.save(newTask));
    }

    @SaveLog(action = "REMOVE", entityClass = "TaskId")
    public Long deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        return taskId;
    }

    @SaveLog(action = "UPDATE")
    public TaskDto changeTask(TaskDto task) {
        TaskEntity previousTask = taskRepository.findByIdWithProducts(task.getId())
                .orElseThrow(() -> new EntityNotFoundException("task с переданным id не найден"));
        Map<Long, PurchaseEntity> previousPurchaseLinks = getPurchaseLinks(previousTask);
        TaskEntity changingTask = taskMapper.toTaskEntity(task);
        resetLinkedProducts(changingTask, getLinkedProductsConnection(task));
        if(task.getProducts() != null) {
            connectProducts(changingTask);
            updateTaskDetails(changingTask, foundAmount(changingTask));
            updatePurchaseLinks(previousPurchaseLinks, changingTask);
        }
        else {
            changingTask.setAmount(BigDecimal.ZERO);
            changingTask.setCompleteness(0);
            removeAllPurchaseLinks(changingTask);
        }
        return taskMapper.toTaskDto(taskRepository.save(changingTask));
    }

    private Map<Long, PurchaseEntity> getPurchaseLinks(TaskEntity task) {
        return task.getProducts().stream()
                .map(ProductInPlaneEntity::getLinkedProduct)
                .filter(Objects::nonNull)
                .map(ProductEntity::getPurchase)
                .collect(Collectors.toMap(
                        PurchaseEntity::getId,
                        Function.identity()
                ));
    }

    //Обновление связи покупки и таска(удаление связи для покупок, продукты которых были связаны с удаленными продуктами в таске)
    private void updatePurchaseLinks(Map<Long, PurchaseEntity> previousLinks, TaskEntity updatedTask) {
        Set<Long> newPurchaseIds = updatedTask.getProducts().stream()
                .map(ProductInPlaneEntity::getLinkedProduct)
                .filter(Objects::nonNull)
                .map(product -> product.getPurchase().getId())
                .collect(Collectors.toSet());
        previousLinks.keySet().removeAll(newPurchaseIds);//Удаление совпавших id покупок(остаются только id покупок для последующего разрыва связи)
        previousLinks.values().forEach(purchase -> {
            purchase.setLinkedTask(null);
            purchaseRepository.save(purchase);
        });
    }

    private void removeAllPurchaseLinks(TaskEntity task) {
        task.getPurchases().forEach(purchase -> {
            purchase.setLinkedTask(null);
            purchaseRepository.save(purchase);
        });
    }

    public void connectProducts(TaskEntity task) {
        List<ProductInPlaneEntity> products = task.getProducts();
        for(ProductInPlaneEntity product : products) {
            product.setTask(task);
        }
    }

    public Map<Long, Long> getLinkedProductsConnection(TaskDto taskDto) {
        return taskDto.getProducts()
                .stream()
                .filter(product -> product.getLinkedProductId() != null)
                .collect(Collectors.toMap(ProductInPlaneDto::getId, ProductInPlaneDto::getLinkedProductId));
    }

    public void resetLinkedProducts(TaskEntity task, Map<Long, Long> linkedProductsConnection) {
        List<Long> productsIds = new ArrayList<>(linkedProductsConnection.values());
        List<ProductEntity> productsEntity = productRepository.getProductsByIds(productsIds);
        for(ProductInPlaneEntity productInPlane: task.getProducts()) {
            if(linkedProductsConnection.containsKey(productInPlane.getId())) {
                Long productId = linkedProductsConnection.get(productInPlane.getId());
                productInPlane.setLinkedProduct(findProductById(productsEntity, productId));
            }
        }
    }

    public BigDecimal foundAmount(TaskEntity task) {
        BigDecimal newAmount = BigDecimal.ZERO;
        for(ProductInPlaneEntity productInPlane: task.getProducts()) {
            if(productInPlane.getLinkedProduct() != null) {
                newAmount = newAmount.add(productInPlane.getPrice());
            }
        }
        return newAmount;
    }

    public ProductEntity findProductById(List<ProductEntity> products, Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public List<TaskShortDto> getTasks(Long groupId) {
        return listTaskMapper.toListTaskShortDto(taskRepository.findAllByGroupId(groupId));
    }

    public TaskDto getTask(Long taskId) {
        return taskMapper.toTaskDto(taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task с переданным значение не найден")));
    }

    public  Boolean canUserAccessTask(String username, Long groupId, Long taskId) {
        if(taskRepository.isTaskInGroup(groupId, taskId)) {
            return groupService.isUserInGroup(username, groupId);
        }
        else return false;
    }

    public Boolean canUserDeleteTask(String username, Long groupId,Long taskId) {
        if(this.isTaskInGroup(groupId, taskId)) {
            return this.isTaskCreator(taskId, username);
        }
        else return false;
    }

    public void updateTaskDetails(TaskEntity task, BigDecimal newAmount) {
        updateTaskAmount(task, newAmount);
        updateTaskCompleteness(task);
    }

    public void updateTaskDetailsAfterAddPurchase(TaskEntity task, BigDecimal addedValue) {
        increaseTaskAmount(task, addedValue);
        updateTaskCompleteness(task);
    }

    public void increaseTaskAmount(TaskEntity task, BigDecimal addedValue) {
        BigDecimal currentAmount = task.getAmount() == null ? BigDecimal.ZERO : task.getAmount();
        BigDecimal updatedAmount = currentAmount.add(addedValue);
        task.setAmount(updatedAmount);
    }

    public void updateTaskCompleteness(TaskEntity task) {
        List<ProductInPlaneEntity> products = task.getProducts();
        int linkedProductsCount = 0;
        for (ProductInPlaneEntity product : products) {
            if (product.getLinkedProduct() != null) linkedProductsCount++;
        }
        if (linkedProductsCount > 0) {
            int completeness = (linkedProductsCount * 100) / products.size();
            task.setCompleteness(completeness);
        }
        else task.setCompleteness(0);
    }

    public void updateTaskAmount(TaskEntity task, BigDecimal newAmount) {
        task.setAmount(newAmount);
    }

    public Boolean isTaskInGroup(Long groupId, Long taskId) {
        return taskRepository.isTaskInGroup(groupId, taskId);
    }

    public Boolean isTaskCreator(Long taskId, String username) {
        return taskRepository.isTaskCreator(taskId, username);
    }

}
