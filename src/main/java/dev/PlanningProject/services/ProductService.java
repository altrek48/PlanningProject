package dev.PlanningProject.services;

import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.repositories.ProductRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<ProductEntity> createProducts(List<ProductEntity> newProducts, PurchaseEntity purchase) {
        for(ProductEntity product : newProducts) {
            product.setPurchase(purchase);
            productRepository.save(product);
        }

        return newProducts;
    }

    public void deleteAllProducts(Long purchase_id) {
        PurchaseEntity purchase = purchaseRepository.getReferenceById(purchase_id);
        if(purchase.getProducts() != null) {
            productRepository.deleteAll(purchase.getProducts());
        }
        purchase.setProducts(null);
    }

}
