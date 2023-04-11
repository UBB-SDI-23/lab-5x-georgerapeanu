package com.example.app.repository;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import com.example.app.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductRepositoryImpl implements IProductRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ProductScoreDTO> getProductsSortedByAverageScore(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Product> product = cq.from(Product.class);
        Join<Product, Review> productReviewJoin = product.join("reviews", JoinType.LEFT);
        cq
                .multiselect(product, cb.avg(cb.coalesce(productReviewJoin.get("score"), 0)))
                .groupBy(product)
                .orderBy(cb.desc(cb.avg(cb.coalesce(productReviewJoin.get("score"), 0))));

        TypedQuery<Object[]> typedQuery = em.createQuery(cq);

        List<ProductScoreDTO> results = typedQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(row -> {
                    return new ProductScoreDTO(
                            ProductDTO.fromProduct((Product) row[0]),
                            (Double)row[1]
                    );
                })
                .toList();

        CriteriaQuery<Long> count_cq = cb.createQuery(Long.class);
        count_cq.select(cb.count(count_cq.from(Product.class)));
        long total = em.createQuery(count_cq).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}
