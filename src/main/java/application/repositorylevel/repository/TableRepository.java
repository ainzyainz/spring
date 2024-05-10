package application.repositorylevel.repository;

import application.entity.Table;
import application.utils.enums.Color;
import application.utils.enums.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    List<Table> findByMaterial(Material material);
    List<Table> findByColor(Color color);
    List<Table> findByBrand(String brand);

}
