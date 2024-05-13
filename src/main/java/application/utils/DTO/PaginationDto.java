package application.utils.DTO;

import application.repositorylevel.repository.TableRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationDto {
    private int page;
    private static final int size = 5;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TableDTO> tableDTOs = new ArrayList<>();
    private int countOfList;


    public void setCountOfList(TableRepository tableRepository){
        this.countOfList = tableRepository.findAll().size();
    }
}
