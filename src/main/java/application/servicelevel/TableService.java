package application.servicelevel;

import application.utils.DTO.TableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TableService {

    List<TableDTO> getTables(int page, int size);

    TableDTO addTable(TableDTO tableDTO);

    List<TableDTO> readTables(String search,int page);

    void deleteTable(Long id);

    List<TableDTO> readAll();

    TableDTO editTable(Long id, TableDTO tableDTO);
}
