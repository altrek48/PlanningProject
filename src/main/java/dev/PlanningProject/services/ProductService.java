package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.mappers.ListProductMapper;
import dev.PlanningProject.mappers.PurchaseMapper;
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

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    ListProductMapper listProductMapper;

    public void createProducts(PurchaseDto purchase, Long savedPurchase_id) {
        List<ProductEntity> newProducts = listProductMapper.toListProductEntity(purchase.getProducts());
        PurchaseEntity savedPurchase = purchaseRepository.getReferenceById(savedPurchase_id);
        for(ProductEntity product : newProducts) {
            product.setPurchase(savedPurchase);
            productRepository.save(product);
        }
    }

    public void deleteAllProducts(Long purchase_id) {
        PurchaseEntity purchase = purchaseRepository.getReferenceById(purchase_id);
        if(purchase.getProducts() != null) {
            productRepository.deleteAll(purchase.getProducts());
        }
        purchase.setProducts(null);
    }

}
