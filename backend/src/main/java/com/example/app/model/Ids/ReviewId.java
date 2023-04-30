package com.example.app.model.Ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ReviewId implements Serializable {
    @Column(name = "user_handle")
    public String userHandle;
    @Column(name = "product_id")
    public Integer productId;

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(userHandle, reviewId.userHandle) && Objects.equals(productId, reviewId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userHandle, productId);
    }
}
