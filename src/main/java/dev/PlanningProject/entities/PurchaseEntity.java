package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.PlanningProject.services.KafkaProducer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(KafkaProducer.class)
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

    private LocalDateTime date;

    private BigDecimal amount;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    //todo возможно вообще не передавать свзяанные покупки и таски?
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "task_id")
    private TaskEntity linkedTask;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Immutable
    private UserEntity userPayer;

}
