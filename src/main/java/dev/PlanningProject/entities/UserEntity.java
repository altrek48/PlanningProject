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

    private String username;

    private String email;

//    private List<GroupEntity> groups;

    @OneToOne
    @JoinColumn(name = "linkedUserCredentials_id")
    private UserCredentialsEntity linkedUserCredentials;


}
