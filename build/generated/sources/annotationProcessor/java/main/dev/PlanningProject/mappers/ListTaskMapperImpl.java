package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.TaskEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-29T18:26:56+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ListTaskMapperImpl implements ListTaskMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskDto> toListTaskDto(List<TaskEntity> taskEntityList) {
        if ( taskEntityList == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<TaskDto>( taskEntityList.size() );
        for ( TaskEntity taskEntity : taskEntityList ) {
            list.add( taskMapper.toTaskDto( taskEntity ) );
        }

        return list;
    }

    @Override
    public List<TaskShortDto> toListTaskShortDto(List<TaskEntity> taskEntityList) {
        if ( taskEntityList == null ) {
            return null;
        }

        List<TaskShortDto> list = new ArrayList<TaskShortDto>( taskEntityList.size() );
        for ( TaskEntity taskEntity : taskEntityList ) {
            list.add( taskMapper.toTaskShortDto( taskEntity ) );
        }

        return list;
    }
}
