package com.example.app.repository;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ManufacturerRepositoryImpl implements IManufacturerRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ManufacturerProductCountDTO> getManufacturersSortedByProductCount(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Manufacturer> manufacturer = cq.from(Manufacturer.class);
        Join<Manufacturer, Product> manufacturerProductJoin = manufacturer.join("products", JoinType.LEFT);
        cq
                .multiselect(manufacturer, cb.count(manufacturerProductJoin))
                .groupBy(manufacturer)
                .orderBy(cb.desc(cb.count(manufacturerProductJoin)));
        TypedQuery<Object[]> typedQuery = em.createQuery(cq);


        List<ManufacturerProductCountDTO> results = typedQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(row -> {
                    return new ManufacturerProductCountDTO(
                            ManufacturerDTO.fromManufacturer((Manufacturer) row[0]),
                            ((Long) row[1]).intValue()
                    );
                })
                .toList();

        CriteriaQuery<Long> count_cq = cb.createQuery(Long.class);
        count_cq.select(cb.count(count_cq.from(Manufacturer.class)));;
        long total = em.createQuery(count_cq).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}
