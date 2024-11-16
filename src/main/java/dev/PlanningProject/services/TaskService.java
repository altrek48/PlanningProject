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
        else {taskRepository.save(newTask);}
            return newTask;
        }

        public TaskEntity changeTask(TaskEntity task,Long task_id) {
            log.info("TaskEntity changeTask");
            TaskEntity changingTask = taskRepository.getReferenceById(task_id);
            changingTask.setComment(task.getComment());
            changingTask.setName(task.getName());
            changingTask.setProducts(productInPlaneService.changeAllProducts(task));
            return taskRepository.save(changingTask);
        }

}
