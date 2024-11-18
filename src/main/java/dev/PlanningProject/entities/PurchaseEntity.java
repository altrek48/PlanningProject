package dev.PlanningProject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEntity {

    public PurchaseEntity(String storeName /*, Date date*/, BigDecimal amount) {
        this.storeName = storeName;
       // this.date = date;
        this.amount = amount;
    }

    public PurchaseEntity(Long id, String storeName, BigDecimal amount, List<ProductEntity> products) {
        this.id = id;
        this.storeName = storeName;
        this.amount = amount;
        this.products = products;
    }

    public PurchaseEntity(String storeName, GroupEntity group) {
        this.storeName = storeName;
        this.group = group;
    }

    public PurchaseEntity(String storeName, List<ProductEntity> products) {
        this.storeName = storeName;
        this.products = products;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;

    //private Date date;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @OneToMany(mappedBy = "purchase")
    List<ProductEntity> products;

}
