package application.service;

import application.entity.Table;
import application.repository.TableRepository;
import application.DTO.PageDTO;
import application.DTO.TableDTO;
import application.converter.TableMapper;
import application.entity.Color;
import application.entity.Material;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static application.utils.constant.Constant.FIRST_PAGE;
import static application.utils.constant.Constant.ROW_IN_MAIN_PAGE;


@RequiredArgsConstructor
@Transactional
@Service
public class TableServiceImpl implements TableService {

    //TODO добавить проверки на нуллы и пустые строки lang3
    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    private final TableRepository tableRepository;

    public PageDTO getTables(String field, int page) {
        List<Table> list;
        if (field == null || field.isEmpty()){
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

//    public List<TableDTO> readTablesByFilter(String search, int page) {
//        tableRepository.findAllWithFilters(page, )
//    }


    // на критерии с пагинацией

    public PageDTO readTables(String search, int page) {
        List<Table> tables = new ArrayList<>();
        if (EnumUtils.isValidEnumIgnoreCase(Color.class, search)) {
            tables.addAll(tableRepository.findByColor(Color.valueOf(search.toUpperCase())));
        }
        if (EnumUtils.isValidEnumIgnoreCase(Material.class, search)) {
            tables.addAll(tableRepository.findByMaterial(Material.valueOf(search.toUpperCase())));
        }
        tables.addAll(tableRepository.findByBrand(search));

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

        return PageDTO.builder()
                .list(resultPage)
                .totalPages(totalPages)
                .pageNumbers(LongStream.rangeClosed(FIRST_PAGE, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .build();
    }
}
