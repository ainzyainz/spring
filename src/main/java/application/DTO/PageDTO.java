package application.DTO;

import lombok.*;

import java.util.List;

@Data
@Builder
public class PageDTO {

    private int pageNumber;

    private long totalPages;

    private List<TableDTO> list;

    private List<Long> pageNumbers;
}
