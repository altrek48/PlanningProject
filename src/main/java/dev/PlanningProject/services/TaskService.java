package dev.PlanningProject.services;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.mappers.ListTaskMapper;
import dev.PlanningProject.mappers.TaskMapper;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.valueOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ListTaskMapper listTaskMapper;
    private final UserRepository userRepository;
    private final GroupService groupService;


    public TaskDto createTask(TaskDto task,Long groupId, String username) {
        UserEntity userCreator = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        TaskEntity newTask = taskMapper.toTaskEntity(task, groupId, userCreator);
        if(task.getProducts() != null) {
            tuneProducts(newTask);
        }
        return taskMapper.toTaskDto(taskRepository.save(newTask));
    }


    public Long deleteTask(Long task_id) {
        taskRepository.deleteById(task_id);
        return task_id;
    }

    //todo Убрать связь покупки и таска при удалении связанного продукта, пересчет итоговой стоимости и completeness
    public TaskDto changeTask(TaskDto task) {
        TaskEntity changingTask = taskMapper.toTaskEntity(task);
        if(task.getProducts() != null) {
            tuneProducts(changingTask);
        }
        else {
            changingTask.setAmount(BigDecimal.ZERO);
            changingTask.setCompleteness(0);
            changingTask.setLinkedPurchases(null);
        }
        return taskMapper.toTaskDto(taskRepository.save(changingTask));
    }


    public void tuneProducts(TaskEntity task) {
        List<ProductInPlaneEntity> products = task.getProducts();
        for(ProductInPlaneEntity product : products) {
            product.setTask(task);
        }
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

    public void updateTaskDetails(TaskEntity task, BigDecimal addedValue) {
        updateTaskAmount(task, addedValue);
        updateTaskCompleteness(task);
    }

    public void updateTaskAmount(TaskEntity task, BigDecimal addedValue) {
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

    public Boolean isTaskInGroup(Long groupId, Long taskId) {
        return taskRepository.isTaskInGroup(groupId, taskId);
    }

    public Boolean isTaskCreator(Long taskId, String username) {
        return taskRepository.isTaskCreator(taskId, username);
    }

}
