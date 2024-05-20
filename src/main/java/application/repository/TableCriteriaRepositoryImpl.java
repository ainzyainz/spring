package application.repository;

import application.entity.Color;
import application.entity.Material;
import application.entity.Table;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static application.utils.Constant.*;

@Repository
public class TableCriteriaRepositoryImpl implements TableCriteriaRepository {

    private final EntityManager entityManager;

    @Autowired
    public TableCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Table> findAllWithFilters(int page, String search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Table> criteriaQuery = criteriaBuilder.createQuery(Table.class);
        Root<Table> tableRoot = criteriaQuery.from(Table.class);

        criteriaQuery.where(getPredicate(search, tableRoot, criteriaBuilder).toArray(Predicate[]::new));

        TypedQuery<Table> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * ROW_IN_MAIN_PAGE);
        typedQuery.setMaxResults(ROW_IN_MAIN_PAGE);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> getPredicate(String search, Root<Table> tableRoot, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        String pattern = String.format(LIKE_QUERY_PATTERN, search);

        if (!StringUtils.isBlank(search)) {

            if (StringUtils.isNumeric(search)) {
                predicates.add(criteriaBuilder.like(tableRoot.get(SIZE).as(String.class), pattern.toLowerCase()));
            }
            if (EnumUtils.isValidEnumIgnoreCase(Color.class, search)) {
                predicates.add(criteriaBuilder.equal(tableRoot.get(COLOR), Color.valueOf(search.toUpperCase())));
            }
            if (EnumUtils.isValidEnumIgnoreCase(Material.class, search)) {
                predicates.add(criteriaBuilder.equal(tableRoot.get(MATERIAL), Material.valueOf(search.toUpperCase())));
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(tableRoot.get(BRAND)), pattern.toLowerCase()));
        }
        return predicates;
    }
}
