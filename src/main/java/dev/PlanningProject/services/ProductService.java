package dev.PlanningProject.services;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.mappers.ListProductMapper;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.repositories.ProductInPlaneRepository;
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
    ProductInPlaneRepository productInPlaneRepository;


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
            List<ProductEntity> products = purchase.getProducts();
            for (ProductEntity product: products) {
                if(product.getProductInPlane() != null) {
                   product = resetAssotiation(product);
                }
                product.setPurchase(null);
                productRepository.save(product);
                productRepository.delete(product);
            }
        }
        purchase.setTask(null);
        purchase.setProducts(null);
        purchaseRepository.save(purchase);
    }

    //todo
    //возможно переделать через .stream
    public Boolean checkCoincidences(Long purchase_id, Long task_id) {
        List<ProductEntity> products = productRepository.getProductsByPurchaseId(purchase_id);
        List<ProductInPlaneEntity> productsInPlane = productInPlaneRepository.getProductsInPlaneByTaskId(task_id);
        boolean coincidences = false;

        for(ProductEntity product: products) {
            for(ProductInPlaneEntity productInPlaneEntity: productsInPlane) {
                if(product.getName().equalsIgnoreCase(productInPlaneEntity.getName())) {
                    product.setProductInPlane(productInPlaneEntity);
                    productInPlaneEntity.setLinkedProduct(product);
                    productInPlaneEntity.setCompleteness(true);
                    coincidences = true;
                }
            }
        }
        return  coincidences;
    }

    //Разорвать связь с продуктом из плана в обе стороны
    public ProductEntity resetAssotiation(ProductEntity product) {
        ProductInPlaneEntity productInPlane = product.getProductInPlane();
        productInPlane.setLinkedProduct(null);
        productInPlaneRepository.save(productInPlane);
        product.setProductInPlane(null);
        return product;
    }

}
