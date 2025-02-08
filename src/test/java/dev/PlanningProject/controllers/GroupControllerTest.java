package dev.PlanningProject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.dtos.Role;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.UserRepository;
import dev.PlanningProject.services.GroupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void setup(@Autowired UserRepository userRepository) {
        CredentialsEntity entity = CredentialsEntity.builder()
                .username("userTest")
                .password(new PasswordEntity("12345"))
                .enabled(true)
                .role(Role.USER)
                .build();

        UserEntity user = UserEntity.builder()
                .linkedUserCredentials(entity)
                .build();

        entity.setLinkedUser(user);
        userRepository.save(entity);
    }

    @AfterEach
    public void clean(@Autowired UserRepository userRepository, @Autowired GroupRepository groupRepository) {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser("userTest")
    public void testCreateGroup() throws Exception {
        GroupDto groupDto = new GroupDto("testGroup");

        mockMvc.perform(
                        post("/api/base/group/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(groupDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(groupDto.getName()));
    }

    @Test
    @WithMockUser("userTest")
    public void testDeleteGroup(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");

        mockMvc.perform(
                        delete("/api/base/group/delete/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(groupDto.getId()));
    }
    @Test
    @WithMockUser("userTest")
    public void testGetAllGroups(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto1 = groupService.createGroup(new GroupDto("testGroup1"), "userTest");
        GroupDto groupDto2 = groupService.createGroup(new GroupDto("testGroup2"), "userTest");

        mockMvc.perform(
                        get("/api/base/group/get")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(groupDto1.getName()))
                .andExpect(jsonPath("$[1].name").value(groupDto2.getName()));
    }

    @Test
    @WithMockUser("userTest")
    public void testIsGroupCreator(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");

        mockMvc.perform(
                        get("/api/base/group/isCreator/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

}