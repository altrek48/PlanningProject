package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.PlanningProject.dtos.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_credentials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    private boolean enabled;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "linkedUserCredentials")
    private UserEntity linkedUser;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    private PasswordEntity password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
