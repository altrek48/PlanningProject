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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;

    private Date date;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    GroupEntity group;



}
