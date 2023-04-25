package com.example.app.repository;


import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserDTO;
import com.example.app.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements IUserRepository{
    @PersistenceContext
    EntityManager em;
    public List<UserReviewCountDTO> getUserReviewCountFromList(List<UserDTO> users) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> user_review_count_cq = cb.createQuery(Tuple.class);
        Root<Review> review = user_review_count_cq.from(Review.class);
        CriteriaBuilder.In<Integer> inClause = cb.in(review.get("user").get("id"));
        for(UserDTO userDTO: users) {
            inClause.value(userDTO.getId());
        }
        user_review_count_cq
                .multiselect(review.get("user").get("id").alias("id"), cb.count(review.get("product").get("id")).alias("count"))
                .groupBy(review.get("user").get("id"))
                .where(inClause);

        TypedQuery<Tuple> typedQuery = em.createQuery(user_review_count_cq);


        return typedQuery
                .getResultStream()
                .map(row -> {
                    UserDTO userDTO = users.stream().filter(user -> user.getId().equals(row.get("id"))).findFirst().get();
                    return new UserReviewCountDTO(userDTO, ((Long)row.get("count")).intValue());
                }).collect(Collectors.toList());
    }
}
