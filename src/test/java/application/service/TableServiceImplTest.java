package application.service;
import application.DTO.TableDTO;
import application.converter.TableMapper;
import application.entity.Table;
import application.repository.TableRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static test.utils.MockConstant.BRAND3;
import static test.utils.MockUtils.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TableServiceImplTest {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableService tableService;

    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    @Test
    void getTablesTest() {
        List<TableDTO> list = tableService.getTables(null, 0).getList();
        searchAssert(list, createTable1());

    }

    @Test
    void addTableTest() {
        Table table = tableMapper.toEntity(tableService.addTable((createTable2())));
        tableRepository.findById(table.getId()).ifPresent(t -> Assert.assertEquals(table, t));
    }

    @Test
    void editTableTest() {
        TableDTO expected = tableService.addTable(createTable1());
        expected.setBrand(BRAND3);
        tableRepository.findById(1L).ifPresent(t -> Assert.assertEquals(
                expected, tableService.editTable(expected)));
    }

    @Test
    void editTableNullTest() {
        Table result = tableMapper.toEntity(tableService.editTable(null));
        Assert.assertNull(result);
    }

    @Test
    void readTablesTest() {
        TableDTO table = tableService.addTable(createTable2());

        List<TableDTO> list1 = tableService.readTables(table.getColor().toString(), 0).getList();
        searchAssert(list1, table);

        List<TableDTO> list2 = tableService.readTables(table.getBrand(), 0).getList();
        searchAssert(list2, table);

    }

    @Test
    void deleteTableTest() {
        TableDTO table = tableService.addTable(createTable1());
        long countBeforeDelete = tableRepository.count();
        tableService.deleteTable(table.getId());
        long countAfterDelete = tableRepository.count();
        Assert.assertEquals(countBeforeDelete - 1, countAfterDelete);
    }

}