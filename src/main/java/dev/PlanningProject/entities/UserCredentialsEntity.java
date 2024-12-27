package dev.PlanningProject.entities;

import dev.PlanningProject.dtos.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_credentials")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCredentialsEntity {

    public UserCredentialsEntity() {
        UserEntity userEntity = new UserEntity();
        this.linkedUser = userEntity;
        userEntity.setLinkedUserCredentials(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "linkedUserCredentials")
    private UserEntity linkedUser;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    private PasswordEntity password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
