package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productsInPlane")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInPlaneEntity {

    public ProductInPlaneEntity(String name) {
        this.name = name;
        this.completeness = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean completeness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity linkedProduct;


}
