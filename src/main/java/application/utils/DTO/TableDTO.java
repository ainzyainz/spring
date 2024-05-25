package application.utils.DTO;

import application.entity.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class TableDTO {
    private Long id;
    private Long size;
    private String brand;
    private Color color;
    private Material material;
}
