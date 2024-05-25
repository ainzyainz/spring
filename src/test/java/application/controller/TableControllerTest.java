package application.controller;

import application.service.TableService;
import application.DTO.TableDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static application.utils.Constant.*;
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

    @MockBean
    private TableService tableService;

    @MockBean
    BindingResult bindingResult;

    @MockBean
    Model model;

    @Autowired
    private TableController tableController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
    }

    @Test
    void getTables() throws Exception {
        when(tableService.getTables(null, 0)).thenReturn(createPageDTO1());

        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_TABLE, new TableDTO()))
                .andExpect(model().attribute(PAGE_LOADER, createPageDTO1()))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).getTables(null, 0);
    }

    @Test
    void readTables() throws Exception {
        when(tableService.readTables(anyString(), anyInt())).thenReturn(createPageDTO1());

        mockMvc.perform(get("/read", anyString(), anyInt(), model))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(PAGE_LOADER, createPageDTO1()))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).readTables("null", 0);
    }

    @Test
    void saveTable() throws Exception {

        mockMvc.perform(post("/addTable"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(INDEX));

    }

    @Test
    void updateTable() throws Exception {

        when(tableService.editTable(createTable1())).thenReturn((createTable1()));
        model.addAttribute(UPDATE_TABLE, createTable1());
        mockMvc.perform(post("/update"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(EDIT));

    }

    @Test
    void deleteTable() throws Exception {
        mockMvc.perform(post("/delete", 1L, anyInt(), model))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ADD_MESSAGE, DELETE_SUCCESSFUL))
                .andExpect(forwardedUrl(INDEX));

        verify(tableService, times(1)).deleteTable(anyLong());
    }

    @Test
    void sortTest() throws Exception {
        mockMvc.perform(get("/sort?field=brand&search=red"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(FIELD, "brand"))
                .andExpect(model().attribute(SEARCH, "red"))
                .andExpect(forwardedUrl(INDEX));
    }

    @Test
    void displayEditTableTest() throws Exception {
        when(tableService.findById(anyLong())).thenReturn(createTable1());

        mockMvc.perform(get("/displayEdit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(TABLE_TO_UPDATE, createTable1()))
                .andExpect(forwardedUrl(EDIT));

        verify(tableService, times(1)).findById(anyLong());
    }

    @Test
    void displayAddTableTest() throws Exception {

        mockMvc.perform(get("/displayAdd"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(ADD_TABLE, new TableDTO()))
                .andExpect(forwardedUrl(ADD));

    }


}