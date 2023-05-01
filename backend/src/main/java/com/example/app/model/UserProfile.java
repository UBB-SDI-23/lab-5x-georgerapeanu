package com.example.app.model;



import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="user_profile")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @NotBlank(message = "User name is mandatory")
    @Getter
    @Setter
    private String name;
    @Id
    @Getter
    @Setter
    @NotBlank(message = "User name is mandatory")
    private String handle;
    @Getter
    @Setter
    @NotBlank(message = "User name is mandatory")
    private String email;
    @Getter
    @Setter
    private Date birthday;
    @Getter
    @Setter
    private Date registeredAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="handle", unique = true)
    User user;

}
