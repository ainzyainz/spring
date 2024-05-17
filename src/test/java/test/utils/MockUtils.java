package test.utils;

import application.entity.Table;
import application.DTO.PageDTO;
import application.DTO.TableDTO;
import application.converter.TableMapper;
import application.entity.Color;
import application.entity.Material;
import org.junit.Assert;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static test.utils.MockConstant.*;

public class MockUtils {

    private static final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    public static List<TableDTO> createTables() {
        List<Table> tableList = new ArrayList<>();
        tableList.add(createTable1());
        tableList.add(createTable2());
        return tableList.stream().map(tableMapper::toDTO).collect(Collectors.toList());
    }

    public static Table createTable1() {
        return Table.builder()
                .brand(BRAND1)
                .size(SIZE1)
                .color(Color.BLACK)
                .material(Material.PLASTIC)
                .build();
    }

    public static Table createTable2() {
        return Table.builder()
                .brand(BRAND2)
                .size(SIZE2)
                .color(Color.WHITE)
                .material(Material.WOOD)
                .build();
    }

    public static PageDTO createPageDTO1() {
        return PageDTO.builder()
                .totalPages(1)
                .list(createTables())
                .build();
    }

    public static PageDTO createPageDTO2() {
        return PageDTO.builder()
                .totalPages(0)
                .list(createTables())
                .pageNumbers(new ArrayList<>())
                .build();
    }

    public static void searchAssert(List<TableDTO> list, Table table) {
        Assert.assertEquals("Color not assert", list.get(0).getColor(), table.getColor());
        Assert.assertEquals("Brand not assert", list.get(0).getBrand(), table.getBrand());
        Assert.assertEquals("Material not assert", list.get(0).getMaterial(), table.getMaterial());
    }
}
