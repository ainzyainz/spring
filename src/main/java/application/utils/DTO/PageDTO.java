package application.utils.DTO;

import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PageDTO {

    private long totalPages;

    private List<TableDTO> list;

    private List<Long> pageNumbers;
}
