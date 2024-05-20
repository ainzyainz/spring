package application.repository;

import application.DTO.TableDTO;

import java.util.List;

public interface TableCriteriaRepository {

    List<TableDTO> findAllWithFilters(int page, TableDTO tableDTO);
}
