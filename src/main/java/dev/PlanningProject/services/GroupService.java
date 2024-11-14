package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.repositories.GroupRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public GroupEntity createGroup(GroupEntity group) {
        return groupRepository.save(group);
    }

    public Long deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return id;
    }

}
