package application.DTO;

import application.entity.Color;
import application.entity.Material;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {

    private Long id;

    @NotNull(message = "size should been not null")
    private Long size;

    @NotBlank(message = "please, add brand")
    @Size(min = 1, max = 50)
    private String brand;

    private Color color;

    private Material material;
}
