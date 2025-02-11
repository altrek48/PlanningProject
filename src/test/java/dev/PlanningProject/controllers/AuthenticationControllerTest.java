package dev.PlanningProject.controllers;

//import dev.PlanningProject.controllers.TestContainersConfig;
//import dev.PlanningProject.controllers.TestContainersConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.PlanningProject.dtos.Role;
import dev.PlanningProject.dtos.auth.JwtRequest;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.CredentialsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class AuthenticationControllerTest {

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
    public void setup(@Autowired CredentialsRepository credentialsRepository){
        CredentialsEntity entity = CredentialsEntity.builder()
                .username("rolikTest")
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
    public void clean(@Autowired CredentialsRepository credentialsRepository) {
        credentialsRepository.deleteAll();
    }

    @Test
    public void testRegistration() throws Exception {
        SignInRequest signInRequest = new SignInRequest("testUser", "testPassword");

        mockMvc.perform(
                        post("/api/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signInRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").isNotEmpty());

    }

    @Test
    public void testLogin() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("rolikTest","12345");

        mockMvc.perform(
                        post("/api/login")
                                .content(objectMapper.writeValueAsString(jwtRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

}