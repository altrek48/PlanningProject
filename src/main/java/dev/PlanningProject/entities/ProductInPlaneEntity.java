package dev.PlanningProject.entities;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean completeness;





}
