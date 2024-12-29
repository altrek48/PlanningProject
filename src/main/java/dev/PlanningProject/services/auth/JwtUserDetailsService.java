package dev.PlanningProject.services.auth;

import dev.PlanningProject.dtos.AuthUser;
import dev.PlanningProject.dtos.Role;
import dev.PlanningProject.dtos.auth.JwtRequest;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserCredentialsEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialsEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        );

        return AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .enabled(user.isEnabled())
                .build();
    }

    public void createUser(SignInRequest request) {

        if (!this.checkAvailableUsername(request.getUsername())) {
            UserCredentialsEntity entity = UserCredentialsEntity.builder()
                    .username(request.getUsername())
                    .password(new PasswordEntity(request.getPwd()))
                    .enabled(true)
                    .role(Role.USER)
                    .build();

            UserEntity user = UserEntity.builder()
                    .linkedUserCredentials(entity)
                    .build();

            entity.setLinkedUser(user);
            userRepository.save(entity);
        }
        else throw new IllegalArgumentException("Username is already taken");
    }

    public boolean checkAvailableUsername(String username) {
        return userRepository.checkAvailableUsername(username);
    }

}
