package dev.PlanningProject.services.auth;

import dev.PlanningProject.annotations.SaveLog;
import dev.PlanningProject.dtos.AuthUser;
import dev.PlanningProject.dtos.Role;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.CredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CredentialsEntity user = credentialsRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );

        return AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .enabled(user.isEnabled())
                .build();
    }

    @SaveLog(action = "PERSIST")
    public CredentialsEntity createUser(SignInRequest request) {

            CredentialsEntity entity = CredentialsEntity.builder()
                    .username(request.getUsername())
                    .password(new PasswordEntity(request.getPassword()))
                    .enabled(true)
                    .role(Role.USER)
                    .build();

            UserEntity user = UserEntity.builder()
                    .linkedUserCredentials(entity)
                    .build();

            entity.setLinkedUser(user);
            return credentialsRepository.save(entity);
    }

}
