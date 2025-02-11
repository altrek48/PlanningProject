package dev.PlanningProject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.PlanningProject.dtos.*;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.PasswordEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.repositories.CredentialsRepository;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.PurchaseService;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PurchaseControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(@Autowired CredentialsRepository credentialsRepository, @Autowired GroupService groupService) {
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
    public void clean(@Autowired CredentialsRepository credentialsRepository, @Autowired GroupRepository groupRepository, @Autowired PurchaseRepository purchaseRepository, @Autowired TaskRepository taskRepository) {
        purchaseRepository.deleteAll();
        taskRepository.deleteAll();
        groupRepository.deleteAll();
        credentialsRepository.deleteAll();
    }

    @Test
    @WithMockUser("userTest")
    public void testCreatePurchase(@Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        PurchaseDto purchaseDto = new PurchaseDto("testPurchase", "");
        List<ProductDto> productsDto = new ArrayList<>();
        productsDto.add(new ProductDto("testProduct", BigDecimal.valueOf(100)));
        purchaseDto.setProducts(productsDto);

        mockMvc.perform(
                        post("/api/base/purchase/create/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(purchaseDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.storeName").value(purchaseDto.getStoreName()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(100)))
                .andExpect(jsonPath("$.groupId").value(groupDto.getId()))
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andExpect(jsonPath("$.products[0].id").isNotEmpty())
                .andExpect(jsonPath("$.products[0].name").value("testProduct"))
                .andExpect(jsonPath("$.products[0].price").value(BigDecimal.valueOf(100)))
                .andExpect(jsonPath("$.products[0].purchaseId").isNotEmpty())
                .andExpect(jsonPath("$.products[0].productInPlaneId").isEmpty())
                .andExpect(jsonPath("$.userPayer").value("userTest"));
    }

    @Test
    @WithMockUser("userTest")
    public void testCreatePurchaseInTask(@Autowired TaskService taskService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        TaskDto taskDtoTest = new TaskDto("testTask", "testComment");
        List<ProductInPlaneDto> productsInPlaneDto = new ArrayList<>();
        productsInPlaneDto.add(new ProductInPlaneDto("testProduct"));
        taskDtoTest.setProducts(productsInPlaneDto);
        TaskDto taskDto = taskService.createTask(taskDtoTest, groupDto.getId(), "userTest");

        PurchaseDto purchaseDto = new PurchaseDto("testPurchase", "");
        List<ProductDto> productsDto = new ArrayList<>();
        productsDto.add(new ProductDto("testProduct", BigDecimal.valueOf(100)));
        purchaseDto.setProducts(productsDto);

        mockMvc.perform(
                        post("/api/base/purchase/create/{groupId}/{taskId}", groupDto.getId(), taskDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(purchaseDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.storeName").value(purchaseDto.getStoreName()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.groupId").value(groupDto.getId()))
                .andExpect(jsonPath("$.taskId").value(taskDto.getId()))
                .andExpect(jsonPath("$.products[0].id").isNotEmpty())
                .andExpect(jsonPath("$.products[0].name").value("testProduct"))
                .andExpect(jsonPath("$.products[0].price").value(100))
                .andExpect(jsonPath("$.products[0].purchaseId").isNotEmpty())
                .andExpect(jsonPath("$.products[0].productInPlaneId").isNotEmpty())
                .andExpect(jsonPath("$.userPayer").value("userTest"));

    }

    @Test
    @WithMockUser("userTest")
    public void testGetAllPurchases(@Autowired PurchaseService purchaseService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        PurchaseDto purchaseDto1 = new PurchaseDto("testPurchase1", "");
        List<ProductDto> productsDto1 = new ArrayList<>();
        productsDto1.add(new ProductDto("testProduct1", BigDecimal.valueOf(100)));
        purchaseDto1.setProducts(productsDto1);

        PurchaseDto purchaseDto2 = new PurchaseDto("testPurchase2", "");
        List<ProductDto> productsDto2 = new ArrayList<>();
        productsDto2.add(new ProductDto("testProduct2", BigDecimal.valueOf(100)));
        purchaseDto2.setProducts(productsDto2);

        PurchaseDto purchaseDtoTest1 = purchaseService.createPurchase(purchaseDto1, groupDto.getId(), "userTest");
        PurchaseDto purchaseDtoTest2 = purchaseService.createPurchase(purchaseDto2, groupDto.getId(), "userTest");

        mockMvc.perform(
                        get("/api/base/purchase/getAll/{groupId}", groupDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].storeName").value(purchaseDtoTest2.getStoreName()))
                .andExpect(jsonPath("$[0].date").isNotEmpty())
                .andExpect(jsonPath("$[0].amount").value(100))
                .andExpect(jsonPath("$[0].groupId").value(purchaseDtoTest2.getGroupId()))
                .andExpect(jsonPath("$[0].taskId").isEmpty())
                .andExpect(jsonPath("$[0].userPayer").value("userTest"))
                .andExpect(jsonPath("$[1].storeName").value(purchaseDtoTest1.getStoreName()))
                .andExpect(jsonPath("$[1].date").isNotEmpty())
                .andExpect(jsonPath("$[1].amount").value(100))
                .andExpect(jsonPath("$[1].groupId").value(purchaseDtoTest1.getGroupId()))
                .andExpect(jsonPath("$[1].taskId").isEmpty())
                .andExpect(jsonPath("$[1].userPayer").value("userTest"));
    }

    @Test
    @WithMockUser("userTest")
    public void testGetPurchase(@Autowired PurchaseService purchaseService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");

        PurchaseDto purchaseDto1 = new PurchaseDto("testPurchase1", "");
        List<ProductDto> productsDto1 = new ArrayList<>();
        productsDto1.add(new ProductDto("testProduct1", BigDecimal.valueOf(100)));
        purchaseDto1.setProducts(productsDto1);

        PurchaseDto purchaseDtoTest1 = purchaseService.createPurchase(purchaseDto1, groupDto.getId(), "userTest");

        mockMvc.perform(
                        get("/api/base/purchase/get/{groupId}/{purchaseId}", groupDto.getId(), purchaseDtoTest1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName").value(purchaseDtoTest1.getStoreName()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.groupId").value(purchaseDtoTest1.getGroupId()))
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andExpect(jsonPath("$.products[0].id").isNotEmpty())
                .andExpect(jsonPath("$.products[0].name").value("testProduct1"))
                .andExpect(jsonPath("$.products[0].price").value(100))
                .andExpect(jsonPath("$.products[0].purchaseId").value(purchaseDtoTest1.getId()))
                .andExpect(jsonPath("$.products[0].productInPlaneId").isEmpty())
                .andExpect(jsonPath("$.userPayer").value("userTest"));
    }

    @Test
    @WithMockUser("userTest")
    public void testGetPurchaseIdByProductId(@Autowired PurchaseService purchaseService, @Autowired GroupService groupService) throws Exception {
        GroupDto groupDto = groupService.createGroup(new GroupDto("testGroup"), "userTest");
        PurchaseDto purchaseDto1 = new PurchaseDto("testPurchase1", "");
        List<ProductDto> productsDto1 = new ArrayList<>();
        productsDto1.add(new ProductDto("testProduct1", BigDecimal.valueOf(100)));
        purchaseDto1.setProducts(productsDto1);

        PurchaseDto purchaseDtoTest1 = purchaseService.createPurchase(purchaseDto1, groupDto.getId(), "userTest");

        mockMvc.perform(
                        get("/api/base/purchase/getPurchaseId/{productId}", purchaseDtoTest1.getProducts().getFirst().getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(purchaseDtoTest1.getId()));
    }

}