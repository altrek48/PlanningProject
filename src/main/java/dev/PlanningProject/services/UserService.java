package dev.PlanningProject.services;

import dev.PlanningProject.dtos.UserProfile;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.CredentialsRepository;
import dev.PlanningProject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final CredentialsRepository credentialsRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    @Transactional
    public void addUser(Long groupId, String username) {
        UserEntity addingUser = this.credentialsRepository.getUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " not found"));
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
        group.addUser(addingUser);
        groupRepository.save(group);
    }

    public UserProfile getUserProfile(String username) {
        return credentialsRepository.getUserProfile(username)
                .orElseThrow(() -> new EntityNotFoundException("UserProfile with username: " + username + " not found"));
    }

    public List<UserProfile> getProfilesByGroupId(Long groupId) {
        return credentialsRepository.getProfilesByGroupId(groupId);
    }

    public void updateAvatar(String username, String avatarURL) {
        UserEntity user = credentialsRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        user.setAvatarUrl(avatarURL);
        userRepository.save(user);
    }

    public String editEmail(String newEmail, String username) {
        UserEntity user = credentialsRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        user.setEmail(newEmail);
        return userRepository.save(user).getEmail();
    }

}
