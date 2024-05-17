package application.DTO;

import application.entity.Color;
import application.entity.Material;
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
