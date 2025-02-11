package dev.PlanningProject.services;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.mappers.ListGroupMapper;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ListGroupMapper listGroupMapper;
    private final CredentialsRepository credentialsRepository;

    @Transactional
    public GroupDto createGroup(GroupDto group, String username) {
        UserEntity user = credentialsRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        GroupEntity newGroup = groupMapper.toGroupEntity(group, user);
        newGroup.addUser(user);
        return groupMapper.toGroupDto(groupRepository.save(newGroup));
    }


    public Long deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return id;
    }

    public List<GroupDto> getAllGroups(String username) {
        return listGroupMapper.toGroupDto(groupRepository.findGroupsByUsername(username));
    }

    public Boolean isUserInGroup(String username, Long groupId) {
        return groupRepository.isUserInGroup(username, groupId);
    }

    public Boolean isUserCreator(String username, Long groupId) {
        return groupRepository.isUserCreator(username, groupId);
    }

    public Long removeUserFromGroup(Long groupId, Long userId) {
        if(Objects.equals(groupRepository.getUserCreatorId(groupId), userId)) {
            throw new IllegalArgumentException("user Creator can't delete himself. UserId: " + userId);
        }
        else groupRepository.removeUserFromGroup(groupId, userId);
        return userId;
    }

}
