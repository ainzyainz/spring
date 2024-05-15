package application.servicelevel;

import application.utils.DTO.PageDTO;
import application.utils.DTO.TableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TableService {

    PageDTO buildPageDTO(List<TableDTO> list, int page);

    List<TableDTO> getTables();

    TableDTO addTable(TableDTO tableDTO);

    List<TableDTO> readTables(String search,int page);

    void deleteTable(Long id);

    TableDTO editTable(Long id, TableDTO tableDTO);
}
