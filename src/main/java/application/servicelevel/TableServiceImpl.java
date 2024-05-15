package application.servicelevel;

import application.entity.Table;
import application.repositorylevel.repository.TableRepository;
import application.utils.DTO.PageDTO;
import application.utils.DTO.TableDTO;
import application.utils.converter.TableMapper;
import application.utils.enums.Color;
import application.utils.enums.Material;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
        return tableDTO != null ? tableMapper.toDTO(tableRepository.save(tableMapper.toEntity(tableDTO))) : null;
    }

    public TableDTO editTable(Long id, TableDTO tableDTO) {
        if (tableDTO != null) {
            Optional<Table> oldTable = tableRepository.findById(id);
            if (oldTable.isPresent()) {
                Table table = tableMapper.toEntity(tableDTO);
                table.setId(id);
                return tableMapper.toDTO(tableRepository.save(table));
            }
        }
        return null;
    }

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
