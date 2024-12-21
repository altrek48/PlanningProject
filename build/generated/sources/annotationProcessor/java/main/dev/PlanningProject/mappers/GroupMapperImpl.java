package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-21T01:44:28+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupEntity toGroupEntity(GroupDto groupDto) {
        if ( groupDto == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        groupEntity.setId( groupDto.getId() );
        groupEntity.setName( groupDto.getName() );

        return groupEntity;
    }

    @Override
    public GroupDto toGroupDto(GroupEntity groupEntity) {
        if ( groupEntity == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setId( groupEntity.getId() );
        groupDto.setName( groupEntity.getName() );

        return groupDto;
    }
}
