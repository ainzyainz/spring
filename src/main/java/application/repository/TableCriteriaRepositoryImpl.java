package application.repository;

import application.DTO.PageDTO;
import application.DTO.TableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static application.utils.constant.Constant.ROW_IN_MAIN_PAGE;

@Repository
@RequiredArgsConstructor
public class TableCriteriaRepositoryImpl implements TableCriteriaRepository {

    private final EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    public List<TableDTO> findAllWithFilters(int page, TableDTO tableDTO) {
        CriteriaQuery<TableDTO> criteriaQuery = criteriaBuilder.createQuery(TableDTO.class);
        Root<TableDTO> tableDTORoot = criteriaQuery.from(TableDTO.class);

        criteriaQuery.where(getPredicate(tableDTO, tableDTORoot).toArray(Predicate[]::new));

        TypedQuery<TableDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * ROW_IN_MAIN_PAGE);
        typedQuery.setMaxResults(ROW_IN_MAIN_PAGE);

//        Pageable pageable = getPegeable()
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> getPredicate(TableDTO tableDTO, Root<TableDTO> tableDTORoot) {

        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(tableDTO.getBrand())) {
            predicates.add(criteriaBuilder.like(tableDTORoot.get("brand"), "%" + tableDTO.getBrand() + "%"));
        }
        if(Objects.nonNull(tableDTO.getSize())) {
            predicates.add(criteriaBuilder.like(tableDTORoot.get("size"), "%" + tableDTO.getSize() + "%"));
        }
        if(Objects.nonNull(tableDTO.getColor())) {
            predicates.add(criteriaBuilder.like(tableDTORoot.get("color"), "%" + tableDTO.getColor() + "%"));
        }
        if(Objects.nonNull(tableDTO.getMaterial())) {
            predicates.add(criteriaBuilder.like(tableDTORoot.get("material"), "%" + tableDTO.getMaterial() + "%"));
        }
        return predicates;
        }
}
