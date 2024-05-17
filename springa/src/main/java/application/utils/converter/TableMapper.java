package application.utils.converter;

import application.entity.Table;
import application.utils.DTO.TableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TableMapper {

    @Mapping(target = "id")
    Table toEntity(TableDTO tableDTO);

    @Mapping(target = "id")
    TableDTO toDTO(Table table);
}
