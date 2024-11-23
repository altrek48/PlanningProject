package dev.PlanningProject.services;

import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.repositories.ProductInPlaneRepository;
import dev.PlanningProject.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductInPlaneService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProductInPlaneRepository productInPlaneRepository;

     public void createProduct(ProductInPlaneEntity newProduct, Long task_id) {
         TaskEntity task = taskRepository.getReferenceById(task_id);
         newProduct.setTask(task);
         productInPlaneRepository.save(newProduct);
     }

     public List<ProductInPlaneEntity> changeAllProducts(TaskEntity newTask) {
         log.info("check 1");
         List<ProductInPlaneEntity> pastProducts = productInPlaneRepository.getProductsInPlaneByTaskId(newTask.getId());
         for (ProductInPlaneEntity product : pastProducts) {
             productInPlaneRepository.delete(product);
         }
         
         List<ProductInPlaneEntity> products = newTask.getProducts();
         for (ProductInPlaneEntity product : products) {
             this.createProduct(product, newTask.getId());
         }
         log.info("check 3");
         List<ProductInPlaneEntity> returnableProducts = productInPlaneRepository.getProductsInPlaneByTaskId(newTask.getId());
         return returnableProducts;
     }

     public void deleteAllProductsInPlane(Long task_id) {
         TaskEntity task = taskRepository.getReferenceById(task_id);
         if(task.getProducts() != null) {
             List<ProductInPlaneEntity> products = task.getProducts();
             for(ProductInPlaneEntity product: products) {
                 if(product.getLinkedProduct() != null) {
                     product.setLinkedProduct(null);
                     productInPlaneRepository.save(product);
                 }
                 productInPlaneRepository.delete(product);
             }
         }
         task.setPurchases(null);
         task.setProducts(null);
         taskRepository.save(task);
     }

}
