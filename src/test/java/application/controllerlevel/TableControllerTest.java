package application.controllerlevel;

import application.servicelevel.TableService;
import application.utils.DTO.TableDTO;
import application.utils.converter.TableMapper;
import application.utils.enums.Color;
import application.utils.enums.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.defaultanswers.ReturnsMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static test.utils.MockConstant.BRAND1;
import static test.utils.MockConstant.SIZE1;
import static test.utils.MockUtils.createTable1;
import static test.utils.MockUtils.createTables;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableService tableService;

    @InjectMocks
    TableController tableController;

    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
        tableService.addTable(tableMapper.toDTO(createTable1()));
    }

    @Test
    void getTables() throws Exception {
        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));

        verify(tableService, times(1)).getTables(0, 2);
    }

    @Test
    void getTable() throws Exception {

        when(tableService.readAll()).thenReturn(createTables());
        mockMvc.perform(get("/mainPage"))
                .andExpect(status().isOk());
        verify(tableService, times(1)).readAll();
    }

    @Test
    void readTables() {
    }

    @Test
    void saveTable() throws Exception {
//        when(tableService.addTable((TableDTO) new Object())).thenReturn(tableMapper.toDTO(createTable1()));
        mockMvc.perform(post("/create", tableMapper.toDTO(createTable1())))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.brand").value(BRAND1))
        .andExpect(jsonPath("$.size").value(SIZE1))
        .andExpect(jsonPath("$.color").value(Color.BLACK))
        .andExpect(jsonPath("$.material").value(Material.PLASTIC));
    }

    @Test
    void updateTable() {
    }

    @Test
    void deleteTable() {
    }

    private void checkPerform(Map<String, Object> params, String url) throws Exception {
        mockMvc.perform(get(getUri(params, url))
                .headers(getHttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    private org.springframework.http.HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    private URI getUri(Map<String, Object> params, String url) {
        return new UriTemplate(url).expand(params);
    }

}