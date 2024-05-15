package application.controllerlevel;

import application.servicelevel.TableService;
import application.utils.DTO.TableDTO;
import application.utils.converter.TableMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static application.utils.constant.ConstantContainer.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static test.utils.MockUtils.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TableService tableService;

    @InjectMocks
    TableController tableController;

    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
    }

    @Test
    void getTables() throws Exception {
        when(tableService.buildPageDTO(anyList(), anyInt())).thenReturn(createPageDTO1());

        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_TABLE, new TableDTO()))
                .andExpect(model().attribute(PAGE_LOADER, createPageDTO1()))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).buildPageDTO(anyList(), anyInt());
    }

    @Test
    void readTables() throws Exception {
        when(tableService.buildPageDTO(anyList(), anyInt())).thenReturn(createPageDTO1());

        mockMvc.perform(get("/read", anyString(), anyInt(), new Object()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(PAGE_LOADER, createPageDTO1()))
                .andExpect(model().attribute(LIST_TABLE, createPageDTO1().getList()))
                .andExpect(forwardedUrl(SEARCH));

        verify(tableService, times(1)).buildPageDTO(anyList(), anyInt());
    }

    @Test
    void saveTable() throws Exception {
        when(tableService.addTable(new TableDTO())).thenReturn(tableMapper.toDTO(createTable1()));
        mockMvc.perform(post("/addTable", tableMapper.toDTO(createTable1()), anyInt(), new Object()))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_MESSAGE, CREATE_SUCCESSFUL))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).addTable(new TableDTO());
    }

    @Test
    void saveTableWithNull() throws Exception {
        when(tableService.addTable(new TableDTO())).thenReturn(null);
        mockMvc.perform(post("/addTable", tableMapper.toDTO(createTable1()), anyInt(), new Object()))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_MESSAGE, CREATE_SUCCESSFUL))
                .andExpect(forwardedUrl(ERROR));

        verify(tableService, times(1)).addTable(new TableDTO());
    }

    @Test
    void updateTable() throws Exception {
        when(tableService.editTable(0L, new TableDTO())).thenReturn(tableMapper.toDTO(createTable1()));
        mockMvc.perform(post("/update", anyLong(), anyInt(), tableMapper.toDTO(createTable1()),
                new Object()))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_MESSAGE, UPDATE_SUCCESSFUL))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).editTable(0L, new TableDTO());
    }

    @Test
    void deleteTable() throws Exception {
        mockMvc.perform(post("/delete", 1L, anyInt(), new Object()))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_MESSAGE, DELETE_SUCCESSFUL))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).deleteTable(anyLong());
    }

}