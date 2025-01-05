package dev.PlanningProject.services;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
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


    public TaskDto createTask(TaskDto task,Long groupId, String username) {
        TaskEntity newTask = taskMapper.toTaskEntity(task, groupId);
        newTask.setUserCreator(userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found")));
        if(task.getProducts() != null) {
            tuneProducts(newTask);
        }
        else {
            log.info("No products in task");
        }
        TaskEntity savedTask = taskRepository.save(newTask);
        return taskMapper.toTaskDto(savedTask);
    }


    public Long deleteTask(Long task_id) {
        taskRepository.deleteById(task_id);
        return task_id;
    }

    public TaskDto changeTask(TaskDto task) {
        TaskEntity changingTask = taskMapper.toTaskEntity(task);
        if(task.getProducts() != null) {
            tuneProducts(changingTask);
        }
        TaskEntity changedTask = taskRepository.save(changingTask);
        return taskMapper.toTaskDto(changedTask);
    }


    public void tuneProducts(TaskEntity task) {
        List<ProductInPlaneEntity> products = task.getProducts();
        for(ProductInPlaneEntity product : products) {
            product.setTask(task);
            product.setCompleteness(false);
        }
    }

    public List<TaskShortDto> getTasks(Long groupId) {
        List<TaskEntity> taskEntities = taskRepository.findAllByGroupId(groupId);
        return listTaskMapper.toListTaskShortDto(taskEntities);
    }

    public BigDecimal getAmount(TaskEntity task) {
        BigDecimal amount = new BigDecimal(0);
        for( ProductInPlaneEntity product: task.getProducts()) {
            if(!Objects.equals(product.getPrice(), valueOf(0))) {
                amount = amount.add(product.getPrice());
            }
        }
        return amount;
    }

    public TaskDto getTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("task с переданным значение не найден"));
        return taskMapper.toTaskDto(task);
    }

}
