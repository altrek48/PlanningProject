package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private List<GroupEntity> groups;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "linked_UserCredentials_id", nullable = false)
    private CredentialsEntity linkedUserCredentials;

}
