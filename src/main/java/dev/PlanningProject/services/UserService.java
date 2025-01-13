package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public synchronized void addUserToGroup(Long groupId, String username) {
        if(!userRepository.isUserConsistsInGroup(groupId, username)) {
            this.addUser(groupId, username);
        }
        else throw new IllegalArgumentException("Пользователь уже добавлен");
    }

    private void addUser(Long groupId, String username) {
        UserEntity addingUser = this.userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
        addingUser.getGroups().add(group);
        group.getUsers().add(addingUser);
        groupRepository.save(group);
    }
}
