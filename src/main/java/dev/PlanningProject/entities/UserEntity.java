package dev.PlanningProject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.PlanningProject.services.KafkaProducer;
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
@EntityListeners(KafkaProducer.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private List<GroupEntity> groups;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "linked_UserCredentials_id", nullable = false)
    private CredentialsEntity linkedUserCredentials;

    private String avatarUrl;
}
