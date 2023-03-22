package com.example.app.model;

import com.example.app.model.Ids.ReviewId;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Review {

    @EmbeddedId
    ReviewId id = new ReviewId();
    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    private Integer score;
    private String comment;
    private Date postedDate;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public ReviewId getId() {
        return id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }
}
