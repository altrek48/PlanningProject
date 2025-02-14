package dev.PlanningProject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.dtos.Role;
import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.repositories.CredentialsRepository;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.KafkaProducer;
import dev.PlanningProject.services.TaskService;
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
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Testcontainers
public class TaskControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(@Autowired CredentialsRepository credentialsRepository){
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
        credentialsRepository.save(entity);
    }

    @AfterEach
    public void clean(@Autowired CredentialsRepository credentialsRepository, @Autowired GroupRepository groupRepository, @Autowired TaskRepository taskRepository) {
        taskRepository.deleteAll();
        groupRepository.deleteAll();
        credentialsRepository.deleteAll();
    }

    @Test
    @WithMockUser("userTest")
    public void createTask(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDto = new TaskDto("testTask", "testComment");
        mockMvc.perform(
                        post("/api/base/task/create/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(taskDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(taskDto.getName()))
                .andExpect(jsonPath("$.comment").value(taskDto.getComment()))
                .andExpect(jsonPath("$.userCreator").value("userTest"))
                .andExpect(jsonPath("$.groupId").value(groupDto.getId()));
    }

    @Test
    @WithMockUser("userTest")
    public void testDeleteTask(@Autowired TaskService taskService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDto = taskService.createTask(new TaskDto("testTask", "testComment"), groupDto.getId(), "userTest");

        mockMvc.perform(
                        delete("/api/base/task/delete/{groupId}/{taskId}", groupDto.getId(), taskDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(taskDto.getId()));
    }

    @Test
    @WithMockUser("userTest")
    public void testUpdateTask(@Autowired TaskService taskService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDto = taskService.createTask(new TaskDto("testTask", "testComment"), groupDto.getId(), "userTest");
        TaskDto newTaskDto = new TaskDto(taskDto.getId(), "newTestTask", "newTestComment", groupDto.getId(), new ArrayList<>());
        mockMvc.perform(
                        put("/api/base/task/change/{groupId}/{taskId}", groupDto.getId(), taskDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newTaskDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.name").value(newTaskDto.getName()))
                .andExpect(jsonPath("$.comment").value(newTaskDto.getComment()))
                .andExpect(jsonPath("$.groupId").value(groupDto.getId()));
    }

    @Test
    @WithMockUser("userTest")
    public void testGetTask(@Autowired TaskService taskService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDto = taskService.createTask(new TaskDto("testTask", "testComment"), groupDto.getId(), "userTest");

        mockMvc.perform(
                        get("/api/base/task/getOne/{groupId}/{taskId}", groupDto.getId(), taskDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.name").value(taskDto.getName()))
                .andExpect(jsonPath("$.comment").value(taskDto.getComment()))
                .andExpect(jsonPath("$.groupId").value(groupDto.getId()))
                .andExpect(jsonPath("$.userCreator").value(taskDto.getUserCreator()));
    }

    @Test
    @WithMockUser("userTest")
    public void testGetAllTasks(@Autowired TaskService taskService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDto1 = taskService.createTask(new TaskDto("testTask1", "testComment1"), groupDto.getId(), "userTest");
        TaskDto taskDto2 = taskService.createTask(new TaskDto("testTask2", "testComment2"), groupDto.getId(), "userTest");
        mockMvc.perform(
                        get("/api/base/task/get/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(taskDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(taskDto1.getName()))
                .andExpect(jsonPath("$[0].comment").value(taskDto1.getComment()))
                .andExpect(jsonPath("$[1].id").value(taskDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(taskDto2.getName()))
                .andExpect(jsonPath("$[1].comment").value(taskDto2.getComment()));
    }

}