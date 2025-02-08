package dev.PlanningProject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    public TaskEntity(String name, String comment, List<ProductInPlaneEntity> products) {
        this.name = name;
        this.comment = comment;
        this.products = products;
    }

    public TaskEntity(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String comment;

    private BigDecimal amount = BigDecimal.ZERO;
    //думаю цельных процентов будет достаточно
    private Integer completeness = 0;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductInPlaneEntity> products;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Immutable
    private UserEntity userCreator;

    @OneToMany(mappedBy = "linkedTask", fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    @PreRemove
    private void preRemove() {
        if (purchases != null) {
            purchases.forEach(purchase -> purchase.setLinkedTask(null));
        }
    }

}
