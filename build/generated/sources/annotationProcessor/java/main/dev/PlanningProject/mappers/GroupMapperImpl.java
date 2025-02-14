package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-14T18:34:40+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupEntity toGroupEntity(GroupDto groupDto, UserEntity user) {
        if ( groupDto == null && user == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        if ( groupDto != null ) {
            groupEntity.setName( groupDto.getName() );
        }
        groupEntity.setUserCreator( user );

        return groupEntity;
    }

    @Override
    public GroupDto toGroupDto(GroupEntity groupEntity) {
        if ( groupEntity == null ) {
            return null;
        }

        GroupDto.GroupDtoBuilder groupDto = GroupDto.builder();

        groupDto.id( groupEntity.getId() );
        groupDto.name( groupEntity.getName() );

        return groupDto.build();
    }
}
