package application.utils.DTO;

import application.utils.enums.Color;
import application.utils.enums.Material;
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
