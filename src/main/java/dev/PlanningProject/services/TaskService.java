package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProductInPlaneService productInPlaneService;

    public TaskEntity createTask(TaskEntity newTask,Long group_id ) {
        GroupEntity group = groupRepository.getReferenceById(group_id);
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
            taskRepository.save(newTask);
        }
            return newTask;
        }

        public TaskEntity changeTask(TaskEntity task) {
            TaskEntity newTask = taskRepository.getReferenceById(task.getId());
            if(!Objects.equals(newTask.getName(), task.getName())) {
                newTask.setName(task.getName());
                log.info("names not equals");
            }
            if(!Objects.equals(newTask.getComment(), task.getComment())) {
                newTask.setComment(task.getComment());
                log.info("comments not equals");
            }
            if(task.getProducts() == null) {
                productInPlaneService.deleteAllProductsInPlane(newTask);
                newTask.setProducts(null);
            }

            //потом поменять(возможно через Map)
            else {
                productInPlaneService.deleteAllProductsInPlane(newTask);
                List<ProductInPlaneEntity> newProducts = task.getProducts();
                for(ProductInPlaneEntity product : newProducts) {
                    product.setTask(newTask);
                }
                newTask.setProducts(newProducts);
            }
            return taskRepository.save(newTask);
        }

}
