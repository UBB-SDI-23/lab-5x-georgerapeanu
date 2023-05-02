package com.example.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class User {
    @Id
    @Getter
    @Setter
    @NotBlank
    private String handle;

    @Getter
    @Setter
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Review> reviews;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy="user")
    UserProfile userProfile;
}
