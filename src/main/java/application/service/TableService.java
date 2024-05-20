package application.service;

import application.DTO.PageDTO;
import application.DTO.TableDTO;
import org.springframework.stereotype.Service;

@Service
public interface TableService {

    PageDTO getTables(String field, int page);

    TableDTO addTable(TableDTO tableDTO);

    PageDTO readTables(String search, int page);

    void deleteTable(Long id);

    TableDTO editTable(TableDTO tableDTO);

    TableDTO findById(Long id);
}
