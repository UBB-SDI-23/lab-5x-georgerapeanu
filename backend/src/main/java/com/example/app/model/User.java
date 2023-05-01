package com.example.app.model;

import jakarta.persistence.*;
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
    private String handle;

    @Getter
    @Setter
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Review> reviews;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="handle", unique = true)
    UserProfile userProfile;
}
