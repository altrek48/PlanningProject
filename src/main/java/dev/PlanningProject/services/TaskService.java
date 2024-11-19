package dev.PlanningProject.services;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.mappers.TaskMapper;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProductInPlaneService productInPlaneService;

    @Autowired
    TaskMapper taskMapper;

    public TaskDto createTask(TaskDto task,Long group_id ) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
        TaskEntity newTask = taskMapper.toTaskEntity(task);
        newTask.setGroup(group);
        if(newTask.getProducts() != null) {
            taskRepository.save(newTask);
            Long task_id = newTask.getId();
            for(ProductInPlaneEntity product : newTask.getProducts()) {
                productInPlaneService.createProduct(product, task_id);
            }
        }
        else {
            log.info("No products in task");
        }
            return taskMapper.toTaskDto(newTask);
        }


    public Long deleteTask(Long task_id) {
        productInPlaneService.deleteAllProductsInPlane(task_id);
        taskRepository.deleteById(task_id);
        return task_id;
    }

    public void deleteAllTasksInGroup(GroupEntity group) {
        List<TaskEntity> tasks = group.getTasks();
        for(TaskEntity task : tasks) {
            deleteTask(task.getId());
        }
    }

        //todo
        //пофиксить создание продуктов вместо изменения старых
        public TaskDto changeTask(TaskDto task) {
            TaskEntity newTask = taskRepository.getReferenceById(task.getId());
            if(!Objects.equals(newTask.getName(), task.getName())) {
                newTask.setName(task.getName());
                log.info("names not equals");
            }
            if(!Objects.equals(newTask.getComment(), task.getComment())) {
                newTask.setComment(task.getComment());
                log.info("comments not equals");
            }

                productInPlaneService.deleteAllProductsInPlane(newTask.getId());
                newTask.setProducts(null);

            if(task.getProducts()!= null) {
                log.info("new products not null");
                List<ProductInPlaneEntity> newProducts = task.getProducts().stream()
                        .map(productDto -> {
                            ProductInPlaneEntity productEntity = new ProductInPlaneEntity();
                            productEntity.setName(productDto.getName());
                            productEntity.setCompleteness(productDto.getCompleteness());
                            productEntity.setTask(newTask);
                            productInPlaneService.createProduct(productEntity, newTask.getId());
                            return productEntity;
                        }).collect(Collectors.toList());

                newTask.setProducts(newProducts);
            }
            taskRepository.save(newTask);
            return toTaskDto(newTask);
        }

        //todo
        public TaskDto toTaskDto(TaskEntity taskEntity) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(taskEntity.getId());
            taskDto.setName(taskEntity.getName());
            taskDto.setComment(taskEntity.getComment());
            if(taskEntity.getProducts() != null) {
                List<ProductInPlaneDto> productDtos = taskEntity.getProducts().stream()
                        .map(product -> {
                            ProductInPlaneDto productDto = new ProductInPlaneDto();
                            productDto.setId(product.getId());
                            productDto.setName(product.getName());
                            productDto.setCompleteness(product.getCompleteness());
                            return productDto;
                        }).collect(Collectors.toList());
                taskDto.setProducts(productDtos);
            } else {
                taskDto.setProducts(null);
            }
            return taskDto;
        }

}
