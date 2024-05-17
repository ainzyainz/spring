package application.servicelevel;

import application.DTO.PageDTO;
import application.DTO.TableDTO;
import application.converter.TableMapper;
import application.entity.Table;
import application.repositorylevel.repository.TableRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void addToBD() {
        tableRepository.save(createTable1());
    }

    @Test
    void getTablesTest() {
        List<TableDTO> list = tableService.getTables();
        searchAssert(list, createTable1());

    }

    @Test
    void addTableTest() {
        Table table = tableMapper.toEntity(tableService.addTable(tableMapper.toDTO(createTable1())));
        tableRepository.findById(table.getId()).ifPresent(t -> Assert.assertEquals(table, t));
    }

    @Test
    void editTableTest() {
        Table table1 = createTable1();
        Table save = tableRepository.save(table1);
        TableDTO expected = tableMapper.toDTO(table1);
        expected.setBrand(BRAND3);

        TableDTO update = tableService.editTable(save.getId(), expected);

        Assert.assertEquals(expected, update);
    }

    @Test
    void editTableNullTest() {
        Table result = tableMapper.toEntity(tableService.editTable(1000L, tableMapper.toDTO(createTable2())));
        Assert.assertNull(result);
    }

    @Test
    void readTablesTest() {
        Table table = tableRepository.save(createTable2());

        List<TableDTO> list1 = tableService.readTables(table.getColor().toString(), 0);
        Assert.assertEquals("Find by color not assert", 1, list1.size());
        searchAssert(list1, table);

        List<TableDTO> list2 = tableService.readTables(table.getBrand(), 0);
        Assert.assertEquals("Find by brand not assert", 1, list2.size());
        searchAssert(list2, table);

        List<TableDTO> list3 = tableService.readTables(table.getMaterial().toString(), 0);
        Assert.assertEquals("Find by material not assert", 1, list3.size());
        searchAssert(list3, table);

    }

    @Test
    void deleteTableTest() {
        Table table = tableRepository.save(createTable2());
        long countBeforeDelete = tableRepository.count();
        tableService.deleteTable(table.getId());
        long countAfterDelete = tableRepository.count();
        Assert.assertEquals(countBeforeDelete - 1, countAfterDelete);
    }

    @Test
    void buildPageDTOTest() {
        PageDTO pageDTO = createPageDTO2();
        Assert.assertEquals(tableService.buildPageDTO(pageDTO.getList(), 0), pageDTO);
    }
}