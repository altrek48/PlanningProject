package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products_in_plane")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInPlaneEntity {

    public ProductInPlaneEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean completeness = false;

    private BigDecimal price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity linkedProduct;

    @PreRemove
    private void preRemove() {
        if (linkedProduct != null) {
            linkedProduct.setProductInPlane(null);
        }
    }

}
