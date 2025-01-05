package dev.PlanningProject.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private String email;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private List<GroupEntity> groups;

    @OneToOne
    @JoinColumn(name = "linked_UserCredentials_id", nullable = false)
    private CredentialsEntity linkedUserCredentials;


}
