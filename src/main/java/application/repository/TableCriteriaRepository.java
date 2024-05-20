package application.repository;

import application.entity.Table;

import java.util.List;

public interface TableCriteriaRepository {

    List<Table> findAllWithFilters(int page, String search);
}
