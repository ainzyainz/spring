package test.utils;

import application.DTO.PageDTO;
import application.DTO.TableDTO;
import application.entity.Color;
import application.entity.Material;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;

import static test.utils.MockConstant.*;

public class MockUtils {

    public static List<TableDTO> createTables() {
        List<TableDTO> tableList = new ArrayList<>();
        tableList.add(createTable1());
        tableList.add(createTable2());
        return tableList;
    }

    public static TableDTO createTable1() {
        return TableDTO.builder()
                .brand(BRAND1)
                .size(SIZE1)
                .color(Color.RED)
                .material(Material.PLASTIC)
                .build();
    }

    public static TableDTO createTable2() {
        return TableDTO.builder()
                .brand(BRAND2)
                .size(SIZE2)
                .color(Color.WHITE)
                .material(Material.MARBLE)
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

    public static void searchAssert(List<TableDTO> list, TableDTO table) {
        Assert.assertEquals("Color not assert", list.get(0).getColor(), table.getColor());
        Assert.assertEquals("Brand not assert", list.get(0).getBrand(), table.getBrand());
        Assert.assertEquals("Material not assert", list.get(0).getMaterial(), table.getMaterial());
    }
}
