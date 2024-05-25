package application.service;

import application.entity.Table;
import application.repository.TableRepository;
import application.DTO.*;
import application.converter.TableMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static application.utils.Constant.FIRST_PAGE;
import static application.utils.Constant.ROW_IN_MAIN_PAGE;

@Transactional
@Service
public class TableServiceImpl implements TableService {

    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    private final TableRepository tableRepository;

    @Autowired
    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    public PageDTO getTables(String field, int page) {
        List<Table> list;
        if (field == null || field.isEmpty()) {
            list = tableRepository.findAll();
        } else {
            list = tableRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        }
        return buildPageDTO(list.stream().map(tableMapper::toDTO).collect(Collectors.toList()), page);
    }

    public TableDTO addTable(TableDTO tableDTO) {

        return Optional.ofNullable(tableDTO)
                .map(tableMapper::toEntity)
                .map(tableRepository::save)
                .map(tableMapper::toDTO)
                .orElse(null);
    }

    public TableDTO editTable(TableDTO tableDTO) {
        return Optional.ofNullable(tableDTO)
                .map(tableMapper::toEntity)
                .map(tableRepository::save)
                .map(tableMapper::toDTO)
                .orElse(null);

    }

    public PageDTO readTables(String search, int page) {
        List<Table> tables = tableRepository.findAllWithFilters(page, search);

        return buildPageDTO(tables.stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList()), page);
    }

    public TableDTO findById(Long id) {
        return tableMapper.toDTO(tableRepository.findById(id).orElse(new Table()));
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }


    private PageDTO buildPageDTO(List<TableDTO> list, int page) {
        long totalPages = list.size() / ROW_IN_MAIN_PAGE;

        PagedListHolder pagedListHolder = new PagedListHolder(list);
        pagedListHolder.setPage(page);
        pagedListHolder.setPageSize(ROW_IN_MAIN_PAGE);
        List<TableDTO> resultPage = pagedListHolder.getPageList();

        return PageDTO.builder().
                list(resultPage)
                .totalPages(totalPages)
                .pageNumbers(LongStream.rangeClosed(FIRST_PAGE, totalPages)
                        .boxed()
                        .collect(Collectors.toList())).build();

    }
}
