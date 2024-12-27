package dev.PlanningProject;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;
    private final PurchaseRepository purchaseRepository;

    public void initial1() {

        GroupEntity newGroup1 = new GroupEntity("Семья");
        groupRepository.save(newGroup1);

        TaskEntity newTask1 = new TaskEntity("На день рождения", "Купить подарке Тане, Ане, Паше");
        newTask1.setGroupId(newGroup1.getId());
        ProductInPlaneEntity newProductInPlane1 = new ProductInPlaneEntity("Кукла");
        newProductInPlane1.setTask(newTask1);
        ProductInPlaneEntity newProductInPlane2 = new ProductInPlaneEntity("Машинка");
        newProductInPlane2.setTask(newTask1);
        ProductInPlaneEntity newProductInPlane3 = new ProductInPlaneEntity("Незамерзайка");
        newProductInPlane3.setTask(newTask1);
        List<ProductInPlaneEntity> newProductsInPlane1 = new ArrayList<>();
        newProductsInPlane1.add(newProductInPlane1);
        newProductsInPlane1.add(newProductInPlane2);
        newProductsInPlane1.add(newProductInPlane3);
        newTask1.setProducts(newProductsInPlane1);
        taskRepository.save(newTask1);

        TaskEntity newTask2 = new TaskEntity("В дорогу на море", "Купить не позднее 12.01.25");
        newTask2.setGroupId(newGroup1.getId());
        ProductInPlaneEntity newProductInPlane4 = new ProductInPlaneEntity("Сникерсы");
        newProductInPlane4.setTask(newTask2);
        ProductInPlaneEntity newProductInPlane5 = new ProductInPlaneEntity("Воду");
        newProductInPlane5.setTask(newTask2);
        ProductInPlaneEntity newProductInPlane6 = new ProductInPlaneEntity("Парацетамол");
        newProductInPlane6.setTask(newTask2);
        List<ProductInPlaneEntity> newProductsInPlane2 = new ArrayList<>();
        newProductsInPlane2.add(newProductInPlane4);
        newProductsInPlane2.add(newProductInPlane5);
        newProductsInPlane2.add(newProductInPlane6);
        newTask2.setProducts(newProductsInPlane2);
        taskRepository.save(newTask2);


    }


}
