package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.entities.UserEntity;
import dev.PlanningProject.repositories.ProductRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "userCreator", source = "userCreator")
    @Mapping(target = "id", ignore = true) //id генерируется автоматически(без этого маппинга вылетает ошибка)
    TaskEntity toTaskEntity(TaskDto taskDto, Long groupId, UserEntity userCreator);

    @Mapping(target = "userCreator", ignore = true)
    @Mapping(target = "products", source = "products", qualifiedByName = "withProductRepository")
    TaskEntity toTaskEntity(TaskDto taskDto, @Context ProductRepository productRepository);


    @Mapping(target = "userCreator", source = "userCreator.linkedUserCredentials.username")
    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskShortDto toTaskShortDto(TaskEntity taskEntity);



}
