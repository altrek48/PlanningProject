package dev.PlanningProject.services;

import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductEntity> createProducts(List<ProductEntity> newProducts, PurchaseEntity purchase) {
        for(ProductEntity product : newProducts) {
            product.setPurchase(purchase);
            productRepository.save(product);
        }

        return newProducts;
    }

}
