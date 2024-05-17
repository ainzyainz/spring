package application.repositorylevel.repository;

import application.entity.Table;
import application.entity.Color;
import application.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findByMaterial(Material material);

    List<Table> findByColor(Color color);

    List<Table> findByBrand(String brand);

    List<Table> findAll();
}
