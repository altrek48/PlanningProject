package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-04T21:57:04+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ListGroupMapperImpl implements ListGroupMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<GroupDto> toGroupDto(List<GroupEntity> groupEntities) {
        if ( groupEntities == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( groupEntities.size() );
        for ( GroupEntity groupEntity : groupEntities ) {
            list.add( groupMapper.toGroupDto( groupEntity ) );
        }

        return list;
    }

    @Override
    public List<GroupEntity> toGroupEntity(List<GroupDto> groupDtos) {
        if ( groupDtos == null ) {
            return null;
        }

        List<GroupEntity> list = new ArrayList<GroupEntity>( groupDtos.size() );
        for ( GroupDto groupDto : groupDtos ) {
            list.add( groupDtoToGroupEntity( groupDto ) );
        }

        return list;
    }

    protected GroupEntity groupDtoToGroupEntity(GroupDto groupDto) {
        if ( groupDto == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        groupEntity.setId( groupDto.getId() );
        groupEntity.setName( groupDto.getName() );

        return groupEntity;
    }
}
