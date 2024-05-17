package application.entity;

import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@javax.persistence.Table(name = "my_table")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long size;

    @Column
    private String brand;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column
    @Enumerated(EnumType.STRING)
    private Material material;

}
