package dev.PlanningProject.controllers;


// dev.PlanningProject.controllers.TestContainersConfig;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

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

        CredentialsEntity entity2 = CredentialsEntity.builder()
                .username("userTest2")
                .password(new PasswordEntity("12345"))
                .enabled(true)
                .role(Role.USER)
                .build();

        UserEntity user2 = UserEntity.builder()
                .linkedUserCredentials(entity2)
                .build();

        entity2.setLinkedUser(user2);
        userRepository.save(entity2);
    }

    @AfterEach
    public void clean(@Autowired UserRepository userRepository, @Autowired GroupRepository groupRepository) {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser("userTest")
    public void testAddToGroup(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");

        mockMvc.perform(
                        post("/api/base/user/add/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("userTest2")
                )
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser("userTest")
    public void testGetUserProfile() throws Exception {
        mockMvc.perform(
                        get("/api/base/user/getInfo")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("userTest"));
    }

}