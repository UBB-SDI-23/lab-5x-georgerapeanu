package com.example.app.model.Ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewId implements Serializable {
    @Column(name = "user_id")
    public Integer userId;
    @Column(name = "product_id")
    public Integer productId;

    public ReviewId() {
    }

    public ReviewId(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReviewId)) {
            return false;
        }
        return Objects.equals(this.userId, ((ReviewId) other).userId) & Objects.equals(this.productId, ((ReviewId) other).productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.productId);
    }
}
