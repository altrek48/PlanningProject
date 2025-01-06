package dev.PlanningProject.services;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.mappers.ListGroupMapper;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ListGroupMapper listGroupMapper;
    private final UserRepository userRepository;

    @Transactional
    public GroupDto createGroup(GroupDto group, String username) {
        GroupEntity newGroup = groupMapper.toGroupEntity(group);
        UserEntity user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        newGroup.addUser(user);
        groupRepository.save(newGroup);
        return groupMapper.toGroupDto(newGroup);
    }


    public Long deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return id;
    }

    public List<GroupDto> getAllGroups(String username) {
        List<GroupEntity> groups = groupRepository.findGroupsByUsername(username);
        return listGroupMapper.toGroupDto(groups);
    }

    public Boolean isUserInGroup(String username, Long groupId) {
        return groupRepository.isUserInGroup(username, groupId);
    }

}
