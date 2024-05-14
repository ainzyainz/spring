package application.utils.converter;

import application.entity.Table;
import application.utils.DTO.TableDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-14T14:48:17+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
public class TableMapperImpl implements TableMapper {

    @Override
    public Table toEntity(TableDTO tableDTO) {
        if ( tableDTO == null ) {
            return null;
        }

        Table table = new Table();

        table.setId( tableDTO.getId() );
        table.setSize( tableDTO.getSize() );
        table.setBrand( tableDTO.getBrand() );
        table.setColor( tableDTO.getColor() );
        table.setMaterial( tableDTO.getMaterial() );

        return table;
    }

    @Override
    public TableDTO toDTO(Table table) {
        if ( table == null ) {
            return null;
        }

        TableDTO tableDTO = new TableDTO();

        tableDTO.setId( table.getId() );
        tableDTO.setSize( table.getSize() );
        tableDTO.setBrand( table.getBrand() );
        tableDTO.setColor( table.getColor() );
        tableDTO.setMaterial( table.getMaterial() );

        return tableDTO;
    }
}
