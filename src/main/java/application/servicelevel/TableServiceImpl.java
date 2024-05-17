package application.servicelevel;

import application.entity.Table;
import application.repositorylevel.repository.TableRepository;
import application.DTO.PageDTO;
import application.DTO.TableDTO;
import application.converter.TableMapper;
import application.entity.Color;
import application.entity.Material;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static application.utils.constant.ConstantContainer.FIRST_PAGE;
import static application.utils.constant.ConstantContainer.ROW_IN_MAIN_PAGE;


@RequiredArgsConstructor
@Transactional
@Service
public class TableServiceImpl implements TableService {

    //TODO добавить проверки на нуллы и пустые строки lang3
    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);

    private final TableRepository tableRepository;

    public PageDTO buildPageDTO(List<TableDTO> list, int page) {
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

    public List<TableDTO> getTables() {
        List<Table> list = tableRepository.findAll();
        return list.stream().map(tableMapper::toDTO).collect(Collectors.toList());
    }

    public TableDTO addTable(TableDTO tableDTO) {
        if (tableDTO.getSize() == null || tableDTO.getBrand() == null
                || tableDTO.getMaterial() == null || tableDTO.getColor() == null) {
            tableDTO = null;
        }
        return Optional.ofNullable(tableDTO)
                .map(tableMapper::toEntity)
                .map(tableRepository::save)
                .map(tableMapper::toDTO)
                .orElse(null);
    }

    //проверить с айди
    public TableDTO editTable(Long id, TableDTO tableDTO) {
        return Optional.ofNullable(tableDTO)
                .filter(tableDTO1 -> tableRepository.existsById(id))
                .map(tableMapper::toEntity)
                .map(table -> {
                    table.setId(id);
                    return table;
                })
                .map(tableRepository::save)
                .map(tableMapper::toDTO)
                .orElse(null);

    }

    // на критерии с пагинацией

    public List<TableDTO> readTables(String search, int page) {
        List<Table> tables = new ArrayList<>();
        if (EnumUtils.isValidEnumIgnoreCase(Color.class, search)) {
            tables.addAll(tableRepository.findByColor(Color.valueOf(search.toUpperCase())));
        }
        if (EnumUtils.isValidEnumIgnoreCase(Material.class, search)) {
            tables.addAll(tableRepository.findByMaterial(Material.valueOf(search.toUpperCase())));
        }
        tables.addAll(tableRepository.findByBrand(search));
        return tables.stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}
